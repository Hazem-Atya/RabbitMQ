package Editor;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.HashMap;

public class Envoi {

    //private final static String QUEUE_NAME = "hello";

    public static void envoyer(String msg,String queueName,int startRange,int endRange,String type) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(queueName,false,false,false,null);

        HashMap<String,Object> ranges = new HashMap<String,Object>();
        ranges.put("startRange",startRange);
        ranges.put("endRange",endRange);
        ranges.put("type",type);
        channel.basicPublish("",queueName,new AMQP.BasicProperties.Builder().headers(ranges).build(),msg.getBytes());
        System.out.println("[x] sent '"+msg+"'");
    }

}
