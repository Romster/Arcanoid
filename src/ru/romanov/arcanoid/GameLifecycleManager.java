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

    private final ArcanPlatform bottomPlatform;
    private final ArcanPlatform topPlatform;
    private final Ball ball;
    private final AnchorPane gameArea;
    private final GameEventListener eventListener;
    private final Label scoreLabel;

    private final double ballYSpeed = 10;

    private long lastPhase;
    private int score;
    public final MousePosition mousePosition = new MousePosition();

    private GameStatus status;

    public GameLifecycleManager(Rectangle rectangleBottom, Rectangle rectangleTop,
            Circle circle,
            AnchorPane gameArea, Label scoreLabel,
            GameEventListener eventListener) {
        this.bottomPlatform = new ArcanPlatform(rectangleBottom);
        this.topPlatform = new ArcanPlatform(rectangleTop);
        this.ball = new Ball(circle, ballYSpeed);
        this.gameArea = gameArea;
        this.scoreLabel = scoreLabel;
        this.eventListener = eventListener;
        prepareNewGame();
    }

    @Override
    public void handle(long now) {
        if (now - lastPhase > SCREEN_UPDATE_TIME) {
            bottomPlatform.movePlatform(mousePosition);
            topPlatform.movePlatform(-bottomPlatform.getCurrentSpeed());
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
        if (bottomPlatform.getPlatformLeft() < 0) {
            bottomPlatform.changePosition(0, bottomPlatform.getPlatformTop());
        }
        if (bottomPlatform.getPlatformRight() > gameArea.getWidth()) {
            bottomPlatform.changePosition(gameArea.getWidth() - bottomPlatform.getShape().getWidth(), bottomPlatform.getPlatformTop());
        }

        if (topPlatform.getPlatformLeft() < 0) {
            topPlatform.changePosition(0, topPlatform.getPlatformTop());
        }
        if (topPlatform.getPlatformRight() > gameArea.getWidth()) {
            topPlatform.changePosition(gameArea.getWidth() - topPlatform.getShape().getWidth(), topPlatform.getPlatformTop());
        }

        processBallAndPlatformsCollision();

        if (ball.getShapeTop() <= 0) {
            eventListener.looseGame();
//            ball.revertSpeedY();
//            ball.changePosition(ball.getShapeLayoutX(), 1);
        }

        if (ball.getShapeBottom() >= gameArea.getHeight()) {
            eventListener.looseGame();
        }

        if (ball.getShapeLeft() <= 0) {
            ball.revertSpeedX();
            ball.changePosition(1, ball.getShapeLayoutY());
        }

        if (ball.getShapeRight() >= gameArea.getWidth()) {
            ball.revertSpeedX();
            ball.changePosition(gameArea.getWidth() - ball.getDiameter() - 1, ball.getShapeLayoutY());
        }
    }

    private void processBallAndPlatformsCollision() {
        if (ball.getShapeBottom() >= bottomPlatform.getPlatformTop()) {
            if (bottomPlatform.getPlatformLeft() < ball.getShapeRight()
                    && bottomPlatform.getPlatformRight() > ball.getShapeLeft()) {
                ball.changePosition(ball.getShapeLayoutX(), bottomPlatform.getPlatformTop() - ball.getDiameter());
                ball.revertSpeedY();
                double xSpeedUp = bottomPlatform.getCurrentSpeed() * 2 / 3;
                ball.speedUpX(xSpeedUp);
                score += 10;
                scoreLabel.setText(Integer.toString(score));

            }
        } else if (ball.getShapeTop() <= topPlatform.getPlatformBottom()) {
            if (topPlatform.getPlatformLeft() < ball.getShapeRight()
                    && topPlatform.getPlatformRight() > ball.getShapeLeft()) {
                ball.changePosition(ball.getShapeLayoutX(), topPlatform.getPlatformBottom());
                ball.revertSpeedY();
                double xSpeedUp = topPlatform.getCurrentSpeed() * 2 / 3;
                ball.speedUpX(xSpeedUp);
                score += 10;
                scoreLabel.setText(Integer.toString(score));
            }
        }
    }

    public final void prepareNewGame() {
        javafx.application.Platform.runLater(new Runnable() {

            @Override
            public void run() {
                ball.changePosition(gameArea.getWidth() / 2, gameArea.getHeight() / 3);
                ball.setBallSpeedX(0);
                bottomPlatform.changePosition(0, bottomPlatform.getPlatformTop());
                bottomPlatform.getShape().setVisible(true);
                topPlatform.changePosition(
                        gameArea.getLayoutX() + gameArea.getWidth() - topPlatform.getShape().getWidth(),
                        topPlatform.getPlatformTop());
                topPlatform.getShape().setVisible(true);
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
