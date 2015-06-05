package be.jmshub.jmsremoteproducer;

import javax.jms.JMSException;
import com.sun.messaging.ConnectionConfiguration;
import com.sun.messaging.ConnectionFactory;
import com.sun.messaging.Queue;
import com.sun.messaging.Topic;
import javax.jms.Connection;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.Message;

public class Producer {

//    @Resource(lookup = "java:comp/DefaultJMSConnectionFactory")
    private static ConnectionFactory connectionFactory;
//    @Resource(lookup = "jms/MyQueue")
    private static Queue queue;
//    @Resource(lookup = "jms/MyTopic")
    private static Topic topic;

    public static void main(String[] args) throws JMSException {
        System.out.println("JMSProducers main class.");

        connectionFactory = new ConnectionFactory();
        connectionFactory.setProperty(ConnectionConfiguration.imqAddressList, "localhost:7676");

        queue = new Queue("MyQueue");
        topic = new Topic("MyTopic");

        try (Connection connection = connectionFactory.createConnection();
                Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                MessageProducer producer = session.createProducer(queue)) {

            Message message = session.createTextMessage("this is my test message");
            producer.send(message);
        }
        System.out.println("Message away");

//        final int NUM_MSGS;
//
//        if ((args.length < 1) || (args.length > 2)) {
//            System.err.println("Program takes one or two arguments: <dest_type> [<number-of-messages>]");
//            System.exit(1);
//        }
//
//        String destType = args[0];
//        System.out.println("Destination type is " + destType);
//
//        if (!(destType.equals("queue") || destType.equals("topic"))) {
//            System.err.println("Argument must be \"queue\" or " + "\"topic\"");
//            System.exit(1);
//        }
//
//        if (args.length == 2) {
//            NUM_MSGS = (new Integer(args[1])).intValue();
//        } else {
//            NUM_MSGS = 1;
//        }
//
//        Destination dest = null;
//
//        try {
//            if (destType.equals("queue")) {
//                dest = (Destination) queue;
//            } else {
//                dest = (Destination) topic;
//            }
//        } catch (JMSRuntimeException e) {
//            System.err.println("Error setting destination: " + e.toString());
//            System.exit(1);
//        }
//
//        /*
//         * Within a try-with-resources block, create context.
//         * Create producer and message.
//         * Send messages, varying text slightly.
//         * Send end-of-messages message.
//         */
//        try (JMSContext context = connectionFactory.createContext();) {
//            int count = 0;
//
//            for (int i = 0; i < NUM_MSGS; i++) {
//                String message = "This is message " + (i + 1)
//                        + " from producer";
//                // Comment out the following line to send many messages
//                System.out.println("Sending message: " + message);
//                context.createProducer().send(dest, message);
//                count += 1;
//            }
//            System.out.println("Text messages sent: " + count);
//
//            /*
//             * Send a non-text control message indicating end of
//             * messages.
//             */
//            context.createProducer().send(dest, context.createMessage());
//            // Uncomment the following line if you are sending many messages
//            // to two synchronous consumers
//            // context.createProducer().send(dest, context.createMessage());
//        } catch (JMSRuntimeException e) {
//            System.err.println("Exception occurred: " + e.toString());
//            System.exit(1);
//        }
//        System.exit(0);
    }

}
