package org.eltech.GeometryUtils;

public class Segment2D {
    private Point2D first;
    private Point2D second;

    public Segment2D(Point2D first, Point2D second) {
        this.first = first;
        this.second = second;
    }

    public Segment2D(double beginX, double beginY, double endX, double endY) {
        this.first = new Point2D(beginX, beginY);
        this.second = new Point2D(endX, endY);
    }

    public Point2D getFirst() {
        return first;
    }

    public Point2D getSecond() {
        return second;
    }
}
