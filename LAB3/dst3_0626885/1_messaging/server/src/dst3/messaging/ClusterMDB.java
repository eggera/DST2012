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
import javax.jms.ObjectMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import dst3.dto.TaskDTO;
import dst3.model.Task;
import dst3.model.TaskComplexity;
import dst3.model.TaskStatus;

@MessageDriven (mappedName="queue.dst.ClusterReplyQueue", activationConfig = {
		@ActivationConfigProperty (propertyName = "destinationType",
								   propertyValue = "javax.jms.Queue")
})
public class ClusterMDB  implements MessageListener {

	// state

	private Connection connection;
	
	// deps

	@Resource (mappedName = "dst.Factory")
	private ConnectionFactory connectionFactory;
	@Resource
	private MessageDrivenContext mdc;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	
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
			
			if( ObjectMessage.class.isInstance(msg) ) {
				
				ObjectMessage message = ObjectMessage.class.cast(msg);
				
				TaskDTO taskDTO = (TaskDTO) message.getObject();
				
				Task task = entityManager.find(Task.class, taskDTO.getId());				
				
				if( message.getStringProperty("type").equals("accept") ) {
					
					System.out.println("Cluster accept");
					if( task != null ) {
						task.setStatus(TaskStatus.valueOf(taskDTO.getStatus().toString()));
						task.setRatedBy(taskDTO.getRatedBy());
						task.setComplexity(TaskComplexity.valueOf(taskDTO.getComplexity().toString()));
					}					
				}
				
				else if( message.getStringProperty("type").equals("deny") ) {

					System.out.println("Cluster deny");
					if( task != null ) {
						task.setStatus(TaskStatus.valueOf(taskDTO.getStatus().toString()));
						task.setRatedBy(taskDTO.getRatedBy());
					}
				}
			}
			else {
				System.out.println("Not an object message, messageType: "+msg.getClass().getName());
			}
			
		} catch(JMSException e) {
			System.out.println("Exception in onMessage, "+e.getMessage());
			mdc.setRollbackOnly();
		}
	}

}
