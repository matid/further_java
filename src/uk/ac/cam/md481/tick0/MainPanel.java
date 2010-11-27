package uk.ac.cam.md481.tick0;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;

public class MainPanel extends JPanel {
  private Document document;
  
  public void setDocument(Document document){
    this.document = document;
  }

  public void paintComponent(Graphics graphics){    
    graphics.setColor(Color.white);
    graphics.fillRect(0, 0, this.getWidth(), this.getHeight());
    graphics.setColor(Color.black);
    
    this.document.paintComponent(graphics);
  }
}