package org.eltech;

import org.eltech.GeometryUtils.Segment2D;
import org.eltech.algorithm.SweepLineSegmentsIntersectionsFinder;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class GUIApp {
    public static final int SEGMENTS_COUNT = 1000;

    public static void main(String[] args) {
        Random rand = new Random();
        ArrayList<Segment2D> segments = new ArrayList<>(SEGMENTS_COUNT);
        for (int i = 0; i < SEGMENTS_COUNT; i++) {
            segments.add(i, new Segment2D(
                    50 + rand.nextDouble() * (SegmentsView.WIDTH - 100),
                    50 + rand.nextDouble() * (SegmentsView.HEIGHT - 100),
                    50 + rand.nextDouble() * (SegmentsView.WIDTH - 100),
                    50 + rand.nextDouble() * (SegmentsView.HEIGHT - 100)
            ));
        }

        EventQueue.invokeLater(() -> {
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new SegmentsView(segments, SweepLineSegmentsIntersectionsFinder.analyze(segments)));
            frame.pack();
            frame.setVisible(true);
        });
    }
}
