package org.eltech;

import org.eltech.GeometryUtils.Point2D;
import org.eltech.GeometryUtils.Segment2D;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Set;

public class SegmentsView extends JPanel {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    private List<Segment2D> segments;
    private Set<Point2D> intersections;

    public SegmentsView(List<Segment2D> segments, Set<Point2D> intersections) {
        this.segments = segments;
        this.intersections = intersections;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(WIDTH, HEIGHT);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        segments.forEach((seg) -> {
            Point2D a = seg.getFirst();
            Point2D b = seg.getSecond();
            g.drawLine((int) a.getX(), (int) a.getY(), (int) b.getX(), (int) b.getY());
        });

        g.setColor(Color.red);
        intersections.forEach((p) -> g.drawOval((int) p.getX() - 2, (int) p.getY() - 2, 2, 2));
    }
}
