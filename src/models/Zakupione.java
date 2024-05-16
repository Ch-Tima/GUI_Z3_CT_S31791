package models;

import models.pakiety.Pakiet;

import java.util.ArrayList;

public class Zakupione extends ArrayList<Zakupione.ZakupionePakiety> {

    private final Klient klient;

    public Zakupione(Klient klient){
        this.klient = klient;
    }

    public void add(Koszyk koszyk){
        for (Pakiet pakiet: koszyk) {
            this.add(new ZakupionePakiety(pakiet, pakiet.getCena(klient.getAbonament())));
        }
    }


    public static class ZakupionePakiety{
        private final Pakiet pakiet;
        private final double cenaOryginalna;


        ZakupionePakiety(Pakiet pakiet, double cenaOryginalna) {
            this.pakiet = pakiet;
            this.cenaOryginalna = cenaOryginalna;
        }

        public Pakiet getPakiet() {
            return pakiet;
        }

        public double getCenaOryginalna() {
            return cenaOryginalna;
        }
    }

}
