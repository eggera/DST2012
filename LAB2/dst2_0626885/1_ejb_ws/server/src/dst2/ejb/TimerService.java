package dst2.ejb;

import javax.ejb.Remote;


@Remote
public interface TimerService {

	public void setTimer(long intervalDuration);
}
