package uk.ac.cam.md481.fjava.tick2;

import uk.ac.cam.cl.fjava.messages.NewMessageType;

public class NewMessageTypeException extends Exception {
  private NewMessageType message;
  
  public NewMessageTypeException(NewMessageType message){
    this.message = message;
  }
  
  public String getName(){
    return this.message.getName();
  }
  
  public byte[] getClassData(){
    return this.message.getClassData();
  }
}