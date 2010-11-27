package uk.ac.cam.md481.tick0;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.RenderingHints;
import java.awt.geom.*;
import java.awt.Stroke;
import java.awt.BasicStroke;

public class Arrow {
  private float thickness = (float) 1.5;
  private int x1, x2, y1, y2;

  private ClassBox source;
  private ClassBox destination;

  public ClassBox getSource(){
    return this.source;
  }

  public void setSource(ClassBox left){
    this.source = left;
  }

  public ClassBox getDestination(){
    return this.destination;
  }

  public void setDestination(ClassBox right){
    this.destination = right;
  }

  public void setThickness(float thickness){
    this.thickness = thickness;
  }

  public void paintComponent(Graphics graphics){
    Graphics2D g = (Graphics2D) graphics;
    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                       RenderingHints.VALUE_ANTIALIAS_ON);

    this.calculate();

    Stroke stroke = g.getStroke();
    g.setStroke(new BasicStroke(this.thickness));
    g.draw(shape());
    g.setStroke(stroke);
  }

  private void calculate(){
    this.x1 = this.source.getX() + this.source.getWidth() / 2;
    this.y1 = this.source.getY() + this.source.getHeight() / 2;
    this.x2 = this.destination.getX() + this.destination.getWidth() / 2;
    this.y2 = this.destination.getY();
  }

  private Shape shape(){
    AffineTransform transformation = AffineTransform.getRotateInstance(-Math.atan((double)(this.x2-this.x1) / (double)(this.y2 - this.y1)), this.x1, this.y1);
    return transformation.createTransformedShape(path());
  }

  private Path2D.Double path() {
    double angle = Math.toRadians(30);
    Path2D.Double path = new Path2D.Double();

    int length = (int) Math.sqrt((double)((Math.pow(this.x2 - this.x1, 2) + Math.pow(this.y1 - this.y2, 2))));
    int sign = y2 - y1 < 0 ? -1 : 1;

    path.moveTo(this.x1, this.y1);
    path.lineTo(this.x1, this.y1 + sign * length);

    path.lineTo(this.x1 - sign * 15 * Math.sin(angle), this.y1 + sign * (length - 15 * Math.cos(angle)));
    path.moveTo(this.x1, this.y1 + sign * length);
    path.lineTo(this.x1 + sign * 15 * Math.sin(angle), this.y1 + sign * (length - 15 * Math.cos(angle)));

    return path;
  }
}