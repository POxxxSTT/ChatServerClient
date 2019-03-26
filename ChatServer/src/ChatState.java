import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

import static java.nio.channels.SelectionKey.OP_READ;

public class ChatState implements MyState{

    private ByteBuffer byteBuffer = ByteBuffer.allocate(2024);
    private String message;

    @Override
    public void myWrite(SocketChannel socketChannel, UserConnect userConnect, SelectionKey selectionKey) throws IOException {
        // System.out.println("посылаем клиенту (Write)ChatState");

        if(message != null){
            for (int i = 0; i < Comynication.getInstance().getUsers().size(); i++) {
                String str = Comynication.getInstance().getUsers().get(i).getList().poll();  // poll peek ???
                byteBuffer.clear();
                byteBuffer.put(str.getBytes());
                byteBuffer.flip();
                socketChannel.write(byteBuffer);
                System.out.println("ChatState myWrite отправил " + str);
                message = null;
                selectionKey.interestOps(OP_READ);
            }
        }
    }

    @Override
    public void myRead(SocketChannel socketChannel, UserConnect userConnect, SelectionKey selectionKey) throws IOException {
        System.out.println("Слушаем клиента (Read)ChatState");

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

        byteBuffer.clear();
        socketChannel.read(byteBuffer);
        byteBuffer.flip();

        message = Charset.forName("UTF-8").decode(byteBuffer).toString();

        System.out.println(" ChatState myRead получил " + message);

        for (int i = 0; i < Comynication.getInstance().getUsers().size(); i++) {
            Comynication.getInstance().getUsers().get(i).setList(message);
        }
        userConnect.getMyState().myWrite(socketChannel, userConnect, selectionKey);
    }
}

