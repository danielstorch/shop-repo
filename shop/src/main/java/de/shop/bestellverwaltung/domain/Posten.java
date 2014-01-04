package de.shop.bestellverwaltung.domain;

import java.io.Serializable;
import java.net.URI;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Index;
import javax.persistence.Transient;
import javax.validation.constraints.Min;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import de.shop.artikelverwaltung.domain.Artikel;
import de.shop.util.persistence.AbstractAuditable;

@Entity
@Table(indexes = {
		@Index(columnList = "bestellung_fk"),
		@Index(columnList = "artikel_fk" )
})
@NamedQueries({
    @NamedQuery(name  = Posten.FIND_LADENHUETER,
   	            query = "SELECT a"
   	            	    + " FROM   Artikel a"
   	            	    + " WHERE  a NOT IN (SELECT bp.artikel FROM Posten bp)")
})
@XmlRootElement
public class Posten extends AbstractAuditable implements Serializable {
	private static final long serialVersionUID = -8996268150091727718L;
	
	private static final String PREFIX = "Posten.";
	public static final String FIND_LADENHUETER = PREFIX + "findLadenhueter";

	private static final int ANZAHL_MIN = 1;

	@Id
	@GeneratedValue
	@Basic(optional = false)
	private Long id;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "artikel_fk", nullable = false)
	@XmlTransient
	private Artikel artikel;
	
	@Basic(optional = false)
	@Min(value = ANZAHL_MIN, message = "{posten.anzahl.min}")
	private int anzahl;
	
	@Transient
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
		Posten other = (Posten) obj;
		if (anzahl != other.anzahl)
			return false;
		if (artikel == null) {
			if (other.artikel != null)
				return false;
		} else if (!artikel.equals(other.artikel))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Posten [id=" + id + ", anzahl=" + anzahl + ", artikelURI="
				+ artikelURI + "]";
	}
}
