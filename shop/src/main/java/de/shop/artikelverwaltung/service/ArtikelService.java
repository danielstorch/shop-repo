package de.shop.artikelverwaltung.service;

import java.io.Serializable;

import de.shop.artikelverwaltung.domain.Artikel;
//import de.shop.util.interceptor.Log;
import de.shop.util.Mock;

public class ArtikelService implements Serializable {
	private static final long serialVersionUID = 2929027248430844352L;
	
	public Artikel findArtikelById(Long id) {
		// TODO id pruefen
		// TODO Datenbanzugriffsschicht statt Mock
		return Mock.findArtikelById(id);
	}
	
	public Artikel createArtikel(Artikel artikel) {	
		return Mock.createArtikel(artikel);
	}
	
	public void updateArtikel(Artikel artikel) {
		 Mock.updateArtikel(artikel);
	}
	
	
}
