package org.juno.controller;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.juno.datapackage.*;
import org.juno.model.deck.Card;
import org.juno.model.deck.WildCard;
import org.juno.model.table.Player;
import org.juno.model.table.Table;
import org.juno.model.table.TurnOrder;
import org.juno.view.AudioPlayer;
import org.juno.view.GenView;
import org.juno.view.NonexistingSceneException;
import org.juno.view.NotExistingSoundException;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.atomic.AtomicBoolean;


/**
 * Defines: GameplayController, class
 *
 * @author R0n3l, ForzaElettromotrice
 */
public class GameplayController
{

    @FXML
    public AnchorPane anchorPane;

     @FXML
     public AudioPlayer AUDIO_PLAYER = AudioPlayer.getINSTANCE();

    @FXML
    public Button pass;
    @FXML
    public GridPane colorGrid;
    @FXML
    public Button juno;


    @FXML
    public HBox userHand;
    @FXML
    public HBox botHand1;
    @FXML
    public HBox botHand2;
    @FXML
    public HBox botHand3;

    @FXML
    public Circle circle1;
    @FXML
    public Circle circle2;
    @FXML
    public Circle circle3;
    @FXML
    public Circle circle4;

    @FXML
    public ImageView topDrawCard;
    @FXML
    public ImageView middleDrawCard;
    @FXML
    public ImageView bottomDrawCard;
    @FXML
    public ImageView cardDiscarded;

    private static final int CARD_WIDTH_SCALED = 189;
    private static final int CARD_HEIGHT_SCALED = 264;

    public void fixWidth(HBox box)
    {

        double space = (box.getChildren().size() * CARD_WIDTH_SCALED) - (box.getMaxWidth());
        space = space / box.getChildren().size();

        if (space < 0) space = 0;

        box.setSpacing(-space);


    }

    @FXML
    public void middleReached()
    {
        topDrawCard.setVisible(false);
    }

    @FXML
    public void quarterReached()
    {
        middleDrawCard.setVisible(false);
    }

    public void draw(DrawData drawData)
    {
        try {
            AUDIO_PLAYER.play(AudioPlayer.Sounds.CARDFLIP);
        } catch (NotExistingSoundException e) {
            throw new RuntimeException(e);
        }
        switch (drawData.player())
        {
            case PLAYER ->
            {
                ImageView iv = new ImageView(new Image(String.format("file:\\%s\\src\\main\\resources\\org\\juno\\images\\%s%d.png", System.getProperty("user.dir"), drawData.color().toString(), drawData.value().getVal())));
                iv.setFitWidth(CARD_WIDTH_SCALED);
                iv.setFitHeight(CARD_HEIGHT_SCALED);

                iv.setOnMouseClicked(this::cardClicked);
                iv.setOnMouseEntered(this::cardEntered);
                iv.setOnMouseExited(this::cardExited);

                userHand.getChildren().add(iv);
                fixWidth(userHand);
            }
            case BOT1 -> drawBot(botHand1);
            case BOT2 -> drawBot(botHand2);
            case BOT3 -> drawBot(botHand3);
        }
    }

    private void cardExited(MouseEvent mouseEvent)
    {
        if (TurnOrder.getINSTANCE().getCurrentPlayer().getID()== BuildMP.PG.PLAYER)
        {
            ImageView card = (ImageView) mouseEvent.getSource();
            card.setTranslateY(30);
        }
    }

    private void cardEntered(MouseEvent mouseEvent)
    {
        if (TurnOrder.getINSTANCE().getCurrentPlayer().getID()== BuildMP.PG.PLAYER)
        {
            try {
                AUDIO_PLAYER.play(AudioPlayer.Sounds.CARDFLIP);
            } catch (NotExistingSoundException e) {
                throw new RuntimeException(e);
            }

            ImageView card = (ImageView) mouseEvent.getSource();
            card.setTranslateY(-30);
        }
    }

