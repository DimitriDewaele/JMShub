package be.jmshub.jmsconsumer;

import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.TextMessage;

public class Consumer {

    @Resource(lookup = "java:comp/DefaultJMSConnectionFactory")
    private static ConnectionFactory connectionFactory;
    @Resource(lookup = "jms/MyQueue")
    private static Queue queue;

    public static void main(String[] args) {
        System.out.println("JMS CONSUMER: start");
        
        JMSConsumer consumer;
        
        try (JMSContext context = connectionFactory.createContext();) {
            System.out.println("JMS CONSUMER: connected");
            consumer = context.createConsumer(queue);
            int count = 0;
            
            while (true) {
                System.out.println("JMS CONSUMER: try message");
                Message m = consumer.receive(1000);

                if (m != null) {
                    if (m instanceof TextMessage) {
                        System.out.println("JMS CONSUMER: test message = " + m.getBody(String.class));
                        
                    } else {
                        System.out.println("JMS CONSUMER: other message");
                    }
                    count += 1;
                }
                System.out.println("JMS CONSUMER: total messages = " + count);
            }
        } catch (JMSException e) {
            System.out.println("JMS CONSUMER: ECEPTION");
            System.exit(1);
        }
        
        System.out.println("JMS CONSUMER: finished");
        System.exit(0);
    }
}
