package dst3.messaging;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import dst3.dto.TaskDTO;
import dst3.model.Task;
import dst3.model.TaskStatus;


@MessageDriven (mappedName="queue.dst.ComputerReplyQueue", activationConfig = {
		@ActivationConfigProperty (propertyName = "destinationType",
								   propertyValue = "javax.jms.Queue")
})
public class ComputerMDB implements MessageListener {

	// state

	private Connection connection;
	private Session session;
	private MessageProducer schedulerProducer;
	
	// deps

	@Resource (mappedName = "dst.Factory")
	private ConnectionFactory connectionFactory;
	@Resource (mappedName = "queue.dst.SchedulerReplyQueue")
	private Queue schedulerReplyQueue;
	@Resource
	private MessageDrivenContext mdc;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	
	@PostConstruct
	public void init() throws JMSException {
		connection = connectionFactory.createConnection();
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		schedulerProducer = session.createProducer(schedulerReplyQueue);
		
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
			
			if( ObjectMessage.class.isInstance(msg) ) {
				
				ObjectMessage message = ObjectMessage.class.cast(msg);
				
				TaskDTO taskDTO = (TaskDTO) message.getObject();
				
				Task task = entityManager.find(Task.class, taskDTO.getId());				
				
				if( message.getStringProperty("type").equals("processed") ) {
					
					if( task != null ) {
						
						task.setStatus(TaskStatus.PROCESSED);
						
						ObjectMessage schedulerMsg = session.createObjectMessage(taskDTO);
						schedulerMsg.setStringProperty("type", "processed");
						schedulerProducer.send(schedulerMsg);
						
					}
					
				}
				
			}
		} catch(JMSException e) {
			System.out.println("Exception in onMessage, "+e.getMessage());
			mdc.setRollbackOnly();
		}
	}
	
}
