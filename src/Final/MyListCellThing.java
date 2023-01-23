/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Final;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.List;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 *
 * @author admin1
 */
public class MyListCellThing extends JLabel implements ListCellRenderer {

    public MyListCellThing() {
        setOpaque(true);
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        // Assumes the stuff in the list has a pretty toString  
        setBounds(0, 0,300,200 );
        setForeground(Color.WHITE);
        setText("18:00  "+value.toString());

        // based on the index you set the color.  This produces the every other effect.
        if (index % 2 == 0) setBackground(new Color(1,185,225));
        else setBackground(new Color(3,20,38));
        
        return this;
    }    
}