package be.jmshub.jmsproducer;

import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSRuntimeException;
import javax.jms.Queue;

public class Producer {

    @Resource(lookup = "java:comp/DefaultJMSConnectionFactory")
    private static ConnectionFactory connectionFactory;
    @Resource(lookup = "jms/MyQueue")
    private static Queue queue;
    
    public static void main(String[] args) {
        System.out.println("Communication to GLASSFISH OPEN MQ");
        System.out.println("JMS PRODUCER: start");

        try (JMSContext context = connectionFactory.createContext();) {
            int count = 0;
            for (int i = 0; i < 10; i++) {
                String message = "This is message " + (i + 1); 
                System.out.println("JMS PRODUCER: sending message = " + message);
                context.createProducer().send(queue, message);
                count += 1;
            }
            System.out.println("JMS PRODUCER: total messages sent = " + count);
        } catch (JMSRuntimeException e) {
            System.out.println("JMS PRODUCER: EXCEPTION");
            System.exit(1);
        }
        
        System.out.println("JMS PRODUCER: finished");
        System.exit(0);
    }
}
