package polynomial;

import java.util.ArrayList;

public class Lagrange extends Polynomial {
    public final ArrayList<Double> XValues;
    public final ArrayList<Double> YValues;

    public Lagrange(ArrayList<Double> xValues, ArrayList<Double> yValues) {
        super(findLagrange(xValues, yValues));
        this.XValues = xValues;
        this.YValues = yValues;
    }

    public void addNode(double x, double y) {
        XValues.add(x);
        YValues.add(y);
        findLagrange(XValues, YValues);
    }

    private static ArrayList<Double> findLagrange(ArrayList<Double> x, ArrayList<Double> y) {
        if (x.size() != y.size()) {
            throw new IllegalArgumentException("ERR: Sizes of arrays should be an equal!!!");
        }
        int n = x.size();
        Polynomial result = new Polynomial(0.0);

        for (int i = 0; i < n; i++) {
            Polynomial lagrange_i = new Polynomial(1.0);
            for (int j = 0; j < n; j++) {
                if (j != i) {
                    double denominator = x.get(i) - x.get(j);
                    Polynomial step = new Polynomial(-x.get(j), 1.0);
                    lagrange_i = lagrange_i.times(step.div(denominator));
                }
            }
            lagrange_i = lagrange_i.times(y.get(i));
            result = result.plus(lagrange_i);
        }

        return result.getCoeffs();
    }

}
