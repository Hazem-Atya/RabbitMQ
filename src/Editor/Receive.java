package Editor;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.awt.*;
import java.util.HashMap;

public class Receive {
    private static final String EXCHANGE_NAME = "logs";

    public static void recevoir(TextArea t, String queueName) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
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
            if(!queueName.equals(senderQueue))
            {
                return ;
            }
            System.out.println("Ranges: "+startRange+" "+endRange+"     type: "+type);
            String msg=message;
            System.out.println("message: "+message+"        MESSAGE LENGTH: "+message.length());
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
