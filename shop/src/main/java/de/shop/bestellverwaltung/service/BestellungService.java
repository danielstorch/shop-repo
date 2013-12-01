package de.shop.bestellverwaltung.service;

import java.util.List;

import de.shop.bestellverwaltung.domain.Bestellung;
import de.shop.kundenverwaltung.domain.Kunde;

public interface BestellungService {
	Bestellung findBestellungById(Long id);
	List<Bestellung> findBestellungenByKunde(Kunde kunde);
	Bestellung createBestellung(Bestellung bestellung);
	Kunde findKundeByBestellungId(Long id);
}