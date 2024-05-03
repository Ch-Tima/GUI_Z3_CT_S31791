package models;

import models.pakiety.Pakiet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Consumer;

public class ListaZyczen extends ArrayList<Pakiet> {

    private Klient klient;

    public ListaZyczen(Klient klient){
        this.klient = klient;
    }

    @Override
    public String toString() {
        String str = "";

        for (int i = 0; i < this.size(); i++) {
            Pakiet pakiet = this.get(i);
            int cena = pakiet.getCena(klient.getAbonament());
            str += pakiet.getImie() + ", typ: " + pakiet.getType() + ", ile: " + pakiet.getIlosc() + ", cena: " + (cena < 1 ? "ceny brak" : cena) + "\n";
        }
        return str;
    }
}
