package Editor;


import java.util.ArrayList;

public class SendMain {


    static ArrayList <SendWindow> sendWindows = new ArrayList<SendWindow>();
    public static void main(String[] args) {

        int n = 6;
        for (int i =1 ;i<=n;i++)
        {
            String queueName="file"+i;
            sendWindows.add(i-1,new SendWindow(queueName));
            sendWindows.get(i-1).afficher(queueName);
        }
    //    ReceiveWindow ri = new ReceiveWindow(queues);
    }
}