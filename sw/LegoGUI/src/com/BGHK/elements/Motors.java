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
public class Motors extends Block {

    JLabel title;
    JLabel power;
    JLabel time;
    int motorNumber=1;
    int powerVal=100;
    int timeVal=2;
    int paramNum =3;

    String titleStr = "Silnik " + Integer.toString(motorNumber);
    String param1Str = Integer.toString(powerVal)+ " %";
    String param2Str = Integer.toString(timeVal)+ "s";

    public Motors(Color clr, int number) {
        super(clr);
        param1Str = Integer.toString (id);
        param2Str = Integer.toString(parentId);
        addMouseListener(this);
        Window.myPanel1.add(this);
        Window.myPanel1.repaint();
        motorNumber=number;
        title = new JLabel(titleStr, JLabel.CENTER);
        title.setFont(new Font ("Tahoma", Font.BOLD, 23));
        power = new JLabel(param1Str, JLabel.CENTER);
        power.setFont(new Font ("Tahoma", Font.PLAIN, 20));
        time = new JLabel(param2Str, JLabel.CENTER);
        time.setFont(new Font ("Tahoma", Font.PLAIN, 20));
        //motorNumber = new JLabel(Integer.toString(number), JLabel.CENTER);

        this.setLayout(new GridLayout(paramNum,0));
        this.add(title);
        this.add(power);
        this.add(time);
        this.validate();

    }

    private void resizeText() {
        title.setFont(new Font ("Tahoma", Font.BOLD, (int)(23*Window.scale)));
        power.setFont(new Font ("Tahoma", Font.PLAIN, (int)(20*Window.scale)));
        time.setFont(new Font ("Tahoma", Font.PLAIN, (int)(20*Window.scale)));
        this.validate();
    }
    
    @Override
    public void mousePressed(MouseEvent me) {
        super.mousePressed(me);
      
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    public void refresh(){
        resizeText();
        titleStr = "Silnik " + Integer.toString(motorNumber);
        param1Str = Integer.toString (id);
        param2Str = Integer.toString(parentId);
        title.setText(titleStr);
        power.setText(param1Str);
        time.setText(param2Str);
        setBounds((int)x, (int)y, (int) (sizeX*Window.scale), (int) (sizeY*Window.scale));
        this.validate();
    }
    
    

    


    
    
    
}
