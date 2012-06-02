package dst3.messaging;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import javax.ejb.ActivationConfigProperty;

import org.apache.log4j.Logger;


@MessageDriven (mappedName="queue.dst.SchedulerQueue", activationConfig = {
		@ActivationConfigProperty (propertyName = "destinationType",
								   propertyValue = "javax.jms.Queue")
})
public class SchedulerMessageBean implements MessageListener {

	// state

	private Connection connection;
	
	// deps

	@Resource (mappedName = "dst.Factory")
	private ConnectionFactory connectionFactory;
	@Resource (mappedName = "queue.dst.SchedulerReplyQueue")
	private Queue replyQueue;
	@Resource
	private MessageDrivenContext mdc;
	
	
	@PostConstruct
	public void init() throws JMSException {
		connection = connectionFactory.createConnection();
		connection.start();
	}
	
	
	@PreDestroy
	public void shutdown() throws JMSException {
		if( connection != null)
			connection.close();
	}
	
	
	@Override
	public void onMessage(Message msg) {
		
		try {
			
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			MessageProducer producer = session.createProducer(replyQueue);
			
			TextMessage textMessage = null;
			
			if( TextMessage.class.isInstance(msg) ) {
				
				textMessage = TextMessage.class.cast(msg);
				System.out.println("message recieved: "+textMessage.getText());
				
//				if(!textMessage.getText().equals("")) {
//					
					System.out.println("NON EMPTY TEXT MESSAGE");
					System.out.println("sending reply ...");
					
					TextMessage txtMsg = session.createTextMessage("message from server returned: "+textMessage.getText());
					producer.send(txtMsg);
					
					System.out.println("reply sent");
//				}
				
			}
			
			else {
				
				System.out.println("other message type: "+msg.getClass().getName());
			}
		} catch (JMSException e) {
			e.printStackTrace();
			mdc.setRollbackOnly();
		}
		
	}

}
