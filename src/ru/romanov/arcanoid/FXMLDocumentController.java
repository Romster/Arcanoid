/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.romanov.arcanoid;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import ru.romanov.arcanoid.util.GameStatus;

/**
 *
 * @author romanov
 */
public class FXMLDocumentController implements Initializable, GameEventListener {

    private static final int SCREEN_UPDATE_TIME = 1_000_000_000 / 60;

    @FXML
    Rectangle bottonRect;
    @FXML
    Rectangle topRect;
    @FXML
    Circle ball;

    @FXML
    AnchorPane gameArea;

    @FXML
    Label infoLabel;
    @FXML
    Label scoreLabel;
    @FXML
    Button replayButton;

    private GameLifecycleManager gameLifecycleManager;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        gameLifecycleManager = new GameLifecycleManager(bottonRect, topRect, ball, gameArea, scoreLabel, this);
        replayButton.setOnMouseClicked(event -> {
            firstInitialization();
            startGame();
        });
        replayButton.setVisible(false);
        infoLabel.setText("START");
        gameLifecycleManager.setStatus(GameStatus.PAUSED);
    }

    @Override
    public void startGame() {
        gameLifecycleManager.start();
        infoLabel.setVisible(false);
        replayButton.setVisible(false);
        gameLifecycleManager.setStatus(GameStatus.STARTED);
    }

    @Override
    public void stopGame() {
        gameLifecycleManager.stop();
        infoLabel.setVisible(true);
        infoLabel.setText("PAUSED");
        gameLifecycleManager.setStatus(GameStatus.PAUSED);
    }

    @Override
    public void looseGame() {
        stopGame();
        infoLabel.setRotate(0);
        infoLabel.setText("U LOSE");
        replayButton.setVisible(true);
        gameLifecycleManager.setStatus(GameStatus.LOSED);
    }

    public void onMouseMoveEvent(MouseEvent mouseEvent) {
        gameLifecycleManager.mousePosition.set(mouseEvent.getSceneX(), mouseEvent.getSceneY());
    }

    private void firstInitialization() {
        gameLifecycleManager.prepareNewGame();
        infoLabel.setText("Pause");

    }

}
