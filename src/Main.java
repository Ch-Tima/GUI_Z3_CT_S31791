import models.*;

import java.util.List;
import models.pakiety.*;

import static models.Abonament.*;
import static models.Cennik.Termin.*;
import static models.PaymentType.*;

public class Main {
    public static void main(String[] args) {

        Cennik cennik = Cennik.pobierzCennik();

        cennik.dodaj(KROTKI, "5GB", 20, 15, 10, 3);
        cennik.dodaj(SREDNI, "10GB", 25, 20, 2);
        cennik.dodaj(DLUGI, "30GB", 35, 30);
        cennik.dodaj(DARMO, "1GB");

        System.out.println("list Cennik:");
        System.out.println(toArray(cennik.getCenaList()));

        Klient lte = new Klient("lte", 200, TAK);

        // Klient lte dodaje do listy życzeń różne pakiety:
        // "5GB" typu krotkoterminowego na 4 kolejne okresy
        // "10GB" typu średnioterminowego na 3 kolejne okresy,
        // "30GB" typu długoterminowego na 2 kolejne okresy,
        // "2GB" typu darmowego na 2 kolejne okresy (ale może mieć tylko 1 okres)
        lte.dodaj(new Krotki("5GB", 4));
        lte.dodaj(new Sredni("10GB", 3));
        lte.dodaj(new Dlugi("30GB", 2));
        lte.dodaj(new Darmo("2GB", 1));

        // Lista życzeń klienta lte
        ListaZyczen listaLte = lte.pobierzListeZyczen();

        System.out.println("Lista życzeń klienta \n" + listaLte);

        // Przed płaceniem, klient przepakuje pakiety z listy życzeń do koszyka (po uprzednim wyczyszczeniu).
        // Możliwe, że na liście życzeń są pakiety niemające ceny w cenniku,
        // w takim przypadku nie trafiłyby do koszyka
        Koszyk koszykLte = lte.pobierzKoszyk();
        lte.przepakuj();

        // Co jest na liście życzeń klienta lte
        System.out.println("Po przepakowaniu, lista życzeń klienta \n" + lte.pobierzListeZyczen());

        // Co jest w koszyku klienta lte
        System.out.println("Po przepakowaniu, koszyk klienta \n" + koszykLte);

        // Ile wynosi cena wszystkich pakietów typu długoterminowego w koszyku klienta lte
        System.out.println("Pakiety 5GB w koszyku klienta lte kosztowały:  " + cena(koszykLte, "5GB"));

        // Klient zapłaci...
        lte.zaplac(KARTA, false);	// płaci kartą płatniczą, prowizja 2%
        // true oznacza, że w przypadku braku środków aplikacja sam odłoży nadmiarowe pakiety,
        // false oznacza rezygnację z płacenia razem z wyczyszczeniem koszyka i listy życzeń

        // Ile klientowi lte zostało pieniędzy?
        System.out.println("\nPo zapłaceniu, klientowi lte zostało: " + lte.pobierzPortfel() + " zł");

        // Mogło klientowi zabraknąć srodków, wtedy, opcjonalnie, pakiety mogą być odkładane,
        // w przeciwnym przypadku, koszyk jest pusty po zapłaceniu
        System.out.println("Po zapłaceniu, koszyk klienta " + lte.pobierzKoszyk());
        System.out.println("Po zapłaceniu, koszyk klienta " + koszykLte);

        // Teraz przychodzi klient gsm,
        // deklaruje 65 zł na zamówienia
        Klient gsm = new Klient("gsm", 65, NIE);

        // Zamówił za dużo jak na tę kwotę
        gsm.dodaj(new Sredni("10GB", 2));
        gsm.dodaj(new Dlugi("30GB", 1));

        // Co klient gsm ma na swojej liście życzeń
        System.out.println("\nLista życzeń klienta gsm:\n" + gsm.pobierzListeZyczen());

        Koszyk koszykGsm = gsm.pobierzKoszyk();
        gsm.przepakuj();

        System.out.println("Po przepakowaniu, lista życzeń klienta " + gsm.pobierzListeZyczen());

        // A co jest w koszyku klienta gsm
        System.out.println("\nPo przepakowaniu, koszyk klienta\n" + gsm.pobierzKoszyk());

        // klient gsm płaci
        gsm.zaplac(PRZELEW, true);	// płaci przelewem, bez prowizji

        // Ile klientowi gsm zostało pieniędzy?
        System.out.println("\nPo zapłaceniu, klientowi gsm zostało: " + gsm.pobierzPortfel() + " zł");

        // Co zostało w koszyku klienta gsm (za mało pieniędzy miał)
        System.out.println("\nPo zapłaceniu, koszyk klienta \n" + koszykGsm);

        gsm.zwroc(DLUGI, "30GB", 1);	// zwrot (do koszyka) pakietu (na 1 okres) z ostatniej transakcji

        // Ile klientowi gsm zostało pieniędzy?
        System.out.println("Po zwrocie, klientowi gsm zostało: " + gsm.pobierzPortfel() + " zł\n");

        // Co zostało w koszyku klienta gsm
        System.out.println("Po zwrocie, koszyk klienta\n" + koszykGsm);

    }

    // cena pakietów danego typu z koszyka
    static int cena(Koszyk k, String nazwaPakietu) {
        int sum = 0;
        for (Pakiet p: k) {
            if(p.getImie().equals(nazwaPakietu)){
                sum += p.getIlosc() * p.getCena(k.getKlient().getAbonament());
            }
        }
        return sum;
    }

    static <T> String toArray(List<T> list){
        String str = "";
        for (Object o : list) {
            str += o.toString() + "\n";
        }
        return str;
    }

}