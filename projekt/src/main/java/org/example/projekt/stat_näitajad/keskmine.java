package org.example.projekt.stat_näitajad;

public class keskmine extends statistilineNäitaja{

    private double väärtus;

    public keskmine(double[] a) {
        super(a);
    }

    public double arvuta(double[] a) {
        summa s = new summa(a);
        kogus k = new kogus(a);

        return s.getVäärtus()/k.getVäärtus();
    }

    public String selgita() {
        return "Andmete keskmine, s.t oodatud väärtus on " + this.getVäärtus();
    }
}
