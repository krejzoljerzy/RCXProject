/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.BGHK.elements;

import com.BGHK.legoGui.Window;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

/**
 *
 * @author johns
 */
public class Block extends JPanel implements MouseMotionListener, MouseListener {

    public String test = "test";
    public int x = 100;
    public int y = 100;
    public int sizeX = 100;
    public int sizeY = 100;
    private int globalPositionX;
    private int globalPositionY;
    private boolean dragged = false;

    public Block() {
        super();
        addMouseListener(this);
        addMouseMotionListener(this);
        setBackground(Color.yellow);
        setVisible(true);
        Window.blockList.add(this);
        setBounds(x, y, (int) (sizeX*Window.scale) , (int) (sizeY*Window.scale));


    }

    @Override
    public void mouseClicked(MouseEvent me) {
        
        
        
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mousePressed(MouseEvent me) {
        Window.blockList.remove(this);
        Window.blockList.add(0,this);
        
        globalPositionX = me.getXOnScreen();
        globalPositionY = me.getYOnScreen();
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        x = this.getBounds().x;
        y = this.getBounds().y;
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        setBackground(Color.red);
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent me) {
        setBackground(Color.yellow);
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseDragged(MouseEvent me) {

            setBounds(x + (me.getXOnScreen() - globalPositionX), y + (me.getYOnScreen() - globalPositionY), (int) (100*Window.scale), (int) (100*Window.scale));

    }

    @Override
    public void mouseMoved(MouseEvent me) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}