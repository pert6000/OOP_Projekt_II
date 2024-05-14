package org.example.projekt.stat_näitajad;

import java.util.Arrays;

public class asümmeetrijakordaja extends statistilineNäitaja {
    private double väärtus;

    public asümmeetrijakordaja(double[] a) {
        super(a);
    }

    public double arvuta(double[] a) {
        keskmine k = new keskmine(a);
        mediaan m = new mediaan(a);
        standardhälve s = new standardhälve(a);

        return 3 * (k.getVäärtus() - m.getVäärtus()) / s.getVäärtus();
    }

    public String selgita() {
        String tulem = "Andmete asümmeetriline kordaja on " + this.getVäärtus() + ", see tähendab, et ";
        if (this.getVäärtus() > 0)
            tulem += "andmetes esineb vasakkalle";
        else if (this.getVäärtus() < 0) {
            tulem += "andmetes esineb paremkalle";
        } else
            tulem += "andmetes ei esine kallet";
        return tulem;
    }
}