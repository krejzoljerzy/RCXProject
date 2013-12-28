/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.BGHK.elements;

import com.BGHK.legoGui.Window;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author johns
 */
public class Root extends Block {

    JLabel title;
    String titleStr;

    public Root(Color clr, String name, int privX, int privY) {
        super(clr,privX,privY);
        x=privX;
        y=privY;
        //removeMouseListener(this);
        //removeMouseMotionListener(this);
        titleStr = name;
        title = new JLabel(name, JLabel.CENTER);
        title.setFont(new Font ("Tahoma", Font.BOLD, 23));
        this.add(title);
        this.validate();

    }

    private void resizeText() {
        title.setFont(new Font ("Tahoma", Font.BOLD, (int)(23*Window.scale)));
        this.validate();
    }   

    @Override
    public void refresh(){
        resizeText();
        title.setText(titleStr);
        setBounds((int)x,(int)y,(int)(sizeX*Window.scale), (int)(sizeY*Window.scale));
        this.validate();
    }
    
    @Override
    public void mouseDragged(MouseEvent me) { 
    }
    
    

    


    
    
    
}
