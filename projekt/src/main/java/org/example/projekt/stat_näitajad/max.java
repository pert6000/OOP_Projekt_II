package org.example.projekt.stat_näitajad;

public class max extends statistilineNäitaja {
    private double väärtus;


    public max(double[] a) {
        super(a);
    }

    public double arvuta(double[] a) {
        double suurim = Integer.MIN_VALUE;

        for (double el : a) {
            if (el > suurim)
                suurim = el;

        }
        return suurim;
    }

    public String selgita() {
        return "Andmete maksimaalne väärtus on " + this.getVäärtus();
    }
}
