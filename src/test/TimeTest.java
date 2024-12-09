package test;

import org.junit.jupiter.api.Test;
import polynomial.Lagrange;
import polynomial.Newton;

import java.util.ArrayList;
import java.util.Random;

public class TimeTest {
    @Test
    void testPerformanceLagrangeVsNewton() {
        Random random = new Random();
        int[] numPointsArray = {100, 500, 1000, 1500, 2000};

        for (int numPoints : numPointsArray) {
            ArrayList<Double> x = new ArrayList<>();
            ArrayList<Double> y = new ArrayList<>();
            for (int i = 0; i < numPoints; i++) {
                x.add((double) i);
                y.add(random.nextDouble() * 100);
            }

            long lagrangeStart = System.currentTimeMillis();
            Lagrange lagrange = new Lagrange(x, y);
            lagrange.calc(500.0);
            long lagrangeEnd = System.currentTimeMillis();
            long lagrangeTime = lagrangeEnd - lagrangeStart;
            System.out.println("Для " + numPoints + " значений, время выполнения Лагранжа составило: " + lagrangeTime + " мс");

            long newtonStart = System.currentTimeMillis();
            Newton newton = new Newton(x, y);
            newton.calc(500.0);
            long newtonEnd = System.currentTimeMillis();
            long newtonTime = newtonEnd - newtonStart;
            System.out.println("Для " + numPoints + " значений, время выполнения Ньютона составило: " + newtonTime + " мс");

            long newtonAddNodesStart = System.currentTimeMillis();
            Newton newtonAddNodes = new Newton(x, y);
            newtonAddNodes.calc(500.0);
            long newtonEndAddNodes = System.currentTimeMillis();
            long newtonTimeAddNodes = newtonEndAddNodes - newtonAddNodesStart;
            System.out.println("Для " + numPoints + " значений, время выполнения Ньютона составило: " + newtonTimeAddNodes + " мс");

            System.out.println("Ньютон в " + (double) lagrangeTime / newtonTime + " раз быстрее при " + numPoints + " значений");
            System.out.println("--------------------------------------------------");
        }
    }
}
