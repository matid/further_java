package uk.ac.cam.md481.fjava.tick5;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Date;
import java.util.Random;
import java.sql.SQLException;

import uk.ac.cam.cl.fjava.messages.ChangeNickMessage;
import uk.ac.cam.cl.fjava.messages.ChatMessage;
import uk.ac.cam.cl.fjava.messages.Message;
import uk.ac.cam.cl.fjava.messages.RelayMessage;
import uk.ac.cam.cl.fjava.messages.StatusMessage;

public class ClientHandler {
  private Socket socket;
  private Database database;
  private MultiMessageQueue<Message> multiQueue;
  private String nickname;
  private MessageQueue<Message> messageQueue = new SafeMessageQueue<Message>();
  
  public ClientHandler(Socket socket, MultiMessageQueue<Message> queue, Database database) throws SQLException{
    this.socket = socket;
    this.database = database;
    this.multiQueue = queue;
    this.multiQueue.register(this.messageQueue);
    this.nickname = "Anonymous" + (new Random()).nextInt(10000);
    this.database.incrementLogins();
    
    for(RelayMessage message: this.database.getRecent()){
      this.messageQueue.put(message);
    }
    
    this.multiQueue.put(new StatusMessage(this.nickname + " connected from " + this.socket.getInetAddress().getHostName() + "."));
    
    input();
    output();
  }
  
  public void input(){
    Thread input = new Thread(){
      public void run(){
        try {
          ObjectInputStream stream = new ObjectInputStream(socket.getInputStream());
          
          Message message;
          while((message = (Message) stream.readObject()) != null){
            if(message instanceof ChangeNickMessage){
              String name = ((ChangeNickMessage) message).name;
              multiQueue.put(new StatusMessage(nickname + " is now known as " + name + "."));
              nickname = name;
            } else if(message instanceof ChatMessage) {
              RelayMessage m = new RelayMessage(nickname, ((ChatMessage) message).message, new Date());
              database.addMessage(m);
              multiQueue.put(m);
            }
          }
        } catch(IOException e){
          multiQueue.deregister(messageQueue);
          multiQueue.put(new StatusMessage(nickname + " has disconnected."));
        } catch(ClassNotFoundException e){
        } catch(SQLException e){}
      }
    };
    input.start();
  }
  
  public void output(){
    Thread output = new Thread(){
      public void run(){
        try {
          ObjectOutputStream stream = new ObjectOutputStream(socket.getOutputStream());
          
          Message message;
          while((message = messageQueue.take()) != null){
            stream.writeObject(message);
            stream.flush();
          }
        } catch(IOException e){}
      }
    };
    output.start();
  }
}
