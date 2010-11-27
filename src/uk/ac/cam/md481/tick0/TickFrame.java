package uk.ac.cam.md481.tick0;

import javax.swing.*;
import java.awt.*;
import java.awt.print.*;
import java.awt.event.*;

public class TickFrame extends javax.swing.JFrame {
  private Document document;
  
  public TickFrame() {
    super("TickFrame");
    Container cp = getContentPane();

    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent event) {
        quitApplication();
      }
    });

    MainPanel main_panel = new MainPanel();
        
    this.document = new Document();
    main_panel.setDocument(this.document);
        
    main_panel.setBorder(new javax.swing.border.LineBorder(Color.BLACK));
    main_panel.setPreferredSize(new Dimension(400, 300));
    main_panel.addMouseMotionListener(new MouseMotionAdapter() {
      public void mouseDragged(MouseEvent event) {
        mainPanelMouseDragged(event);
      }
    });
    main_panel.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent event) {
        mainPanelMouseClicked(event);
      }
    });
    main_panel.addMouseListener(new MouseAdapter(){
      public void mousePressed(MouseEvent event){
        mainPanelMousePressed(event);
      }
    });
    main_panel.addMouseListener(new MouseAdapter(){
      public void mouseReleased(MouseEvent event){
        mainPanelMouseReleased(event);
      }
    });
    cp.add(main_panel, BorderLayout.CENTER);

    JPanel button_panel = new JPanel();
    button_panel.setBorder(new javax.swing.border.LineBorder(Color.BLACK));
    
    Box buttons = Box.createVerticalBox();
    
    JButton add_class_button = new JButton("Add Class");
    add_class_button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        addClassButtonActionPerformed(event);
      }
    });
    
    buttons.add(add_class_button);
    
    JButton add_arrow_button = new JButton("Add Arrow");
    add_arrow_button.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent event){
        addArrowButtonActionPerformed(event);
      }
    });
    
    buttons.add(add_arrow_button);
    
    button_panel.add(buttons);
    
    cp.add(button_panel, BorderLayout.WEST);
    
    InfoPanel info_panel = new InfoPanel();
    info_panel.setBorder(new javax.swing.border.LineBorder(Color.BLACK));
    info_panel.setPreferredSize(new Dimension(200, 300));
    info_panel.setDocument(document);
    
    cp.add(info_panel, BorderLayout.EAST);

    JPanel status_panel = new JPanel();
    status_panel.setBorder(new javax.swing.border.LineBorder(Color.BLACK));
    status_panel.setPreferredSize(new Dimension(10, 50));
    cp.add(status_panel, BorderLayout.SOUTH);

    JMenuBar menu_bar = new JMenuBar();
    JMenu file_menu = new JMenu("File");
    JMenuItem print_menu_item = new JMenuItem ("Print");
    JMenuItem exit_menu_item = new JMenuItem ("Exit");
    print_menu_item.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        printDisplay();
      }
    });
    exit_menu_item.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        quitApplication();
      }
    });

    file_menu.add(print_menu_item);
    file_menu.add(exit_menu_item);
    menu_bar.add(file_menu);
    setJMenuBar(menu_bar);

    pack();
  }
  
  ClassBox activeBox;
  int startX, startY;
  Arrow activeArrow;
  boolean dragging, adding = false;
  
  private ClassBox findActiveBox(MouseEvent event){
    this.activeBox = this.document.find(event.getX(), event.getY());
    return this.activeBox;
  }
  
  private void addClassButtonActionPerformed(ActionEvent event) {
    String name = JOptionPane.showInputDialog("Class name:");
    
    ClassBox klass = new ClassBox(name);
    this.document.addClass(klass);
    
    this.repaint();
  }
  
  private void addArrowButtonActionPerformed(ActionEvent event){
    this.adding = true;
    this.activeArrow = new Arrow();
  }

  private void mainPanelMouseDragged(MouseEvent event) {
    if(dragging){
      this.activeBox.move(event.getX() - this.startX, event.getY() - this.startY);
      this.repaint();
    }
  }
  
  private void mainPanelMousePressed(MouseEvent event){  
    if(!adding && this.findActiveBox(event) != null){    
      this.dragging = true;
      this.startX = event.getX() - this.activeBox.getX();
      this.startY = event.getY() - this.activeBox.getY();
    }
  }
  
  private void mainPanelMouseReleased(MouseEvent event){
    this.dragging = false;
    this.activeBox = null;
  }

  private void mainPanelMouseClicked(MouseEvent event){
    switch(event.getClickCount()){
      case 1:
        if(adding){
          if(this.findActiveBox(event) != null){
            if(this.activeArrow.getSource() == null){
              this.activeArrow.setSource(this.activeBox);
            } else {
              this.activeArrow.setDestination(this.activeBox);
              this.document.addArrow(this.activeArrow);
              this.adding = false;
              this.activeArrow = null;
              this.repaint();
            }
          }
        }
        break;
      case 2:
        if(this.findActiveBox(event) != null){
          String name = JOptionPane.showInputDialog("Method name:");
          this.activeBox.addMethod(name);
          this.repaint();
        }
        break;
    } 
  }
  
  private void printDisplay() {
    PrinterJob printJob = PrinterJob.getPrinterJob();
    printJob.setPrintable(new Printable () {
      public int print(Graphics g, PageFormat pf, int pageIndex) {
        if (pageIndex > 0) return Printable.NO_SUCH_PAGE;
        Graphics2D g2d = (Graphics2D)g;
        g2d.translate(pf.getImageableX(), pf.getImageableY());
        paint(g2d);
        return Printable.PAGE_EXISTS;
      }
    });
    
    if (printJob.printDialog())
      try { 
        printJob.print();
      } catch(PrinterException pe) {
        System.out.println("Error printing: " + pe);
      }
  }
      
  private void quitApplication() {
    System.exit(0);
  }

  public static void main(String args[]) {
    TickFrame tf = new TickFrame();
    tf.setVisible(true);
  }
}
