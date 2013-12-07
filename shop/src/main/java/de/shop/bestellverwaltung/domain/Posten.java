package de.shop.bestellverwaltung.domain;

import java.io.Serializable;
import java.net.URI;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import de.shop.artikelverwaltung.domain.Artikel;

@XmlRootElement
public class Posten implements Serializable {
	private static final long serialVersionUID = -8996268150091727718L;

	private static final int ANZAHL_MIN = 1;

	private Long id;
	
	//@NotNull(message = "{bestellverwaltung.posten.artikel.notNull}")
	@XmlTransient
	private Artikel artikel;
	
	@Min(value = ANZAHL_MIN, message = "{posten.anzahl.min}")
	private int anzahl;
	
	private URI artikelURI;
	
	public Artikel getArtikel() {
		return artikel;
	}
	public void setArtikel(Artikel artikel) {
		this.artikel = artikel;
	}
	public int getAnzahl() {
		return anzahl;
	}
	public void setAnzahl(int anzahl) {
		this.anzahl = anzahl;
	}
	public URI getArtikelURI() {
		return artikelURI;
	}
	public void setArtikelURI(URI artikelURI) {
		this.artikelURI = artikelURI;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + anzahl;
		result = prime * result + ((artikel == null) ? 0 : artikel.hashCode());
		result = prime * result
				+ ((artikelURI == null) ? 0 : artikelURI.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Posten other = (Posten) obj;
		if (anzahl != other.anzahl)
			return false;
		
		if (artikel == null) {
			if (other.artikel != null)
				return false;
		}
		else if (!artikel.equals(other.artikel))
			return false;
		
		if (artikelURI == null) {
			if (other.artikelURI != null)
				return false;
		}
		else if (!artikelURI.equals(other.artikelURI))
			return false;
		
		if (id == null) {
			if (other.id != null)
				return false;
		}
		else if (!id.equals(other.id))
			return false;
		
		return true;
	}
	
	@Override
	public String toString() {
		return "Posten [id=" + id + ", anzahl=" + anzahl + ", artikelURI="
				+ artikelURI + "]";
	}
}
