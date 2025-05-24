package gic2;



public class Productie {
    private String neterminal;  // Neterminalul din partea stângă
    private String dreapta;     // Partea dreaptă a producției
    private int numarP;

    public Productie(String neterminal, String dreapta, int numarP) {
        this.neterminal = neterminal;
        this.dreapta = dreapta;
        this.numarP = numarP;
    }

    public String getNeterminal() {
        return this.neterminal;
    }

    public String getDreapta() {
        return this.dreapta;
    }
    
    public int getNumar(){
        return this.numarP;
    } 

    @Override
    public String toString() {
        return neterminal + " -> " + dreapta;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Productie productie = (Productie) obj;
        return neterminal.equals(productie.neterminal) && dreapta.equals(productie.dreapta);
    }

    
}
