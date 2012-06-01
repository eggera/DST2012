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
import javax.jms.Queue;
import javax.jms.TextMessage;

import javax.ejb.ActivationConfigProperty;


@MessageDriven (mappedName="queue.dst.SchedulerQueue", activationConfig = {
		@ActivationConfigProperty (propertyName = "destinationType",
								   propertyValue = "javax.jms.Queue")
})
public class SchedulerMessageBean implements MessageListener {

	@Resource (mappedName = "dst.Factory")
	private ConnectionFactory connectionFactory;
	@Resource (mappedName = "queue.dst.SchedulerQueue")
	private Queue queue;
	@Resource
	private MessageDrivenContext mdc;
	
	private Connection connection;
	
	
	@PostConstruct
	public void init() throws JMSException {
		connection = connectionFactory.createConnection();
	}
	
	
	@PreDestroy
	public void shutdown() throws JMSException {
		if( connection != null)
			connection.close();
	}
	
	
	@Override
	public void onMessage(Message msg) {
		
		try {
			TextMessage textMessage = null;
			
			
			if( TextMessage.class.isInstance(msg) ) {
				
				textMessage = TextMessage.class.cast(msg);
				System.out.println("message received: "+textMessage.getText());
				
			}
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
