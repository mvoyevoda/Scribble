package cuny.csi.csc330.scribble;

import java.util.List;
import java.util.ArrayList;

public class Model {

    private double lastX = 0;
    private double lastY = 0;
    private List<double[]> interpolationPoints;

    public double getLastX(){
        return this.lastX;
    }
    public double getLastY(){
        return this.lastY;
    }
    public void setLastX(double newX){
        this.lastX = newX;
    }
    public void setLastY(double newY){
        this.lastY = newY;
    }
    public List<double[]> getInterpolationPoints(){
        return interpolationPoints;
    }
    public void setInterpolationPoints(double newX, double newY){

        interpolationPoints = new ArrayList<>(); // Reset interpolationPoints
        double dx = newX - this.lastX;
        double dy = newY - this.lastY;
        double distance = Math.sqrt(dx * dx + dy * dy);
        int steps = (int)distance * 4;

        for (int i = 0; i <= steps; i++) {
            double t = (double) i / steps;
            double interpolatedX = this.lastX + t * dx;
            double interpolatedY = this.lastY + t * dy;
            interpolationPoints.add(new double[]{interpolatedX, interpolatedY});
        }

    }

}
