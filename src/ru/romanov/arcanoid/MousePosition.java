/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.romanov.arcanoid;

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

}
