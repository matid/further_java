package uk.ac.cam.md481.fjava.tick1;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class StringReceive {
  private String host;
  private int port;
  private Socket socket;
  
  public StringReceive(String host, int port){
    this.host = host;
    this.port = port;
  }
  
  public void connect() throws IOException {
    this.socket = new Socket(host, port);
  }
  
  public void loop() throws IOException {
    InputStream stream = this.socket.getInputStream();
//    BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
    
//    String line = null;
//    while((line = reader.readLine()) != null){
//      System.out.println(line);
//    }
    byte[] buffer = new byte[1024];
    int size;
    while((size = stream.read(buffer, 0, 1000)) != -1){
      System.out.println(new String(buffer, 0, size).trim());
    }
  }
  
  public void disconnect(){
    try {
      if(this.socket != null) this.socket.close();
    } catch(IOException e){}
  }
  
  public static void main(String[] args){
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
    
    StringReceive sr = new StringReceive(host, port);
    
    try {
      sr.connect();
      sr.loop();
    } catch(IOException e){
      System.err.println("Cannot connect to " + args[0] + " on port " + args[1]);
    } finally {
      sr.disconnect();
    }
  }
}