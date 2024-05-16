package models;

import models.pakiety.Pakiet;

import java.util.ArrayList;

public class Koszyk extends ArrayList<Pakiet> {

    private Klient klient;

    public Koszyk(Klient klient){
        this.klient = klient;
    }


    @Override
    public String toString() {
        String str = "";

        if(size() == 0)
            return "-- pusto";

        for (int i = 0; i < this.size(); i++) {
            Pakiet pakiet = this.get(i);
            int cena = pakiet.getCena(klient.getAbonament());
            str += pakiet.getImie() + ", typ: " + pakiet.getType() + ", ile: " + pakiet.getIlosc() + ", cena: " + (cena < 1 ? "ceny brak" : cena) + "\n";
        }
        return str;
    }

    public Klient getKlient() {
        return klient;
    }
}
