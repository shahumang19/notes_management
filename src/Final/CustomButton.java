/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Final;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import javax.swing.JButton;

/**
 *
 * @author admin1
 */
public class CustomButton extends JButton{

    int x,y,width,height,id;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
    String label;
    
    public CustomButton(String name,String label , int x,int y,int width,int height) {
        super(name);
        this.setName(name);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.label=label;
        Dimension size = getPreferredSize();
        setPreferredSize(size);
        setContentAreaFilled(false);
    }

    @Override
    public void paint(Graphics g) {
        
        super.paint(g); //To change body of generated methods, choose Tools | Templates.
        
               
    }

    @Override
    protected void paintComponent(Graphics g) {
       // g.setColor(new Color(1,185,225));
       
        
        //g.fillRoundRect(0, 0,350,100, 30,20);
        super.paintComponent(g); //To change body of generated methods, choose Tools | Templates.
        
        
    }

    @Override
    protected void paintBorder(Graphics g) {
        super.paintBorder(g);//To change body of generated methods, choose Tools | Templates.
         g.setColor(new Color(1,185,225));
         g.fillRoundRect(0, 0,width,height, 30,20);
         g.setColor(Color.white);
         g.setFont(new Font(Font.SERIF,Font.BOLD, 30));
        g.drawString(label, 15, 30);
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
