package app.mycycle;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by carloconnor on 16/02/17.
 */

public class HaversineDistanceCalculatorTest {

    @Test
    public void testDistanceCalculator() {
        DistanceCalculator distanceCalculator = new HaversineDistanceCalculator();
        assertEquals(2887.2599506071106*1000, distanceCalculator.calculateDistance(36.12, -86.67, 33.94, -118.40), 0);
    }

    @Test
    public void testLongLatInverted() {
        DistanceCalculator distanceCalculator = new HaversineDistanceCalculator();
        //assertEquals(2887.2599506071106, distanceCalculator.calculateDistance(-86.67, 36.12, -118.40, 33.94), 0);
    }

}
