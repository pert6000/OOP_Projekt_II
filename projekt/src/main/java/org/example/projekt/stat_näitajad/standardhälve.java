package org.example.projekt.stat_näitajad;

public class standardhälve extends statistilineNäitaja{

    private double väärtus;

    public standardhälve(double[] a) {
        super(a);
    }

    public double arvuta(double[] a) {
        hälve h = new hälve(a);
        kogus k = new kogus(a);
        return Math.sqrt(h.getVäärtus());
    }

    public String selgita() {
        return "Andmete standardhälve on " + this.getVäärtus();
    }
}
