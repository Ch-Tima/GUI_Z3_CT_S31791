package models;

import models.pakiety.Pakiet;

import java.util.List;

public class Klient {

    private static int counter = 0;

    private int id;

    private String imie;
    private int balanc;
    private Abonament abonament;

    private ListaZyczen listaZyczen;
    private Koszyk koszyk;

    public Klient(String imie, int balanc, Abonament abonament) {
        this.imie = imie;
        this.balanc = balanc;
        this.abonament = abonament;
        listaZyczen = new ListaZyczen(this);
        koszyk = new Koszyk(this);
        id = ++counter;
    }

    public Abonament getAbonament() {
        return abonament;
    }

    public void dodaj(Pakiet pakiet){
        listaZyczen.add(pakiet);
    }

    public ListaZyczen pobierzListeZyczen(){
        return this.listaZyczen;
    }

    public Koszyk pobierzKoszyk() {
        return koszyk;
    }

    public void przepakuj() {

        listaZyczen.stream().toList().forEach(x -> {
            if(x.getCena(getAbonament()) == -404) return;

            this.koszyk.add(x);
            listaZyczen.remove(x);
        });

    }
}
