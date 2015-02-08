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

    private double platformMaxSpeed = 35;
    private double platformSpeedModificator = 0.1;

    private final Rectangle platform;
    private double currentSpeed = 0;

    public ArcanPlatform(Rectangle platform) {
        this.platform = platform;

    }

    public double getPlatformCenter() {
        return getPlatformLeft() + platform.getWidth() / 2;
    }

    public double getPlatformBottom() {
        return getPlatformTop() + platform.getHeight();
    }

    public double getPlatformTop() {
        return platform.getLayoutY();
    }

    public double getPlatformLeft() {
        return platform.getLayoutX();
    }

    public double getPlatformRight() {
        return getPlatformLeft() + platform.getWidth();
    }

    public double getCurrentSpeed() {
        return currentSpeed;
    }

    public Rectangle getPlatform() {
        return platform;
    }

    public void movePlatform(MousePosition mousePosition) {
        if (!mousePosition.isMouseOut()) {
            double platformCenterX = platform.getLayoutX() + platform.getWidth() / 2;
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
            platform.setLayoutX(platform.getLayoutX() + this.currentSpeed);

        }
    }

    private double countPlatformSpeed(double distanceBetweenCenterAndMouse) {
        double platformSpeed = distanceBetweenCenterAndMouse * platformSpeedModificator;
        if (platformSpeed > platformMaxSpeed) {
            platformSpeed = platformMaxSpeed;
        }
        return platformSpeed;
    }

    public void changePosition(double newX, double newY) {
        platform.setLayoutX(newX);
        platform.setLayoutY(newY);
    }

}
