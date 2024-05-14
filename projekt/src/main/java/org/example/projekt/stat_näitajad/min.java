package org.example.projekt.stat_näitajad;

public class min extends statistilineNäitaja {
    private double väärtus;


    public min(double[] a) {
        super(a);
    }

    public double arvuta(double[] a) {
        double vähim = Integer.MAX_VALUE;

        for (double el : a) {
            if (el < vähim)
                vähim = el;

        }
        return vähim;
    }


    public String selgita() {
        return "Andmete minimaalne väärtus on " + this.getVäärtus();
    }
}
