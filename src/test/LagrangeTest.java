package test;

import polynomial.Lagrange;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LagrangeTest {
    private Lagrange lagrange;

    @BeforeEach
    void setUp() {
        ArrayList<Double> xValues = new ArrayList<>();
        ArrayList<Double> yValues = new ArrayList<>();
        xValues.add(1.0);
        xValues.add(2.0);
        yValues.add(2.0);
        yValues.add(3.0);
        lagrange = new Lagrange(xValues, yValues);
    }

    @Test
    void testConstructor() {
        assertEquals(2, lagrange.XValues.size());
        assertEquals(2, lagrange.YValues.size());
        assertEquals(1.0, lagrange.XValues.get(0));
        assertEquals(2.0, lagrange.YValues.get(0));
    }

    @Test
    void testAddNode() {
        lagrange.addNode(3.0, 5.0);
        assertEquals(3, lagrange.XValues.size());
        assertEquals(3, lagrange.YValues.size());
        assertEquals(3.0, lagrange.XValues.get(2));
        assertEquals(5.0, lagrange.YValues.get(2));
    }

    @Test
    void testFindLagrange() {
        // Проверка, что коэффициенты полинома корректно вычисляются
        ArrayList<Double> coeffs = lagrange.getCoeffs();
        assertNotNull(coeffs);
        assertFalse(coeffs.isEmpty());
    }

    @Test
    void testFindLagrangeWithDifferentSizes() {
        ArrayList<Double> xValues = new ArrayList<>();
        ArrayList<Double> yValues = new ArrayList<>();
        xValues.add(1.0);
        yValues.add(2.0);
        yValues.add(3.0); // Разные размеры

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Lagrange lagrange = new Lagrange(xValues, yValues);
        });

        assertEquals("ERR: Sizes of arrays should be an equal!!!", exception.getMessage());
    }

    @Test
    void testSinglePoint() {
        ArrayList<Double> x = new ArrayList<>(List.of(1.0));
        ArrayList<Double> y = new ArrayList<>(List.of(2.0));

        Lagrange lagrange = new Lagrange(x, y);
        assertEquals("2.0", lagrange.toString());
    }

    @Test
    void testLinearFunction() {
        ArrayList<Double> x = new ArrayList<>(List.of(1.0, 2.0));
        ArrayList<Double> y = new ArrayList<>(List.of(3.0, 5.0));

        Lagrange lagrange = new Lagrange(x, y);
        assertEquals("2.0x+1.0", lagrange.toString());
    }

    @Test
    void testSimple() {
        ArrayList<Double> x = new ArrayList<>(List.of(1.0, 2.0, 3.0, 4.0));
        ArrayList<Double> y = new ArrayList<>(List.of(1.0, 4.0, 9.0, 16.0));

        Lagrange lagrange = new Lagrange(x, y);
        assertEquals(3, lagrange.getPower());
        assertEquals(0, lagrange.getCoeffs().get(0), 1e-9);
        assertEquals(0, lagrange.getCoeffs().get(1), 1e-9);
        assertEquals(1.0, lagrange.getCoeffs().get(2), 1e-9);
    }

    @Test
    void testQuadraticFunction() {
        ArrayList<Double> x = new ArrayList<>(List.of(1.0, 2.0, 3.0));
        ArrayList<Double> y = new ArrayList<>(List.of(6.0, 11.0, 18.0));

        Lagrange lagrange = new Lagrange(x, y);
        assertEquals("x^2+2.0x+3.0", lagrange.toString());
    }

    @Test
    void testInvalidInputSize() {
        ArrayList<Double> x = new ArrayList<>(List.of(1.0, 2.0));
        ArrayList<Double> y = new ArrayList<>(List.of(3.0));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Lagrange(x, y));

        assertEquals("ERR: Sizes of arrays should be an equal!!!", exception.getMessage());
    }

    @Test
    void testCalculateValue() {
        ArrayList<Double> x = new ArrayList<>(List.of(1.0, 2.0, 3.0));
        ArrayList<Double> y = new ArrayList<>(List.of(2.0, 3.0, 5.0));

        Lagrange lagrange = new Lagrange(x, y);
        assertEquals(2.0, lagrange.calc(1.0), 1e-6);
        assertEquals(3.0, lagrange.calc(2.0), 1e-6);
        assertEquals(5.0, lagrange.calc(3.0), 1e-6);
    }

    @Test
    void testEdgeCaseWithZeros() {
        ArrayList<Double> x = new ArrayList<>(List.of(0.0, 1.0));
        ArrayList<Double> y = new ArrayList<>(List.of(0.0, 0.0));

        Lagrange lagrange = new Lagrange(x, y);
        assertEquals("0", lagrange.toString());
    }

    @Test
    void testSinglePointValue() {
        ArrayList<Double> x = new ArrayList<>(List.of(0.0));
        ArrayList<Double> y = new ArrayList<>(List.of(5.0));

        Lagrange lagrange = new Lagrange(x, y);
        assertEquals("5.0", lagrange.toString());
    }

    @Test
    void testTwoPointsLinear() {
        ArrayList<Double> x = new ArrayList<>(List.of(0.0, 1.0));
        ArrayList<Double> y = new ArrayList<>(List.of(1.0, 3.0));

        Lagrange lagrange = new Lagrange(x, y);
        assertEquals("2.0x+1.0", lagrange.toString());
    }

    @Test
    void testFourPointsQuadratic() {
        ArrayList<Double> x = new ArrayList<>(List.of(1.0, 2.0, 3.0, 4.0));
        ArrayList<Double> y = new ArrayList<>(List.of(1.0, 4.0, 9.0, 16.0)); // y = x^2

        Lagrange lagrange = new Lagrange(x, y);
        assertEquals(3, lagrange.getPower());
        assertEquals(0, lagrange.getCoeffs().get(0), 1e-9);
        assertEquals(0, lagrange.getCoeffs().get(1), 1e-9);
        assertEquals(1.0, lagrange.getCoeffs().get(2), 1e-9);
    }

    @Test
    void testCubicFunction() {
        ArrayList<Double> x = new ArrayList<>(List.of(1.0, 2.0, 3.0, 4.0));
        ArrayList<Double> y = new ArrayList<>(List.of(1.0, 8.0, 27.0, 64.0)); // y = x^3

        Lagrange lagrange = new Lagrange(x, y);
        assertEquals(3, lagrange.getPower());
        assertEquals(0, lagrange.getCoeffs().get(0), 1e-9);
        assertEquals(0, lagrange.getCoeffs().get(1), 1e-9);
    }

    @Test
    void testQuadraticInterpolation() {
        ArrayList<Double> x = new ArrayList<>(List.of(1.0, 2.0, 3.0));
        ArrayList<Double> y = new ArrayList<>(List.of(2.0, 3.0, 5.0)); // Парабола

        Lagrange lagrange = new Lagrange(x, y);
        assertEquals("0.5x^2-0.5x+2.0", lagrange.toString());
    }
}
