import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

import static java.nio.channels.SelectionKey.OP_READ;
import static java.nio.channels.SelectionKey.OP_WRITE;

public class ClientHandler implements Runnable{
    private Selector selector;

    public ClientHandler() {
        try {
            selector = Selector.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true){
            try{
                SocketChannel socketChannel = Comynication.getInstance().getSocket();
                if (socketChannel != null){
                    socketChannel.configureBlocking(false);
                    SelectionKey selectionKey = socketChannel.register(selector, OP_READ | OP_WRITE);

                    UserConnect user = new UserConnect();
                    Comynication.getInstance().addUsers(user);
                    user.setMyState(new LoginState());
                    selectionKey.attach(user);
                }
                if(selector.selectNow() > 0) {
                    Iterator<SelectionKey> key = selector.selectedKeys().iterator();
                    while(key.hasNext()) {
                        try {
                            SelectionKey selectionKey = key.next();
                            UserConnect user = (UserConnect) selectionKey.attachment();
                            SocketChannel socket = (SocketChannel) selectionKey.channel();

                            if (selectionKey.isReadable()) {
                                System.out.println("Слушаем клиента (Read)");
                                user.getMyState().myRead(socket, user, selectionKey);
                            }else if (selectionKey.isWritable()) {
                                // System.out.println("посылаем клиенту (Write)");
                                user.getMyState().myWrite(socket, user, selectionKey);
                            }
                        }catch (IOException e) {
                            e.printStackTrace();
                        }
                        key.remove();
                    }
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
