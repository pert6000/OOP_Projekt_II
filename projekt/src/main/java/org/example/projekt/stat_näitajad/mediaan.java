package org.example.projekt.stat_näitajad;

import java.util.Arrays;

public class mediaan extends statistilineNäitaja{
    private double väärtus;
    public mediaan(double[] a) {
        super(a);
    }
    public double arvuta(double[] a ){
        Arrays.sort(a);
        int length = a.length;
        if (length % 2 != 0) {
            return a[length / 2];
        } else {
            return (a[length / 2 - 1] + a[length / 2]) / 2.0;
        }
    }
    public String selgita() {
        return "Andmete mediaan on " + this.getVäärtus();
    }
}
