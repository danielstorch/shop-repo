//package de.shop.util;
//
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//import java.math.BigDecimal;
//
//import javax.validation.Valid;
//
//import de.shop.bestellverwaltung.domain.Bestellung;
//import de.shop.kundenverwaltung.domain.Kunde;
//import de.shop.kundenverwaltung.domain.Adresse;
//import de.shop.kundenverwaltung.domain.HobbyType;
//import de.shop.artikelverwaltung.domain.Artikel;
//import de.shop.bestellverwaltung.domain.Posten;
//
///**
// * Emulation des Anwendungskerns
// */
//public final class Mock {
//	private static final int MAX_ID = 99;
//	private static final int MAX_KUNDEN = 8;
//	private static final int MAX_BESTELLUNGEN = 4;
//	private static final int MAX_ARTIKEL = 55;
//
//	public static Kunde findKundeById(Long id) {
//		if (id > MAX_ID) {
//			return null;
//		}
//		
//		final Kunde kunde =  new Kunde();
//		kunde.setId(id);
//		kunde.setNachname("Nachname");
//		kunde.setVorname("Vorname");
//		kunde.setEmail("" + id + "@hska.de");
//		
//		final Adresse adresse = new Adresse();
//		adresse.setId(id + 1);        // andere ID fuer die Adresse
//		adresse.setPlz("12345");
//		adresse.setOrt("Testort");
//		adresse.setStrasse("Teststrasse");
//		adresse.setHausnr("12");
//		adresse.setKunde(kunde);
//		kunde.setAdresse(adresse);
//		
////		if (kunde.getClass().equals(Kunde.class)) {
////			final Kunde privatkunde = (Kunde) kunde;
//			final Set<HobbyType> hobbies = new HashSet<>();
//			hobbies.add(HobbyType.LESEN);
//			hobbies.add(HobbyType.REISEN);
//			kunde.setHobbies(hobbies);
////		}
//		
//		return kunde;
//	}
//
//	public static List<Kunde> findAllKunden() {
//		final int anzahl = MAX_KUNDEN;
//		final List<Kunde> kunden = new ArrayList<>(anzahl);
//		for (int i = 1; i <= anzahl; i++) {
//			final Kunde kunde = findKundeById(Long.valueOf(i));
//			kunden.add(kunde);			
//		}
//		return kunden;
//	}
//
//	public static List<Kunde> findKundenByNachname(String nachname) {
//		final int anzahl = nachname.length();
//		final List<Kunde> kunden = new ArrayList<>(anzahl);
//		for (int i = 1; i <= anzahl; i++) {
//			final Kunde kunde = findKundeById(Long.valueOf(i));
//			kunde.setNachname(nachname);
//			kunden.add(kunde);			
//		}
//		return kunden;
//	}
//	
//	public static List<Kunde> findKundenByVorname(String vorname) {
//		final int anzahl = vorname.length();
//		final List<Kunde> kunden = new ArrayList<>(anzahl);
//		for (int i = 1; i <= anzahl; i++) {
//			final Kunde kunde = findKundeById(Long.valueOf(i));
//			kunde.setVorname(vorname);
//			kunden.add(kunde);			
//		}
//		return kunden;
//	}
//	
//
//	public static List<Bestellung> findBestellungenByKunde(Kunde kunde) {
//		// Beziehungsgeflecht zwischen Kunde und Bestellungen aufbauen
//		final int anzahl = kunde.getId().intValue() % MAX_BESTELLUNGEN + 1;  // 1, 2, 3 oder 4 Bestellungen
//		final List<Bestellung> bestellungen = new ArrayList<>(anzahl);
//		for (int i = 1; i <= anzahl; i++) {
//			final Bestellung bestellung = findBestellungById(Long.valueOf(i));
//			bestellung.setKunde(kunde);
//			bestellungen.add(bestellung);			
//		}
//		kunde.setBestellungen(bestellungen);
//		
//		return bestellungen;
//	}
//
//	public static Kunde findKundeByBestellungId(Long id) {
//		final Bestellung bestellung = findBestellungById(id);
//		return bestellung.getKunde();
//	}
//	
//	public static Bestellung findBestellungById(Long id) {
//		if (id > MAX_ID) {
//			return null;
//		}
//
//		final Kunde kunde = findKundeById(id + 1);  // andere ID fuer den Kunden
//
//		final Bestellung bestellung = new Bestellung();
//		bestellung.setId(id);
//		bestellung.setAusgeliefert(false);
//		bestellung.setKunde(kunde);
//		final List<Posten> posten = new ArrayList<>();
//		for (int zl = 1; zl < 4; ++zl) {
//			final Posten p = new Posten();
//			p.setAnzahl(5);
//			p.setArtikel(findArtikelById(Long.valueOf(zl)));
//			p.setId(Long.valueOf(zl));
//			posten.add(p);
//		}
//		bestellung.setPosten(posten);
//		bestellung.setGesamtpreis(bestellung.gesamtpreisBerechnung());
//		
//		return bestellung;
//	}
//	
//	// Create bestellung 
//	public static Bestellung createBestellung(Bestellung bestellung) {
//		final Kunde kunde = findKundeById(bestellung.getId() + 1);  // andere ID fuer den Kunden
//		bestellung.setId(bestellung.getId());
//		bestellung.setAusgeliefert(false);
//		bestellung.setKunde(kunde);
//		final List<Posten> posten = bestellung.getPosten();
//		final List<Posten> finalposten = new ArrayList<>();
//		Long id = new Long(1);
//		for (Posten post : posten) {
//			final Posten p = new Posten();
//			p.setAnzahl(post.getAnzahl());
//			p.setArtikel(findArtikelById(id));
//			p.setId(id);
//			finalposten.add(p);
//			id++;
//		}
//
//		bestellung.setPosten(finalposten);
//		bestellung.setGesamtpreis(bestellung.gesamtpreisBerechnung());
//		
//		return bestellung;
//	}
//
//	public static Kunde createKunde(Kunde kunde) {
//		// Neue IDs fuer Kunde und zugehoerige Adresse
//		// Ein neuer Kunde hat auch keine Bestellungen
//		final String nachname = kunde.getNachname();
//		kunde.setId(Long.valueOf(nachname.length()));
//		final Adresse adresse = kunde.getAdresse();
//		adresse.setId((Long.valueOf(nachname.length())) + 1);
//		adresse.setKunde(kunde);
//		kunde.setBestellungen(null);
//		
//		System.out.println("Neuer Kunde: " + kunde);
//		return kunde;
//	}
//
//	public static void updateKunde(Kunde kunde) {
//		System.out.println("Aktualisierter Kunde: " + kunde);
//	}
//
//	public static void deleteKunde(Long kundeId) {
//		System.out.println("Kunde mit ID=" + kundeId + " geloescht");
//	}
//	
//	
//	// Gibt ein Artikel aus
//	public static Artikel findArtikelById(Long id) {
//		if (id > MAX_ARTIKEL) {
//			return null;
//		}
//		
//		final Artikel artikel = new Artikel();
//		artikel.setId(id);
//		artikel.setPreis(new BigDecimal(50.0 + id));
//		artikel.setBezeichnung("Bezeichnung_" + id);
//		return artikel;
//	}
//	//create Artikel  ohne Id (wird automatisch ermitellt)
//	public static Artikel createArtikel(Artikel artikel) {
//		final String bezeichnung = artikel.getBezeichnung();
//		artikel.setId(Long.valueOf(bezeichnung.length()));
//		artikel.setPreis(artikel.getPreis());
//		
//		System.out.println("Neuer Artikel: " + artikel.toString());
//		return artikel;
//	}
//	//update Artikel
//	public static void updateArtikel(Artikel artikel) {
//		System.out.println("Update Artikel mit ID = " + artikel.getId());
//	}
//	
//	private Mock() { /**/ }
//}
