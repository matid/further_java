package uk.ac.cam.md481.fjava.tick2;

import java.text.DateFormat;
import java.util.Date;

abstract class SystemMessage {
  protected Date time;
  protected String from;
  protected String text;
  
  private String getTimeStamp(){
    return DateFormat.getTimeInstance().format(this.time);
  }
  
  public String getMessage(){
    return getTimeStamp() + " [" + this.from + "] " + this.text;
  }
  
  public String toString(){
    return getMessage();
  }
  
  public void print(){
    System.out.println(this);
  }
}
