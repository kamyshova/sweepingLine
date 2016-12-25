package org.eltech;

import org.eltech.GeometryUtils.Point2D;
import org.eltech.GeometryUtils.Segment2D;
import org.eltech.algorithm.SweepLineSegmentsIntersectionsFinder;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;


@Fork(1)
@State(Scope.Benchmark)
@Warmup(iterations = 3)
@Measurement(iterations = 5)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class SweepLineBenchmark {

    @Param({"1", "2", "3", "4"})
    public int size;

    @Param({"1", "2", "3", "4", "5", "6", "7", "8", "9"})
    public int subSize;

    List<Segment2D> input;

    @Setup
    public void setUp() {
        int length = (int) Math.pow(10, size);
        length *= subSize;
        System.out.println(length);
        if (input == null || input.size() != length) {

            input = new ArrayList<>(length);
            for (int i = 0; i < length; ++i) {
                input.add(
                        new Segment2D(
                                new Point2D(Math.random(), Math.random()),
                                new Point2D(Math.random(), Math.random())));
            }
        }

    }

    @Benchmark
    public void testQuery1(Blackhole bh) {
        Set<Point2D> intersections = SweepLineSegmentsIntersectionsFinder.analyze(input);
        bh.consume(intersections);
    }
}
