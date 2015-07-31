package be.jmshub.jmsactivemqproducer;

import javax.jms.Message;
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Producer {

    public static void main(String[] args) throws JMSException {
        System.out.println("Communication to ACTIVE MQ");
        System.out.println("JMS REMOTE PRODUCER: start");

        try {
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
            
            Connection connection = connectionFactory.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Queue queue = session.createQueue("testQueue");
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
