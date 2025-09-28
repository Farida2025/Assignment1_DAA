package algorithms.geometry;

import algorithms.metrics.MetricsTracker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

public class ClosestPairTest {

    private MetricsTracker tracker;
    private static final int N_SMALL = 50;
    private static final int N_HUGE = 10000;
    private static final double DELTA = 1e-9;

    @BeforeEach
    void setUp() {
        tracker = new MetricsTracker();
    }

    private double bruteForce(Point[] points) {
        double min = Double.MAX_VALUE;
        int n = points.length;
        if (n < 2) return min;

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                min = Math.min(min, Point.distance(points[i], points[j]));
            }
        }
        return min;
    }

    @Test
    void testSmallSetSimple() {
        Point[] points = {
                new Point(1.0, 1.0),
                new Point(3.0, 3.0),
                new Point(1.5, 1.5)
        };
        double expected = Point.distance(points[0], points[2]);
        double actual = ClosestPair.findClosestPair(points, tracker);
        assertEquals(expected, actual, DELTA, "Failed simple small set check.");
    }

    @Test
    void testCollinearPoints() {
        Point[] points = {
                new Point(1.0, 1.0),
                new Point(2.0, 1.0),
                new Point(5.0, 1.0),
                new Point(7.0, 1.0)
        };
        double expected = 1.0;
        double actual = ClosestPair.findClosestPair(points, tracker);
        assertEquals(expected, actual, DELTA, "Failed collinear points check.");
    }

    @Test
    void testRandomSetAgainstBruteForce() {
        Random rand = new Random(42);
        Point[] points = new Point[N_SMALL];
        for (int i = 0; i < N_SMALL; i++) {
            points[i] = new Point(rand.nextDouble() * 100, rand.nextDouble() * 100);
        }

        double expected = bruteForce(points);
        double actual = ClosestPair.findClosestPair(points.clone(), tracker);

        assertEquals(expected, actual, DELTA, "ClosestPair result does not match Brute Force.");
    }

    @Test
    void testRecursionDepthO_LogN() {
        int N = N_HUGE;
        Random rand = new Random(42);
        Point[] points = new Point[N];
        for (int i = 0; i < N; i++) {
            points[i] = new Point(rand.nextDouble() * 1000, rand.nextDouble() * 1000);
        }

        ClosestPair.findClosestPair(points, tracker);

        int expectedMaxDepth = (int) Math.ceil(Math.log(N) / Math.log(2));

        int actualMaxDepth = tracker.getSnapshot(N, 0).MaxDepth;

        assertTrue(actualMaxDepth < N / 2,
                "Depth is linear (O(N)), expected O(log N). Got: " + actualMaxDepth);

        assertTrue(actualMaxDepth <= expectedMaxDepth + 5,
                "Incorrect recursion depth. Expected O(log N) approx. " + expectedMaxDepth + ", got: " + actualMaxDepth);
    }
}