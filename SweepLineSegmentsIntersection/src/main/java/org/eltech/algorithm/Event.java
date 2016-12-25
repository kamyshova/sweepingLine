package org.eltech.algorithm;

import org.eltech.GeometryUtils.GeometryUtils;
import org.eltech.GeometryUtils.Point2D;
import org.eltech.GeometryUtils.Segment2D;

import java.util.InputMismatchException;

class Event implements Comparable<Event> {
    public enum Type {
        START,
        END,
        INTERSECTION
    }

    private Type type;
    private Segment2D segment;
    private Point2D point;

    private Point2D choosePoint(Segment2D segment, Type type) {
        int segmentEndingsComparison = GeometryUtils.comparePoints(segment.getFirst(), segment.getSecond());
        switch (type) {
            case START:
                if (segmentEndingsComparison < 0) {
                    return segment.getFirst();
                } else {
                    return segment.getSecond();
                }
            case END:
                if (segmentEndingsComparison < 0) {
                    return segment.getSecond();
                } else {
                    return segment.getFirst();
                }
            default:
                throw new InputMismatchException(
                        "Type should have value START or END but "
                                + type.toString() + " was provided.");

        }
    }

    Event(Segment2D segment, Type type) {
        this.segment = segment;
        this.type = type;
        this.point = choosePoint(segment, type);
    }

    Event(Point2D point) {
        this.segment = null;
        this.type = Type.INTERSECTION;
        this.point = point;
    }

    public Type getType() {
        return type;
    }

    public Segment2D getSegment() {
        return segment;
    }

    public Point2D getPoint() {
        return point;
    }

    @Override
    public int compareTo(Event o) {
        int xCompare = GeometryUtils.fuzzyCompare(point.getX(), o.point.getX());
        if (xCompare != 0) return xCompare;

        int yCompare = GeometryUtils.fuzzyCompare(point.getY(), o.point.getY());
        if (yCompare != 0) return yCompare;

        if (this.type == Type.INTERSECTION && o.type == Type.INTERSECTION) {
            return 0;
        }

        if (this.type != o.type) {
            return (this.type == Type.START) ? -1 : 1;
        }

        return 0;
    }
}
