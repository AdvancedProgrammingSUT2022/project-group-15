package enums;

import java.util.ArrayList;

/**
 * @author Parsa
 */
public enum Technology {
    AGRICULTURE("agriculture", 20, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    // TODO : implement
    public final String name;
    public final int cost;
    public final ArrayList<Resource> openingResources;
    public final ArrayList<Feature> openingFeatures;
    public final ArrayList<UnitName> openedUnits;
    public final ArrayList<Technology> prerequisiteTechnologies;

    Technology(String name, int cost, ArrayList<Resource> openingResources, ArrayList<Feature> openingFeatures, ArrayList<UnitName> openedUnits, ArrayList<Technology> prerequisiteTechnologies) {
        this.name = name;
        this.cost = cost;
        this.openingResources = openingResources;
        this.openingFeatures = openingFeatures;
        this.openedUnits = openedUnits;
        this.prerequisiteTechnologies = prerequisiteTechnologies;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
