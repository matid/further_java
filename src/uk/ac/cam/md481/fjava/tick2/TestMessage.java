package uk.ac.cam.md481.fjava.tick2;

import java.io.Serializable;

public class TestMessage implements Serializable {
  private static final long serialVersionUID = 1L;
  private String text;
  public String getMessage(){ return this.text; }
  public void setMessage(String message){ this.text = message; }
}