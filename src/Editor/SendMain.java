package Editor;


import java.util.ArrayList;

public class SendMain {


    static   ArrayList<String> queues = new ArrayList<String>();
    static ArrayList <SendWindow> sendWindows = new ArrayList<SendWindow>();
    public static void main(String[] args) {

        int n = 4;
        for (int i =1 ;i<=n;i++)
        {
            String queueName="file"+i;
            queues.add(queueName);
            sendWindows.add(i-1,new SendWindow(queueName));
            sendWindows.get(i-1).afficher(queueName);

        }

    }
}