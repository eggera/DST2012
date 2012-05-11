package dst2.ejb;

import java.util.List;
import java.util.Map;

import javax.ejb.Remote;

import dst2.ejb.exception.JobAssignmentException;
import dst2.ejb.exception.LoginFailedException;
import dst2.ejb.exception.NotLoggedInException;


@Remote
public interface JobManagement {
	
	void test();

	
	void addJobToList(Long gridId, int numCPUs, String workflow, List<String> params) throws JobAssignmentException;
	
	void submitJobList() throws JobAssignmentException, NotLoggedInException;
	
	void removeJobsFromList(Long gridId);
	
	Map<Long,Integer> getAmountOfJobsPerGrid();
	
	void login(String username, String password) throws LoginFailedException;
	
}
