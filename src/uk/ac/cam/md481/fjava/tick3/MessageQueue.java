package uk.ac.cam.md481.fjava.tick3;

public interface MessageQueue<T> {
  public abstract void put(T message);
  public abstract T take();
}
