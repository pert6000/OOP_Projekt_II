package org.example.projekt.stat_näitajad;

import java.io.*;
import java.nio.file.Paths;
import java.util.*;

public class Main {

    public static HashMap<String, ArrayList<Double>> failistLugemine(String failitee, boolean päistega, String eraldaja) throws IOException {

        HashMap<String, ArrayList<Double>> andmed = new HashMap<>();
        int tulpasid;
        BufferedReader br;
        String[] päis;
        String rida;
        String[] jupid;

        File fail = new File(failitee);
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(fail)));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Faili ei leitud");
        }

        if (eraldaja.isEmpty())
            eraldaja = "___";

        päis = br.readLine().split(eraldaja);
        tulpasid = päis.length;

        try {
            if (päistega) {

                for (int i = 0; i < tulpasid; i++) {
                    andmed.put(päis[i], new ArrayList<>());
                }
            } else {
                for (int i = 0; i < tulpasid; i++) {
                    String uusPäis = (i + 1) + ". tulp";
                    andmed.put(uusPäis, new ArrayList<>());
                    andmed.get((i + 1) + ". tulp").add(Double.parseDouble(päis[i]));
                    päis[i] = uusPäis;
                }
            }

            rida = br.readLine();
            while (rida != null) {
                jupid = rida.split(eraldaja);

                for (int i = 0; i < tulpasid; i++) {
                    andmed.get(päis[i]).add(Double.parseDouble(jupid[i]));
                }
                rida = br.readLine();
            }
        } catch (NumberFormatException e) {
            throw new RuntimeException("Viga andmetes");
        }

        br.close();

        return andmed;
    }


        public static void kirjutaFaili(HashMap<String, statistilineNäitaja> väärtused, String path) {
        try {
            File fail = new File(path);
            String kogu_tee = Paths.get(fail.getAbsolutePath()).getParent().toString() +
                    "\\" + fail.getName().split("\\.")[0] + "_analüüs.txt";

            FileWriter writer = new FileWriter(kogu_tee);

            for (Map.Entry<String, statistilineNäitaja> el: väärtused.entrySet()) {
                writer.write(el.getValue().selgita() + "\n");
            }

            writer.close();

            System.out.println("Andmed kirjutatud faili " + path);
        } catch (IOException e) {
            System.out.println("Faili kirjutamisel tekkis viga: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static String selgitaKõik(HashMap<String, statistilineNäitaja> väärtused) {
        String tulem = "";

        for (Map.Entry<String, statistilineNäitaja> el: väärtused.entrySet()) {
            tulem += el.getValue().selgita() + System.lineSeparator();
        }
        return tulem;

    }

    public static double[] ujukomaMassiiviks(ArrayList<Double> arvud) {
        double[] tulem = new double[arvud.size()];

        for (int i = 0; i < arvud.size(); i++) {
            tulem[i] = arvud.get(i);
        }
        return tulem;
    }

    public static HashMap<String, statistilineNäitaja> teeMap(double[] arvudeMassiiv) {
        HashMap<String, statistilineNäitaja> väärtused = new HashMap<String, statistilineNäitaja>();

        väärtused.put("max", new max(arvudeMassiiv));
        väärtused.put("min", new min(arvudeMassiiv));
        väärtused.put("rng", new vahemik(arvudeMassiiv));
        väärtused.put("avg", new keskmine(arvudeMassiiv));
        väärtused.put("sum", new summa(arvudeMassiiv));
        väärtused.put("len", new kogus(arvudeMassiiv));
        väärtused.put("kurt", new kurtosis(arvudeMassiiv));
        väärtused.put("med", new mediaan(arvudeMassiiv));
        väärtused.put("asüm", new asümmeetrijakordaja(arvudeMassiiv));
        väärtused.put("se", new standardviga(arvudeMassiiv));
        väärtused.put("md", new mood(arvudeMassiiv));
        väärtused.put("h", new hälve(arvudeMassiiv));
        väärtused.put("sd", new standardhälve(arvudeMassiiv));

        return väärtused;
    }

    public static void main(String[] args) throws IOException {
        HashMap<String, ArrayList<Double>> data = failistLugemine("data_päistega.txt", true, ";");
        System.out.println(data.keySet());

        for (int i = 0; i < 5; i++) {
            for (String s: data.keySet()) {
                System.out.print(data.get(s).get(i) + " ");
            }
            System.out.println();
        }

    }
}
