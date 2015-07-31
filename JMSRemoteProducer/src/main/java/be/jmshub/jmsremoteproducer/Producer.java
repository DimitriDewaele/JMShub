package be.jmshub.jmsremoteproducer;

import javax.jms.JMSException;
import com.sun.messaging.ConnectionConfiguration;
import com.sun.messaging.ConnectionFactory;
import com.sun.messaging.Queue;
import javax.jms.Connection;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.Message;

public class Producer {

    private static ConnectionFactory connectionFactory;
    private static Queue queue;

    public static void main(String[] args) throws JMSException {
        System.out.println("Communication to GLASSFISH OPEN MQ - remote (localhost) connection");
        System.out.println("JMS REMOTE PRODUCER: start");

        queue = new Queue("MyQueue");

        connectionFactory = new ConnectionFactory();
        connectionFactory.setProperty(ConnectionConfiguration.imqAddressList, "localhost:7676");

        try {
            Connection connection = connectionFactory.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer producer = session.createProducer(queue);

            System.out.println("JMS REMOTE PRODUCER: connected");

            int count = 0;

            for (int i = 0; i < 5; i++) {
                String messageText = "This is message " + (i + 1);
                Message message = session.createTextMessage(messageText);
                System.out.println("JMS REMOTE PRODUCER: sending - " + messageText);
                producer.send(message);
                count += 1;
            }
            System.out.println("JMS REMOTE PRODUCER: total messages sent: " + count);

            // Clean up
            session.close();
            connection.close();
        } catch (Exception ex) {
            System.out.println("JMS REMOTE PRODUCER: EXCEPTION");
        }
        System.out.println("JMS REMOTE PRODUCER: finished");
    }
}
