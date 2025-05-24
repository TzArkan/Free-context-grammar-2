package gic2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class Analiza {

    private ListaProductii listaProductii;

    public Analiza(ListaProductii listaProductii) {
        this.listaProductii = listaProductii;
    }


    public static ListaProductii citesteDinFisier(String numeFisier) throws IOException {
        ListaProductii listaProductii = new ListaProductii();

        try (BufferedReader reader = new BufferedReader(new FileReader(numeFisier))) {
            String linie;
            int i=0;
            while ((linie = reader.readLine()) != null) {
                linie = linie.trim();
                if (!linie.isEmpty()) {
                    String[] parti = linie.split(" ", 2);
                    String neterminal = parti[0];
                    String dreapta = (parti.length > 1) ? parti[1] : "";
                    listaProductii.addProductie(new Productie(neterminal, dreapta, i));
                    i++;
                    
                }
            }
        }

        return listaProductii;
    }
}
