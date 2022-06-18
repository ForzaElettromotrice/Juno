package org.juno.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.juno.view.GenView;
import org.juno.view.NonexistingSceneException;

/**
 * Defines: ChooseModeController, class
 *
 * @author R0n3l, ForzaElettromotrice
 */
public class ChooseModeController
{
    private static final GenView GEN_VIEW = GenView.getINSTANCE();

    @FXML
    public Label classicLabel;
    @FXML
    public Label reflexLabel;
    @FXML
    public Label tradeLabel;
    @FXML
    public Button back;
    @FXML
    public AnchorPane modeAnchor;

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

    @FXML
    public void backClicked() throws NonexistingSceneException {
        GEN_VIEW.changeScene(0, modeAnchor);
    }

    @FXML
    public void backExited()
    {
        back.setStyle("-fx-background-color:transparent;");
    }

    public void backEntered()
    {
        back.setStyle("-fx-background-color:transparent; -fx-border-color:BLACK; -fx-border-radius:90; -fx-border-width:3;");
    }
}
