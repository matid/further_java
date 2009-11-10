package uk.ac.cam.md481.fjava.tick2;

import uk.ac.cam.cl.fjava.messages.DynamicObjectInputStream;
import uk.ac.cam.cl.fjava.messages.NewMessageType;

public class NewMessageTypeException extends Exception {
  private NewMessageType message;
  
  public NewMessageTypeException(NewMessageType message){
    this.message = message;
  }
  
  public void print(){
	  new ClientMessage("New class " + this.message.getName().replaceAll(".*\\.(.*)", "$1") + " loaded.").print();
	}
	
	public void run(DynamicObjectInputStream stream){
	  stream.addClass(this.message.getName(), this.message.getClassData());
	}
}