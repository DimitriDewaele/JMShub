package be.jmshub.jmsremotejndiproducer;

import javax.jms.JMSException;
import javax.jms.Connection;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueConnectionFactory;
import javax.naming.Context;
import javax.naming.InitialContext;

public class Producer {

    public static void main(String[] args) throws JMSException {
        System.out.println("Communication to ACTIVE MQ with JNDI lookup");
        System.out.println("JMS REMOTE PRODUCER: start");

        try {
            // create a new intial context, which loads from jndi.properties file
            Context ctx = new InitialContext();
            QueueConnectionFactory factory = (QueueConnectionFactory) ctx.lookup("connectionFactory");
            Connection connection = factory.createConnection();
            Queue queue = (javax.jms.Queue) ctx.lookup("testQueue");
            Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);

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
            System.out.println("JMS REMOTE PRODUCER: EXCEPTION:" + ex.toString());
        }
        System.out.println("JMS REMOTE PRODUCER: finished");
    }
}
