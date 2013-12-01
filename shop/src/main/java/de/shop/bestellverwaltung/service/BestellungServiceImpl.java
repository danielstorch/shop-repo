package de.shop.bestellverwaltung.service;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.Size;

import de.shop.bestellverwaltung.domain.Bestellung;
import de.shop.kundenverwaltung.domain.Kunde;
import de.shop.util.Mock;

public class BestellungServiceImpl implements BestellungService, Serializable {
	private static final long serialVersionUID = -904844601529273628L;
	
	
	@Override
	public Bestellung findBestellungById(Long id) {
		// TODO Datenbanzugriffsschicht statt Mock
        return Mock.findBestellungById(id);
	}
	
	@Override
	public Kunde findKundeByBestellungId(Long id) {
		// TODO Datenbanzugriffsschicht statt Mock
        return Mock.findKundeByBestellungId(id);
	}
	
	@Override
	@Size(min = 1, message = "{bestellung.notFound.kunde}")
	public List<Bestellung> findBestellungenByKunde(Kunde kunde) {
		// TODO Datenbanzugriffsschicht statt Mock
		return Mock.findBestellungenByKunde(kunde);
	}
	
	@Override
	public Bestellung createBestellung(Bestellung bestellung) {
		// TODO Datenbanzugriffsschicht statt Mock
		bestellung = Mock.createBestellung(bestellung);
		//für email
		//event.fire(bestellung);
		
		return bestellung;
	}
}
