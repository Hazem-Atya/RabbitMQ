package Editor;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.awt.*;

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

            String msg=message;
            System.out.println("MESSAGE LENGTH"+message.length());
            if(message.charAt(message.length()-1)=='i')
            {
                String s1 = message.substring(message.lastIndexOf("/")+1,message.length()-1);
                int deb =Integer.parseInt(s1);
                String c = message.substring(0,message.lastIndexOf("/"));
                System.out.println("message: "+c);
                System.out.println("index: "+deb);
                t.insert(c,deb);
            }
            if(message.charAt(message.length()-1)=='d')
            {
                String s1 = message.substring(0,message.lastIndexOf("/"));
                String s2 = message.substring(message.lastIndexOf("/")+1,message.length()-1);
                int deb = Integer.parseInt(s1);
                int fin = Integer.parseInt(s2);
                t.replaceRange("",deb,fin);
            }



        };
        channel.basicConsume(queueName,true, deliverCallBack, consumerTag->{});
    }
}
