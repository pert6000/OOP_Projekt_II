package org.example.projekt.stat_näitajad;

public class summa extends statistilineNäitaja {

    private double väärtus;

    public summa(double[] a) {
        super(a);
    }

    public double arvuta(double[] a) {
        double sum = 0;

        for (double d : a)
            sum += d;

        return sum;
    }

    public String selgita() {
        return "Andmepuntkide summa on " + this.getVäärtus();
    }
}
