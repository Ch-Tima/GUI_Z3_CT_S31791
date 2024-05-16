package models.pakiety;

import models.Abonament;
import models.Cennik;

public class Krotki extends Pakiet{

    public static final String TYPE = "krótkoterminowy";

    public Krotki(String imie, int ilosc) {
        super(imie, ilosc);
    }

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public int getCena(Abonament abonament) {
        Cennik c = Cennik.pobierzCennik();
        Cennik.Cena cena = c. getCenaList().stream().filter(x -> x.getImie().equals(this.getImie()) && x.getTermin() == Cennik.Termin.KROTKI).findFirst().orElse(null);

        if(cena == null) return -404;

        if(getIlosc() == 1) return cena.getMap().get(1);
        else if(getIlosc() > 1 && getIlosc() < 4) return cena.getMap().get(2);
        else return cena.getMap().get(3);

    }

    @Override
    public boolean isEqualsType(Cennik.Termin termin) {
        return termin == Cennik.Termin.KROTKI;
    }
}
