package view;

import controller.Controller;
import controller.ScoreBoardController;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ScoreBoard extends Menu implements Initializable {
    private final ScoreBoardController controller = new ScoreBoardController();

    @FXML private TableView<User> scoreboard;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<User> listOfUser = FXCollections.observableArrayList();
        controller.loadSortedPlayers(listOfUser);
        TableColumn<User, Integer> rankingColumn = new TableColumn<>("Rank");
        rankingColumn.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(scoreboard.getItems().indexOf(p.getValue()) + 1));
        rankingColumn.setPrefWidth(110);

        TableColumn<User, String> usernameColumn = new TableColumn<>("Player");
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        usernameColumn.setPrefWidth(350);

        TableColumn<User, Integer> scoreColumn = new TableColumn<>("Score");
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));
        scoreColumn.setPrefWidth(120);

        scoreboard.setItems(listOfUser);
        scoreboard.getColumns().addAll(rankingColumn, usernameColumn, scoreColumn);

        for (TableColumn<User, ?> column : scoreboard.getColumns()) {
            column.setSortable(false);
        }
    }

    @Override
    public Scene getScene() {
        if (scene == null) {
            try {
                AnchorPane root = FXMLLoader.load(new URL(this.getClass().getResource("/fxml/scoreBoard.fxml").toExternalForm()));
                scene = new Scene(root);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return scene;
    }

    public void openMainMenu() {
        setup(scoreboard);
        window.setScene(Controller.getMainMenu().getScene());
        Controller.getScoreBoard().setScene(null);
    }
}
