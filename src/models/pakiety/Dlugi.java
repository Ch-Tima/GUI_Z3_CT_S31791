package models.pakiety;

import models.Abonament;
import models.Cennik;

public class Dlugi extends Pakiet{
    public Dlugi(String imie, int ilosc) {
        super(imie, ilosc);
    }

    @Override
    public String getType() {
        return "długotermoniwy";
    }

    @Override
    public int getCena(Abonament abonament) {
        Cennik c = Cennik.pobierzCennik();
        Cennik.Cena cena = c. getCenaList().stream().filter(x -> x.getImie().equals(this.getImie()) && x.getTermin() == Cennik.Termin.DLUGI).findFirst().orElse(null);

        if(cena == null) return -404;

        if(abonament == Abonament.TAK) return cena.getMap().get(2);
        return cena.getMap().get(1);

    }

}
