package ba.unsa.etf.rpr;

import java.sql.*;
import java.util.ArrayList;

public class GeografijaDAO {
    private static GeografijaDAO instance=null;
    private PreparedStatement dajGradoveStatement,dajDrzaveStatement,dajGlavniGrad;
    private Connection conn;

    private GeografijaDAO(){
        try {
            conn= DriverManager.getConnection("/src/main/resources/jdbc:sqlite:baza.db");
            dajDrzaveStatement=conn.prepareStatement("SELECT naziv FROM drzava");
            dajGradoveStatement=conn.prepareStatement("SELECT * FROM grad ORDER BY broj_stanovnika DESC");
            dajGlavniGrad=conn.prepareStatement("SELECT g.id,g.naziv,g.broj_stanovnika,g.drzava FROM grad g,drzava d WHERE g.drzava=d.id AND d.naziv=?");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static GeografijaDAO getInstance(){
        if(instance==null) instance=new GeografijaDAO();
        return instance;
    }

    public static void removeInstance(){
        if(instance!=null){
            try{
                instance.conn.close();
            }
            catch(SQLException e){
                e.printStackTrace();
            }
        }
        instance=null;
    }

    public ArrayList<Grad> gradovi(){
        ArrayList<Grad> rezultat=new ArrayList<>();
        try {
            ResultSet result=dajGradoveStatement.executeQuery();
            while(result.next()){
                Grad grad=new Grad(result.getInt(1),result.getString(2),result.getInt(3),result.getInt(4));
                rezultat.add(grad);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rezultat;
    }

    public Grad glavniGrad(String drzava){
        Grad grad;
        try {
            dajGlavniGrad.setString(1,drzava);
            ResultSet result=dajGlavniGrad.executeQuery();
            if(!result.isBeforeFirst()) return null;
            grad=new Grad(result.getInt(1),result.getString(2),result.getInt(3),result.getInt(4));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return grad;
    }

    void obrisiDrzavu(String drzava){
        try {
            PreparedStatement uzmiIdStatement=conn.prepareStatement("SELECT id FROM drzava WHERE naziv="+drzava);
            PreparedStatement obrisiDrzavuStatement=conn.prepareStatement("DELETE drzava WHERE naziv="+drzava);
            PreparedStatement obrisiGradoveStatement=conn.prepareStatement("DELETE grad WHERE drzava=?");
            ResultSet rezultatId=uzmiIdStatement.executeQuery();
            int id=rezultatId.getInt(1);
            obrisiDrzavuStatement.executeQuery();
            obrisiGradoveStatement.setInt(1,id);
            obrisiGradoveStatement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    void dodajGrad(Grad grad){
        try {
            PreparedStatement dodajGradStatement=conn.prepareStatement("INSERT INTO grad (id,naziv,broj_stanovnika,drzava) VALUES(?,?,?,?)");
            dodajGradStatement.setInt(1,grad.getId());
            dodajGradStatement.setString(2,grad.getNaziv());
            dodajGradStatement.setInt(3,grad.getBroj_stanovnika());
            dodajGradStatement.setInt(4,grad.getDrzava());
            dodajGradStatement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    void dodajDrzavu(Drzava drzava){
        try {
            PreparedStatement dodajDrzavuStatement=conn.prepareStatement("INSERT INTO drzava (id,naziv,glavni_grad) VALUES(?,?,?)");
            dodajDrzavuStatement.setInt(1,drzava.getId());
            dodajDrzavuStatement.setString(2, drzava.getNaziv());
            dodajDrzavuStatement.setInt(3,drzava.getGrad());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    void izmijeniGrad(Grad grad){
        try {
            PreparedStatement izmjeniGradStatement=conn.prepareStatement("UPDATE grad SET naziv='?',broj_stanovnika=?,drzava=? WHERE id=?");
            izmjeniGradStatement.setString(1,grad.getNaziv());
            izmjeniGradStatement.setInt(2,grad.getBroj_stanovnika());
            izmjeniGradStatement.setInt(3,grad.getDrzava());
            izmjeniGradStatement.setInt(4,grad.getId());
            izmjeniGradStatement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    Drzava nadjiDrzavu(String drzava){
        Drzava res;
        try {
            PreparedStatement nadjiDrzavuStatement=conn.prepareStatement("SELECT * FROM drzava WHERE naziv=?");
            nadjiDrzavuStatement.setString(1,drzava);
            ResultSet rezultat=nadjiDrzavuStatement.executeQuery();
            if(!rezultat.isBeforeFirst()) return null;
            res=new Drzava(rezultat.getInt(1),rezultat.getString(2),rezultat.getInt(3));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }
    String uzmiDrzavuOdGrada(Grad grad){
        String rezultat;
        try {
            PreparedStatement stmnt=conn.prepareStatement("SELECT naziv FROM drzava WHERE id="+grad.getDrzava());
            ResultSet result=stmnt.executeQuery();
            rezultat=result.getString(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rezultat;
    }
}
