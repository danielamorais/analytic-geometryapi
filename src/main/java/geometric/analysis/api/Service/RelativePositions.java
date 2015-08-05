package geometric.analysis.api.Service;

import com.google.gson.Gson;
import geometric.analysis.api.Entity.Line;
import javafx.geometry.Point3D;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import java.util.ArrayList;

/**
 * Relative positions between two lines
 * The line is set to a point in space (x, y, z), a director vector and lambda.
 *
 * @author Daniela Morais
 * @since 1.0
 */
public class RelativePositions {

    public String getRelativePosition(Line lineOne, Line lineTwo) {
        double[][] matrix = {
                {lineOne.getVector().getX(), lineOne.getVector().getY(), lineOne.getVector().getZ()},
                {lineTwo.getVector().getX(), lineTwo.getVector().getY(), lineTwo.getVector().getZ()},
                {lineTwo.getPoint().getX() - lineOne.getPoint().getX(), lineTwo.getPoint().getY() - lineOne.getPoint().getY(), lineTwo.getPoint().getZ() - lineOne.getPoint().getZ()}
        };
        if (checkDeterminant(matrix) != 0) {
            return "Reversas";
        } else {
            if (isParallel(lineOne.getVector(), lineTwo.getVector())) {
                if (areCoincidents(lineOne.getPoint(), lineTwo)) {
                    return "Coincidentes";
                } else {
                    return "Paralelas distintas";
                }
            } else {
                return "Concorrentes\n" + new Gson().toJson(intersectingLines(lineOne, lineTwo));
            }
        }
    }

    /**
     * This method search a lambda in common in the two lines of equations
     *
     * @param lineOne Line one
     * @param lineTwo Line two
     * @return Point of intersection
     */
    public Point3D intersectingLines(Line lineOne, Line lineTwo) {
        double x = lineOne.getPoint().getX() - lineTwo.getPoint().getX();
        double y = lineOne.getPoint().getY() - lineTwo.getPoint().getY();
        double z = lineOne.getPoint().getZ() - lineTwo.getPoint().getZ();
        Vector3D pointsDifference = new Vector3D(x, y, z);
        Vector3D second = pointsDifference.crossProduct(lineTwo.getVector());
        Vector3D first = lineOne.getVector().crossProduct(lineTwo.getVector());

        double lambda = second.getNorm() / first.getNorm();
        double xIntersection = lineOne.getPoint().getX() + (lambda * lineOne.getVector().getX());
        double yIntersection = lineOne.getPoint().getY() + (lambda * lineOne.getVector().getY());
        double zIntersection = lineOne.getPoint().getZ() + (lambda * lineOne.getVector().getZ());

        double xInLineTwo = (xIntersection - lineTwo.getPoint().getX()) / lineTwo.getVector().getX();
        double yInLineTwo = (yIntersection - lineTwo.getPoint().getY()) / lineTwo.getVector().getY();
        double zInLineTwo = (zIntersection - lineTwo.getPoint().getZ()) / lineTwo.getVector().getZ();

        if (xInLineTwo == yInLineTwo && xInLineTwo == zInLineTwo) {
            return new Point3D(xIntersection, yIntersection, zIntersection);
        } else {
            xIntersection = lineOne.getPoint().getX() + (-1 * lambda * lineOne.getVector().getX());
            yIntersection = lineOne.getPoint().getY() + (-1 * lambda * lineOne.getVector().getY());
            zIntersection = lineOne.getPoint().getZ() + (-1 * lambda * lineOne.getVector().getZ());

            return new Point3D(xIntersection, yIntersection, zIntersection);
        }
    }

    /**
     * The code validates the line point one belong the line two
     * then the lines are coincident. If not they will be distincit.
     *
     * @param pointOne Point belonging to line one
     * @param line     Line two
     * @return Distinct or coincidents
     */
    public boolean areCoincidents(Point3D pointOne, Line line) {
        Point3D pointTwo = line.getPoint();
        Vector3D vectorTwo = line.getVector();

        if (containsCoordinatesEqualsZero(vectorTwo)) {
            if (pointOne.getX() - pointTwo.getX() == 0.0) {
                double y = pointOne.getY() - pointTwo.getY();
                double z = pointOne.getZ() - pointTwo.getZ();
                return (y == 0.0 && z == 0.0);
            } else if (pointOne.getY() - pointTwo.getY() == 0.0) {
                double x = pointOne.getX() - pointTwo.getX();
                double z = pointOne.getZ() - pointTwo.getZ();
                return (x == 0.0 && z == 0.0);
            } else if (pointOne.getZ() - pointTwo.getZ() == 0.0) {
                double x = pointOne.getX() - pointTwo.getX();
                double y = pointOne.getY() - pointTwo.getY();
                return (x == 0.0 && y == 0.0);
            } else {
                return false;
            }
        }

        double x = (pointOne.getX() - pointTwo.getX()) / vectorTwo.getX();
        double y = (pointOne.getY() - pointTwo.getY()) / vectorTwo.getY();
        double z = (pointOne.getZ() - pointTwo.getZ()) / vectorTwo.getZ();
        return (x == y && y == z);
    }

    /**
     * Checks all vector coordinates and returns if any coordinated equals zero
     *
     * @param vectorTwo Vector
     * @return True if contains some coordinantes equals zero
     */
    private boolean containsCoordinatesEqualsZero(Vector3D vectorTwo) {
        return (vectorTwo.getX() == 0.0 || vectorTwo.getY() == 0.0 || vectorTwo.getZ() == 0.0);
    }

    /**
     * This method checks if the cross product between vector1 and
     * vector2 is equals zero if are they will be paralells
     *
     * @param lineOneVector Director vector 1
     * @param lineTwoVector Director vector 2
     * @return Is parallel or not
     */
    public boolean isParallel(Vector3D lineOneVector, Vector3D lineTwoVector) {
        Vector3D result = lineOneVector.crossProduct(lineTwoVector);
        return (result.getX() == 0.0 && result.getY() == 0.0 && result.getZ() == 0.0);
    }

    /**
     * This method is respnsible for return the value of the determinant of the matrix.
     * The algoritms follows the Cramer's rule.
     * First it's multiplied the elements from the main diagonal and after
     * the elements of the secundary diagonal
     *
     * @param matrix Matrix 3x3
     * @return Determinant
     */
    public int checkDeterminant(double[][] matrix) {
        if (matrix.length != 3) {
            throw new IllegalArgumentException("This isn't not a matrix 3x3.");
        }
        double[][] matrixaux = new double[3][5];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                matrixaux[i][j] = matrix[i][j];
            }
        }
        int column = 3;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                matrixaux[j][column] = matrix[j][i];
            }
            column++;
        }
        ArrayList<Integer> determinantValues = new ArrayList<>();
        Double values = 1.0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                values *= matrixaux[i][j];
                i++;
                if (i == 3) {
                    determinantValues.add(values.intValue());
                    values = 1.0;
                    if (j == 4) {
                        break;
                    }
                    i = 0;
                    int aux = j;
                    j = aux - 2;
                }
            }
        }

        values = 1.0;
        for (int i = 2; i >= 0; i--) {
            for (int j = 0; j < 5; j++) {
                values *= matrixaux[i][j];
                if (i == 0) {
                    determinantValues.add(values.intValue() * -1);
                    values = 1.0;
                    if (j == 4) {
                        break;
                    }
                    i = 2;
                    j = j - 2;
                } else {
                    --i;
                }
            }
        }

        int determinant = 0;
        for (Integer value : determinantValues) {
            determinant += value;
        }

        return determinant;
    }

}
