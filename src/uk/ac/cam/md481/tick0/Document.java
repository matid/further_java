package uk.ac.cam.md481.tick0;

import java.util.List;
import java.util.LinkedList;
import java.util.Iterator;
import java.awt.Graphics;

public class Document {
  protected List<ClassBox> classes;
  protected List<Arrow> arrows;

  public Document(){
    this.classes = new LinkedList<ClassBox>();
    this.arrows = new LinkedList<Arrow>();
  }

  public List<ClassBox> getClasses(){
    return this.classes;
  }

  public void addClass(ClassBox klass){
    this.classes.add(klass);
  }

  public List<Arrow> getArrows(){
    return this.arrows;
  }

  public void addArrow(Arrow arrow){
    this.arrows.add(arrow);
  }

  public List<String> getMethods(){
    List<String> methods = new LinkedList<String>();
    for(ClassBox klass: getClasses()){
      methods.addAll(klass.getMethods());
    }
    return methods;
  }

  public ClassBox find(int x, int y){
    Iterator iterator = ((LinkedList) this.getClasses()).descendingIterator();
    while(iterator.hasNext()){
      ClassBox klass = (ClassBox) iterator.next();
      if((klass.getX() < x  && x < (klass.getX() + klass.getWidth())) &&
         (klass.getY() < y && y < (klass.getY() + klass.getHeight()))){
        return klass;
      }
    }
    return null;
  }

  public void paintComponent(Graphics graphics){
    for(Arrow arrow: this.getArrows()){
      arrow.paintComponent(graphics);
    }

    for(ClassBox klass: this.getClasses()){
      klass.paintComponent(graphics);
    }
  }
}