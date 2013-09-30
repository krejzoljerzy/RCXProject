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
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author johns
 */
public class Root extends Block {

    JLabel title;
    JLabel power;
    JLabel time;
    int motorNumber = 1;
    int powerVal = 100;
    int timeVal = 2;
    int paramNum = 3;
    String titleStr = "";

    public Root(Color clr, String name) {

        super(clr);
        sizeX = 100;
        sizeY = 150;
        addMouseListener(this);
        Window.myPanel1.add(this);
        Window.myPanel1.repaint();
        titleStr = name;
        title = new JLabel(titleStr, JLabel.CENTER);
        title.setFont(new Font("Tahoma", Font.BOLD, 23));
        title.setText(titleStr);

        this.setLayout(new GridLayout(paramNum, 0));
        this.add(title);
        this.add(power);
        this.add(time);
        this.validate();

    }

    private void resizeText() {
        title.setFont(new Font("Tahoma", Font.BOLD, (int) (23 * Window.scale)));
        this.validate();
    }

    @Override
    public void mousePressed(MouseEvent me) {
        super.mousePressed(me);

        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void refresh() {
        resizeText();
        title.setText(titleStr);
        this.validate();
    }
}
