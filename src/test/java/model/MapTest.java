package model;

import enums.HexVisibility;
import enums.Resource;
import junit.framework.TestCase;
import org.mockito.Mock;

import java.util.ArrayList;

public class MapTest extends TestCase {

    public void testCloneIfHexesAreCloned() {

        Map originalMap = new Map(2,2);
        originalMap.fillMap();
        Map newMap = originalMap.clone();
        newMap.map.get(0).get(0).setHexVisibility(HexVisibility.TRANSPARENT);
        assertEquals(originalMap.map.get(0).get(0).getHexVisibility() , HexVisibility.FOG_OF_WAR);
    }

}