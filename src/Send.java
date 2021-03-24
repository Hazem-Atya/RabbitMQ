

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.HashMap;

public class Send {

    private static final String EXCHANGE_NAME = "logs";

    public static void envoyer(String msg,String queueName,int startRange,int endRange,String type,String dest) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
            HashMap<String,Object> ranges = new HashMap<String,Object>();
            ranges.put("startRange",startRange);
            ranges.put("endRange",endRange);
            ranges.put("type",type);
            ranges.put("queue",queueName);
            ranges.put("dest",dest);
            channel.basicPublish(EXCHANGE_NAME, "", new AMQP.BasicProperties.Builder().headers(ranges).build(), msg.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + msg + "'");
        }
    }
}
