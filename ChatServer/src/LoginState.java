import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

public class LoginState implements MyState {

    private ByteBuffer byteBuffer = ByteBuffer.allocate(2024);

    @Override
    public void myWrite(SocketChannel socketChannel, UserConnect userConnect, SelectionKey selectionKey) throws IOException {
        //System.out.println("посылаем клиенту (Write)LoginState");

        if(byteBuffer.position() > 0){
            byteBuffer.clear();
            byteBuffer.put("Такой логин уже занят.".getBytes());
            byteBuffer.flip();
            socketChannel.write(byteBuffer);
        }
    }

    @Override
    public void myRead(SocketChannel socketChannel, UserConnect userConnect, SelectionKey selectionKey) throws IOException {
        System.out.println("Слушаем клиента (Read) LoginState");
//        while(byteBuffer.position() < 2){
//            socketChannel.read(byteBuffer);
//        }
//
//        byteBuffer.flip();
//        short size = byteBuffer.getShort();
//        byteBuffer.compact();
//
//        while (byteBuffer.position() < size){
//            socketChannel.read(byteBuffer);
//        }
//
//        String message = Charset.forName("UTF-8").decode(byteBuffer).toString();
//
//        System.out.println(message);

        byteBuffer.clear();
        socketChannel.read(byteBuffer);
        byteBuffer.flip();

        String message = Charset.forName("UTF-8").decode(byteBuffer).toString();

        System.out.println("Login = " + message);

        if(Comynication.getInstance().checkLogin(message)){
            userConnect.setLogin(message);
            userConnect.setMyState(new ChatState());
        }else {
            userConnect.getMyState().myWrite(socketChannel, userConnect, selectionKey);
        }
    }
}
