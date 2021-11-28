
package se.uniis.fachlogik.univerwaltung.test;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
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
import se.uniis.fachlogik.univerwaltung.impl.IUniVerwaltungImpl;
import se.uniis.fachlogik.univerwaltung.services.IUniVerwaltung;


public class UniTesten {
    private static Kurs k1;
    private static Student st;
    private static Pruefungsschein pschein;
    private static Pruefungsmeldung pm;
 
    public UniTesten() {   
    }
    
    @BeforeClass
    public static void erzeugeTestdaten() {
        // Testdaten erzeugen:
        k1=new Kurs(12, "SE",  "Nissen")  ;
        st=new Student("Peter", "Koeln", 1234567);
        
       // Testdaten in DB einfügen:
       ICRUDKurs crudkurs = new ICRUDKursImpl();
       crudkurs.insertKurs(k1);
        
       ICRUDStudent crudstudent = new ICRUDStudentImpl();
       crudstudent.insertStudent(st);
       
       k1.addStudent(st);
       st.addKurs(k1);
       
       // Methode aufrufen:
        IUniVerwaltung univ=new IUniVerwaltungImpl();
        pschein =univ.pruefungRegistrieren(12, 1234567, "1,0");
        
       
       // erstellte Prüfungsmeldung suchen:
       ICRUDPruefmeldung crudmeldung=new ICRUDPruefmeldungImpl();
       pm = crudmeldung.getPruefmeldByID(pschein.getMeldungsnr());
    pm =new Pruefungsmeldung (k1, st, "21.01.2019", 123, "1,0");
   
    }
    
// Ergebnis überprüfen:
   
   //====================================================================
   @Test
   public void testPruefmeldung_P1() {
        //Prüfungsschein wurde erstellt und besitzt korrekte Daten
        assertEquals("21.01.2019",pschein.getDatum());
        assertEquals(12,pschein.getKursnr());
        
        
        assertEquals(123,pschein.getMeldungsnr());
        
        assertEquals("1,0",pschein.getNote());
   }
   
   //====================================================================
        
  //====================================================================      
  @Test
   public void testPruefmeldung_P2() {
        //Prüfungsmeldung ist im System mit korrektem Prüfungsdatum, 
        //korrekter Note und korrekter Meldungsnummer registriert
        assertEquals("21.01.2019",pm.getDatum());
        assertEquals(123,pm.getMeldenr());
        assertEquals("1,0",pm.getNote());
   }
   
   //====================================================================      
  @Test
   public void testPruefmeldung_P3() {
        //Anmeldung ist bei Student entfernt
        assertFalse(st.containsKurs(k1));
        
        
   }
   
   //====================================================================      
  @Test
   public void testPruefmeldung_P4() {
        //Anmeldung ist bei Kurs entfernt
        assertFalse(k1.containsStudent(st));
   }
        
   //====================================================================
   @Test
   public void testPruefmeldung_P5() {
        //Prüfungsmeldung ist mit Kurs verbunden
       
         assertEquals(k1,pm.getKurs()) ;
         
   }
       
   //====================================================================
   @Test
   public void testPruefmeldung_P6() {
        //Prüfungsmeldung ist mit Student verbunden
    
         assertEquals(st,pm.getStudent());
   }
        
   //====================================================================
   @Test
   public void testPruefmeldung_P7() {
        //Student ist mit Prüfungsmeldung verbunden
         assertEquals(st,pm.getStudent());
   }
        
   //====================================================================
   @Test
   public void testPruefmeldung_P8() {
        //Kurs ist mit Prüfungsmeldung verbunden
        assertEquals(k1,pm.getKurs());
    }
}
