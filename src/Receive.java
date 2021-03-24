

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import javax.swing.*;

import java.util.HashMap;

public class Receive {
    private static final String EXCHANGE_NAME = "logs";

    public static void recevoir(JTextArea t, String queueName, Window w) throws Exception {

      //  w.jt.getDocument().removeDocumentListener(w);
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
            System.out.println("sender Queue: "+senderQueue);
            if(queueName.equals(senderQueue))
            {
                return;
            }
            System.out.println("Ranges: "+startRange+" "+endRange+"     type: "+type);
            String msg=message;
            System.out.println("message: "+message+"        MESSAGE LENGTH: "+message.length());
            if(type.equals("i")) {
                System.out.println("message: " + message);
                synchronized (w)     {
                    t.getDocument().removeDocumentListener(w);
                    t.insert(message, startRange);
                    t.getDocument().addDocumentListener(w);
                }

            } else {
                System.out.println("delete");
                t.getDocument().removeDocumentListener(w);
                t.replaceRange("",startRange,endRange);
                t.getDocument().addDocumentListener(w);

            }
           // w.jt.getDocument().addDocumentListener(w);
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });
    }
}
