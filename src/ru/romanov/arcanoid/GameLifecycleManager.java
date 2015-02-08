/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.romanov.arcanoid;

import ru.romanov.arcanoid.util.MousePosition;
import ru.romanov.arcanoid.elements.Ball;
import javafx.animation.AnimationTimer;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import ru.romanov.arcanoid.elements.ArcanPlatform;
import ru.romanov.arcanoid.util.GameStatus;

/**
 *
 * @author romanov
 */
public class GameLifecycleManager extends AnimationTimer {

    private static final int SCREEN_UPDATE_TIME = 1_000_000_000 / 60;

    private final ArcanPlatform platform;
    private final Ball ball;
    private final AnchorPane gameArea;
    private final GameEventListener eventListener;
    private final Label scoreLabel;

    private final double ballYSpeed = 10;

    private long lastPhase;
    private int score;
    public final MousePosition mousePosition = new MousePosition();

    private GameStatus status;

    public GameLifecycleManager(Rectangle rectangle, Circle circle, AnchorPane gameArea, Label scoreLabel, GameEventListener eventListener) {
        this.platform = new ArcanPlatform(rectangle);
        this.ball = new Ball(circle, ballYSpeed);
        this.gameArea = gameArea;
        this.scoreLabel = scoreLabel;
        this.eventListener = eventListener;
        prepareNewGame();
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

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    private void processCollisions() {
        if (platform.getPlatformLeft() < 0) {
            platform.changePosition(0, platform.getPlatformTop());
        }
        if (platform.getPlatformRight() > gameArea.getWidth()) {
            platform.changePosition(gameArea.getWidth() - platform.getPlatform().getWidth(), platform.getPlatformTop());
        }
        if (ball.getBallBottom() >= platform.getPlatformTop()) {
            if (platform.getPlatformLeft() < ball.getBallRight()
                    && platform.getPlatformRight() > ball.getBallLeft()) {
                ball.changePosition(ball.getBallLayoutX(), platform.getPlatformTop() - ball.getBallDiameter());
                ball.revertSpeedY();
                double platfCenter = platform.getPlatformCenter();
                double ballCenter = ball.getBallCenterX();
                double xSpeedUp = platform.getCurrentSpeed() * 2 / 3;
                ball.speedUpX(xSpeedUp);

                score += 10;
                scoreLabel.setText(Integer.toString(score));

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

    public final void prepareNewGame() {
        javafx.application.Platform.runLater(new Runnable() {

            @Override
            public void run() {
                ball.changePosition(gameArea.getWidth() / 2, 0);
                ball.setBallSpeedX(0);
                platform.changePosition(0, platform.getPlatformTop());
                score = 0;
                scoreLabel.setText(Integer.toString(score));

                gameArea.getScene().setOnKeyPressed(ke -> {
                    if (ke.getCode() == KeyCode.SPACE) {
                        switch (status) {
                            case PAUSED:
                                eventListener.startGame();
                                break;
                            case STARTED:
                                eventListener.stopGame();
                        }
                    }
                });
                gameArea.getScene().setOnMouseClicked(mc -> {
                     if (mc.getButton() == MouseButton.PRIMARY) {
                        switch (status) {
                            case PAUSED:
                                eventListener.startGame();
                                break;
                            case STARTED:
                                eventListener.stopGame();
                        }
                    }
                });
            }
        });
    }

}
