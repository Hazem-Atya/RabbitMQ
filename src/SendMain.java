import java.util.ArrayList;

public class SendMain {


    static ArrayList <Window> sendWindows = new ArrayList<Window>();
    public static void main(String[] args) {

        int n = 4;
        for (int i =1 ;i<=n;i++)
        {
            String queueName="file"+i;
            sendWindows.add(i-1,new Window(queueName));
            sendWindows.get(i-1).afficher(queueName);
        }
    //    ReceiveWindow ri = new ReceiveWindow(queues);
    }
}