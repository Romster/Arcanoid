/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.romanov.arcanoid.util;

/**
 *
 * @author romanov
 */
public class MousePosition {

    private double x;
    private double y;

    public MousePosition() {
        mouseOut();
    }

    public MousePosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    final public void mouseOut() {
        x = Double.NaN;
        y = Double.NaN;
    }

    public boolean isMouseOut() {
        return Double.isNaN(x) || Double.isNaN(y);
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }
    
    

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void set(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "MousePosition{" + "x=" + x + ", y=" + y + '}';
    }
    
    

}
