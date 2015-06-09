package be.jmshub.jmsremotejndiproducer;

import javax.jms.JMSException;
import com.sun.messaging.ConnectionConfiguration;
import com.sun.messaging.ConnectionFactory;
import com.sun.messaging.Queue;
import java.util.Properties;
import javax.jms.Connection;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.Message;
import javax.naming.Context;
import javax.naming.InitialContext;

public class Producer {

    public static void main(String[] args) throws JMSException {
        System.out.println("JMS REMOTE PRODUCER: start");
//        Queue queue = new Queue("MyQueue");
        Queue queue = new Queue("testQueue");
        try {

            Properties jndiParamaters = new Properties();
            jndiParamaters.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
            jndiParamaters.put(Context.PROVIDER_URL, "tcp://localhost:61616");
            Context ctx = new InitialContext(jndiParamaters);

            //FOR ACTIVEMQ
            //FOR GLASSFISH
//            Properties env = new Properties();
//            env.setProperty(Context.INITIAL_CONTEXT_FACTORY, "com.sun.enterprise.naming.SerialInitContextFactory");
//            env.setProperty(Context.URL_PKG_PREFIXES, "com.sun.enterprise.naming");
//            env.setProperty(Context.STATE_FACTORIES, "com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl");
////            env.setProperty(Context.PROVIDER_URL, "iiop://localhost:7676");
//            env.put("org.omg.CORBA.ORBInitialHost", "localhost");
//            env.put("org.omg.CORBA.ORBInitialPort", "7676");
            //TODO: should connect to remote accessable queue like: jms/queue/test
//            //FOR JBOSS
//            Properties env = new Properties();
//            env.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
//            env.setProperty(Context.PROVIDER_URL, "remote://localhost:4447");
            
            //InitialContext initialContext = new InitialContext(env);
            InitialContext initialContext = new InitialContext(jndiParamaters);
            ConnectionFactory connectionFactory = null;
            try {
//                connectionFactory = (ConnectionFactory) initialContext.lookup("jms/DefaultJMSConnectionFactory");
                connectionFactory = (ConnectionFactory) initialContext.lookup("jms/connectionFactory");

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

            } catch (Exception e) {
                System.out.println("JNDI API lookup failed: " + e.toString());
                e.printStackTrace();
                System.exit(1);
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }

        System.out.println("JMS REMOTE PRODUCER: finished");
    }
}
