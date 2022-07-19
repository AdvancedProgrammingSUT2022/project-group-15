package view;

import enums.Technology;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import model.Game;

public class TechnologyNode extends Button {
    Technology technology;

    public TechnologyNode(Technology technology) {
        this.technology = technology;
        this.setLayoutX(technology.x);
        this.setLayoutY(technology.y);
        this.setBackground(Background.EMPTY);
        this.setPrefWidth(260);
        this.setPrefHeight(60);
        updateNode();
    }

    public void updateNode() {
        this.setDisable(true);
        if (technology == Game.getGame().getSelectedCivilization().getTechnologyInProgress()) {
            this.setStyle("-fx-border-color: blue");
        } else if (Game.getGame().getSelectedCivilization().getTechnologies().contains(technology)) {
            this.setStyle("-fx-border-color: gold");
        } else if (Game.getGame().getSelectedCivilization().getAvailableTechnologies().contains(technology)) {
            this.setStyle("-fx-border-color: green");
            setDisable(false);
        }
    }
}
