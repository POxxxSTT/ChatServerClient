import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Comynication {
    private static Comynication instance = new Comynication();
    private ArrayList<UserConnect> users;
    private ConcurrentLinkedQueue<SocketChannel> incomingConnections;

    private Comynication() {
        users = new ArrayList<>();
        incomingConnections = new ConcurrentLinkedQueue<>();
    }

    public static Comynication getInstance(){
        return instance;
    }

    public synchronized void addUsers(UserConnect u) {
        users.add(u);
    }

    public ArrayList<UserConnect> getUsers() {
        return users;
    }

    public void addSocket(SocketChannel s) {
        incomingConnections.add(s);
    }

    public SocketChannel getSocket() {
        return incomingConnections.poll();
    }

    public void removeUser(UserConnect user) {
        users.remove(user);
    }

    public boolean checkLogin(String login){

        for (int i = 0; i < users.size(); i++) {
            if(login.equals(users.get(i).getLogin())){
                System.out.println("return false");
                return false;
            }
        }
        System.out.println("return true");
        return true;
    }


}
