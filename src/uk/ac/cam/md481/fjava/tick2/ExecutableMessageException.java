package uk.ac.cam.md481.fjava.tick2;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import uk.ac.cam.cl.fjava.messages.Message;

public class ExecutableMessageException extends Exception {
  Message message;
  Class<?> klass;
  
  public ExecutableMessageException(Message message){
    this.message = message;
    this.klass = message.getClass();
  }
  
  public void print(){
    String text = this.klass.getName().replaceAll(".*\\.(.*)", "$1") + ": ";
    for(Field field: this.klass.getDeclaredFields()){
      field.setAccessible(true);
      try {
        text += field.getName();
        text += "(" + field.get(message) + ")";
      } catch(IllegalAccessException e){}
      text += ", ";
    }
    text = text.replaceAll("(, $)", "");
    
    new ClientMessage(text).print();
  }
  
  public void run(){
    try {
      this.klass.getMethod("run", (Class<?>[]) null).invoke(message);
    } catch(NoSuchMethodException e){
    } catch(IllegalAccessException e){
    } catch(InvocationTargetException e){}
  }
}