    private void cardClicked(MouseEvent mouseEvent)
    {
        ImageView imageView = (ImageView) mouseEvent.getSource();
        String name = null;
        try
        {
            name = new File(new URI(imageView.getImage().getUrl())).getName();
        } catch (URISyntaxException e)
        {
            e.printStackTrace();
        }

        Card.Color color = switch (name.charAt(0))
                {
                    case 'r' -> Card.Color.RED;
                    case 'b' -> Card.Color.BLUE;
                    case 'g' -> Card.Color.GREEN;
                    case 'y' -> Card.Color.YELLOW;
                    default -> Card.Color.BLACK;
                };


        Card.Value value = color == Card.Color.BLACK ? switch (name.substring(0, 2))
                {
                    case "13" -> Card.Value.JOLLY;
                    default -> Card.Value.PLUSFOUR;
                } : switch (name.substring(1, 3))
                {
                    case "0." -> Card.Value.ZERO;
                    case "1." -> Card.Value.ONE;
                    case "2." -> Card.Value.TWO;
                    case "3." -> Card.Value.THREE;
                    case "4." -> Card.Value.FOUR;
                    case "5." -> Card.Value.FIVE;
                    case "6." -> Card.Value.SIX;
                    case "7." -> Card.Value.SEVEN;
                    case "8." -> Card.Value.EIGHT;
                    case "9." -> Card.Value.NINE;
                    case "10" -> Card.Value.PLUSTWO;
                    case "11" -> Card.Value.REVERSE;
                    default -> Card.Value.STOP;
                };

        try
        {
            TurnOrder.getINSTANCE().getCurrentPlayer().chooseCard(color, value);
        } catch (MessagePackageTypeNotExistsException e)
        {
            throw new RuntimeException(e);
        }
        if (color == Card.Color.BLACK && TurnOrder.getINSTANCE().getCurrentPlayer().getID() == BuildMP.PG.PLAYER)
            colorGrid.setVisible(true);

    }


    private void drawBot(HBox botHand)
    {
        ImageView iv = new ImageView(new Image(String.format("file:\\%s\\src\\main\\resources\\org\\juno\\images\\back.png", System.getProperty("user.dir"))));
        iv.setFitWidth(CARD_WIDTH_SCALED);
        iv.setFitHeight(CARD_HEIGHT_SCALED);
        botHand.getChildren().add(iv);
        fixWidth(botHand);
    }

    public void discard(DiscardData discardData)
    {
        try {
            AUDIO_PLAYER.play(AudioPlayer.Sounds.ALERTBEEP);
        } catch (NotExistingSoundException e) {
            throw new RuntimeException(e);
        }
        if (discardData.player() == null)
        {
            cardDiscarded.setImage(new Image(String.format("file:\\%s\\src\\main\\resources\\org\\juno\\images\\%s%d.png", System.getProperty("user.dir"), discardData.color().toString(), discardData.value().getVal())));
            return;
        }

        switch (discardData.player())

        {
            case PLAYER ->
            {
                String name = String.format("%s%d.png", discardData.color().toString(), discardData.value().getVal());
                for (Node node : userHand.getChildren())
                {
                    if (node instanceof ImageView iV && iV.getImage().getUrl().endsWith(name))
                    {
                        userHand.getChildren().remove(node);
                        break;
                    }
                }
                fixWidth(userHand);
            }
            case BOT1 ->
            {
                botHand1.getChildren().remove(0);
                fixWidth(botHand1);
            }
            case BOT2 ->
            {
                botHand2.getChildren().remove(0);
                fixWidth(botHand2);
            }
            case BOT3 ->
            {
                botHand3.getChildren().remove(0);
                fixWidth(botHand3);
            }
        }
        cardDiscarded.setImage(new Image(String.format("file:\\%s\\src\\main\\resources\\org\\juno\\images\\%s%d.png", System.getProperty("user.dir"), discardData.color().toString(), discardData.value().getVal())));
    }

    public void turn(TurnData turnData)
    {
        pass.setDisable(true);
        circle1.setFill(Color.WHITE);
        circle2.setFill(Color.WHITE);
        circle3.setFill(Color.WHITE);
        circle4.setFill(Color.WHITE);
        switch (turnData.player())
        {
            case PLAYER -> circle1.setFill(Color.YELLOW);
            case BOT1 -> circle2.setFill(Color.YELLOW);
            case BOT2 -> circle3.setFill(Color.YELLOW);
            case BOT3 -> circle4.setFill(Color.YELLOW);
        }
    }

    public void effect(EffectData effectData) throws NonexistingSceneException
    {
        switch (effectData.effect())
        {
            case ONECARD -> juno.setVisible(true);
            case RESETMATCH -> nextMatch();
            case ENDGAME -> goEndgame();
            case DIDNTSAYUNO -> juno.setVisible(false);
        }
    }

    @FXML
    public void drawClicked() throws MessagePackageTypeNotExistsException
    {
        Player player = TurnOrder.getINSTANCE().getCurrentPlayer();

        if (player.getID() == BuildMP.PG.PLAYER)
        {
            player.draw();
            pass.setDisable(false);
        }

    }

    @FXML
    public void passClicked()
    {
        try {
            AUDIO_PLAYER.play(AudioPlayer.Sounds.ALERTBEEP);
        } catch (NotExistingSoundException e) {
            throw new RuntimeException(e);
        }
        Player player = TurnOrder.getINSTANCE().getCurrentPlayer();
        player.setHasPassed();
        pass.setDisable(true);
    }

