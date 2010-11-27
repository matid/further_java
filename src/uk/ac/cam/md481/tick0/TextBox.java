package uk.ac.cam.md481.tick0;

import java.util.List;
import java.util.LinkedList;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;

public class TextBox {
  private List<String> lines;
  private Graphics graphics;
  private int x, y, width, height, padding;
  private boolean border;
  private String header;
  private Font headerFont, bodyFont;
  
  public TextBox(int x, int y, int width, int height, boolean border){
    this.lines = new LinkedList<String>();
    
    this.border = border;
    
    this.padding = 10;
    this.x = x + this.padding;
    this.y = y + this.padding;
    this.width = width;
    this.height = height;
    
    this.headerFont = new Font("Default", Font.BOLD, 15);
    this.bodyFont = new Font("Default", Font.PLAIN, 13);
  }
  
  public TextBox(int x, int y, int width, int height){
    this(x, y, width, height, true);
  }
  
  public int getWidth(){
    return this.width;
  }
  
  public int getHeight(){
    return this.height;
  }
  
  public void header(String header){
    this.header = header;
  }
  
  public void add(String line){
    this.lines.add(line);
  }  
  
  public void paintComponent(Graphics graphics){
    this.graphics = graphics;
    this.resize();
    
    if(this.border)
      this.draw();
      
    if(this.header != null)
      this.printHeader();
      
    for(String line: this.lines){
      this.print(line);
    }
  }
  
  private void resize(){
    this.height = 2 * this.padding + this.lines.size() * this.graphics.getFontMetrics().getHeight();
    if(this.header != null){
      graphics.setFont(this.headerFont);
      this.height += this.graphics.getFontMetrics().getHeight();
      graphics.setFont(this.bodyFont);
    }
  }
  
  private void draw(){
    graphics.setColor(Color.white);
    graphics.fillRect(this.x - this.padding, this.y - this.padding, this.width, this.height);
    
    graphics.setColor(Color.black);
    graphics.drawRect(this.x - this.padding, this.y - this.padding, this.width, this.height);
  }
  
  private void printHeader(){
    this.graphics.setFont(this.headerFont);
    this.print(this.header);
    this.graphics.setFont(this.bodyFont);
  }
  
  private void print(String line){
    this.next();
    while(this.graphics.getFontMetrics().stringWidth(line) > this.width - this.padding * 2){
      line = line.replaceAll(".{4}$", "...");
    }
    this.graphics.drawString(line, this.x, this.y);
  }
  
  private void next(){
    this.y += this.graphics.getFontMetrics().getHeight();
  }
}