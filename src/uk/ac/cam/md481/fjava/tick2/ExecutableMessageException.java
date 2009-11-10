package uk.ac.cam.md481.fjava.tick2;

import java.lang.reflect.InvocationTargetException;

import uk.ac.cam.cl.fjava.messages.Message;

public class ExecutableMessageException extends Exception {
  Message message;
  
  public ExecutableMessageException(Message message){
    this.message = message;
  }
  
  public void run(){
    Class<?> unknownClass = message.getClass();
    
    try {
      unknownClass.getMethod("run", (Class<?>[]) null).invoke(message);
    } catch(NoSuchMethodException e){
    } catch(IllegalAccessException e){
    } catch(InvocationTargetException e){}
  }
}