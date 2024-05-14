package org.example.projekt.stat_näitajad;

public class kogus extends statistilineNäitaja {
    private double väärtus;

    public kogus(double[] a) {
        super(a);
    }

    public double arvuta(double[] a) {
        return a.length;
    }

    public String selgita() {
        return "Andmepuntkide kogus on " + this.getVäärtus();
    }
}

