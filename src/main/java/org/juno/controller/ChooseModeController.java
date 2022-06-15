package org.juno.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * Defines: ChooseModeController, class
 *
 * @author R0n3l, ForzaElettromotrice
 */
public class ChooseModeController
{
    @FXML
    public Label classicLabel;
    @FXML
    public Label reflexLabel;
    @FXML
    public Label tradeLabel;

    @FXML
    public void classicClicked(ActionEvent actionEvent)
    {

    }

    @FXML
    public void classicEntered()
    {
        classicLabel.setVisible(true);
    }

    @FXML
    public void classicExited()
    {
        classicLabel.setVisible(false);
    }

    @FXML
    public void tradeClicked()
    {

    }

    @FXML
    public void tradeEntered()
    {
        tradeLabel.setVisible(true);
    }

    @FXML
    public void tradeExited()
    {
        tradeLabel.setVisible(false);
    }

    @FXML
    public void reflexClicked()
    {

    }

    @FXML
    public void reflexEntered()
    {
        reflexLabel.setVisible(true);
    }

    @FXML
    public void reflexExited()
    {
        reflexLabel.setVisible(false);
    }
}
