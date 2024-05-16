package models;

import models.pakiety.*;

import java.text.DecimalFormat;
import java.util.List;

public class Klient {

    private static int counter = 0;

    private int id;

    private String imie;
    private double balanc;
    private Abonament abonament;

    private ListaZyczen listaZyczen;
    private Koszyk koszyk;
    private Zakupione zakupionePakieties;


    public Klient(String imie, double balanc, Abonament abonament) {
        id = ++counter;
        this.imie = imie;
        this.balanc = balanc;
        this.abonament = abonament;
        listaZyczen = new ListaZyczen(this);
        koszyk = new Koszyk(this);
        zakupionePakieties = new Zakupione(this);
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

    public void zaplac(PaymentType type, boolean mode){
        double suma = getSuma(type);

        if(suma <= balanc){
            placic(suma);
            return;
        }

        if(!mode){
            koszyk.clear();
            listaZyczen.clear();
            return;
        }

        for (Pakiet pakiet: koszyk.stream().toList()){

            if(pakiet.getType().equals(Darmo.TYPE))
                continue;

            int staraIlosc = pakiet.getIlosc();

            while (pakiet.getIlosc() > 1){
                pakiet.setIlosc(pakiet.getIlosc() - 1);
                suma = getSuma(type);
                if(suma <= balanc){
                    Pakiet copy = getCopyPakiet(pakiet, staraIlosc - pakiet.getIlosc());
                    listaZyczen.add(copy);
                    placic(suma);
                    return;
                }
            }

            if(pakiet.getIlosc() == 1){
                listaZyczen.add(pakiet);
                koszyk.remove(pakiet);
                suma = getSuma(type);

                if(suma <= balanc){
                    placic(suma);
                    return;
                }
            }

        }

    }

    public void zwroc(Cennik.Termin termin, String imie, int iloscZwrotu){

        if (termin == Cennik.Termin.DARMO)
            return;

        int i = zakupionePakieties.size();

        for (Zakupione.ZakupionePakiety pakiety: zakupionePakieties.stream().toList()) {
            if(pakiety.getPakiet().getImie().equals(imie)){

                if(pakiety.getPakiet().isEqualsType(termin)){

                    if(pakiety.getPakiet().getIlosc() >= iloscZwrotu){//zwrot

                        balanc = balanc + (iloscZwrotu * pakiety.getCenaOryginalna()); //Zwracać pieniądze

                        //szukam duplikatu w koszu
                        Pakiet pakietWKoszu = koszyk.stream().filter(x -> x.equals(pakiety.getPakiet())).findFirst().orElse(null);//find copy

                        if(pakiety.getPakiet().getIlosc() - iloscZwrotu == 0){//Jesli ilosc w paketi równa jednemu
                            zakupionePakieties.remove(pakiety);//usuwamy z zakupionePakieties

                            if(pakietWKoszu != null){
                                pakietWKoszu.setIlosc(pakietWKoszu.getIlosc() + iloscZwrotu);//dodaj ilość zwracaną
                            } else {
                                koszyk.add(pakiety.getPakiet());//Dodaj nowe
                            }

                        }else {
                            if(pakietWKoszu != null){
                                pakietWKoszu.setIlosc(pakietWKoszu.getIlosc() + iloscZwrotu);//dodaj ilość zwracaną
                            } else {
                                koszyk.add(getCopyPakiet(pakiety.getPakiet(), iloscZwrotu));//Dodaj nowe
                            }

                            pakiety.getPakiet().setIlosc(pakiety.getPakiet().getIlosc() - iloscZwrotu);
                        }

                        return;

                    }else {
                        System.out.println("Nie mogę zwrócić " + iloscZwrotu +" paketow, gdy kupiłem " + pakiety.getPakiet().getIlosc());
                    }

                }else {
                    System.out.println("Nie znaleźliśmy takiego pakietu");
                }

            }
        }

        System.out.println("Nie znaleźliśmy takiego pakietu");

    }

    private Pakiet getCopyPakiet(Pakiet pakiet, int ilosc) {
        Pakiet copy = null;

        if(pakiet.getType().equals(Dlugi.TYPE))
            copy = new Dlugi(pakiet.getImie(), ilosc);
        else if(pakiet.getType().equals(Sredni.TYPE))
            copy = new Sredni(pakiet.getImie(), ilosc);
        else
            copy = new Krotki(pakiet.getImie(), ilosc);

        return copy;
    }

    private void placic(double suma){
        balanc = balanc - suma;
        zakupionePakieties.add(koszyk);
        koszyk.clear();
        przepakuj();
    }

    private double getSuma(PaymentType type){
        double suma = 0;
        for (Pakiet pakiet: koszyk) {
            suma += pakiet.getIlosc() * pakiet.getCena(abonament);
        }

        if(type == PaymentType.KARTA)
            suma = suma + (suma * 0.02); //0.02 == 2% | (2/100) == 2%

        return suma;
    }


    public double pobierzPortfel(){
        return balanc;
    }

}
