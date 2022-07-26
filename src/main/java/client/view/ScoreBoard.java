package client.view;

import client.controller.Controller;
import client.controller.SocketController;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import client.model.User;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ScoreBoard extends Menu implements Initializable {

    @FXML private TableView<User> scoreboard;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<User> listOfUser = FXCollections.observableArrayList();
        listOfUser.addAll((ArrayList<User>) Controller.send("loadSortedUsers"));
        TableColumn<User, Integer> rankingColumn = new TableColumn<>("Rank");
        rankingColumn.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(scoreboard.getItems().indexOf(p.getValue()) + 1));
        rankingColumn.setPrefWidth(100);

        TableColumn<User, Image> avatarColumn = new TableColumn<>("Avatar");
        avatarColumn.setCellFactory(param -> {
            final ImageView imageview = new ImageView();
            imageview.setFitHeight(100);
            imageview.setFitWidth(100);

            TableCell<User, Image> cell = new TableCell<User, Image>() {
                public void updateItem(Image item, boolean empty) {
                    if (item != null) {
                        imageview.setImage(item);
                    }
                }
            };
            cell.setGraphic(imageview);
            return cell;
        });
        avatarColumn.setCellValueFactory(new PropertyValueFactory<>("avatarImage"));
        avatarColumn.setPrefWidth(100);

        TableColumn<User, String> usernameColumn = new TableColumn<>("Player");
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        usernameColumn.setPrefWidth(260);

        TableColumn<User, Integer> scoreColumn = new TableColumn<>("Score");
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));
        scoreColumn.setPrefWidth(120);

        TableColumn<User, String> lastOnlineTime = new TableColumn<>("Last Online Time");
        lastOnlineTime.setCellValueFactory(new PropertyValueFactory<>("onlineTime"));
        lastOnlineTime.setPrefWidth(300);

        scoreboard.setItems(listOfUser);
        scoreboard.getColumns().addAll(rankingColumn, avatarColumn, usernameColumn, scoreColumn, lastOnlineTime);

        for (TableColumn<User, ?> column : scoreboard.getColumns()) {
            column.setSortable(false);
        }

        scoreboard.setRowFactory(tv -> new TableRow<User>() {
            @Override
            protected void updateItem(User item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && item.getUsername().equals(Controller.getMyUser().getUsername())) {
                    setStyle("-fx-background-color: rgba(209,77,250,0.73);");
                }
            }
        });
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
