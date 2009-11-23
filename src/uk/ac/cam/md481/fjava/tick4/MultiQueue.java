package uk.ac.cam.md481.fjava.tick4;

import java.util.HashSet;
import java.util.Set;

public class MultiQueue<T> {
  private Set<MessageQueue<T>> outputs = new HashSet<MessageQueue<T>>();
  
  public synchronized void register(MessageQueue<T> queue){
    this.outputs.add(queue);
  }
  
  public synchronized void deregister(MessageQueue<T> queue){
    this.outputs.remove(queue);
  }
  
  public synchronized void put(T message){
    for(MessageQueue<T> queue: this.outputs){
      queue.put(message);
    }
  }
}