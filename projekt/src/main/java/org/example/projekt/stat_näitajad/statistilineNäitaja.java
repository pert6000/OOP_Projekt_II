package org.example.projekt.stat_näitajad;

public abstract class statistilineNäitaja {
    private double väärtus;

    public statistilineNäitaja(double[] a) {
        this.väärtus = arvuta(a);
    }

    public abstract double arvuta(double[] a);

    public abstract String selgita();

    public double getVäärtus() {
        return Math.round(väärtus*100.0)/100.0;
    }

}
