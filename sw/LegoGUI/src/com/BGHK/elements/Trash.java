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
public class Trash extends Block {

    JLabel title = new JLabel();
    String titleStr;
    Font white = new Font("Calibri", Font.BOLD , 23);

    public Trash(Color clr, String name, int privX, int privY) {
        super(clr,privX,privY);
        setTextColor(Color.white);
        x=privX;
        y=privY;
        setOpaque(false);
        title.setFont(new Font("Calibri",Font.BOLD,23));
        title.setText("Delete");
        add(title);
        //removeMouseListener(this);
        //removeMouseMotionListener(this);
        //Window.blockList.remove(this);
        this.validate();
        this.setVisible(false);

    }

    private void resizeText() {
        //title.setFont(new Font ("Tahoma", Font.BOLD, (int)(23*Window.scale)));
        this.validate();
    }   

    @Override
    public void refresh(){
        //resizeText();
       // title.setText(titleStr);
        setBounds((int)x,(int)y,(int)(sizeX*Window.scale), (int)(sizeY*Window.scale));
        this.validate();
    }
    
    @Override
    public void mouseDragged(MouseEvent me) {

    }
    
    public void setTextColor(Color clr){
        title.setForeground(clr);
    }
    
    

    


    
    
    
}
