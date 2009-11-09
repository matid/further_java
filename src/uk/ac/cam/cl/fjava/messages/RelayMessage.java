package uk.ac.cam.cl.fjava.messages;

import java.io.Serializable;
import java.util.Date;

/**
 *  server->client relaying from another client e.g. a standard message sent from one client and relayed to the others
 * @author acr31
 *
 */
public class RelayMessage extends Message implements Serializable {

	private static final long serialVersionUID = 1L;
	private String from;
	private String message;
	private Date time;
	
	public RelayMessage(String from, String message, Date time) {
		super();
		this.from = from;
		this.message = message;
		this.time = time;
	}

	public String getFrom() {
		return from;
	}

	public String getMessage() {
		return message;
	}

	public Date getTime() {
		return time;
	}
}
