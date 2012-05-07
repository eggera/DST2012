package dst2.ejb;

import javax.ejb.Remote;

@Remote
public interface Testing {

	String saveEntities(String entity);
}
