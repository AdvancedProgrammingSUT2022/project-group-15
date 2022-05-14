package model;

import enums.HexVisibility;
import junit.framework.TestCase;

public class MapTest extends TestCase {

    public void testCloneIfHexesAreCloned() {

        Map originalMap = new Map(2,2);
        originalMap.fillMap();
        Map newMap = originalMap.clone();
        newMap.map.get(0).get(0).setHexVisibility(HexVisibility.TRANSPARENT);
        assertEquals(originalMap.map.get(0).get(0).getHexVisibility() , HexVisibility.FOG_OF_WAR);
    }

}