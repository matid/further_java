package uk.ac.cam.cl.fjava.messages;

import java.io.Serializable;

/**
 * Message sent by the client to the server to notify the server of 
 * a change in the nickname.
 */
public class ChangeNickMessage extends Message implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public String name;

	public ChangeNickMessage(String name) {
		super();
		this.name = name;
	}
	
}
