package de.shop.kundenverwaltung.service;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import de.shop.kundenverwaltung.domain.Kunde;
import de.shop.util.Mock;

public class KundeService implements Serializable {
	private static final long serialVersionUID = 4360325837484294309L;
	
	@NotNull(message = "{kunde.notFound.id}")
	public Kunde findKundeById(Long id) {
		if (id == null) {
			return null;
		}
		// TODO Datenbanzugriffsschicht statt Mock
		return Mock.findKundeById(id);
	}
	
	public List<Kunde> findAllKunden() {
		// TODO Datenbanzugriffsschicht statt Mock
		return Mock.findAllKunden();
	}

	@Size(min = 1, message = "{kunde.notFound.nachname}")
	public List<Kunde> findKundenByNachname(String nachname) {
		// TODO Datenbanzugriffsschicht statt Mock
		return Mock.findKundenByNachname(nachname);
	}
	
	@Size(min = 1, message = "{kunde.notFound.nachname}")
	public List<Kunde> findKundenByVorname(String vorname) {
		// TODO Datenbanzugriffsschicht statt Mock
		return Mock.findKundenByNachname(vorname);
	}
	
	public Kunde createKunde(Kunde kunde) {
		if (kunde == null) {
			return kunde;
		}

		// TODO Datenbanzugriffsschicht statt Mock
		kunde = Mock.createKunde(kunde);

		return kunde;
	}
	
	public Kunde updateKunde(Kunde kunde) {
		if (kunde == null) {
			return null;
		}

		// TODO Datenbanzugriffsschicht statt Mock
		Mock.updateKunde(kunde);
		
		return kunde;
	}
	
	public void deleteKunde(Long kundeId) {
		final Kunde kunde = findKundeById(kundeId);  // Kein Aufruf als Business-Methode
		if (kunde == null) {
			return;
		}
		
		// TODO Datenbanzugriffsschicht statt Mock
		Mock.deleteKunde(kundeId);
	}
	
}
