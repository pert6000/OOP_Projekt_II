package org.example.projekt.stat_näitajad;

public class kurtosis extends statistilineNäitaja {

    private double väärtus;

    public kurtosis(double[] a) {
        super(a);
    }

    public double arvuta(double[] a) {
        keskmine avg = new keskmine(a);
        standardhälve s = new standardhälve(a);
        kogus k = new kogus(a);
        double n = k.getVäärtus();

        double tulem = 0;

        double kurt;

        for (double el : a) {
            tulem += Math.pow((el - avg.getVäärtus()) / s.getVäärtus(), 4);

        }

        kurt = (n * (n + 1) / ((n - 1) * (n - 2) * (n - 3))) * tulem - (3 * Math.pow(n - 1, 2)) / ((n - 2) * (n - 3));

        return kurt;
    }


    public String selgita() {
        return "Andmete püstakus on " + this.getVäärtus();
    }
}
