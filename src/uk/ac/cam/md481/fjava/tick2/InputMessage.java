package uk.ac.cam.md481.fjava.tick2;

import uk.ac.cam.cl.fjava.messages.ChangeNickMessage;
import uk.ac.cam.cl.fjava.messages.ChatMessage;
import uk.ac.cam.cl.fjava.messages.Message;

public class InputMessage {
  private Message message;
  
  public InputMessage(String message) throws UserQuitException, UnknownCommandException {
    if(message.startsWith("\\nick")){
      this.message = new ChangeNickMessage(message.replace("\\nick ", ""));
    } else if(message.equals("\\quit")){
      throw new UserQuitException();
    } else if(message.startsWith("\\")){
      throw new UnknownCommandException(message.replace("\\", "").split(" ")[0]);
    } else {
      this.message = new ChatMessage(message);
    }
  }
  
  public Message getMessage(){
    return this.message;
  }
}