package Editor;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.awt.*;

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
            System.out.println(" [x] recieved ' "+message+"'");
            Reception.msg=message;
            t.setText(msg);

        };
        channel.basicConsume(queueName,true, deliverCallBack, consumerTag->{});
    }
}
