package org.example.projekt.stat_näitajad;

import java.util.HashMap;
import java.util.Map;

public class mood extends statistilineNäitaja{
    private double väärtus;
    public mood(double[] a) {
        super(a);
    }
    public double arvuta(double[] a ){
        Map<Integer, Integer> lugeja = new HashMap<>();

        for (double number : a){
            int võti  = (int) number; // muudab double int-iks
            lugeja.put(võti, lugeja.getOrDefault(võti, 0) + 1);
        }
        int mood = 0;
        int suurim = 0;
        for (Map.Entry<Integer, Integer> el : lugeja.entrySet()){
            int mitu = el.getValue();
            if (mitu > suurim){
                suurim = mitu;
                mood = el.getKey();
            }
        }
        väärtus = mood;
        return mood;
    }
    public String selgita() {
        return "Arv mida failis kõige rohkem esineb on " + this.getVäärtus();
    }
}
