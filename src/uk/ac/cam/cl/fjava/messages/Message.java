package uk.ac.cam.cl.fjava.messages;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable {
	private static final long serialVersionUID = 1L;
	transient private Date receivedTime;
	
	public void setReceivedTime() { receivedTime =  new Date(); }
	
	public Date getReceivedTime() { return receivedTime; }
}
