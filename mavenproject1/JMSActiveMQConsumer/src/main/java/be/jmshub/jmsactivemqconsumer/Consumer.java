package be.jmshub.jmsremoteconsumer;

import javax.jms.JMSException;
import com.sun.messaging.ConnectionConfiguration;
import com.sun.messaging.ConnectionFactory;
import com.sun.messaging.Queue;
import com.sun.messaging.Topic;
import javax.jms.Connection;
import javax.jms.Session;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.TextMessage;

public class Consumer {

    private static ConnectionFactory connectionFactory;
    private static Queue queue;

    public static void main(String[] args) throws JMSException {
        System.out.println("JMS REMOTE CONSUMER: start");

        queue = new Queue("MyQueue");

        connectionFactory = new ConnectionFactory();
        connectionFactory.setProperty(ConnectionConfiguration.imqAddressList, "localhost:7676");

        try {
            Connection connection = connectionFactory.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageConsumer consumer = session.createConsumer(queue);

            System.out.println("JMS REMOTE CONSUMER: connected");

            int count = 0;

            while (true) {
                Message m = consumer.receive(1000);

                if (m == null) {
                    System.out.println("JMS REMOTE CONSUMER: no message");
                } else {
                    System.out.println("JMS REMOTE CONSUMER: message not empty");
                    if (m instanceof TextMessage) {
                        System.out.println("JMS REMOTE CONSUMER: message text: " + m.getBody(String.class));
                    } else {
                        System.out.println("JMS REMOTE CONSUMER: other message");
                    }
                    count += 1;
                }
                System.out.println("JMS REMOTE CONSUMER: total received: " + count);
            }
        } catch (Exception ex) {
            System.out.println("JMS REMOTE PRODUCER: EXCEPTION");
        }

        System.out.println("JMS REMOTE CONSUMER: finished");
    }
}
