package org.eltech.algorithm;

import org.eltech.GeometryUtils.GeometryUtils;
import org.eltech.GeometryUtils.Point2D;
import org.eltech.GeometryUtils.Segment2D;

import java.util.*;

public class SweepLineSegmentsIntersectionsFinder {

    private static class IntersectionFinder {

        private void addPointToIntersectionMap(Point2D intersectionPoint,
                                               Segment2D seg1,
                                               Segment2D seg2) {
            List<Segment2D> intersectionAtX = intersectionPointSegmentMap.computeIfAbsent(intersectionPoint, k -> new ArrayList<>());
            intersectionAtX.add(seg1);
            intersectionAtX.add(seg2);
        }

        private void checkPossibleIntersection(Event event, Segment2D segment1, Segment2D segment2) {
            if (GeometryUtils.checkSegmentsIntersection(segment2, segment1)) {
                Point2D intersectionPoint = GeometryUtils.getIntersectionPoint(segment2, segment1);

                intersections.add(intersectionPoint);
                addPointToIntersectionMap(intersectionPoint, segment2, segment1);

                if (GeometryUtils.comparePoints(event.getPoint(), intersectionPoint) < 0) {
                    events.add(new Event(intersectionPoint));
                }
            }
        }

        private Event getEvent() {
            Event event = events.poll();
            segmentComparator.setCurrentX(event.getPoint().getX());
            segmentComparator.setReverseEqualSegments(false);
            return event;
        }

        Set<Point2D> findIntersectionsWithMinX(List<Segment2D> segments) {
            for (Segment2D segment : segments) {
                events.add(new Event(segment, Event.Type.START));
                events.add(new Event(segment, Event.Type.END));
            }

            while (!events.isEmpty()) {
                Event event = getEvent();
                Segment2D eventSegment = event.getSegment();
                if (event.getType() == Event.Type.START) {
                    Segment2D lower = status.lower(eventSegment);
                    Segment2D higher = status.higher(eventSegment);

                    if (lower != null) {
                        checkPossibleIntersection(event, eventSegment, lower);
                    }
                    if (higher != null) {
                        checkPossibleIntersection(event, eventSegment, higher);
                    }

                    status.add(eventSegment);
                } else if (event.getType() == Event.Type.END) {
                    if (status.remove(eventSegment)) {
                        Segment2D lower = status.floor(eventSegment);
                        Segment2D higher = status.ceiling(eventSegment);

                        if (lower != null && higher != null) {
                            checkPossibleIntersection(event, higher, lower);
                        }
                    }
                } else if (event.getType() == Event.Type.INTERSECTION) {
                    List<Segment2D> intersectingSegments = intersectionPointSegmentMap.remove(event.getPoint());
                    if (intersectingSegments == null) {
                        continue;
                    }
                    ArrayList<Segment2D> stack = new ArrayList<>();

                    segmentComparator.setCurrentX(event.getPoint().getX() - GeometryUtils.TOLERANCE);
                    for (Segment2D segment : intersectingSegments) {
                        if (status.remove(segment)) {
                            stack.add(segment);
                        }
                    }

                    segmentComparator.setReverseEqualSegments(true);
                    segmentComparator.setCurrentX(event.getPoint().getX() + GeometryUtils.TOLERANCE);
                    while (!stack.isEmpty()) {
                        Segment2D seg = stack.remove(stack.size() - 1);
                        Segment2D lower = status.lower(seg);
                        Segment2D higher = status.higher(seg);
                        status.add(seg);

                        if (lower != null) {
                            checkPossibleIntersection(event, seg, lower);
                        }
                        if (higher != null) {
                            checkPossibleIntersection(event, seg, higher);
                        }
                    }
                    segmentComparator.setCurrentX(event.getPoint().getX());
                }
            }

            return intersections;
        }

        private Queue<Event> events = new PriorityQueue<>();
        private Set<Point2D> intersections = new HashSet<>();
        private SweepLineSegmentComparator segmentComparator = new SweepLineSegmentComparator();
        private TreeSet<Segment2D> status = new TreeSet<>(segmentComparator);
        private TreeMap<Point2D, List<Segment2D>> intersectionPointSegmentMap = new TreeMap<>(GeometryUtils::comparePoints);
    }

    static public Set<Point2D> analyze(List<Segment2D> segments) {
        return new IntersectionFinder().findIntersectionsWithMinX(segments);
    }

}
