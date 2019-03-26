import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    ExecutorService service = Executors.newFixedThreadPool(6);

    public static void main(String[] args) {
        new Main().runServer();
    }

    public void runServer(){
        try {
            Selector selector = Selector.open();
            ServerSocketChannel ssc =  ServerSocketChannel.open();
            ssc.configureBlocking(false);
            ServerSocket ss = ssc.socket();
            ss.bind(new InetSocketAddress(9877));
            ssc.register(selector, SelectionKey.OP_ACCEPT);

            for (int i = 0; i < 6; i++) {
                service.submit(new ClientHandler());
            }

            while(true) {
                // System.out.println("Ожидание соединения с клиентом");

                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> it = keys.iterator();

                if(selector.select() > 0) {
                    System.out.println("Прошол select()");
                    Comynication.getInstance().addSocket(ssc.accept());
                }
                while (it.hasNext()) {
//                    SelectionKey key = it.next();
//                    System.out.println("Selected");
                    it.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
