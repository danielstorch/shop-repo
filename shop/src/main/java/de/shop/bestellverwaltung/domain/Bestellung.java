package de.shop.bestellverwaltung.domain;

import java.io.Serializable;
import java.net.URI;
import java.math.BigDecimal;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.validator.constraints.NotEmpty;

import de.shop.kundenverwaltung.domain.Kunde;

@XmlRootElement
public class Bestellung implements Serializable {
	private static final long serialVersionUID = 1618359234119003714L;
	
	@NotNull
	private Long id;
	
	@NotNull(message = "{bestellverwaltung.bestellung.gesamtpreis.notNull}")
	private BigDecimal gesamtpreis;
	
	private boolean ausgeliefert;
	
	@NotEmpty(message = "{bestellverwaltung.bestellung.posten.notEmpty}")
	@Valid
	private List<Posten> posten;
	
	@XmlTransient
	private Kunde kunde;
	
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
	public Kunde getKunde() {
		return kunde;
	}
	public void setKunde(Kunde kunde) {
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
	public List<Posten> getPosten() {
		return posten;
	}
	public void setPosten(List<Posten> posten) {
		this.posten = posten;
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
		result = prime * result + ((posten == null) ? 0 : posten.hashCode());
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
		if (posten == null) {
			if (other.posten != null)
				return false;
		} else if (!posten.equals(other.posten))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Bestellung [id=" + id + ", gesamtpreis=" + gesamtpreis
				+ ", ausgeliefert=" + ausgeliefert + ", posten=" + posten
				+ ", kundeUri=" + kundeUri + "]";
	}
	//Rechnet den gesamtpreis aus aller posten
	public BigDecimal gesamtpreisBerechnung() {
		BigDecimal gp = new BigDecimal(0.0);
		
		for(Posten p : posten) {
			gp = gp.add((p.getArtikel().getPreis()).multiply(new BigDecimal(p.getAnzahl())));
		}
		
		return gp;
	}
}
