package ba.unsa.etf.rpr;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Spisak svih gradova: ");
        System.out.println(ispisiGradove());
        glavniGrad();
    }

    public static String ispisiGradove(){
        GeografijaDAO dao=GeografijaDAO.getInstance();
        String rezultat="";
        ArrayList<Grad> gradovi=dao.gradovi();
        for(Grad grad: gradovi){
            rezultat+=(grad.getNaziv()+"("+dao.uzmiDrzavuOdGrada(grad)+")"+"-"+grad.getBroj_stanovnika()+"\n");
        }
        GeografijaDAO.removeInstance();
        return rezultat;
    }

    public static void glavniGrad(){
        GeografijaDAO dao=GeografijaDAO.getInstance();
        System.out.println("Unesite naziv države: ");
        Scanner scanner=new Scanner(System.in);
        String drzava=scanner.nextLine();
        Grad grad=dao.glavniGrad(drzava);
        if(grad!=null )System.out.println("Glavni grad drzave "+drzava+" je "+grad.getNaziv());
        else System.out.println("Nepostojeća država");
        GeografijaDAO.removeInstance();
    }
}