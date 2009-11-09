package uk.ac.cam.md481.fjava.tick2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import uk.ac.cam.cl.fjava.messages.ChangeNickMessage;
import uk.ac.cam.cl.fjava.messages.ChatMessage;
import uk.ac.cam.cl.fjava.messages.Message;

public class ChatClient {
  private String host;
  private Integer port;
  private Socket connection;
  
  class UserQuitException extends Exception {}
  
  public ChatClient(String host, Integer port){
    this.host = host;
    this.port = port;
  }
  
  public void connect() throws IOException {
    this.connection = new Socket(this.host, this.port);
    System.out.println(new ClientMessage("Connected to " + this.host + " on port " + this.port));
  }
  
  public void disconnect(){
    try {
      this.connection.close();
    } catch(IOException e){}
  }
  
  public void run() throws UserQuitException {
    output();
    input();    
  }
  
  private void input() throws UserQuitException {
    try {
      BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
      ObjectOutputStream stream = new ObjectOutputStream(this.connection.getOutputStream());
      
      String line = null;
      while((line = reader.readLine()) != null){
        stream.writeObject(parseInput(line));
        stream.flush();
      }
      
      stream.close();
      
      } catch(IOException e){
        
      }
  }
  
  private void output(){
    Thread output = new Thread(){
      public void run(){
        try {
          ObjectInputStream stream = new ObjectInputStream(connection.getInputStream());
          
          Message message = null;
          while((message = (Message) stream.readObject()) != null){
            System.out.println((new ServerMessage(message)));
            
          }
        } catch(IOException e){
          
        } catch(ClassNotFoundException e){
          
        }
      }
    };
    output.start();
  }
  
  public Message parseInput(String line) throws UserQuitException {
    if(line.startsWith("\\nick")){
      return new ChangeNickMessage(line.replace("\\nick ", ""));
    } else if(line.equals("\\quit")){
      throw new UserQuitException();
    } else if(line.startsWith("\\")){
      return null;
    } else {
      return new ChatMessage(line);
    }
  }
  
  public static void main(String[] args) {
    String host;
    Integer port;
    
    try {
      if(args.length != 2) throw new IllegalArgumentException();
      host = args[0];
      port = Integer.parseInt(args[1]);
    } catch(IllegalArgumentException e){
      System.err.println("This application requires two arguments: <machine> <port>");
      return;
    }
    
    ChatClient client = new ChatClient(host, port);
    
    try {
      client.connect();
      client.run();
      
    } catch(IOException e){
      System.err.println("Cannot connect to " + args[0] + " on port " + args[1]);
    } catch(UserQuitException e){
      System.out.println((new ClientMessage("Connection terminated.")));
    } finally {
      client.disconnect();
    }
  }

}
