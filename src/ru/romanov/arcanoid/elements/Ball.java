/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.romanov.arcanoid.elements;

import javafx.scene.shape.Circle;

/**
 *
 * @author romanov and enot =)
 */
public class Ball {

    private final double ballDiameter;
    private double ballBottom;
    private double ballTop;
    private double ballLeft;
    private double ballRight;
    private double ballSpeedY;
    private double ballSpeedX;
    private final double ballMaxSpeedX;

    private final Circle ball;

    public Ball(Circle ball, double ySpeed) {
        this.ball = ball;
        ballDiameter = ball.getRadius() * 2;
        ballSpeedY = ySpeed;
        ballMaxSpeedX = ySpeed * 2;
        initParams();
    }

    public double getDiameter() {
        return ballDiameter;
    }

    public double getShapeBottom() {
        return ballBottom;
    }

    public double getShapeTop() {
        return ballTop;
    }

    public double getShapeLeft() {
        return ballLeft;
    }

    public double getShapeRight() {
        return ballRight;
    }

    public Circle getShape() {
        return ball;
    }

    public double getShapeLayoutX() {
        return ball.getLayoutX();
    }

    public double getShapeLayoutY() {
        return ball.getLayoutY();
    }

    public double getShapeCenterX() {
        return getShapeLeft() + ball.getRadius();
    }

    public double getBallSpeedY() {
        return ballSpeedY;
    }

    public double getBallSpeedX() {
        return ballSpeedX;
    }

    public void setBallSpeedX(double ballSpeedX) {
        this.ballSpeedX = ballSpeedX;
    }

    public void move() {
        ballLeft += ballSpeedX;
        ballRight += ballSpeedX;
        ball.setLayoutX(ballLeft);
        ballTop += ballSpeedY;
        ballBottom += ballSpeedY;
        ball.setLayoutX(ballLeft);
        ball.setLayoutY(ballTop);
    }

    public void changePosition(double newX, double newY) {
        ball.setLayoutX(newX);
        ball.setLayoutY(newY);
        initParams();
    }

    public void revertSpeedY() {
        ballSpeedY = -ballSpeedY;
    }

    public void revertSpeedX() {
        ballSpeedX = -ballSpeedX;
    }

    /**
     * Добавить speedUp к текущей скорости по Х
     *
     * @param speedUp > 0 - вправо, < 0 - влево
     */
    public void speedUpX(double speedUp) {
        ballSpeedX += speedUp;
        double moduleSpeedX = Math.abs(ballSpeedX);
        if (moduleSpeedX > ballMaxSpeedX) {
            ballSpeedX = (ballSpeedX / moduleSpeedX) * ballMaxSpeedX;
        }
    }

    private void initParams() {
        ballBottom = ball.getLayoutY() + ballDiameter;
        ballTop = ball.getLayoutY();
        ballLeft = ball.getLayoutX();
        ballRight = ball.getLayoutX() + ballDiameter;
    }

}
