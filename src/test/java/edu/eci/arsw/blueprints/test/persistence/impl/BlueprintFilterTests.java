package edu.eci.arsw.blueprints.test.persistence.impl;

import edu.eci.arsw.blueprints.filters.impl.RedundancyFiltering;
import edu.eci.arsw.blueprints.filters.impl.SubsamplingFiltering;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BlueprintFilterTests {

    @Test
    public void testRedundancyFiltering() {
        RedundancyFiltering redundancyFiltering = new RedundancyFiltering();

        Point[] points = {new Point(0, 0), new Point(0, 0), new Point(1, 1), new Point(1, 1)};
        Blueprint blueprint = new Blueprint("author", "test", points);

        Blueprint filteredBlueprint = redundancyFiltering.filter(blueprint);

        assertEquals(2, filteredBlueprint.getPoints().size());
        assertEquals(Arrays.asList(new Point(0, 0), new Point(1, 1)), filteredBlueprint.getPoints());
    }

    @Test
    public void testSubsamplingFiltering() {
        SubsamplingFiltering subsamplingFiltering = new SubsamplingFiltering();

        Point[] points = {new Point(0, 0), new Point(1, 1), new Point(2, 2), new Point(3, 3)};
        Blueprint blueprint = new Blueprint("author", "test", points);

        Blueprint filteredBlueprint = subsamplingFiltering.filter(blueprint);

        assertEquals(2, filteredBlueprint.getPoints().size());
        assertEquals(Arrays.asList(new Point(0, 0), new Point(2, 2)), filteredBlueprint.getPoints());
    }
}