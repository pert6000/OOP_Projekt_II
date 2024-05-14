package org.example.projekt.stat_näitajad;

public class vahemik extends statistilineNäitaja {
    private double väärtus;


    public vahemik(double[] a) {
        super(a);
    }

    public double arvuta(double[] a) {
        double vahemik;

        max suurim = new max(a);
        min vähim = new min(a);

        vahemik = suurim.getVäärtus() - vähim.getVäärtus();

        return vahemik;
    }


    public String selgita() {
        return "Andmete minimaalne väärtus on " + this.getVäärtus();
    }
}
