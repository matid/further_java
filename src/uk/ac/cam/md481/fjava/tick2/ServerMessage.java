package uk.ac.cam.md481.fjava.tick2;

import uk.ac.cam.cl.fjava.messages.Message;
import uk.ac.cam.cl.fjava.messages.RelayMessage;
import uk.ac.cam.cl.fjava.messages.StatusMessage;

public class ServerMessage extends SystemMessage {
  public ServerMessage(Message message){
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
    }
  }
}
