package de.shop.bestellverwaltung.domain;

import java.io.Serializable;
import java.net.URI;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import de.shop.kundenverwaltung.domain.AbstractKunde;

@XmlRootElement
public class Bestellung implements Serializable {
	private static final long serialVersionUID = 1618359234119003714L;
	
	private Long id;
	private BigDecimal gesamtpreis;
	private boolean ausgeliefert;
	
	@XmlTransient
	private AbstractKunde kunde;
	
	private URI kundeUri;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public boolean isAusgeliefert() {
		return ausgeliefert;
	}
	public void setAusgeliefert(boolean ausgeliefert) {
		this.ausgeliefert = ausgeliefert;
	}
	public AbstractKunde getKunde() {
		return kunde;
	}
	public void setKunde(AbstractKunde kunde) {
		this.kunde = kunde;
	}
	public URI getKundeUri() {
		return kundeUri;
	}
	public void setKundeUri(URI kundeUri) {
		this.kundeUri = kundeUri;
	}
	public BigDecimal getGesamtpreis() {
		return gesamtpreis;
	}
	public void setGesamtpreis(BigDecimal gesamtpreis) {
		this.gesamtpreis = gesamtpreis;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (ausgeliefert ? 1231 : 1237);
		result = prime * result
				+ ((gesamtpreis == null) ? 0 : gesamtpreis.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((kunde == null) ? 0 : kunde.hashCode());
		result = prime * result
				+ ((kundeUri == null) ? 0 : kundeUri.hashCode());
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
		Bestellung other = (Bestellung) obj;
		if (ausgeliefert != other.ausgeliefert)
			return false;
		if (gesamtpreis == null) {
			if (other.gesamtpreis != null)
				return false;
		} else if (!gesamtpreis.equals(other.gesamtpreis))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (kunde == null) {
			if (other.kunde != null)
				return false;
		} else if (!kunde.equals(other.kunde))
			return false;
		if (kundeUri == null) {
			if (other.kundeUri != null)
				return false;
		} else if (!kundeUri.equals(other.kundeUri))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Bestellung [id=" + id + ", gesamtpreis=" + gesamtpreis
				+ ", ausgeliefert=" + ausgeliefert + ", kundeUri=" + kundeUri
				+ "]";
	}
}
