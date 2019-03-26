import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

public interface MyState {
    public void myWrite(SocketChannel socketChannel, UserConnect userConnect, SelectionKey selectionKey) throws IOException;
    public void myRead(SocketChannel socketChannel, UserConnect userConnect, SelectionKey selectionKey) throws IOException;

}
