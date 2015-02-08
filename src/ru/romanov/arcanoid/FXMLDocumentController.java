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
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author romanov
 */
public class FXMLDocumentController implements Initializable, GameEventListener {

    private static final int SCREEN_UPDATE_TIME = 1_000_000_000 / 60;

    @FXML
    Rectangle arcan;
    @FXML
    Circle ball;
    @FXML
    Label infoLabel;
    @FXML
    AnchorPane gameArea;
    
    private GameLifecycleManager gameLifecycleManager;




    @Override
    public void initialize(URL url, ResourceBundle rb) {
        gameLifecycleManager = new GameLifecycleManager(arcan, ball, gameArea, this);
    }

    @Override
    public void startGame() {
        System.out.println("Game started!");
        gameLifecycleManager.start();
        infoLabel.setVisible(false);
    }

    @Override
    public void stopGame() {
        System.out.println("Game stopped!");
        gameLifecycleManager.stop();
        infoLabel.setVisible(true);
    }

    @Override
    public void looseGame() {
        stopGame();
        infoLabel.setText("U LOSE");
    }

    public void onMouseMoveEvent(MouseEvent mouseEvent) {
        gameLifecycleManager.mousePosition.set(mouseEvent.getSceneX(), mouseEvent.getSceneY());
    }

}
