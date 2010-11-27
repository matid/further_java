package uk.ac.cam.md481.tick0;

import javax.swing.JPanel;
import java.awt.Graphics;

public class InfoPanel extends JPanel {
  private Document document;

  public void setDocument(Document document){
    this.document = document;
  }

  public void paintComponent(Graphics graphics){
    TextBox text = new TextBox(0, 0, this.getWidth(), this.getHeight(), false);
    text.add("Items: " + document.getClasses().size());
    text.add("Arrows: " + document.getArrows().size());
    text.add("Methods: " + document.getMethods().size());
    text.paintComponent(graphics);
  }
}