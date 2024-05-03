import models.*;

import java.util.List;
import models.pakiety.*;
import static models.Cennik.Termin.*;

public class Main {
    public static void main(String[] args) {

        Cennik cennik = Cennik.pobierzCennik();

        cennik.dodaj(KROTKI, "5GB", 20, 15, 10, 3);
        cennik.dodaj(SREDNI, "10GB", 25, 20, 2);
        cennik.dodaj(DLUGI, "30GB", 35, 30);
        cennik.dodaj(DARMO, "1GB");

        System.out.println("list Cennik:");
        System.out.println(toArray(cennik.getCenaList()));

        Klient lte = new Klient("lte", 200, Abonament.TAK);

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
    }

    // cena pakietów danego typu z koszyka
    static int cena(Koszyk k, String nazwaPakietu) {
        /*<- tu trzeba wpisać ciało metody */
        return -1;
    }

    static <T> String toArray(List<T> list){
        String str = "";
        for (Object o : list) {
            str += o.toString() + "\n";
        }
        return str;
    }

}