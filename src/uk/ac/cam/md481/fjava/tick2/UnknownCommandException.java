package uk.ac.cam.md481.fjava.tick2;

public class UnknownCommandException extends Exception {
  private String command;
  
  public UnknownCommandException(String command){
    this.command = command;
  }
  
  public String getCommand(){
    return this.command;
  }
}