package main.java;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import com.ibm.mq.jms.MQQueue;
import com.ibm.mq.jms.MQQueueConnection;
import com.ibm.mq.jms.MQQueueConnectionFactory;
import com.ibm.mq.jms.MQQueueReceiver;
import com.ibm.mq.jms.MQQueueSession;

public class NicMqStub {
	public static void main(String[] arg) {
		try {
			MQQueueConnection connection;
			MQQueueConnectionFactory factory = new MQQueueConnectionFactory();
			final MQQueueSession session;
			MQQueueReceiver receiver;
			factory.setHostName("localhost");
			factory.setPort(1410);
			factory.setQueueManager("NicoMQ");
			factory.setChannel("SYSTEM.DEF.SVRCONN");
			connection = (MQQueueConnection) factory.createQueueConnection();
			session = (MQQueueSession) connection.createQueueSession(true, Session.AUTO_ACKNOWLEDGE);
			MQQueue in = (MQQueue) session.createQueue("Mq.IN");
			receiver = (MQQueueReceiver) session.createReceiver(in);
			javax.jms.MessageListener listener = new javax.jms.MessageListener() {

				@Override
				public void onMessage(Message m) {
					// TODO Auto-generated method stub
					System.out.println("The message was cought.");
					if (m instanceof TextMessage) {
						try {
							TextMessage tm = (TextMessage) m;
							String text = tm.getText();
							System.out.println(text);
						} catch (JMSException e) {
							e.printStackTrace();
						}
					}

				}
			};
			receiver.setMessageListener(listener);
			connection.start();
			System.out.println("Stub started");
		} catch (JMSException e) {
			e.printStackTrace();
		}
		try {
			Thread.sleep(7000);

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
