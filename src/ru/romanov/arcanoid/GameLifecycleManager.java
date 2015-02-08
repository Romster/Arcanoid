/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.romanov.arcanoid;

import ru.romanov.arcanoid.util.MousePosition;
import ru.romanov.arcanoid.elements.Ball;
import javafx.animation.AnimationTimer;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import ru.romanov.arcanoid.elements.Platform;

/**
 *
 * @author romanov
 */
public class GameLifecycleManager extends AnimationTimer {

    private static final int SCREEN_UPDATE_TIME = 1_000_000_000 / 60;

    private final Platform platform;
    private final Ball ball;
    private final AnchorPane gameArea;
    private final GameEventListener eventListener;

    private final double ballYSpeed = 10;

    private long lastPhase;
    public final MousePosition mousePosition = new MousePosition();

    public GameLifecycleManager(Rectangle rectangle, Circle circle, AnchorPane gameArea, GameEventListener eventListener) {
        this.platform = new Platform(rectangle);
        this.ball = new Ball(circle, ballYSpeed);
        this.gameArea = gameArea;
        this.eventListener = eventListener;
    }

    @Override
    public void handle(long now) {
        if (now - lastPhase > SCREEN_UPDATE_TIME) {
            platform.movePlatform(mousePosition);
            ball.move();
            processCollisions();

            lastPhase = now;
        }
    }

    private void processCollisions() {
        if (ball.getBallBottom() >= platform.getPlatformTop()) {
            if (platform.getPlatformLeft() < ball.getBallRight()
                    && platform.getPlatformRight() > ball.getBallLeft()) {
                ball.changePosition(ball.getBallLayoutX(), platform.getPlatformTop() - ball.getBallDiameter());
                ball.revertSpeedY();
                double platfCenter = platform.getPlatformCenter();
                double ballCenter = ball.getBallCenterX();

                double xSpeedUp = ball.getBallCenterX() - platform.getPlatformCenter();
                ball.speedUpX(xSpeedUp);

            }
        }

        if (ball.getBallTop() <= 0) {
            ball.revertSpeedY();
            ball.changePosition(ball.getBallLayoutX(), 1);
        }

        if (ball.getBallBottom() >= gameArea.getHeight()) {
            eventListener.looseGame();
        }

        if (ball.getBallLeft() <= 0) {
            ball.revertSpeedX();
            ball.changePosition(1, ball.getBallLayoutY());
        }

        if (ball.getBallRight() >= gameArea.getWidth()) {
            ball.revertSpeedX();
            ball.changePosition(gameArea.getWidth() - ball.getBallDiameter() - 1, ball.getBallLayoutY());
        }
    }

}
