package test;

import polynomial.Newton;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NewtonTest {
    private Newton newton;

    @BeforeEach
    void setUp() {
        ArrayList<Double> xValues = new ArrayList<>(List.of(1.0, 2.0, 3.0));
        ArrayList<Double> yValues = new ArrayList<>(List.of(1.0, 4.0, 9.0)); // y = x^2
        newton = new Newton(xValues, yValues);
    }

    @Test
    void testConstructor() {
        assertEquals(3, newton.XValues.size());
        assertEquals(3, newton.YValues.size());
        assertEquals(1.0, newton.XValues.get(0));
        assertEquals(4.0, newton.YValues.get(1));
    }

    @Test
    void testDividedDifferences() {
        ArrayList<Double> x = new ArrayList<>(List.of(1.0, 2.0, 3.0));
        ArrayList<Double> y = new ArrayList<>(List.of(1.0, 4.0, 9.0));

        ArrayList<Double> dividedDiff = Newton.dividedDifferences(x, y);
        assertEquals(1.0, dividedDiff.get(0), 1e-9); // f[x0]
        assertEquals(3.0, dividedDiff.get(1), 1e-9); // f[x1]
        assertEquals(1.0, dividedDiff.get(2), 1e-9); // f[x2]
    }

    @Test
    void testFindNewton() {
        ArrayList<Double> x = new ArrayList<>(List.of(1.0, 2.0, 3.0));
        ArrayList<Double> y = new ArrayList<>(List.of(1.0, 4.0, 9.0));

        ArrayList<Double> coeffs = Newton.findNewton(x, y);
        assertEquals(0.0, coeffs.get(1), 1e-9);
        assertEquals(1.0, coeffs.get(2), 1e-9);
    }

    @Test
    void testAddNode() {
        newton.addNode(4.0, 16.0);
        assertEquals(4, newton.XValues.size());
        assertEquals(4, newton.YValues.size());
        assertEquals(16.0, newton.YValues.get(3), 1e-9);
    }

    @Test
    void testAddNodeWithCalculation() {
        newton.addNode(4.0, 16.0);
        double result = newton.calc(4.0); // Проверяем значение в точке x = 4
        assertEquals(16.0, result, 1e-9); // Ожидаемое значение
    }

    @Test
    void testInvalidSizes() {
        ArrayList<Double> x = new ArrayList<>(List.of(1.0, 2.0));
        ArrayList<Double> y = new ArrayList<>(List.of(1.0, 2.0, 3.0)); // Разные размеры

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Newton(x, y);
        });

        assertEquals("ERR: Sizes of arrays should be an equal!!!", exception.getMessage());
    }

    @Test
    void testSinglePoint() {
        ArrayList<Double> x = new ArrayList<>(List.of(1.0));
        ArrayList<Double> y = new ArrayList<>(List.of(2.0));
        Newton newton = new Newton(x, y);
        assertEquals("2.0", newton.toString(), "ERR: Sizes of arrays should be an equal!!!");
    }

    @Test
    void testLinearFunction() {
        ArrayList<Double> x = new ArrayList<>(List.of(1.0, 2.0));
        ArrayList<Double> y = new ArrayList<>(List.of(3.0, 5.0));

        Newton newton = new Newton(x, y);
        assertEquals("2.0x+1.0", newton.toString());
        assertEquals(3.0, newton.calc(1.0), 1e-6);
        assertEquals(5.0, newton.calc(2.0), 1e-6);
    }

    @Test
    void testAddNodes() {
        ArrayList<Double> x = new ArrayList<>(List.of(1.0, 2.0));
        ArrayList<Double> y = new ArrayList<>(List.of(3.0, 5.0));

        Newton newton = new Newton(x, y);

        assertEquals("2.0x+1.0", newton.toString());
        assertEquals(3.0, newton.calc(1.0), 1e-6);
        assertEquals(5.0, newton.calc(2.0), 1e-6);

        newton.addNode(3.0, 11.0); // Добавляем (3, 11)

        assertEquals("2.0x^2-4.0x+5.0", newton.toString());
        assertEquals(3.0, newton.calc(1.0), 1e-6);
        assertEquals(5.0, newton.calc(2.0), 1e-6);
        assertEquals(11.0, newton.calc(3.0), 1e-6);
    }

    @Test
    void testAddNodesFunc() {
        ArrayList<Double> x = new ArrayList<>(List.of(1.0, 2.0));
        ArrayList<Double> y = new ArrayList<>(List.of(3.0, 5.0));

        Newton newton = new Newton(x, y);
        //2.0 3.0   x
        //5.0 11.0  y
        assertEquals("2.0x+1.0", newton.toString());
        assertEquals(3.0, newton.calc(1.0), 1e-6);
        assertEquals(5.0, newton.calc(2.0), 1e-6);

        ArrayList<Double> newxs = new ArrayList<>(List.of(3.0));
        ArrayList<Double> newys = new ArrayList<>(List.of(11.0));

        newton.addNodes(newxs, newys);

        assertEquals("2.0x^2-4.0x+5.0", newton.toString());
        assertEquals(3.0, newton.calc(1.0), 1e-6);
        assertEquals(5.0, newton.calc(2.0), 1e-6);
        assertEquals(11.0, newton.calc(3.0), 1e-6);
    }

    @Test
    void testNodes() {
        ArrayList<Double> x = new ArrayList<>(List.of(1.0, 2.0));
        ArrayList<Double> y = new ArrayList<>(List.of(3.0, 5.0));

        Newton newton = new Newton(x, y);

        assertEquals("2.0x+1.0", newton.toString());
        assertEquals(3.0, newton.calc(1.0), 1e-6);
        assertEquals(5.0, newton.calc(2.0), 1e-6);

        newton.addNode(3.0, 11.0);

        assertEquals("2.0x^2-4.0x+5.0", newton.toString());
        assertEquals(3.0, newton.calc(1.0), 1e-6);
        assertEquals(5.0, newton.calc(2.0), 1e-6);
        assertEquals(11.0, newton.calc(3.0), 1e-6);

        ArrayList<Double> newx = new ArrayList<>(List.of(2.0, 3.0));
        ArrayList<Double> newy = new ArrayList<>(List.of(4.0, 5.0));

        Newton new_newton = new Newton(newx, newy);

        assertEquals("x+2.0", new_newton.toString());
        assertEquals(5.0, new_newton.calc(3.0), 1e-6);
        assertEquals(4.0, new_newton.calc(2.0), 1e-6);
        assertEquals(13.0, new_newton.calc(11.0), 1e-6);
    }

    @Test
    void testDividedDifference() {
        ArrayList<Double> x = new ArrayList<>(List.of(1.0, 2.0, 3.0));
        ArrayList<Double> y = new ArrayList<>(List.of(6.0, 11.0, 18.0));

        ArrayList<Double> dividedDiff = Newton.dividedDifferences(x, y);
        ArrayList<Double> expectedCoefficients = new ArrayList<>(List.of(6.0, 5.0, 1.0));

        assertEquals(expectedCoefficients, dividedDiff);
    }

    @Test
    void testInvalidInputSize() {
        ArrayList<Double> x = new ArrayList<>(List.of(1.0, 2.0));
        ArrayList<Double> y = new ArrayList<>(List.of(3.0));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Newton(x, y));

        assertEquals("ERR: Sizes of arrays should be an equal!!!", exception.getMessage());
    }

    @Test
    void testEdgeCaseWithZeros() {
        ArrayList<Double> x = new ArrayList<>(List.of(0.0, 1.0));
        ArrayList<Double> y = new ArrayList<>(List.of(0.0, 0.0));

        Newton newton = new Newton(x, y);
        assertEquals("0", newton.toString(), "Polynomial should be zero if all y are zero");
    }

    @Test
    void testAddMultipleNodes() {
        ArrayList<Double> x = new ArrayList<>(List.of(1.0, 2.0));
        ArrayList<Double> y = new ArrayList<>(List.of(3.0, 5.0));

        Newton newton = new Newton(x, y);
        newton.addNode(3.0, 7.0); // Добавляем (3, 7)
        newton.addNode(4.0, 10.0); // Добавляем (4, 10)

        assertEquals(4, newton.XValues.size());
        assertEquals(4, newton.YValues.size());
        assertEquals(7.0, newton.calc(3.0), 1e-6);
        assertEquals(10.0, newton.calc(4.0), 1e-6);
    }

    @Test
    void testPolynomialAfterAddingNodes() {
        ArrayList<Double> x = new ArrayList<>(List.of(1.0, 2.0));
        ArrayList<Double> y = new ArrayList<>(List.of(3.0, 5.0));

        Newton newton = new Newton(x, y);
        newton.addNode(3.0, 11.0);
        newton.addNode(4.0, 17.0);

        assertEquals(11.0, newton.calc(3.0), 1e-6);
        assertEquals(17.0, newton.calc(4.0), 1e-6);
    }
}
