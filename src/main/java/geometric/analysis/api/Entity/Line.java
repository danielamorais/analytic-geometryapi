package geometric.analysis.api.Entity;

import javafx.geometry.Point3D;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class Line {

    private Point3D point;
    private Vector3D vector;
    private float lambda;

    public Point3D getPoint() {
        return point;
    }

    public Line setPoint(Point3D point) {
        this.point = point;
        return this;
    }

    public Vector3D getVector() {
        return vector;
    }

    public Line setVector(Vector3D vector) {
        this.vector = vector;
        return this;
    }

    public float getLambda() {
        return lambda;
    }

    public Line setLambda(float lambda) {
        this.lambda = lambda;
        return this;
    }
}
