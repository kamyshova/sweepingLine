package org.eltech.algorithm;

import org.eltech.GeometryUtils.GeometryUtils;
import org.eltech.GeometryUtils.LineSlope;
import org.eltech.GeometryUtils.Segment2D;

import java.util.Comparator;

class SweepLineSegmentComparator implements Comparator<Segment2D> {

    @Override
    public int compare(Segment2D seg1, Segment2D seg2) {
        int segmentsComparison = GeometryUtils.compareSegmentsYAtX(seg1, seg2, currentX);
        if (segmentsComparison != 0) {
            return segmentsComparison;
        }

        LineSlope seg1Slope = new LineSlope(seg1);
        LineSlope seg2Slope = new LineSlope(seg2);
        segmentsComparison = seg1Slope.compareTo(seg2Slope);
        if (segmentsComparison != 0) {
            if (!reverseEqualSegments) {
                return segmentsComparison;
            } else {
                return -segmentsComparison;
            }
        }

        segmentsComparison = GeometryUtils.fuzzyCompare(
                Math.min(seg1.getFirst().getX(), seg1.getSecond().getX()),
                Math.min(seg2.getFirst().getX(), seg2.getSecond().getX()));
        if (segmentsComparison != 0) {
            return segmentsComparison;
        }

        int compare = GeometryUtils.fuzzyCompare(
                Math.max(seg1.getFirst().getY(), seg1.getSecond().getY()),
                Math.max(seg2.getFirst().getY(), seg2.getSecond().getY()));

        return compare;
    }

    public void setCurrentX(double currentX) {
        this.currentX = currentX;
    }

    public void setReverseEqualSegments(boolean reverseEqualSegments) {
        this.reverseEqualSegments = reverseEqualSegments;
    }

    private boolean reverseEqualSegments = false;
    private double currentX;
}
