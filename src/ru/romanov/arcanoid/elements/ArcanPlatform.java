/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.romanov.arcanoid.elements;

import javafx.scene.shape.Rectangle;
import ru.romanov.arcanoid.util.MousePosition;

/**
 *
 * @author romanov
 */
public class ArcanPlatform {

    private final double platformMaxSpeed = 35;
    private final double platformSpeedModificator = 0.1;

    private final Rectangle shape;
    private double currentSpeed = 0;

    public ArcanPlatform(Rectangle platform) {
        this.shape = platform;

    }

    public double getPlatformCenter() {
        return getPlatformLeft() + shape.getWidth() / 2;
    }

    public double getPlatformBottom() {
        return getPlatformTop() + shape.getHeight();
    }

    public double getPlatformTop() {
        return shape.getLayoutY();
    }

    public double getPlatformLeft() {
        return shape.getLayoutX();
    }

    public double getPlatformRight() {
        return getPlatformLeft() + shape.getWidth();
    }

    public double getCurrentSpeed() {
        return currentSpeed;
    }

    public Rectangle getShape() {
        return shape;
    }

    public void movePlatform(MousePosition mousePosition) {
        if (!mousePosition.isMouseOut()) {
            double platformCenterX = shape.getLayoutX() + shape.getWidth() / 2;
            double distance = Math.abs(platformCenterX - mousePosition.getX());
            double platformSpeed = countPlatformSpeed(distance);
            if (distance > platformSpeed) {
                this.currentSpeed = platformSpeed;
            } else {
                this.currentSpeed = distance;
            }
            if (platformCenterX > mousePosition.getX()) {
                currentSpeed = -currentSpeed;
            }
            shape.setLayoutX(shape.getLayoutX() + this.currentSpeed);

        }
    }

    public void movePlatform(double speed) {
        if (Math.abs(speed) > platformMaxSpeed) {
            speed = Math.signum(speed) * platformMaxSpeed;
        }
        this.currentSpeed = speed;
        shape.setLayoutX(shape.getLayoutX() + this.currentSpeed);
    }

    private double countPlatformSpeed(double distanceBetweenCenterAndMouse) {
        double platformSpeed = distanceBetweenCenterAndMouse * platformSpeedModificator;
        if (platformSpeed > platformMaxSpeed) {
            platformSpeed = platformMaxSpeed;
        }
        return platformSpeed;
    }

    public void changePosition(double newX, double newY) {
        shape.setLayoutX(newX);
        shape.setLayoutY(newY);
    }

}
