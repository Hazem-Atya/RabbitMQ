package Editor;

import com.rabbitmq.client.*;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Reception {
    static String msg="";

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
            HashMap<String,Object> ranges = (HashMap)delivery.getProperties().getHeaders();
            System.out.println(" [x] recieved ' "+message+"'");
            Reception.msg=message;
            int startRange = (int)ranges.get("startRange");
            int endRange = (int)ranges.get("endRange");
            System.out.println(startRange+" "+endRange);
            t.replaceRange(message,startRange,endRange);

        };
        channel.basicConsume(queueName,true, deliverCallBack, consumerTag->{});
    }
}
