package uk.ac.cam.md481.fjava.tick1;

public class HelloWorld {
  public static void main(String[] args){
    System.out.println("Hello, " + (args.length > 0 ? args[0] : "world"));
  }
}