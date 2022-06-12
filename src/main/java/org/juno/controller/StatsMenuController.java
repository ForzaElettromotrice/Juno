package org.juno.controller;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import org.juno.view.NonexistingSceneException;
import org.juno.view.StatsMenuView;

/**
 * Defines: StatsMenuController, class
 *
 * @author R0n3l, ForzaElettromotrice
 */
public class StatsMenuController
{
    private static final StatsMenuView STATS_MENU_VIEW = StatsMenuView.getINSTANCE();

    @FXML
    public void menuClicked() throws NonexistingSceneException
    {
        STATS_MENU_VIEW.changeScene(0);
    }

    public void changeEntered(MouseEvent mouseEvent) {
    }

    public void changeExited(MouseEvent mouseEvent) {
    }

    public void backEntered(MouseEvent mouseEvent) {
    }

    public void backExited(MouseEvent mouseEvent) {
    }

    public void backClicked(MouseEvent mouseEvent) {
    }
}
