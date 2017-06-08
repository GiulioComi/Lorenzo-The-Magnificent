package it.polimi.ingsw.LM34.UI.GUI;


import it.polimi.ingsw.LM34.Enums.Controller.LeaderCardsAction;
import it.polimi.ingsw.LM34.Enums.Controller.PlayerSelectableContexts;
import it.polimi.ingsw.LM34.Enums.UI.NetworkType;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.Market;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.Tower;
import it.polimi.ingsw.LM34.Model.Player;
import it.polimi.ingsw.LM34.UI.GUI.GuiViews.CurchReportDialog;
import it.polimi.ingsw.LM34.UI.GUI.GuiViews.NetworkTypeDialog;
import it.polimi.ingsw.LM34.UI.GUI.GuiViews.UseServantsDialog;
import it.polimi.ingsw.LM34.UI.UIInterface;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vladc on 6/6/2017.
 */
public class GUI extends Application implements UIInterface {

    @Override
    public void show() {
        launch(null);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("gui/gui.fxml"));
        prepareWindow(primaryStage, root);
        //servantsSelection(5,1);
        curchReportDecision(4,2);
        //leaderCardAction(primaryStage); //TODO
        addPlayersInfo(root);
        connectionTypeSelection();


        primaryStage.show();

    }

    private void leaderCardAction(Stage primaryStage) {
        try {
            Parent root2 = FXMLLoader.load(getClass().getClassLoader().getResource("gui/leaderCardAction.fxml"));
            primaryStage.setScene(new Scene(
                    root2, 500, 400));
            primaryStage.show();

        }
        catch (IOException e) {
            e.printStackTrace();
        }



    }

    private void addPlayersInfo(Parent root) {
        ScrollPane scrollPane = (ScrollPane) root.lookup("#scrollPanePaneResource");
        VBox content = new VBox();
        scrollPane.setContent(content);
        // Just to see that the lines are actually added
        scrollPane.setPrefWidth(200);

        for (Integer i = 1; i < 5; i++) {
            ImageView imageView = new ImageView();
            imageView.setImage(new Image(Thread.currentThread().getContextClassLoader().getResource("images/icon.png").toExternalForm()));
            imageView.minHeight(200);
            imageView.minWidth(250);
            imageView.setOnMouseEntered(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent me) {
                    System.out.println("Mouse entered");
                }
            });
            //scrollPane.getRowConstraints().add(new RowConstraints(30));
            content.getChildren().add(imageView);
        }
    }

    private void prepareWindow(Stage primaryStage, Parent root) {
        primaryStage.getIcons().add(new Image(Thread.currentThread().getContextClassLoader().getResource("images/icon.png").toExternalForm()));
        primaryStage.setTitle("Lorenzo il Magnifico by CranioCreations");
        primaryStage.setScene(new Scene(root, primaryStage.getMaxWidth(), primaryStage.getMaxHeight()));
        primaryStage.setFullScreen(true);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    @Override
    public void loginMenu() {

    }

    @Override
    public void loginResult(Boolean result) {

    }

    @Override
    public NetworkType connectionTypeSelection() {
        NetworkTypeDialog dialog = new NetworkTypeDialog();
        return dialog.interactWithPlayer();
    }

    @Override
    public Integer contextSelection(List<PlayerSelectableContexts> allContext) {
        return null;
    }

    @Override
    public String towerSlotSelection(Integer towerNumber, Integer towerFloor) {
        return null;
    }

    @Override
    public Integer servantsSelection(Integer servantsAvailable, Integer minimumServantsRequested) {
        UseServantsDialog dialog = new UseServantsDialog();
        return dialog.interactWithPlayer(servantsAvailable, minimumServantsRequested);
    }


    public Integer curchReportDecision(Integer servantsAvailable, Integer minimumServantsRequested) {
        CurchReportDialog dialog = new CurchReportDialog();
        return dialog.interactWithPlayer(servantsAvailable, minimumServantsRequested);
    }

    @Override
    public Integer leaderCardAction(List<String> playerLeaderCards, LeaderCardsAction action) {
        return null;
    }

    @Override
    public Integer marketSlotSelection(Market market) {
        return null;
    }

    @Override
    public void workingArea(String workingAreaChoice, Player player) {

    }

    @Override
    public void printTowers(ArrayList<Tower> towers) {

    }

    @Override
    public void printGameBoard() {

    }


    public static void main(String [] args) {
        GUI gui = new GUI();
        gui.show();
    }
}
