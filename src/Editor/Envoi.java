package Editor;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Envoi {

    //private final static String QUEUE_NAME = "hello";

    public static void envoyer(String msg,String queueName) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(queueName,false,false,false,null);

        channel.basicPublish("",queueName,null,msg.getBytes());
        System.out.println("[x] sent '"+msg+"'");
    }

}
