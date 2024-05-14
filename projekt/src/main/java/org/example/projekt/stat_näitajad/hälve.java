package org.example.projekt.stat_näitajad;

public class hälve extends statistilineNäitaja{

    private double väärtus;

    public hälve(double[] a) {
        super(a);
    }

    public double arvuta(double[] a ) {
        keskmine kesk = new keskmine(a);
        kogus k = new kogus(a);

        double hälve_summa = 0;

        for (double el: a) {
            hälve_summa += Math.pow((el - kesk.getVäärtus()), 2);
        }

        double hälve = hälve_summa/ (k.getVäärtus() -1);

        return hälve;
    }

    public String selgita() {
        return "Andmete hälve e. disperioon on " + this.getVäärtus();
    }
}
