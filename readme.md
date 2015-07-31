# JMS hub

## Overview

Parent project for JMS related applications.

## Projects

### JMS API (Java Message Service API)

Send and receive messages using: reliable, asynchronous, loosely coupled communications.

- Asynchronous: The client can send, and does not have to wait.
- Reliable: ensure that the message is delivered once and only once.

JMS provider can be integrated in the application server, allowing to use Java EE connector architecture.

JMS application is composed of the following parts:

- JMS provider: Messaging system that implements the JMS interfaces and provides administrative control.
- JMS clients: Components that produce and consume messages. Java EE or Java SE applications.
- Messages: Objects that communicate the information.
- Administered objects: Objects for configuration. Example: destination and connection factories. This can be specific for an application, even annotations.

Two parts of a JMS application, destinations and connection factories, are commonly maintained administratively rather than programmatically. The technology is different for every provider.

### JMS Message: 3 parts

- Header: set the destination, use one queue for all printers
- Properties: set the printer
- Body: our print message

###GlassFish setup:
JMS connection factory - JNDI lookup name: java:comp/DefaultJMSConnectionFactory

this factory is mapped to resource: connection factory name: jms/__defaultConnectionFactory

###GlassFish configuration

Configuration:

    C:\AppSpace\glassfish4\bin\asadmin
    C:\AppSpace\glassfish4\bin\asadmin create-jms-resource
    C:\AppSpace\glassfish4\bin\asadmin list-jms-resources
    C:\AppSpace\glassfish4\bin\asadmin delete-jms-resource

### Test applications

Run Producer in appclient: (J2EE Tutorial example)

	C:\AppSpace\glassfish4\glassfish\bin\appclient -client target/producer.jar queue 3

APPCLIENT - Communication to GLASSFISH OPEN MQ

	C:\SourceSpace\GitHub\JMShub\JMSProducer>C:\AppSpace\glassfish4\glassfish\bin\appclient -client target/JMSProducer-1.0-SNAPSHOT.jar queue 3

	C:\SourceSpace\GitHub\JMShub\JMSConsumer>C:\AppSpace\glassfish4\glassfish\bin\appclient -client target/JMSConsumer-1.0-SNAPSHOT.jar -queue

JAVA CLIENT - Communication to GLASSFISH OPEN MQ

    C:\SourceSpace\GitHub\JMShub\JMSRemoteProducer>
    java -cp "target/JMSRemoteProducer-1.0-SNAPSHOT.jar;C:/AppSpace/glassfish4/mq/lib/*" be.jmshub.jmsremoteproducer.Producer
    
    C:\SourceSpace\GitHub\JMShub\JMSRemoteConsumer>
    java -cp "target/JMSRemoteConsumer-1.0-SNAPSHOT.jar;C:/AppSpace/glassfish4/mq/lib/*" be.jmshub.jmsremoteconsumer.Consumer

JAVA CLIENT - Communication to ACTIVE MQ

	cd C:\SourceSpace\GitHub\JMShub\JMSActiveMQProducer
	java -cp "target/JMSActiveMQProducer-1.0-SNAPSHOT.jar;C:/SourceSpace/GitHub/JMShub/JMSActiveMQProducer/lib/*" be.jmshub.jmsactivemqproducer.Producer

	cd C:\SourceSpace\GitHub\JMShub\JMSActiveMQConsumer
	java -cp "target/JMSActiveMQConsumer-1.0-SNAPSHOT.jar;C:/SourceSpace/GitHub/JMShub/JMSActiveMQConsumer/lib/*" be.jmshub.jmsactivemqconsumer.Consumer

JAVA CLIENT - Communication to ACTIVE MQ with JNDI lookup

	cd C:\SourceSpace\GitHub\JMShub\JMSRemoteJNDIProducer
	java -cp "target/JMSRemoteJNDIProducer-1.0-SNAPSHOT.jar;C:/SourceSpace/GitHub/JMShub/JMSRemoteJNDIProducer/lib/*" be.jmshub.jmsremotejndiproducer.Producer

	cd C:\SourceSpace\GitHub\JMShub\JMSRemoteJNDIConsumer
	java -cp "target/JMSRemoteJNDIConsumer-1.0-SNAPSHOT.jar;C:/SourceSpace/GitHub/JMShub/JMSRemoteJNDIConsumer/lib/*" be.jmshub.jmsremotejndiconsumer.Consumer

JAVA CLIENT - Communication to ACTIVE MQ with JNDI lookup over HTTP

add protocol to activemq.xml:

	<transportConnector name="http" uri="http://0.0.0.0:8081"/>