    @FXML
    public void redClicked()
    {
        try {
            AUDIO_PLAYER.play(AudioPlayer.Sounds.ALERTBEEP);
        } catch (NotExistingSoundException e) {
            throw new RuntimeException(e);
        }
        ((WildCard) TurnOrder.getINSTANCE().getCurrentPlayer().getChosenCard()).setColor(Card.Color.RED);
        colorGrid.setVisible(false);
    }

    @FXML
    public void blueClicked()
    {
        try {
            AUDIO_PLAYER.play(AudioPlayer.Sounds.ALERTBEEP);
        } catch (NotExistingSoundException e) {
            throw new RuntimeException(e);
        }
        ((WildCard) TurnOrder.getINSTANCE().getCurrentPlayer().getChosenCard()).setColor(Card.Color.BLUE);
        colorGrid.setVisible(false);
    }

    @FXML
    public void greenClicked()
    {
        try {
            AUDIO_PLAYER.play(AudioPlayer.Sounds.ALERTBEEP);
        } catch (NotExistingSoundException e) {
            throw new RuntimeException(e);
        }
        ((WildCard) TurnOrder.getINSTANCE().getCurrentPlayer().getChosenCard()).setColor(Card.Color.GREEN);
        colorGrid.setVisible(false);
    }

    @FXML
    public void yellowClicked()
    {
        try {
            AUDIO_PLAYER.play(AudioPlayer.Sounds.ALERTBEEP);
        } catch (NotExistingSoundException e) {
            throw new RuntimeException(e);
        }
        ((WildCard) TurnOrder.getINSTANCE().getCurrentPlayer().getChosenCard()).setColor(Card.Color.YELLOW);
        colorGrid.setVisible(false);
    }

    @FXML
    public void sayUno() throws MessagePackageTypeNotExistsException
    {
        TurnOrder.getINSTANCE().getCurrentPlayer().sayUno();
        juno.setVisible(false);
    }

    public void nextMatch()
    {
        Stage win = new Stage();
        win.initModality(Modality.APPLICATION_MODAL);
        win.setTitle("End-match points");
        win.setMinHeight(500);
        win.setMinWidth(500);
        win.setResizable(false);
        win.setOnCloseRequest(x->
        {
            win.close();
            reset();
        });

        Label label;

        VBox layout = new VBox();
        for (Player player : TurnOrder.getINSTANCE().getPlayers())
        {
            label = new Label(String.format("\t%s:\t\t\t%d/500", player.getID(), player.getPoints()));
            label.setPrefSize(500, 100);
            label.setStyle("-fx-border-color: BLACK");
            label.setFont(new Font(30));
            layout.getChildren().add(label);
        }

        Button next = new Button("Next match");
        Button exit = new Button("Exit game");
        next.setMinSize(250, 100);
        next.setStyle("-fx-background-color: transparent; -fx-border-color: RED; -fx-border-width: 3");
        next.setOnAction(x ->
        {
            try {
                AUDIO_PLAYER.play(AudioPlayer.Sounds.BUTTONCLICK);
            } catch (NotExistingSoundException e) {
                throw new RuntimeException(e);
            }
            win.close();
            reset();
        });
        exit.setMinSize(250, 100);
        exit.setStyle("-fx-background-color: transparent; -fx-border-color: RED; -fx-border-width: 3");
        exit.setOnAction(x ->
        {
            try {
                AUDIO_PLAYER.play(AudioPlayer.Sounds.BUTTONCLICK);
            } catch (NotExistingSoundException e) {
                throw new RuntimeException(e);
            }
            win.close();
            Table.getINSTANCE().stopEarlier();
            reset();

        });
        next.setFont(new Font(25));
        exit.setFont(new Font(25));
        HBox buttons = new HBox(exit, next);
        buttons.setAlignment(Pos.CENTER);
        layout.getChildren().add(buttons);

        Scene scene = new Scene(layout);
        win.setScene(scene);
        win.showAndWait();
    }

    public void reset()
    {
        userHand.getChildren().clear();
        botHand1.getChildren().clear();
        botHand2.getChildren().clear();
        botHand3.getChildren().clear();
        Table.getINSTANCE().canStart();
    }

    public void goEndgame() throws NonexistingSceneException
    {
        GenView.getINSTANCE().changeScene(GenView.SCENES.ENDGAME, anchorPane);
        ((EndgameController) (GenView.getEndgame().getUserData())).load();
    }
}
