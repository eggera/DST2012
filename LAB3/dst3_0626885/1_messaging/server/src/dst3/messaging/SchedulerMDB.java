package dst3.messaging;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.StreamMessage;
import javax.jms.TextMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import javax.ejb.ActivationConfigProperty;

import dst3.dto.TaskDTO;
import dst3.model.Task;
import dst3.model.TaskComplexity;
import dst3.model.TaskStatus;


@MessageDriven (mappedName="queue.dst.SchedulerQueue", activationConfig = {
		@ActivationConfigProperty (propertyName = "destinationType",
								   propertyValue = "javax.jms.Queue")
})
public class SchedulerMDB implements MessageListener {

	// state

	private Connection connection;
	
	// deps

	@Resource (mappedName = "dst.Factory")
	private ConnectionFactory connectionFactory;
	@Resource (mappedName = "queue.dst.SchedulerReplyQueue")
	private Queue schedulerReplyQueue;
	@Resource (mappedName = "queue.dst.ClusterQueue")
	private Queue clusterQueue;
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
			
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			MessageProducer schedulerMsgProducer = session.createProducer(schedulerReplyQueue);
			MessageProducer clusterMsgProducer = session.createProducer(clusterQueue);
			
			TextMessage textMessage = null;
			
			if( TextMessage.class.isInstance(msg) ) {
				
				textMessage = TextMessage.class.cast(msg);
				System.out.println("message recieved: "+textMessage.getText());
		
				System.out.println("NON EMPTY TEXT MESSAGE");
				System.out.println("sending reply ...");
				
				TextMessage txtMsg = session.createTextMessage("message from server returned: "+textMessage.getText());
				schedulerMsgProducer.send(txtMsg);
				
				System.out.println("reply sent");
				
			}
			
			if( MapMessage.class.isInstance(msg) ) {

				MapMessage clientMessage = MapMessage.class.cast(msg);
				
				if( clientMessage.getBooleanProperty("assign") == true ) {
					Long jobId 	= clientMessage.getLong("jobId");
					Task task 	= new Task(jobId, TaskStatus.ASSIGNED, TaskComplexity.UNRATED);
					
					entityManager.persist(task);
					
					// reply to scheduler with task id
					StreamMessage streamMsg = session.createStreamMessage();
					streamMsg.writeLong(task.getId());
					schedulerMsgProducer.send(streamMsg);
					
					// send task to queue for clusters
					TaskDTO taskDTO = new TaskDTO(task.getId(), task.getJobId(), TaskDTO.TaskDTOStatus.valueOf(task.getStatus().toString()), task.getRatedBy(), TaskDTO.TaskDTOComplexity.valueOf(task.getComplexity().toString()));
					ObjectMessage clusterMsg = session.createObjectMessage(taskDTO);
					clusterMsgProducer.send(clusterMsg);
				}
				else if( clientMessage.getBooleanProperty("info") == true ) {
					Long taskId = clientMessage.getLong("taskId");
					Task task 	= entityManager.find(Task.class, taskId);
					
					if( task != null ) {
						TaskDTO taskDTO = new TaskDTO(taskId, task.getJobId(), TaskDTO.TaskDTOStatus.valueOf(task.getStatus().toString()), task.getRatedBy(), TaskDTO.TaskDTOComplexity.valueOf(task.getComplexity().toString()));
						
						ObjectMessage objectMsg = session.createObjectMessage(taskDTO);
						schedulerMsgProducer.send(objectMsg);
					}
				}
				else {
					System.out.println("OTHER MESSAGE");
				}
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
