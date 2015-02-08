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
public class Platform {

    private double platformMaxSpeed = 20;
    private double platformSpeedModificator = 0.1;

    private final Rectangle platform;
    private double currentSpeed = 0;

    public Platform(Rectangle platform) {
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
                platform.setLayoutX(platform.getLayoutX() - this.currentSpeed);
            } else if (platformCenterX < mousePosition.getX()) {
                platform.setLayoutX(platform.getLayoutX() + this.currentSpeed);
            }
        }
    }

    private double countPlatformSpeed(double distanceBetweenCenterAndMouse) {
        double platformSpeed = distanceBetweenCenterAndMouse * platformSpeedModificator;
        if (platformSpeed > platformMaxSpeed) {
            platformSpeed = platformMaxSpeed;
        }
        return platformSpeed;
    }

}
