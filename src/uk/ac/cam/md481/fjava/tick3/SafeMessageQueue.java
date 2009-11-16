package uk.ac.cam.md481.fjava.tick3;

public class SafeMessageQueue<T> implements MessageQueue<T> {
  public static class Link<L> {
    L value;
    Link<L> next;
    Link(L value){ this.value = value; this.next = null; }
  }
  private Link<T> first = null;
  private Link<T> last = null;
  
  public synchronized void put(T value){
    Link<T> link = new Link<T>(value);
    
    if(this.first == null)
      this.first = link;
    if(this.last != null)
      this.last.next = link;
    
    this.last = link;
    
    this.notify();
  }
  
  public synchronized T take(){
    while(this.first == null)
      try { this.wait(); } catch(InterruptedException ie){}
    T value = this.first.value;
    this.first = this.first.next;
    return value;
  }
}
