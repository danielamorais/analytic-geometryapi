package geometric.analysis.api.Service;

import geometric.analysis.api.Entity.Line;
import javafx.geometry.Point3D;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by danielamorais on 03/08/15.
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

    @Test
    public void testIsParallel_isParallel() throws Exception {
        Vector3D vectorOne = new Vector3D(2.0, -1.0, 1.0);
        Vector3D vectorTwo = new Vector3D(-4.0, 2.0, -2.0);
        Assert.assertEquals(true, new RelativePositions().isParallel(vectorOne, vectorTwo));
    }

    @Test
    public void testIsParallel_isntParallel() throws Exception {
        Vector3D vectorOne = new Vector3D(2.0, 3.0, 4.0);
        Vector3D vectorTwo = new Vector3D(1.0, -1.0, -2.0);
        Assert.assertEquals(false, new RelativePositions().isParallel(vectorOne, vectorTwo));
    }

    @Test
    public void testIsParallel_isntParallelTwo() throws Exception {
        Vector3D vectorOne = new Vector3D(1.0, 2.0, 4.0);
        Vector3D vectorTwo = new Vector3D(-1.0, -1.0, -1.0);
        Assert.assertEquals(false, new RelativePositions().isParallel(vectorOne, vectorTwo));
    }

    @Test
    public void testAreDistinct_areDistinct() throws Exception {
        Line lineOne = new Line().setPoint(new Point3D(1.0, 3.0, 1.0)).setVector(new Vector3D(-1.0, 2.0, -3.0));
        Point3D point = new Point3D(1.0, 2.0, 3.0);
        Assert.assertEquals(false, new RelativePositions().areCoincidents(point, lineOne));
    }

    @Test
    public void testAreDistinct_coincidents() throws Exception {
        Line lineOne = new Line().setPoint(new Point3D(2.0, 0.0, 1.0)).setVector(new Vector3D(-4.0, 2.0, -2.0));
        Point3D point = new Point3D(0.0, 1.0, 0.0);
        Assert.assertEquals(true, new RelativePositions().areCoincidents(point, lineOne));
    }

    @Test
    public void testIntersectingLines_getPoints() throws Exception {
        Line lineOne = new Line().setPoint(new Point3D(0.0, 2.0, -1.0)).setVector(new Vector3D(1.0, -3.0, 3.0));
        Line lineTwo = new Line().setPoint(new Point3D(0.0, 1.0, 0.0)).setVector(new Vector3D(-1.0, 2.0, -2.0));

        Point3D expected = new Point3D(1.0, -1.0, 2.0);
        Point3D actual = new RelativePositions().intersectingLines(lineOne, lineTwo);
        Assert.assertEquals(expected, actual);

    }

    @Test
    public void testgetRelativePosition_coincidentsLines() throws Exception {
        Line lineOne = new Line().setPoint(new Point3D(1.0, 2.0, 1.0)).setVector(new Vector3D(-1.0, 2.0, 1.0));
        Line lineTwo = new Line().setPoint(new Point3D(1.0, 2.0, 1.0)).setVector(new Vector3D(-0.5, 1.0, 0.5));

        String expected = "Coincidentes";
        String actual = new RelativePositions().getRelativePosition(lineOne, lineTwo);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testAreDistinct_cordinateEqualZero() throws Exception {
        Line lineOne = new Line().setPoint(new Point3D(2.0, 0.0, 1.0)).setVector(new Vector3D(-4.0, 0.0, -2.0));
        Point3D point = new Point3D(0.0, 1.0, 0.0);

        Assert.assertEquals(false, new RelativePositions().areCoincidents(point, lineOne));

    }

    @Test
    public void testgetRelativePosition_reversas() throws Exception{
        Line lineOne = new Line().setPoint(new Point3D(1.0, 1.0, 1.0)).setVector(new Vector3D(2.0, 1.0, -3.0));
        Line lineTwo = new Line().setPoint(new Point3D(0.0, 1.0, 0.0)).setVector(new Vector3D(-1.0, 2.0, 0.0));

        String expected = "Reversas";
        String actual = new RelativePositions().getRelativePosition(lineOne, lineTwo);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testgetRelativePosition_concorrentes() throws Exception{
        Line lineOne = new Line().setPoint(new Point3D(1.0, 2.0, 3.0)).setVector(new Vector3D(1.0, 2.0, 1.0));
        Line lineTwo = new Line().setPoint(new Point3D(2.0, 4.0, 4.0)).setVector(new Vector3D(-1.0, 1.0, -1.0));

        String expected = "Concorrentes\n" +
                "{\"x\":2.0,\"y\":4.0,\"z\":4.0,\"hash\":0}";
        String actual = new RelativePositions().getRelativePosition(lineOne, lineTwo);
        Assert.assertEquals(expected, actual);
    }

    /**
     * r: X= (8,1,9) + λ(2,-1,3) s: X (3,-4,4) + µ(1,-2,2) P=(-2,6,-6)
     * @throws Exception
     */
    @Test
    public void testgetRelativePosition_concorrentesTwo() throws Exception{
        Line lineOne = new Line().setPoint(new Point3D(8.0, 1.0, 9.0)).setVector(new Vector3D(2.0, -1.0, 3.0));
        Line lineTwo = new Line().setPoint(new Point3D(3.0, -4.0, 4.0)).setVector(new Vector3D(1.0, -2.0, 2.0));

        String expected = "Concorrentes\n" +
                "{\"x\":-2.0,\"y\":6.0,\"z\":-6.0,\"hash\":0}";
        String actual = new RelativePositions().getRelativePosition(lineOne, lineTwo);
        Assert.assertEquals(expected, actual);
    }

    /**
     * r: X= (-1,0,-1) + λ(2,3,2) s: X (0,0,0) + µ(1,2,0)
     * @throws Exception
     */
    @Test
    public void testgetRelativePosition_reversasTwo() throws Exception{
        Line lineOne = new Line().setPoint(new Point3D(-1.0, 0.0, -1.0)).setVector(new Vector3D(2.0, 3.0, 2.0));
        Line lineTwo = new Line().setPoint(new Point3D(0.0, 0.0, 0.0)).setVector(new Vector3D(1.0, 2.0, 0.0));

        String expected = "Reversas";
        String actual = new RelativePositions().getRelativePosition(lineOne, lineTwo);
        Assert.assertEquals(expected, actual);
    }


    /**
     * r: X= (-3,2,1) + λ(1,2,3) s: X (0,2,2) + µ(1,1,-1)
     */
    @Test
    public void testgetRelativePosition_reversasThree() throws Exception{
        Line lineOne = new Line().setPoint(new Point3D(-3.0, 2.0, 1.0)).setVector(new Vector3D(1.0, 2.0, 3.0));
        Line lineTwo = new Line().setPoint(new Point3D(0.0, 2.0, 2.0)).setVector(new Vector3D(1.0, 1.0, -1.0));

        String expected = "Reversas";
        String actual = new RelativePositions().getRelativePosition(lineOne, lineTwo);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testgetRelativePosition_reversasFour() throws Exception{
        Line lineOne = new Line().setPoint(new Point3D(1.0, 5.0, -2.0)).setVector(new Vector3D(3.0, 5.0, 5.0));
        Line lineTwo = new Line().setPoint(new Point3D(0.0, 0.0, 1.0)).setVector(new Vector3D(1.0, -1.0, 4.0));

        String expected = "Reversas";
        String actual = new RelativePositions().getRelativePosition(lineOne, lineTwo);
        Assert.assertEquals(expected, actual);
    }

    /**
     * r: X= (1,5,-2) + λ(3,3,5) s: X (0,0,1) + µ(1,-1,4) P=(-2,2,-7)
     */
    @Test
    public void testgetRelativePosition_lambdaNegative() throws Exception{
        Line lineOne = new Line().setPoint(new Point3D(1.0, 5.0, -2.0)).setVector(new Vector3D(3.0, 3.0, 5.0));
        Line lineTwo = new Line().setPoint(new Point3D(0.0, 0.0, 1.0)).setVector(new Vector3D(1.0, -1.0, 4.0));

        String expected = "Concorrentes\n" +
                "{\"x\":-2.0,\"y\":2.0,\"z\":-7.0,\"hash\":0}";
        String actual = new RelativePositions().getRelativePosition(lineOne, lineTwo);
        Assert.assertEquals(expected, actual);
    }


}