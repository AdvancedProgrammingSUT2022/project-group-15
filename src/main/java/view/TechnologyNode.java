package view;

import controller.GameMenuController;
import enums.Technology;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import model.Game;

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
        int remainingTurns = (int) Math.ceil((Game.getGame().getSelectedCivilization().getTechnologyInProgress().cost - Game.getGame().getSelectedCivilization().getScienceStorage())
                / (double) Game.getGame().getSelectedCivilization().getSciencePerTurn());
        this.setTooltip(new Tooltip(this.technology.toString() + "\n" + remainingTurns + " Turns"));
        if (technology == Game.getGame().getSelectedCivilization().getTechnologyInProgress()) {
            this.setStyle("-fx-border-color: blue");
        } else if (Game.getGame().getSelectedCivilization().getTechnologies().contains(technology)) {
            this.setStyle("-fx-border-color: gold");
        } else if (Game.getGame().getSelectedCivilization().getAvailableTechnologies().contains(technology)) {
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
    public boolean handleClick(GameMenuController controller) {
        if (Game.getGame().getSelectedCivilization().getAvailableTechnologies().contains(technology)) {
            controller.buyNewTechnology(this.technology);
            return true;
        }
        return false;
    }
}
