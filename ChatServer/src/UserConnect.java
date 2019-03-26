import java.util.concurrent.ConcurrentLinkedQueue;

public class UserConnect {
    private String login;
    private ConcurrentLinkedQueue<String> list = new ConcurrentLinkedQueue<String>();
    private MyState myState;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public ConcurrentLinkedQueue<String> getList() {
        return list;
    }

    public void setList(String carList) {
        this.list.add(String.valueOf(carList));
    }

    public MyState getMyState() {
        return myState;
    }

    public void setMyState(MyState myState) {
        this.myState = myState;
    }
}
