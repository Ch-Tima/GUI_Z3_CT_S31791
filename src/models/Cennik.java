package models;

import java.util.*;

public class Cennik {

    private static Cennik cennik;
    private List<Cena> cenaList = new ArrayList<>();

    private Cennik(){};

    public void dodaj(Termin termin, String imie, int cena1, int cena2, int cena3, int limit){
        HashMap<Integer, Integer> map = new HashMap<>();
        if(cena1 <= 0){
            map.put(1, 0);
        }else {
            map.put(1, cena1);
            if(cena2 > 0){
                map.put(2, cena2);
                if(cena3 > 0){
                    map.put(3, cena3);
                }
            }
        }

        cenaList.add(new Cena(termin, imie, (Math.max(limit, 1)), map));
    }

    public void dodaj(Termin termin, String imie, int cena1, int cena2, int limit){
        dodaj(termin, imie, cena1, cena2, -1, limit);
    }

    public void dodaj(Termin termin, String imie, int cena1, int cena2){
        dodaj(termin, imie, cena1, cena2, -1, 1);
    }

    public void dodaj(Termin termin, String imie){
        dodaj(termin, imie, -1, -1, -1, 1);
    }

    public List<Cena> getCenaList() {
        return cenaList;
    }

    public static Cennik pobierzCennik(){
        if(Cennik.cennik == null)
            cennik = new Cennik();
        return cennik;
    }

    public enum Termin{
        KROTKI,
        SREDNI,
        DLUGI,
        DARMO
    }

    public class Cena{
        private Termin termin;
        private String imie;
        private int limit;
        private HashMap<Integer, Integer> map;

        public Cena(Termin termin, String imie, int limit, HashMap<Integer, Integer> map) {
            this.termin = termin;
            this.imie = imie;
            this.limit = limit;
            this.map = map;
        }

        public Termin getTermin() {
            return termin;
        }

        public String getImie() {
            return imie;
        }

        public int getLimit() {
            return limit;
        }

        public HashMap<Integer, Integer> getMap() {
            return map;
        }

        @Override
        public String toString() {
            String str = "termin: " + termin.name() + ", imie:" + imie + ", limit: " + limit + ", ceny: ";

            for (Iterator<Map.Entry<Integer, Integer>> iterator = map.entrySet().iterator(); iterator.hasNext();) {
                Map.Entry<Integer, Integer> entry = iterator.next();
                str += "cena_" + entry.getKey() + ": " + entry.getValue() + (iterator.hasNext() ? ", " : "");
            }

            return str;
        }
    }

}
