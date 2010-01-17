package uk.ac.cam.md481.fjava.tick5;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

import uk.ac.cam.cl.fjava.messages.Message;

public class ChatServer {
  public static void main(String[] args){
    Integer port;
    ServerSocket socket;
    MultiMessageQueue<Message> queue = new MultiMessageQueue<Message>();
    
    try {
      if(args.length != 2) throw new IllegalArgumentException();
      port = Integer.parseInt(args[0]);
    } catch(IllegalArgumentException e){
      System.err.println("Usage: java ChatServer <port> <database>");
      return;
    }
    
    try {
      socket = new ServerSocket(port);
      Database database = new Database(args[1]);
      Socket client;
      while((client = socket.accept()) != null){
        new ClientHandler(client, queue, database);
      }
    } catch(SQLException e){
      System.err.println("Cannot execute SQL operation" + e.getMessage());
    } catch(ClassNotFoundException e){
      System.err.println("Cannot load required classes");
    } catch(IOException e){
      System.err.println("Cannot use port number " + port);
    }
  }
}
