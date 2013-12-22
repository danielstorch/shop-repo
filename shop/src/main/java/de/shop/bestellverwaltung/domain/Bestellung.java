package de.shop.bestellverwaltung.domain;

import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REMOVE;
import static javax.persistence.FetchType.EAGER;

import java.io.Serializable;
import java.net.URI;
import java.lang.invoke.MethodHandles;
import java.math.BigDecimal;
import java.util.List;
import static de.shop.util.Constants.KEINE_ID;

import org.jboss.logging.Logger;

import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedEntityGraphs;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PostPersist;
import javax.persistence.Table;
import javax.persistence.Index;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.validator.constraints.NotEmpty;

import de.shop.kundenverwaltung.domain.Kunde;
import de.shop.util.persistence.AbstractAuditable;

@Entity
@Table(indexes = @Index(columnList = "kunde_fk"))
@XmlRootElement
@NamedQueries({
	@NamedQuery(name  = Bestellung.FIND_BESTELLUNGEN_BY_KUNDE,
                query = "SELECT b"
			            + " FROM   Bestellung b"
						+ " WHERE  b.kunde = :" + Bestellung.PARAM_KUNDE),
	@NamedQuery(name  = Bestellung.FIND_KUNDE_BY_ID,
 			    query = "SELECT b.kunde"
                        + " FROM   Bestellung b"
  			            + " WHERE  b.id = :" + Bestellung.PARAM_ID)
})
//@NamedEntityGraphs({
//	@NamedEntityGraph(name = Bestellung.GRAPH_LIEFERUNGEN,
//					  attributeNodes = @NamedAttributeNode("lieferungen"))
//})
//@Cacheable
public class Bestellung extends AbstractAuditable implements Serializable {
	private static final long serialVersionUID = 1618359234119003714L;
	private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass());
	
	private static final String PREFIX = "Bestellung.";
	public static final String FIND_BESTELLUNGEN_BY_KUNDE = PREFIX + "findBestellungenByKunde";
	public static final String FIND_KUNDE_BY_ID = PREFIX + "findBestellungKundeById";
	
	public static final String PARAM_KUNDE = "kunde";
	public static final String PARAM_ID = "id";
	
//	public static final String GRAPH_LIEFERUNGEN = PREFIX + "lieferungen";
	
	@Id
	@GeneratedValue
	@Basic(optional = false)
	private Long id = KEINE_ID;
	
	@Digits(integer = 10, fraction = 2)
	private BigDecimal gesamtpreis;
	
	
	@Basic(optional = false)
	private boolean ausgeliefert;
	
	@OneToMany(fetch = EAGER, cascade = { PERSIST, REMOVE })
	@JoinColumn(name = "bestellung_fk", nullable = false)
	@NotEmpty(message = "{bestellverwaltung.bestellung.posten.notEmpty}")
	@Valid
	private List<Posten> posten;
	
	@ManyToOne
	@JoinColumn(name = "kunde_fk", nullable = false, insertable = false, updatable = false)
	@XmlTransient
	private Kunde kunde;
	
	@Transient
	private URI kundeUri;
	
	@PostPersist
	private void postPersist() {
		LOGGER.debugf("Neue Bestellung mit ID=%d", id);
	}
	
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
		
		for (Posten p : posten) {
			gp = gp.add((p.getArtikel().getPreis()).multiply(new BigDecimal(p.getAnzahl())));
		}
		
		return gp;
	}
}