Run the program
	
	cd C:\SourceSpace\GitHub\JMShub\JMSRemoteJNDIhttpProducer
	java -cp "target/JMSRemoteJNDIhttpProducer-1.0-SNAPSHOT.jar;C:/SourceSpace/GitHub/JMShub/JMSRemoteJNDIhttpProducer/lib/*" be.jmshub.jmsremotejndihttpproducer.Producer
	
	cd C:\SourceSpace\GitHub\JMShub\JMSRemoteJNDIhttpConsumer
	java -cp "target/JMSRemoteJNDIhttpConsumer-1.0-SNAPSHOT.jar;C:/SourceSpace/GitHub/JMShub/JMSRemoteJNDIhttpConsumer/lib/*" be.jmshub.jmsremotejndihttpconsumer.Consumer

JAVA CLIENT - Communication to ACTIVE MQ with JNDI lookup over HTTPS

add protocol to activemq.xml:

	<transportConnector name="https" uri="https://0.0.0.0:8443"/>

create valid keystore: http://activemq.apache.org/how-do-i-use-ssl.html

	C:\AppSpace\jdk1.7.0_67\bin>keytool -genkey -alias broker -keyalg RSA -keystore broker.ks

IMPORTANT: CN should be the host name: cyberserver.mips.be; pc-prd0170; localhost; ...

STEP 1: Using keytool, create a certificate for the broker:

keystore password: CyberBroker

key password: CyberBroker

	Enter keystore password:
	Re-enter new password:
	What is your first and last name?
	  [Unknown]:  localhost
	What is the name of your organizational unit?
	  [Unknown]:  CyberLab
	What is the name of your organization?
	  [Unknown]:  Mips
	What is the name of your City or Locality?
	  [Unknown]:  Ghent
	What is the name of your State or Province?
	  [Unknown]:  OV
	What is the two-letter country code for this unit?
	  [Unknown]:  BE
	Is CN=localhost, OU=CyberLab, O=Mips, L=Ghent, ST=OV, C=BE correct?
	  [no]:  yes
	
	Enter key password for <broker>
	        (RETURN if same as keystore password):

STEP 2: Export the broker's certificate so it can be shared with clients:

	C:\AppSpace\jdk1.7.0_67\bin>keytool -export -alias broker -keystore broker.ks -file broker_cert

	Enter keystore password:
	Certificate stored in file <broker_cert>

STEP 3: Create a certificate/keystore for the client:

	keystore password: CyberClient
	key password: CyberClient

Keytool

	C:\AppSpace\jdk1.7.0_67\bin>keytool -genkey -alias client -keyalg RSA -keystore client.ks

IMPORTANT: CN should be the host name: cyberserver.mips.be; pc-prd0170; localhost; ...

	Enter keystore password:
	Re-enter new password:
	What is your first and last name?
	  [Unknown]:  localhost
	What is the name of your organizational unit?
	  [Unknown]:  CyberLab
	What is the name of your organization?
	  [Unknown]:  Mips
	What is the name of your City or Locality?
	  [Unknown]:  Ghent
	What is the name of your State or Province?
	  [Unknown]:  OV
	What is the two-letter country code for this unit?
	  [Unknown]:  BE
	Is CN=localhost, OU=CyberLab, O=Mips, L=Ghent, ST=OV, C=BE correct?
	  [no]:  yes
	
	Enter key password for <client>
	        (RETURN if same as keystore password):

STEP 4: Create a truststore for the client, and import the broker's certificate. This establishes that the client "trusts" the broker:

	password: CyberClient

Keytool

	C:\AppSpace\jdk1.7.0_67\bin>keytool -import -alias broker -keystore client.ts -file broker_cert

	Enter keystore password:
	Re-enter new password:
	Owner: CN=localhost, OU=CyberLab, O=Mips, L=Ghent, ST=OV, C=BE
	Issuer: CN=localhost, OU=CyberLab, O=Mips, L=Ghent, ST=OV, C=BE
	Serial number: 68189368
	Valid from: Fri Jun 12 11:39:53 CEST 2015 until: Thu Sep 10 11:39:53 CEST 2015
	Certificate fingerprints:
	         MD5:  CE:8F:5E:3B:E2:E6:DE:E7:E8:01:11:6D:F4:5A:BF:CC
	         SHA1: 2C:C0:73:0B:26:84:48:2B:E8:0C:BD:93:BE:F9:63:68:11:48:DF:05
	         SHA256: 3F:1D:45:4F:2E:10:48:07:64:61:51:CD:AD:2B:3C:5E:1C:E9:66:05:9C:EE:80:92:41:15:B5:DD:4F:05:D2:44
	         Signature algorithm name: SHA256withRSA
	         Version: 3
	
	Extensions:
	
	#1: ObjectId: 2.5.29.14 Criticality=false
	SubjectKeyIdentifier [
	KeyIdentifier [
	0000: 2E E8 02 5D AD 1C C7 6C   F5 CA 15 0A 48 D6 8E AB  ...]...l....H...
	0010: 7B AC 96 98                                        ....
	]
	]
	
	Trust this certificate? [no]:  yes
	Certificate was added to keystore
	Certificate was added to keystore

