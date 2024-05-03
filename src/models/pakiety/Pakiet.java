package models.pakiety;

import models.Abonament;
import models.Cennik;
import models.Klient;

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




}
