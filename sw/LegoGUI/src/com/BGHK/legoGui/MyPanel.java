/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.BGHK.legoGui;

import com.BGHK.elements.Block;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class MyPanel extends JPanel implements MouseWheelListener, MouseMotionListener, MouseListener {

    private Image image;
    public static int status;
    public static Block dragged = null;
    private double prevScale=1;
    Point coordinates;

    public MyPanel() {
        super();
        addMouseWheelListener(this);
        addMouseMotionListener(this);
        addMouseListener(this);
        this.setBackground(new Color(0x535555));
        try {
            image = ImageIO.read(new File("img.jpg"));
            //  this.setLayout(null);

        } catch (IOException ex) {
            // handle exception...
        }
        

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //prepareFrame
        prepareFrame(g);

    }

    private void prepareFrame(Graphics g) {
        Dimension size = this.getSize();
        removeAll();
        for (Block b : Window.blockList) {
            add(b);
        }

        // getAllInstances to draw on frame

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent mwe) {
        int mouseRotationDirection = mwe.getWheelRotation();
        Dimension dim = getSize();
        if (mouseRotationDirection > 0) {
            Window.scale -= 0.0625;
            System.out.println(Window.scale);
            if (Window.scale < 0.0625) {
                Window.scale = 0.0625;
            }
            
            for (Block b : Window.blockList){
            // Transpose
                b.x = ((b.x-dim.width/2)/prevScale*Window.scale+dim.width/2);
                b.y = ((b.y-dim.width/2)/prevScale*Window.scale+dim.width/2);
                
               // b.setBounds((int)b.x, (int)b.y, (int) (100*Window.scale), (int) (100*Window.scale));
                b.refresh();
            }
            
            
        } else {
            Window.scale += 0.0625;
            if (Window.scale > 2) {
                Window.scale = 2;
            }
            for (Block b : Window.blockList){
            // Transpose
                b.x = ((b.x-dim.width/2)/prevScale*Window.scale+dim.width/2);
                b.y = ((b.y-dim.width/2)/prevScale*Window.scale+dim.width/2);
                
               // b.setBounds((int)b.x, (int)b.y, (int) (100*Window.scale), (int) (100*Window.scale));
                b.refresh();
            }
            
            System.out.println(Window.scale);
        }
        
        
        
        
        prevScale=Window.scale;


    }
    public static void setDragged (Block draggedObject){
        dragged=draggedObject;
    }

    @Override
    public void mouseDragged(MouseEvent me) {
      System.out.println("dragged");
        for(Block b : Window.blockList){
            try{
            int tempX = (int) b.x+(me.getX()-coordinates.x);
            int tempY = (int) b.y+(me.getY()-coordinates.y);
            b.setBounds(tempX,tempY,(int)(b.sizeX*Window.scale),(int)(b.sizeY*Window.scale));
           // b.refresh();
            } catch (NullPointerException np){
            System.out.println("No blocks to move.");
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent me) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseClicked(MouseEvent me) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mousePressed(MouseEvent me) {
       coordinates =  me.getPoint();
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        // Store coordinates in object's variable.
        for (Block b : Window.blockList){
                Rectangle temp = b.getBounds();
                b.x = temp.x;
                b.y = temp.y;
            }

    }

    @Override
    public void mouseEntered(MouseEvent me) {
        System.out.println("test");
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent me) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    

}
