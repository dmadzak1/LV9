package ba.unsa.etf.rpr;

public class Grad {
    private int id;
    private int broj_stanovnika;
    private int drzava;
    private String naziv;

    public Grad(int id, String naziv,int broj_stanovnika, int drzava) {
        this.id = id;
        this.broj_stanovnika = broj_stanovnika;
        this.drzava = drzava;
        this.naziv = naziv;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBroj_stanovnika() {
        return broj_stanovnika;
    }

    public void setBroj_stanovnika(int broj_stanovnika) {
        this.broj_stanovnika = broj_stanovnika;
    }

    public int getDrzava() {
        return drzava;
    }

    public void setDrzava(int drzava) {
        this.drzava = drzava;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

}
