package org.eltech.GeometryUtils;

public class GeometryUtils {

    public static final double TOLERANCE = 0.000001;

    public static int fuzzyCompare(double val1, double val2) {
        return Double.compare(val1, val2);
    }

    public static int fuzzyCompareWithZero(double value) {
        return Double.compare(value, 0.0);
    }

    public static int comparePoints(Point2D o1, Point2D o2) {
        int xCompare = fuzzyCompare(o1.getX(), o2.getX());
        return xCompare != 0 ? xCompare : fuzzyCompare(o1.getY(), o2.getY());
    }

    public static boolean isIntersectIn1D(double left1, double left2, double right1, double right2) {
        if (left1 > left2) {
            double temp = left1;
            left1 = left2;
            left2 = temp;
        }

        if (right1 > right2) {
            double temp = right1;
            right1 = right2;
            right2 = temp;
        }

        return fuzzyCompare(Math.max(left1, right1), Math.min(left2, right2)) <= 0;
    }

    public static double cross2D(Point2D basePoint, Point2D pointA, Point2D pointB) {
        return (pointA.getX() - basePoint.getX()) * (pointB.getY() - basePoint.getY()) -
                (pointB.getX() - basePoint.getX()) * (pointA.getY() - basePoint.getY());
    }

    public static boolean checkSegmentsIntersection(Segment2D seg1, Segment2D seg2) {
        return isIntersectIn1D(seg1.getFirst().getX(), seg1.getSecond().getX(), seg2.getFirst().getX(), seg2.getSecond().getX()) &&
                isIntersectIn1D(seg1.getFirst().getY(), seg1.getSecond().getY(), seg2.getFirst().getY(), seg2.getSecond().getY()) &&
                fuzzyCompareWithZero(cross2D(seg1.getFirst(), seg1.getSecond(), seg2.getFirst()) * cross2D(seg1.getFirst(), seg1.getSecond(), seg2.getSecond())) <= 0 &&
                fuzzyCompareWithZero(cross2D(seg2.getFirst(), seg2.getSecond(), seg1.getFirst()) * cross2D(seg2.getFirst(), seg2.getSecond(), seg1.getSecond())) <= 0;
    }

    public static double calculateLineSegmentValueAtX(Segment2D seg, double x) {
        double deltaX = seg.getSecond().getX() - seg.getFirst().getX();
        double deltaY = seg.getSecond().getY() - seg.getFirst().getY();

        if (fuzzyCompareWithZero(deltaX) == 0) {
            throw new UnsupportedOperationException();
        }

        double k = fuzzyCompareWithZero(deltaY) == 0 ? 0 : deltaY / deltaX;
        return k * (x - seg.getFirst().getX()) + seg.getFirst().getY();
    }

    public static int compareSegmentsYAtX(Segment2D seg1, Segment2D seg2, double x) {
        int seg1XCompare = fuzzyCompare(seg1.getFirst().getX(), seg1.getSecond().getX());
        int seg2XCompare = fuzzyCompare(seg2.getFirst().getX(), seg2.getSecond().getX());

        if (seg1XCompare == 0 && seg2XCompare == 0) {
            Point2D seg1BottomPoint = seg1.getFirst().getY() < seg1.getSecond().getY() ? seg1.getFirst() : seg1.getSecond();
            Point2D seg2BottomPoint = seg2.getFirst().getY() < seg2.getSecond().getY() ? seg2.getFirst() : seg2.getSecond();
            return fuzzyCompare(seg1BottomPoint.getY(), seg2BottomPoint.getY());
        } else if (seg1XCompare == 0) {
            Point2D seg1BottomPoint = seg1.getFirst().getY() < seg1.getSecond().getY() ? seg1.getFirst() : seg1.getSecond();
            return fuzzyCompare(seg1BottomPoint.getY(), calculateLineSegmentValueAtX(seg2, x));
        } else if (seg2XCompare == 0) {
            Point2D seg2BottomPoint = seg2.getFirst().getY() < seg2.getSecond().getY() ? seg2.getFirst() : seg2.getSecond();
            return fuzzyCompare(calculateLineSegmentValueAtX(seg1, x), seg2BottomPoint.getY());
        } else {
            return fuzzyCompare(calculateLineSegmentValueAtX(seg1, x), calculateLineSegmentValueAtX(seg2, x));
        }
    }

    public static Point2D getIntersectionPoint(Segment2D seg1, Segment2D seg2) {
        double norm = (seg2.getSecond().getY() - seg2.getFirst().getY()) * (seg1.getSecond().getX() - seg1.getFirst().getX()) -
                (seg2.getSecond().getX() - seg2.getFirst().getX()) * (seg1.getSecond().getY() - seg1.getFirst().getY());

        if (norm != 0) {
            double segment1Coefficient = (seg2.getSecond().getX() - seg2.getFirst().getX()) * (seg1.getFirst().getY() - seg2.getFirst().getY()) -
                    (seg2.getSecond().getY() - seg2.getFirst().getY()) * (seg1.getFirst().getX() - seg2.getFirst().getX());

            double deltaX = seg1.getSecond().getX() - seg1.getFirst().getX();
            double deltaY = seg1.getSecond().getY() - seg1.getFirst().getY();

            return new Point2D(seg1.getFirst().getX() + (deltaX * segment1Coefficient) / norm,
                    seg1.getFirst().getY() + (deltaY * segment1Coefficient) / norm);
        } else {
            Point2D seg1LeftPoint = seg1.getFirst().getX() < seg1.getSecond().getX() ? seg1.getFirst() : seg1.getSecond();
            Point2D seg2LeftPoint = seg2.getFirst().getX() < seg2.getSecond().getX() ? seg2.getFirst() : seg2.getSecond();
            return seg1LeftPoint.getX() < seg2LeftPoint.getX() ? seg2LeftPoint : seg1LeftPoint;
        }
    }
}
