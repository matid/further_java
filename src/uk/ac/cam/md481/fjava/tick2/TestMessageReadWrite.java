package uk.ac.cam.md481.fjava.tick2;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.net.URLConnection;

public class TestMessageReadWrite {
  static boolean writeMessage(String message, String filename){
    try {
      ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(filename));
    
      TestMessage testMessage = new TestMessage();
      testMessage.setMessage(message);
    
      stream.writeObject(testMessage);
      
      stream.close();
      
      return true;
    } catch(IOException e){
      return false;
    }
  }
  
  static String readMessage(String location) {
    try {
      InputStream source;
      if(location.startsWith("http://")){
        source = loadFromURL(location); 
      } else {
        source = loadFromFile(location);
      }
    
      ObjectInputStream stream = new ObjectInputStream(source);
      TestMessage message = (TestMessage) stream.readObject();
      stream.close();
    
      return message.getMessage();
    } catch(IOException e){
      return null;
    } catch(ClassNotFoundException e){
      return null;
    }
  }
  
  private static InputStream loadFromURL(String url) throws IOException {
    URL destination = new URL(url);
    URLConnection connection = destination.openConnection();
    return connection.getInputStream();
  }
  
  private static InputStream loadFromFile(String filename) throws IOException {
    return new FileInputStream(filename);
  }
  
  public static void main(String args[]){
    try {
      if(args.length != 1) throw new IllegalArgumentException();
    } catch(IllegalArgumentException e){
      System.err.println("This application requires one argument: <url> or <path>");
      return;
    }
    
    String message = readMessage(args[0]);
    System.out.println(message);
  }
}
