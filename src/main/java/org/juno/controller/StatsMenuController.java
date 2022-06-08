package org.juno.controller;

import javafx.fxml.FXML;
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
    public void menuClicked()
    {
        STATS_MENU_VIEW.changeScene(0);
    }
}
