package client.view;

import client.controller.Controller;
import client.enums.Technology;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;

public class TechnologyNode extends Button {
    Technology technology;

    public TechnologyNode(Technology technology) {
        super();
        this.technology = technology;
        this.setPrefHeight(60);
        this.setPrefWidth(260);
        this.setLayoutX(technology.x);
        this.setLayoutY(technology.y);
        updateNode();
    }

    public void updateNode() {
        int remainingTurns = (int) Math.ceil((this.technology.cost - (double) Controller.send("getScience"))
                / (double) Controller.send("getSciencePerTurn"));
        this.setTooltip(new Tooltip(this.technology.toString() + "\n" + remainingTurns + " Turns"));
        if (technology.name == Technology.valueOf((String) Controller.send("getTechnologyInProgress")).name) {
            this.setStyle("-fx-border-color: blue");
        } else if ((boolean) Controller.send("hasTechnology", this.technology.name)) {
            this.setStyle("-fx-border-color: gold");
        } else if ((boolean) Controller.send("isAvailableTechnology", this.technology.name)) {
            this.setStyle("-fx-border-color: green");
        } else {
            this.setStyle("-fx-border-color: gray; -fx-cursor: default;");
        }
    }

    /**
     * handles mouse click on this technology node
     * @return true if technologyInProgress changed
     * @author parsa
     */
    public boolean handleClick() {
        if ((boolean) Controller.send("isAvailableTechnology", this.technology.name)) {
            Controller.send("buyNewTechnology", this.technology.name);
            return true;
        }
        return false;
    }
}
