package Final;


import javax.swing.JButton;
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author admin1
 */
public class button extends JButton{
    public button(String label) {
    super(label);
    Dimension size = getPreferredSize();
    size.width = size.height = Math.max(size.width,size.height);
    setPreferredSize(size);

    setContentAreaFilled(false);
  }

    @Override
  protected void paintComponent(Graphics g) {
    if (getModel().isArmed()) {
      g.setColor(Color.lightGray);
    } else {
      g.setColor(Color.BLACK);
    }
   
    
    
    g.fillOval(0, 0, getSize().width-1,getSize().height-1);
     //g.drawImage(new ImageIcon("C:\\Users\\admin1\\Downloads\\menu.png").getImage(),4 ,0 , this);
    // g.drawRect(0, 0, 20, 20);
    super.paintComponent(g);
  }

    @Override
  protected void paintBorder(Graphics g) {
    g.setColor(getForeground());
    g.drawOval(0, 0, getSize().width-1,     getSize().height-1);
  }

  Shape shape;
  public boolean contains(int x, int y) {
    if (shape == null || 
      !shape.getBounds().equals(getBounds())) {
      shape = new Ellipse2D.Float(0, 0, getWidth(), getHeight());
    }
    return shape.contains(x, y);
  }

  
}
