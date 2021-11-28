/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.uniis.fachlogik.univerwaltung.impl;
import se.uniis.datenhaltung.db.entities.Kurs;
import se.uniis.datenhaltung.db.entities.Pruefungsmeldung;
import se.uniis.datenhaltung.db.entities.Student;
import se.uniis.datenhaltung.db.impl.ICRUDKursImpl;
import se.uniis.datenhaltung.db.impl.ICRUDPruefmeldungImpl;
import se.uniis.datenhaltung.db.impl.ICRUDStudentImpl;
import se.uniis.datenhaltung.db.services.ICRUDKurs;
import se.uniis.datenhaltung.db.services.ICRUDPruefmeldung;
import se.uniis.datenhaltung.db.services.ICRUDStudent;
import se.uniis.fachlogik.grenzklassen.Pruefungsschein;
import se.uniis.fachlogik.univerwaltung.services.IUniVerwaltung;
/**
 * Steuerungsklasse IUniVerwaltungImpl
 * implementiert alle Anwendungsfälle, die sich mit der
 * Verwaltung von einer Uni beschäftigen
 * 
 * @author gruppe 46
 * @version 1.0
 * 
 */
public class IUniVerwaltungImpl implements IUniVerwaltung {
/**
 * Methode zur Realisierung des Anwendungsfall 
 * /LF20/ Prüfungsergebnis registrieren 
 * 
 * @param kursnr Kursnummer der zu registrierender Prüfung
 * @param mnr Matrikelnummer des Studenten
 * @param note Note der Prüfung
 * @return Pruefungsschein
 * 
 */
    @Override
    public Pruefungsschein pruefungRegistrieren(int kursnr, int mnr, String note) {
        ICRUDKurs crudkurs = new ICRUDKursImpl();
        Kurs k1 = crudkurs.getKursByID(kursnr);
        
        ICRUDStudent crudstudent = new ICRUDStudentImpl();
        Student st = crudstudent.getStudentByID(mnr);
        Pruefungsschein pschein=null ; 
        if ( st.containsKurs(k1)==false) {
            return pschein;}
        else {
            Pruefungsmeldung pm = new Pruefungsmeldung(k1,st,"21.01.2019",mnr, note);
            
            ICRUDPruefmeldung crudpruefmeldung =new ICRUDPruefmeldungImpl();
            if (crudpruefmeldung.insertPruefmeld(pm) == true ){
                pschein = new Pruefungsschein(kursnr,"21.01.2019",note,123);
            }
           
            st.delKurs(k1);
            k1.delStudent(st);
            return pschein;
        }
    
    }
}
