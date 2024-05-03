package models.pakiety;

import models.Abonament;
import models.Cennik;

public class Darmo extends Pakiet{
    public Darmo(String imie, int ilosc) {
        super(imie, ilosc);
    }

    @Override
    public String getType() {
        return "darmowy";
    }

    @Override
    public int getCena(Abonament abonament) {
        Cennik c = Cennik.pobierzCennik();
        Cennik.Cena cena = c. getCenaList().stream().filter(x -> x.getImie().equals(this.getImie()) && x.getTermin() == Cennik.Termin.DARMO).findFirst().orElse(null);

        if(cena == null) return -404;

        return 0;
    }

    @Override
    public String toString() {
        return getImie() + ", typ: " + getType() + ", ile: " + getIlosc() + " cena: ceny brak";
    }
}
