package uk.ac.cam.md481.fjava.tick2;

import java.util.Date;

public class ClientMessage extends SystemMessage { 
  public ClientMessage(String message){
    this.time = new Date();
    this.from = "Client";
    this.text = message;
  }
}
