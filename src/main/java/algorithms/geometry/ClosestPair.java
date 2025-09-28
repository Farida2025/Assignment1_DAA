package algorithms.geometry;

import algorithms.metrics.MetricsTracker;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;


public class ClosestPair {

    private static final Comparator<Point> COMPARE_BY_X = Comparator.comparingDouble(p -> p.x);
    private static final Comparator<Point> COMPARE_BY_Y = Comparator.comparingDouble(p -> p.y);

    public static double findClosestPair(Point[] points, MetricsTracker tracker) {
        if (points == null || points.length < 2) {
            return Double.MAX_VALUE;
        }


        Point[] Px = points.clone();
        Arrays.sort(Px, COMPARE_BY_X);

        Point[] Py = points.clone();
        Arrays.sort(Py, COMPARE_BY_Y);


        return closestPairRec(Px, Py, tracker);
    }


    private static double closestPairRec(Point[] Px, Point[] Py, MetricsTracker tracker) {
        int n = Px.length;


        if (n <= 3) {
            double min = Double.MAX_VALUE;

            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) {
                    min = Math.min(min, Point.distance(Px[i], Px[j]));
                }
            }
            return min;
        }


        int mid = n / 2;
        Point midPoint = Px[mid - 1];


        Point[] PxL = Arrays.copyOfRange(Px, 0, mid);
        Point[] PxR = Arrays.copyOfRange(Px, mid, n);


        List<Point> PyL_list = new ArrayList<>();
        List<Point> PyR_list = new ArrayList<>();
        for (Point p : Py) {
            if (p.x <= midPoint.x) {
                PyL_list.add(p);
            } else {
                PyR_list.add(p);
            }
        }
        Point[] PyL = PyL_list.toArray(new Point[0]);
        Point[] PyR = PyR_list.toArray(new Point[0]);


        tracker.enterRecursion();
        double deltaL = closestPairRec(PxL, PyL, tracker);
        double deltaR = closestPairRec(PxR, PyR, tracker);
        tracker.exitRecursion();

        double delta = Math.min(deltaL, deltaR);


        return combine(Px, Py, midPoint, delta, tracker);
    }


    private static double combine(Point[] Px, Point[] Py, Point midPoint, double delta, MetricsTracker tracker) {


        List<Point> stripList = new ArrayList<>();
        for (Point p : Py) {
            if (Math.abs(p.x - midPoint.x) < delta) {
                stripList.add(p);
            }
        }

        Point[] strip = stripList.toArray(new Point[0]);
        double minDistance = delta;


        for (int i = 0; i < strip.length; i++) {


            for (int j = i + 1; j < strip.length && (strip[j].y - strip[i].y) < minDistance; j++) {


                double dist = Point.distance(strip[i], strip[j]);
                minDistance = Math.min(minDistance, dist);
            }
        }

        return minDistance;
    }
}