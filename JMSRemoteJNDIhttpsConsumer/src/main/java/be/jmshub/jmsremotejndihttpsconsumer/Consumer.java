package be.jmshub.jmsremotejndihttpsconsumer;

import javax.jms.JMSException;
import javax.jms.Connection;
import javax.jms.Session;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.MessageConsumer;
import javax.jms.QueueConnectionFactory;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;

public class Consumer {

    public static void main(String[] args) throws Exception {
        System.out.println("Communication to ACTIVE MQ with JNDI lookup over HTTPS");
        System.out.println("JMS REMOTE CONSUMER: start");

        try {
            // create a new intial context, which loads from jndi.properties file
            Context ctx = new InitialContext();
            QueueConnectionFactory factory = (QueueConnectionFactory) ctx.lookup("connectionFactory");
            Connection connection = factory.createConnection();
            Queue queue = (javax.jms.Queue) ctx.lookup("testQueue");
            Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
            
            MessageConsumer consumer = session.createConsumer(queue);

            System.out.println("JMS REMOTE CONSUMER: connected");

            int count = 0;
            
            // Start connection or nothing will happen!!!
            connection.start();

            while (true) {
                //Message m = consumer.receive(1000);
                Message m = consumer.receiveNoWait();

                if (m == null) {
                    System.out.println("JMS REMOTE CONSUMER: no message");
                } else {
                    System.out.println("JMS REMOTE CONSUMER: message not empty");
                    if (m instanceof TextMessage) {
                        TextMessage t = (TextMessage) m;
                        System.out.println("JMS REMOTE CONSUMER: message text: " + t.getText());
                    } else {
                        System.out.println("JMS REMOTE CONSUMER: other message");
                    }
                    count += 1;
                }
                System.out.println("JMS REMOTE CONSUMER: total received: " + count);
            }
            
            //Code not reached
            //session.close();
            //connection.close();
        } catch (Exception ex) {
            System.out.println("JMS REMOTE CONSUMER: EXCEPTION - " + ex.toString());
            throw ex;
        }
    }
}
