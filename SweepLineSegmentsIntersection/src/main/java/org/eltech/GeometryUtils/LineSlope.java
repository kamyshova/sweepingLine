package org.eltech.GeometryUtils;

public class LineSlope implements Comparable<LineSlope> {

    private double k;

    public LineSlope(Segment2D segment) {
        double deltaX = segment.getFirst().getX() - segment.getSecond().getX();
        double deltaY = segment.getFirst().getY() - segment.getSecond().getY();

        if (GeometryUtils.fuzzyCompareWithZero(deltaX) == 0) {
            k = Double.MAX_VALUE;
        } else {
            k = deltaY / deltaX;
        }
    }

    @Override
    public int compareTo(LineSlope o) {
        return GeometryUtils.fuzzyCompare(k, o.k);
    }
}
