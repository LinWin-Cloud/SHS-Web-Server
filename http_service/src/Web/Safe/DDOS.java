package Web.Safe;

public class DDOS {
    private int AllowRequests = 2^31-1;
    private final Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            
        }
    });
    public void setAllowRequests(int AllowRequests)
    {
        this.AllowRequests = AllowRequests;
    }
    public void DDOS_Safe_Run() {
        this.thread.start();
    }
}
