package models.pakiety;

import models.Abonament;
import models.Cennik;

public class Sredni extends Pakiet{
    public Sredni(String imie, int ilosc) {
        super(imie, ilosc);
    }

    @Override
    public String getType() {
        return "średnioterminowy";
    }

    @Override
    public int getCena(Abonament abonament) {
        Cennik c = Cennik.pobierzCennik();
        Cennik.Cena cena = c. getCenaList().stream().filter(x -> x.getImie().equals(this.getImie()) && x.getTermin() == Cennik.Termin.SREDNI).findFirst().orElse(null);

        if(cena == null) return -404;

        if(getIlosc() > 0 && getIlosc() <= 2) return cena.getMap().get(1);
        return cena.getMap().get(2);

    }



}
