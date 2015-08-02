package geometric.analysis.api.Service;

import geometric.analysis.api.Entity.Line;
import javafx.geometry.Point3D;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by danielamorais on 02/08/15.
 */
public class RelativePositionsTest {

    @Test
    public void testCheckDeterminant_determinantEqualZero() throws Exception {
        Line lineOne = new Line().setPoint(new Point3D(1.0, 2.0, 3.0)).setVector(new Vector3D(1.0, 2.0, 3.0));
        Line lineTwo = new Line().setPoint(new Point3D(4.0, 5.0, 6.0)).setVector(new Vector3D(4.0, 5.0, 6.0));

        double[][] matrix = {
                {lineOne.getVector().getX(), lineOne.getVector().getY(), lineOne.getVector().getZ()},
                {lineTwo.getVector().getX(), lineTwo.getVector().getY(), lineTwo.getVector().getZ()},
                {lineTwo.getPoint().getX() - lineOne.getPoint().getX(), lineTwo.getPoint().getY() - lineOne.getPoint().getY(), lineTwo.getPoint().getZ() - lineOne.getPoint().getZ()}
        };

        Assert.assertEquals(0, new RelativePositions().checkDeterminant(matrix));
    }

    @Test
    public void testCheckDeterminant_allElementsEqualsZero_determinantEqualZeroTwo() throws Exception {
        double[][] matrix = {
                {1, 9, 8},
                {-7, 7, 6},
                {0, 0, 0}
        };
        Assert.assertEquals(0, new RelativePositions().checkDeterminant(matrix));
    }

    @Test
    public void testCheckDeterminant_determinantNotEqualsZero() throws Exception {
        double[][] matrix = {
                {1, 5, 2},
                {3, 1, 2},
                {4, 2, 3}
        };
        Assert.assertEquals(-2, new RelativePositions().checkDeterminant(matrix));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCheckDeterminant_invalidMatrix() throws Exception {
        double[][] matrix = {
                {1, 5, 2},
                {3, 1, 2}
        };
        Assert.assertEquals(-2, new RelativePositions().checkDeterminant(matrix));
    }
}