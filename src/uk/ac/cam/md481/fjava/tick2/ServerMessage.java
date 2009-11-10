package uk.ac.cam.md481.fjava.tick2;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Date;

import uk.ac.cam.cl.fjava.messages.Message;
import uk.ac.cam.cl.fjava.messages.NewMessageType;
import uk.ac.cam.cl.fjava.messages.RelayMessage;
import uk.ac.cam.cl.fjava.messages.StatusMessage;

public class ServerMessage extends SystemMessage {
  public ServerMessage(Message message) throws NewMessageTypeException, ExecutableMessageException {
    if(message instanceof RelayMessage){
      RelayMessage m = (RelayMessage) message;
      this.time = m.getTime();
      this.from = m.getFrom();
      this.text = m.getMessage().trim();
    } else if(message instanceof StatusMessage){
      StatusMessage m = (StatusMessage) message;
      m.setReceivedTime();
      this.time = m.getReceivedTime();
      this.from = "Server";
      this.text = m.getMessage().trim();
    } else if(message instanceof NewMessageType){
      throw new NewMessageTypeException((NewMessageType) message);
    } else {
      Class<?> unknownClass = message.getClass();
      String text = unknownClass.getName() + ": ";
      for(Field field: unknownClass.getDeclaredFields()){
        try {
          if(Modifier.isPublic(field.getModifiers())){
            text += field.getName();
            text += "(" + field.get(message) + "), ";
          }
        } catch(IllegalAccessException e){}
      }
      text = text.replaceAll("(, $)", "");
      
      new ClientMessage(text).print();
      
      throw new ExecutableMessageException(message);
    }
  }
}
