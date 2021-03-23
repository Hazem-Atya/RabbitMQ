package Editor;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.awt.*;
import java.util.HashMap;

public class Reception {

    //private final static String QUEUE_NAME = "hello";
    public static void recevoir(TextArea t, String queueName) throws Exception
    {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(queueName,false,false,false,null);
        System.out.println("[*] waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallBack = (consumerTag,delivery)->{
            String message = new String (delivery.getBody(),"UTF-8");
            System.out.println(" [x] recieved '"+message+"'");
            HashMap<String,Object> ranges = (HashMap)delivery.getProperties().getHeaders();
            int startRange = Integer.valueOf(ranges.get("startRange").toString());
            int endRange = Integer.valueOf(ranges.get("endRange").toString());
            String type = ranges.get("type").toString();
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
        channel.basicConsume(queueName,true, deliverCallBack, consumerTag->{});
    }
}