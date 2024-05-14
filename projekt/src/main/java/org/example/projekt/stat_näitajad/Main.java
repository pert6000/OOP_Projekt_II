package org.example.projekt.stat_näitajad;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

public class Main {

    public static List<Double> failistLugemine(String failitee) {

        List<Double> arvud = new ArrayList<>(); // List kuhu arvud loetakse failist

        try {
            File fail = new File(failitee);

            Scanner failiScanner = new Scanner(fail);

            // Loeb arvud failist ja lisab listi
            while (failiScanner.hasNextLine()) {
                String rida = failiScanner.nextLine();
                try {
                    double arv = Double.parseDouble(rida); // teeb rea double väärtuseks
                    arvud.add(arv); // Lisab arvu listi
                } catch (NumberFormatException e) {
                    System.out.println("Ignoreerin rida " + rida);
                }
            }
            failiScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Ei leia faili");
        }
        return arvud;
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


    public static void main(String[] args) {

        // tekst, mis kuvatakse ekraanile programmi käivitamisel
        System.out.println("//////");
        System.out.println("Tegu on programmiga, mille abil saab teostada andmete statistilist analüüsi.");
        System.out.println("Andmefailile viitamise järel saab pärida erinevaid näitajaid andmestiku kohta või teostada terviklik analüüs.");
        System.out.println("Viimast valides tekib uus tekstifail andmefailiga samasse kausta, mis sisaldab arvutatud näitajaid.");
        System.out.println("//////\n");


        System.out.println("Sisesta failitee: ");

        java.util.Scanner scanner = new java.util.Scanner(System.in);
        String failitee = scanner.nextLine();

        List<Double> arvud = failistLugemine(failitee);

        String tekst = "Võimalikud tegevused on:\nTeosta kogu analüüs - all\nMaksimum - max \nMiinimum - min\n" +
                "Vahemik - rng\nKeskmine - avg \nSumma - sum \nKogus - len \nEkstsess (näitab andmete püstakust) - kurt \n" +
                "Mediaan - med \nAsümmeetrijakordaja - asüm \nStandardviga - se\nMood - md\n" +
                "Hälve (dispersioon) - h\nStandardhälve (kui hajunud andmed on keskmise ümber)- sd\n\nLõpeta töö - jäta tühjaks\n";

        System.out.println(tekst);

        // Arvutame failist saadud arvudega
        if (!arvud.isEmpty()) {
            // Muudab listi massiiviks
            double[] arvudeMassiiv = new double[arvud.size()];
            for (int i = 0; i < arvud.size(); i++) {
                arvudeMassiiv[i] = arvud.get(i);
            }

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



            while (true) {

                System.out.println("Sisesta soovitud arvutus: ");
                java.util.Scanner sc = new java.util.Scanner(System.in);
                String sisend = "";

                if (sc.hasNextLine()) {
                    sisend = sc.nextLine();
                }

                if(sisend.isEmpty()) {
                    System.out.println("Programm lõpetas töö");
                    break;
                }
                if (sisend.equals("all")) {
                    kirjutaFaili(väärtused, failitee);
                }else
                    System.out.println(väärtused.get(sisend).selgita());

            }

        } else {
            System.out.println("Failist ei leitud sobivaid arve arvutamiseks.");
        }
    }
}