STEP 5: activemq.be

	line 95
	add to classpath:
	-Djavax.net.ssl.keyStore=C:/AppSpace/jdk1.7.0_67/bin/broker.ks -Djavax.net.ssl.keyStorePassword=CyberBroker
	
	or set system properties:
	set SSL_OPTS = -Djavax.net.ssl.keyStore=C:/AppSpace/jdk1.7.0_67/bin/broker.ks -Djavax.net.ssl.keyStorePassword=CyberBroker


Info: Clients normally do not need to provide their own certificate, unless the broker SSL/TLS configuration specifies that client authentication is required.

Here: Message producer client in Java, where the message producer connects to the broker using the SSL/TLS protocol.
The key step here is that the client uses the connectionFactory class to create the connection, also setting the trust store and trust store password (no key store is required here, because we are assuming that the broker port does not require client authentication).

Runthe program:

	cd C:\SourceSpace\GitHub\JMShub\JMSRemoteJNDIhttpsProducer
	java -Djavax.net.ssl.keyStore=C:/AppSpace/jdk1.7.0_67/bin/client.ks -Djavax.net.ssl.keyStorePassword=CyberClient -Djavax.net.ssl.trustStore=C:/AppSpace/jdk1.7.0_67/bin/client.ts -cp "target/JMSRemoteJNDIhttpsProducer-1.0-SNAPSHOT.jar;C:/SourceSpace/GitHub/JMShub/JMSRemoteJNDIhttpsProducer/lib/*" be.jmshub.jmsremotejndihttpsproducer.Producer
	
	cd C:\SourceSpace\GitHub\JMShub\JMSRemoteJNDIhttpsConsumer
	java -Djavax.net.ssl.keyStore=C:/AppSpace/jdk1.7.0_67/bin/client.ks -Djavax.net.ssl.keyStorePassword=CyberClient -Djavax.net.ssl.trustStore=C:/AppSpace/jdk1.7.0_67/bin/client.ts -cp "target/JMSRemoteJNDIhttpsConsumer-1.0-SNAPSHOT.jar;C:/SourceSpace/GitHub/JMShub/JMSRemoteJNDIhttpsConsumer/lib/*" be.jmshub.jmsremotejndihttpsconsumer.Consumer

CYBERLAB CLIENT

	cd C:\SourceSpace\CyberLabClient\
	java -cp "target/cyberlab-client.jar;C:/SourceSpace/CyberLabClient/lib/*" be.mips.cyberlab.cyberlab_client.App

### extra info

TODO: standalon JMS client

- https://nhachicha.wordpress.com/2012/02/11/standalone-jms-client/
- https://nnbharadwaj.wordpress.com/2012/10/02/simple-jms-example/

Remote JMS client for WildFly - example:

- http://www.mastertheboss.com/jboss-server/jboss-jms/how-to-code-a-remote-jms-client-for-wildfly-8

HermesJMS:

- https://blogs.oracle.com/sebsto/entry/using_hermesjms_to_browse_glassfissh

Remote on wildfly:

- jms/RemoteConnectionFactory
- java:jboss/exported/jms/RemoteConnectionFactory
- java:jboss/exported/jms/queue/first"

to guest group

	jmsuser
	jmsuser

TODO: folder structure:

	/src/
	/classes/
	/lib/ --> for needed jars

Active MQ: java client with jndi jms connection

- http://activemq.apache.org/jndi-support.html

Status

	AppClient: OK
	JavaClient: OK --> connection.start()!!!
	ActiveMQ JavaClient: OK
	ActiveMQ JavaClient with JNDI: OK --> jndi.properties in client and server classpath
	ActiveMQ JavaClient with JNDI over HTTP: --> activemq.xml: <transportConnector name="http" uri="http://0.0.0.0:8081"/>
	ActiveMQ JavaClient with JNDI over HTTPS: --> activemq.xml: <transportConnector name="https" uri="https://0.0.0.0:8443"/> --> add certificates



dependencies: /lib/

	activemq-all-5.11.1.jar: for jms api
	httpcore-4.4.1.jar: httpClient necessary for http transport
	httpclient-4.5.jar: httpClient necessary for http transport
	commons-logging-1.2.jar: neded for httpclient
	xstream-1.4.8.jar: necessary for http transport
	xpp3-1.1.4c.jar: necessary for xstream

Important: Make sure no system property is pointing

Find communication:

	netstat -an|find "61616"
	netstat -an|find "8081"
	netstat -an|find "8443"