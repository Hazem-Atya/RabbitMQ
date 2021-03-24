

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import javax.swing.*;

import java.util.HashMap;

public class Receive {
    private static final String EXCHANGE_NAME = "logs";

    public static void recevoir(JTextArea t, String queueName, Window w) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        channel.queueDeclare(queueName,false,false,false,null);
        channel.queueBind(queueName, EXCHANGE_NAME, "");

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String (delivery.getBody(),"UTF-8");
            System.out.println(" [x] Received '" + message + "'");
            HashMap<String,Object> ranges = (HashMap)delivery.getProperties().getHeaders();
            int startRange = Integer.valueOf(ranges.get("startRange").toString());
            int endRange = Integer.valueOf(ranges.get("endRange").toString());
            String type = ranges.get("type").toString();
            String senderQueue = ranges.get("queue").toString();
            String dest = ranges.get("dest").toString();
            System.out.println("sender Queue: "+senderQueue+" to "+dest);
            System.out.println("queue name : "+queueName+" and dest: "+dest);
            if(!queueName.equals(dest))
            {
                System.out.println("not for me");
                return;
            }
            String msg=message;
            if(type.equals("i")) {
                System.out.println("message: " + message);
                    t.insert(message, startRange);


            } else {
                System.out.println("delete");
                t.replaceRange("",startRange,endRange);

            }
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });
    }
}
