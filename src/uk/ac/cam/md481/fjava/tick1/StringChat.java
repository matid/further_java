package uk.ac.cam.md481.fjava.tick1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class StringChat {
  private String host;
  private int port;
  private Socket socket;
  
  // Since socket is an instance variable in my implementation
  // the compiler knows how to pass that on to the thread
  // and there's no need to declare it as final  
  
  public StringChat(String host, int port){
    this.host = host;
    this.port = port;
  }
  
  public void connect() throws IOException {
    this.socket = new Socket(host, port);
  }
  
  public void disconnect(){
    try {
      if(this.socket != null) this.socket.close();
    } catch(IOException e){}
  }
  
  public void loop() throws IOException {
    Thread output = new Thread(){
      public void run(){
        try {
          InputStream stream = socket.getInputStream();
          BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
          
          String line = null;
          while((line = reader.readLine()) != null){
            System.out.println(line);
          }
        } catch(IOException e){}
      }
    };
    output.start();
    
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
    
    String line = null;
    while((line = reader.readLine()) != null){
      writer.write(line);
      writer.flush();
    }
  }
  
  public static void main(String[] args) {
    String host;
    int port;
    
    try {
      if(args.length != 2) throw new IllegalArgumentException();
      host = args[0];
      port = Integer.parseInt(args[1]);
    } catch(IllegalArgumentException e){
      System.err.println("This application requires two arguments: <machine> <port>");
      return;
    }
    
    StringChat chat = new StringChat(host, port);
    
    try {
      chat.connect();
      chat.loop();
    } catch(IOException e){
      System.err.println("Cannot connect to " + args[0] + " on port " + args[1]);
    } finally {
      chat.disconnect();
    }
  }
}
