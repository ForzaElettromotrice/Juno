package org.juno.controller;

import javafx.fxml.FXML;
import javafx.stage.Stage;

/**
 * Defines: startMenu, class
 *
 * @author R0n3l, ForzaElettromotrice
 */
public class startMenu {

    @FXML
    public javafx.scene.control.Button exitButton;
    @FXML
    public void exitClicked()
    {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
}
