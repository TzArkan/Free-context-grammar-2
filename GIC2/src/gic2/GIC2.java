package gic2;

import java.io.IOException;
import java.util.*;

public class GIC2 {
    public static void main(String[] args) {
        try {
            // Citim producțiile din fișierul GiC1.txt
            ListaProductii listaProductii = Analiza.citesteDinFisier("GiC.txt");

            // Mapare: neterminal -> set de părți din dreapta
            Map<String, Set<String>> mapareProductii = new HashMap<>();

            for (Productie productie : listaProductii.getProductii()) {
                String neterminal = productie.getNeterminal();
                String dreapta = productie.getDreapta();

                // Adăugăm dreapta în setul corespunzător neterminalului
                mapareProductii.putIfAbsent(neterminal, new HashSet<>());
                mapareProductii.get(neterminal).add(dreapta);
            }

            // Afișăm maparea
            System.out.println("Maparea neterminalelor cu partile din dreapta:");
            for (Map.Entry<String, Set<String>> entry : mapareProductii.entrySet()) {
                System.out.println(entry.getKey() + " -> " + entry.getValue());
            }

            // Șirul de intrare pentru algoritmul CYK
            String input = "baba";

            // Apelăm algoritmul CYK
            Set<String>[][] matrice = algoritmCYK(input, mapareProductii);

            // Afișăm matricea
            System.out.println("\nMatricea CYK:");
            afiseazaMatriceCYK(matrice, input);
            
        } catch (IOException e) {
            System.err.println("Eroare la citirea fișierului: " + e.getMessage());
        }
    }

    public static Set<String>[][] algoritmCYK(String input, Map<String, Set<String>> mapareProductii) {
    int n = input.length();

    // Matricea CYK: matrice de seturi de string-uri
    Set<String>[][] matrice = new HashSet[n][n];

    // Inițializăm matricea cu seturi vide
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            matrice[i][j] = new HashSet<>();
        }
    }

    // Faza 1: Populăm prima linie a matricei (Vi1)
    for (int i = 0; i < n; i++) {
        char simbol = input.charAt(i);
        String caracter = String.valueOf(simbol);

        // Căutăm toate neterminalele care duc la caracterul curent
        for (Map.Entry<String, Set<String>> entry : mapareProductii.entrySet()) {
            String neterminal = entry.getKey();
            Set<String> partiDreapta = entry.getValue();

            if (partiDreapta.contains(caracter)) {
                matrice[0][i].add(neterminal);
            }
        }
    }

    // Faza 2: Calculăm restul matricei
    for (int lungime = 2; lungime <= n; lungime++) { // Lungimea subșirului curent
        for (int start = 0; start <= n - lungime; start++) { // Poziția de start a subșirului
            int end = start + lungime - 1; // Poziția de sfârșit a subșirului

            // Împărțim subșirul în două părți și verificăm toate combinațiile
            for (int k = start; k < end; k++) {
                Set<String> parteaStanga = matrice[k - start][start];
                Set<String> parteaDreapta = matrice[end - k - 1][k + 1];

                // Generăm toate combinațiile de neterminale stânga-dreapta
                for (String neterminalStanga : parteaStanga) {
                    for (String neterminalDreapta : parteaDreapta) {
                        String combinatie = neterminalStanga + neterminalDreapta;

                        // Verificăm dacă există o producție care generează această combinație
                        for (Map.Entry<String, Set<String>> entry : mapareProductii.entrySet()) {
                            String neterminal = entry.getKey();
                            Set<String> partiDreapta = entry.getValue();

                            if (partiDreapta.contains(combinatie)) {
                                matrice[lungime - 1][start].add(neterminal);
                            }
                        }
                    }
                }
            }
        }
    }

    return matrice;
}


    public static void afiseazaMatriceCYK(Set<String>[][] matrice, String input) {
        int n = input.length();

        // Afișăm capul de tabel (simbolurile șirului)
        System.out.print("     ");
        for (int i = 0; i < n; i++) {
            System.out.print(input.charAt(i) + "   ");
        }
        System.out.println();

        // Afișăm fiecare rând al matricei
        for (int i = 0; i < n; i++) {
            System.out.print((i + 1) + ": ");
            for (int j = 0; j < n - i; j++) {
                System.out.print(matrice[i][j] + " ");
            }
            System.out.println();
        }
        // Verificăm dacă simbolul de start 'S' se află în ultima linie și prima coloană a matricei
        if (matrice[n - 1][0].contains("S")) {
            System.out.println("Cuvantul apartine gramaticii.");
        } else {
            System.out.println("Cuvantul NU apartine gramaticii.");
        }
    }
}
