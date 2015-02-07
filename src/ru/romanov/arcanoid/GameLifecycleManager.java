/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.romanov.arcanoid;

import ru.romanov.arcanoid.elements.Ball;
import javafx.animation.AnimationTimer;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author romanov
 */
public class GameLifecycleManager extends AnimationTimer {

    private static final int SCREEN_UPDATE_TIME = 1_000_000_000 / 60;

    private final Rectangle platform;
    private final Ball ball;
    private final AnchorPane gameArea;
    private final GameEventListener eventListener;

    private double ballYSpeed = 2;
    private double ballXSpeed = 0;
    
    private double platformMaxSpeed = 20;
    private double platformSpeedModificator = 0.1;

    private long lastPhase;
    public final MousePosition mousePosition = new MousePosition();

    public GameLifecycleManager(Rectangle paltform, Circle circle, AnchorPane gameArea, GameEventListener eventListener) {
        this.platform = paltform;
        this.ball = new Ball(circle);
        this.gameArea = gameArea;
        this.eventListener = eventListener;
    }

    @Override
    public void handle(long now) {
        if (now - lastPhase > SCREEN_UPDATE_TIME) {
            movePlatform();
            moveBall();
            lastPhase = now;
        }
    }

    private void moveBall() {
        ball.move(ballXSpeed, ballYSpeed);
        if (ball.getBallBottom() >= platform.getLayoutY()) {
            double platformLeft = platform.getLayoutX();
            double platformRight = platform.getLayoutX() + platform.getWidth();
            double platformTop = platform.getLayoutY();

            if (platformLeft < ball.getBallRight()
                    && platformRight > ball.getBallLeft()) {
                ball.changePosition(ball.getBallLayoutX(), platformTop - ball.getBallDiameter());
                ballYSpeed = -ballYSpeed;
            }
        }

        if (ball.getBallTop() < 0) {
            ballYSpeed = -ballYSpeed;
            ball.changePosition(ball.getBallLayoutX(), 0);
        }

        if (ball.getBallBottom() >= gameArea.getHeight()) {
            eventListener.looseGame();
        }
    }

    private void movePlatform() {
        if (!mousePosition.isMouseOut()) {
            double platformCenterX = platform.getLayoutX() + platform.getWidth()/ 2;
            double distance = Math.abs(platformCenterX - mousePosition.getX());
            double platformSpeed = countPlatformSpeed(distance);
            if (distance > platformSpeed) {
                distance = platformSpeed;
            }
            if (platformCenterX > mousePosition.getX()) {
                platform.setLayoutX(platform.getLayoutX() - distance);
            } else if (platformCenterX < mousePosition.getX()) {
                platform.setLayoutX(platform.getLayoutX() + distance);
            }
        }
    }
    
    private double countPlatformSpeed(double distanceBetweenCenterAndMouse) {
        double platformSpeed = distanceBetweenCenterAndMouse * platformSpeedModificator;
        if(platformSpeed > platformMaxSpeed) {
            platformSpeed = platformMaxSpeed;
        }
        return platformSpeed;
    }

}
