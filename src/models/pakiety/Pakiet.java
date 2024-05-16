package models.pakiety;

import models.Abonament;
import models.Cennik;

import java.util.Objects;

public abstract class Pakiet {

    private String imie;
    private int ilosc;

    public Pakiet(String imie, int ilosc) {
        this.imie = imie;
        this.ilosc = ilosc;
    }

    public String getImie() {
        return imie;
    }

    public int getIlosc() {
        return ilosc;
    }

    public abstract String getType();

    public abstract int getCena(Abonament abonament);


    public void setIlosc(int ilosc) {
        this.ilosc = ilosc;
    }

    public abstract boolean isEqualsType(Cennik.Termin termin);



    public boolean equals(Pakiet p) {
        if(p == null) return false;
        if(this == p) return true;
        return this.getType().equals(p.getType()) && this.getImie().equals(p.getImie());
    }

}
