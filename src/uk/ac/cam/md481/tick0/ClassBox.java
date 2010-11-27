package uk.ac.cam.md481.tick0;

import java.util.List;
import java.util.LinkedList;
import java.awt.Graphics;

public class ClassBox {
  private String name;
  private List<String> methods;
  private int x;
  private int y;
  private int height;
  private int width;

  public ClassBox(String name){
    this.name = name;
    this.methods = new LinkedList<String>();
    this.x = 10;
    this.y = 10;
    this.height = 19 + 5;
    this.width = 150;
  }

  public String getName(){
    return this.name;
  }

  public List<String> getMethods(){
    return this.methods;
  }

  public int getX(){
    return this.x;
  }

  public int getY(){
    return this.y;
  }

  public int getHeight(){
    return this.height;
  }

  public int getWidth(){
    return this.width;
  }

  public int getCentreX(){
    return this.getX() + this.getWidth() / 2;
  }

  public int getCentreY(){
    return this.getY() + this.getHeight() / 2;
  }

  public void move(int x, int y){
    this.x = x;
    this.y = y;
  }

  public void resize(int width, int height){
    this.width = width;
    this.height = height;
  }

  public void addMethod(String method){
    this.methods.add(method);
  }

  public void paintComponent(Graphics graphics){
    TextBox text = new TextBox(this.getX(), this.getY(), this.getWidth(), this.getHeight());

    text.header(this.getName());

    for(String method: this.getMethods()){
      text.add("- " + method + "()");
    }

    text.paintComponent(graphics);

    this.resize(text.getWidth(), text.getHeight());
  }
}