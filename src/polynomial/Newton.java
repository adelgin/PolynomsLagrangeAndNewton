package polynomial;

import java.util.ArrayList;

public class Newton extends Polynomial {

    public final ArrayList<Double> XValues;
    public final ArrayList<Double> YValues;
    public Newton(ArrayList<Double> x, ArrayList<Double> y) {
        super(findNewton(x, y));
        this.XValues = x;
        this.YValues = y;
    }

    private static Double calculateDividedDifference(ArrayList<Double> x, ArrayList<Double> y) {
        double dif = 0.0;
        for (int j = 0; j < x.size(); j++) {
            double den = 1.0;
            for (int i = 0; i < x.size(); i++) {
                if (i != j) {
                    den *= x.get(j) - x.get(i);
                }
            }
            dif += y.get(j) / den;
        }
        return dif;
    }

    public static ArrayList<Double> dividedDifferences(ArrayList<Double> x, ArrayList<Double> y) {
        int n = x.size();
        ArrayList<Double> dividedDiffs = new ArrayList<>();

        if (n != y.size()) {
            throw new IllegalArgumentException("Sizes of x and y must be the same.");
        }

        for (int j = 0; j < n; j++) {
            ArrayList<Double> tempX = new ArrayList<>(x.subList(0, j + 1));
            ArrayList<Double> tempY = new ArrayList<>(y.subList(0, j + 1));
            dividedDiffs.add(calculateDividedDifference(tempX, tempY));
        }

        return dividedDiffs;
    }

    public static ArrayList<Double> findNewton(ArrayList<Double> x, ArrayList<Double> y) {
        if (x.size() != y.size()) {
            throw new IllegalArgumentException("ERR: Sizes of arrays should be an equal!!!");
        }

        ArrayList<Double> dividedDiff = dividedDifferences(x, y);
        int n = dividedDiff.size(), k = x.size();
        Polynomial result = new Polynomial(0.0), buff = new Polynomial(1.0);

        for (int i = 0; i < n; i++) {
            Polynomial step = buff.times(dividedDiff.get(i));
            result = result.plus(step);
            if (i < k - 1) {
                buff = buff.times(new Polynomial(-x.get(i), 1.0));
            }
        }

        return result.getCoeffs();
    }

    public void addNode(double xNew, double yNew) {
        XValues.add(xNew);
        YValues.add(yNew);
        Polynomial current = this;

        ArrayList<Double> newDifArray = dividedDifferences(XValues, YValues);

        Polynomial prod = new Polynomial(1.0);

        for (int i = 0; i < this.XValues.size() - 1; i++) {
            prod = prod.times(new Polynomial(-XValues.get(i), 1.0));
        }

        current = current.plus(prod.times(newDifArray.get(this.XValues.size() - 1)));

        super.setCoeffs(current.getCoeffs());
    }

    public void addNodes(ArrayList<Double> x, ArrayList<Double> y) {
        if (x.size() != y.size()) {
            throw new IllegalArgumentException("ERR: Sizes of arrays should be an equal!!!");
        }

        ArrayList<Double> newXNodes = new ArrayList<>(this.XValues);
        ArrayList<Double> newYNodes = new ArrayList<>(this.YValues);
        newXNodes.addAll(x);
        newYNodes.addAll(y);

        Polynomial current = this;

        ArrayList<Double> newDifArray = dividedDifferences(newXNodes, newYNodes);

        for (int k = this.XValues.size(); k < newXNodes.size(); k++) {
            Polynomial prod = new Polynomial(1.0);

            for (int i = 0; i < k; i++) {
                prod = prod.times(new Polynomial(-newXNodes.get(i), 1.0));
            }

            current = current.plus(prod.times(newDifArray.get(k)));
        }
        super.setCoeffs(current.getCoeffs());
    }
}
