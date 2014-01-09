package de.shop.bestellverwaltung.service;

import java.util.List;

import de.shop.artikelverwaltung.domain.Artikel;
import de.shop.bestellverwaltung.domain.Bestellung;
import de.shop.kundenverwaltung.domain.Kunde;

public interface BestellungService {
	public enum FetchType { NUR_BESTELLUNG }
//	Bestellung findBestellungById(Long id);
	Bestellung findBestellungById(Long id, FetchType fetch);
	Kunde findKundeById(Long id);
	List<Bestellung> findBestellungenByKunde(Kunde kunde);
	List<Bestellung> findBestellungenByIds(List<Long> ids, FetchType fetch);
//	Bestellung createBestellung(Bestellung bestellung);
	Bestellung createBestellung(Bestellung bestellung, Long kundeId);
	Bestellung createBestellung(Bestellung bestellung, Kunde kunde);
	List<Artikel> ladenhueter(int anzahl);
//	Kunde findKundeByBestellungId(Long id);
}
