/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.romanov.arcanoid;

/**
 *
 * @author romanov
 */
public interface GameEventListener {

    public void startGame();

    public void stopGame();

    public void looseGame();
}
