package view;

import enums.Technology;
import javafx.scene.control.Button;

public class TechnologyNode extends Button {
    Technology technology;

    public TechnologyNode(Technology technology) {
        this.technology = technology;
    }
}
