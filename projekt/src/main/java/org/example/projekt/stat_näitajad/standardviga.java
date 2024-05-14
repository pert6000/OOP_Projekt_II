package org.example.projekt.stat_näitajad;

public class standardviga extends statistilineNäitaja{
    private double väärtus;
    public standardviga(double[] a) {
        super(a);
    }
    public double arvuta(double[] a ){
        hälve h = new hälve(a);
        kogus k = new kogus(a);
        double kogus = k.getVäärtus();
        double hälve = h.getVäärtus();

        return (hälve / Math.sqrt(kogus));
    }
    public String selgita() {
        return "Andmete standardviga on " + this.getVäärtus();
    }
}
