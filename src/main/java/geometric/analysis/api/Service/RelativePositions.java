package geometric.analysis.api.Service;

import geometric.analysis.api.Entity.Line;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

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
            return isParallel(lineOne.getVector(), lineTwo.getVector()) ? "Paralelas" : "Concorrentes ou perpendiculares";
        }
    }

    private boolean isParallel(Vector3D lineOneVector, Vector3D lineTwoVector) {
        double xPosition = lineOneVector.getX() / lineTwoVector.getX();
        double yPosition = lineOneVector.getY() / lineTwoVector.getY();
        double zPosition = lineOneVector.getZ() / lineTwoVector.getZ();
        if (xPosition == yPosition && yPosition == zPosition) {
            return true;
        }
        return false;
    }

    /**
     * This method is respnsible for return the value of the determinant of the matrix.
     * The algoritms follows the Carmer's rule.
     * First it's multiplied the elements from the main diagonal and after
     * the elements of the secundary diagonal
     *
     * @param matrix Matrix 3x3
     * @return Determinant
     */
    public int checkDeterminant(double[][] matrix) {
        if(matrix.length != 3){
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
        for(Integer value : determinantValues){
            determinant += value;
        }

        return determinant;
    }

    public int getScalarProduct() {
        throw new NotImplementedException();
    }
}
