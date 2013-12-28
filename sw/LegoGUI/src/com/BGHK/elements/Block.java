/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.BGHK.elements;

import com.BGHK.legoGui.MyPanel;
import com.BGHK.legoGui.Window;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JLabel;

import javax.swing.JPanel;

/**
 *
 * @author johns
 */
public class Block extends JPanel implements MouseMotionListener, MouseListener {

    public String test = "test";
    public double x = 100;
    public double y = 100;
    public int sizeX = 100;
    public int sizeY = 100;
    public int sizeXSelected = 80;
    public int sizeYSelected = 80;
    public Color color = null;
    private int globalPositionX;
    private int globalPositionY;
    private boolean dragged = false;
    boolean grow = false;
    public Block childBlock = null;
    public Block parentBlock = null;
    public int childCount = 0;
    private boolean wasResized = false;
    int id = -1;
    int parentId = -1;
    private static int enumer = 0;

    public Block(Color clr) {
        super();
        enumer++;
        id = enumer;
        color = clr;
        addMouseListener(this);
        addMouseMotionListener(this);

        setBackground(color);
        setVisible(true);
        Window.blockList.add(0, this);
        setBounds((int)x, (int)y, (int) (sizeX * Window.scale), (int) (sizeY * Window.scale));



    }
    public Block(Color clr, int mX, int mY) {
        super();
        x=mX;
        y=mY;
        enumer++;
        id = enumer;
        color = clr;
        addMouseListener(this);
        addMouseMotionListener(this);

        setBackground(color);
        setVisible(true);
        Window.blockList.add(0, this);
        setBounds((int)x, (int)y, (int) (sizeX * Window.scale), (int) (sizeY * Window.scale));



    }

    @Override
    public void mouseClicked(MouseEvent me) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mousePressed(MouseEvent me) {
        Window.blockList.remove(this);
        Window.blockList.add(0, this);
        
        System.out.println("Pressed");


        globalPositionX = me.getXOnScreen();
        globalPositionY = me.getYOnScreen();

        removeParent();

        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent me) {

        
        //Rectangle rect = this.getBounds();
        Block ancestor = intersectsWith();
        System.out.println("released");
        try {

            if (ancestor!= null){
                ancestor.setChild(this);
            }
        } catch (NullPointerException e) {
            System.out.println("ancestor was null pointered");
        }

        
        x = this.getBounds().x;
        y = this.getBounds().y;
        refresh();
       
        dragged = false;


    }

    @Override
    public void mouseEntered(MouseEvent me) {
        setBackground(color.brighter());
        System.out.println("id: " + id);
        System.out.println("parent id: " + parentId);
        try {
            System.out.println("child id: " + childBlock.id);
        } catch (NullPointerException e) {
        }

    }

    @Override
    public void mouseExited(MouseEvent me) {
        //setBounds(x, y, (int) (sizeX * Window.scale), (int) (sizeY * Window.scale));
        setBackground(color);
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseDragged(MouseEvent me) {
        dragged = true;
        int tempX = (int)x + (me.getXOnScreen() - globalPositionX);
        int tempY = (int)y + (me.getYOnScreen() - globalPositionY);
        int tempWidth = (int) (sizeX * Window.scale);
        int tempHeight = (int) (sizeY * Window.scale);
        setBounds(tempX, tempY, tempWidth, tempHeight);
        Block lastChild = this;

        try {
            while (lastChild.childBlock != null) {
                lastChild = lastChild.childBlock;
                lastChild.x = tempX + 10 * Window.scale + tempWidth;
                lastChild.y = tempY;
                lastChild.setBounds((int)lastChild.x, (int)lastChild.y, (int)(lastChild.sizeX*Window.scale), (int)(lastChild.sizeY*Window.scale));
                //lastChild.refresh();
                tempX = (int) lastChild.x;
                tempY = (int) lastChild.y;
 
            }
        } catch (NullPointerException e) {
        }



        // Check for collisions

    }

    @Override
    public void mouseMoved(MouseEvent me) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void refresh() {
        reposition();

    }

    public void reposition() {
        setBounds((int)x,(int)y, (int) (sizeX*Window.scale), (int) (sizeY*Window.scale));        
    }

    /**
     * Handles childBlock-parentBlock relations. Sets position of blocks.
     *
     */
    public void setChild(Block orphan) {

        if (childBlock == null) {
            childBlock = orphan;
            orphan.parentBlock = this;
            childCount += orphan.childCount + 1;
            orphan.parentId = id;
        } else {
            // Set current childBlock as childBlock of an last childBlock of an orphan.
            Block last = orphan.getLastChild();
            last.childBlock = childBlock;
            childBlock.parentBlock = last;
            last.childCount += childBlock.childCount + 1;
            childBlock.parentId = last.id;

            childBlock = orphan;
            orphan.parentBlock = this;
            childCount += orphan.childCount + 1;
            orphan.parentId = id;
        }
        // Place children in their places.
        Block lastChild = this;
        while (lastChild.childBlock != null) {
            lastChild = lastChild.childBlock;
            int tempX = (int)lastChild.parentBlock.x;
            int tempY = (int)lastChild.parentBlock.y;
            int tempWidth = lastChild.parentBlock.sizeX;
            int tempHeight = lastChild.parentBlock.sizeY;
            lastChild.x = (int) (tempX + 10 * Window.scale + tempWidth*Window.scale);
            lastChild.y = tempY;
            lastChild.setBounds((int)lastChild.x, (int)lastChild.y, (int)(lastChild.sizeX*Window.scale),(int)( lastChild.sizeY*Window.scale));
            lastChild.revalidate();
        }


    }

    /**
     * Iterates through all blocks and selects most intersected.
     *
     * @return Block that intersetcs the most or null when no intersection.
     */
    public Block intersectsWith() {
        Block intersected = null;
        int field = 0;
        Rectangle rect = getBounds();
        int counter = 0;
        for (Block b : Window.blockList) {
            Rectangle temp = b.getBounds();
            if (rect.intersects(temp) && counter != 0) {
                Rectangle tempIntersection = rect.intersection(temp);
                if (tempIntersection.width * tempIntersection.height > field) {
                    field = tempIntersection.width * tempIntersection.height;
                    intersected = b;
                }
            }
            counter++;
        }
        return intersected;
    }

    /**
     * Gets last childBlock of this Block.
     *
     * @return Block last childBlock or this.
     */
    public Block getLastChild() {
        Block lastChild = this;
        if (lastChild.childBlock==null){
            return this;
        }
        while (lastChild.childBlock != null) {
            lastChild = lastChild.childBlock;
        }

        return lastChild;
    }

    /**
     * Removes parentBlock safely.
     */
    public void removeParent() {
        try {
            parentBlock.childCount -= (childCount + 1);
            parentBlock.childBlock = null;
            parentBlock = null;
            parentId = -1;
        } catch (NullPointerException e) {
        }
    }
}