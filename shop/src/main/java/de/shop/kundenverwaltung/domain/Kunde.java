package de.shop.kundenverwaltung.domain;

import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REMOVE;
import static javax.persistence.FetchType.EAGER;

import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.persistence.Index;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.persistence.UniqueConstraint;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedEntityGraphs;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import de.shop.bestellverwaltung.domain.Bestellung;
import de.shop.util.persistence.AbstractAuditable;

@Entity
@Table(name = "kunde", indexes = @Index(columnList = "nachname"))
@XmlRootElement
@NamedQueries({
	@NamedQuery(name  = Kunde.FIND_KUNDEN,
                query = "SELECT k"
				        + " FROM   Kunde k"),
	@NamedQuery(name  = Kunde.FIND_KUNDEN_ORDER_BY_ID,
		        query = "SELECT   k"
				        + " FROM  Kunde k"
		                + " ORDER BY k.id"),
	@NamedQuery(name  = Kunde.FIND_IDS_BY_PREFIX,
		        query = "SELECT   k.id"
		                + " FROM  Kunde k"
		                + " WHERE CONCAT('', k.id) LIKE :" + Kunde.PARAM_KUNDE_ID_PREFIX
		                + " ORDER BY k.id"),
	@NamedQuery(name  = Kunde.FIND_KUNDEN_BY_NACHNAME,
	            query = "SELECT k"
				        + " FROM   Kunde k"
	            		+ " WHERE  UPPER(k.nachname) = UPPER(:" + Kunde.PARAM_KUNDE_NACHNAME + ")"),
	@NamedQuery(name  = Kunde.FIND_KUNDEN_BY_VORNAME,
				query = "SELECT k"
	    		        + " FROM   Kunde k"
	    	       		+ " WHERE  UPPER(k.vorname) = UPPER(:" + Kunde.PARAM_KUNDE_VORNAME + ")"),
	@NamedQuery(name  = Kunde.FIND_NACHNAMEN_BY_PREFIX,
   	            query = "SELECT   DISTINCT k.nachname"
				        + " FROM  Kunde k "
	            		+ " WHERE UPPER(k.nachname) LIKE UPPER(:"
	            		+ Kunde.PARAM_KUNDE_NACHNAME_PREFIX + ")"),
   	@NamedQuery(name  = Kunde.FIND_KUNDE_BY_EMAIL,
   	            query = "SELECT DISTINCT k"
   			            + " FROM   Kunde k"
   			            + " WHERE  k.email = :" + Kunde.PARAM_KUNDE_EMAIL),
    @NamedQuery(name  = Kunde.FIND_KUNDEN_BY_PLZ,
	            query = "SELECT k"
				        + " FROM  Kunde k"
			            + " WHERE k.adresse.plz = :" + Kunde.PARAM_KUNDE_ADRESSE_PLZ),
//	@NamedQuery(name = Kunde.FIND_KUNDEN_BY_DATE,
//			    query = "SELECT k"
//			            + " FROM  Kunde k"
//			    		+ " WHERE k.seit = :" + Kunde.PARAM_KUNDE_SEIT),
//	@NamedQuery(name = Kunde.FIND_PRIVATKUNDEN_FIRMENKUNDEN,
//			    query = "SELECT k"
//			            + " FROM  AbstractKunde k"
//			    		+ " WHERE TYPE(k) IN (Privatkunde, Firmenkunde)")
})
@NamedEntityGraphs({
	@NamedEntityGraph(name = Kunde.GRAPH_BESTELLUNGEN,
					  attributeNodes = @NamedAttributeNode("bestellungen"))})
public class Kunde extends AbstractAuditable implements Serializable {
	private static final long serialVersionUID = -8477316271106761155L;
	
	private static final String NAME_PATTERN = "[A-Z\u00C4\u00D6\u00DC][a-z0-9+-_\u00E4\u00F6\u00FC\u00DF]+";
	private static final String EMAIL_PATTERN = "[\\w.%-]+@[\\w.%-]+\\.[A-Za-z]{2,4}";
    private static final String PREFIX_ADEL = "(o'|von|von der|von und zu|van)?";

    public static final String NACHNAME_PATTERN = PREFIX_ADEL + NAME_PATTERN + "(-" + NAME_PATTERN + ")?";
    public static final String VORNAME_PATTERN = NAME_PATTERN + "(-" + NAME_PATTERN + ")?";

    public static final int NAME_LENGTH_MIN = 2;
    public static final int NAME_LENGTH_MAX = 32;
    public static final int EMAIL_LENGTH_MAX = 32;
    
    private static final String PREFIX = "Kunde.";
	public static final String FIND_KUNDEN = PREFIX + "findKunden";
	public static final String FIND_KUNDEN_ORDER_BY_ID = PREFIX + "findKundenOrderById";
	public static final String FIND_IDS_BY_PREFIX = PREFIX + "findIdsByPrefix";
	public static final String FIND_KUNDEN_BY_NACHNAME = PREFIX + "findKundenByNachname";
	public static final String FIND_KUNDEN_BY_VORNAME = PREFIX + "findKundenByVorname";
	public static final String FIND_NACHNAMEN_BY_PREFIX = PREFIX + "findNachnamenByPrefix";
	public static final String FIND_KUNDE_BY_EMAIL = PREFIX + "findKundeByEmail";
	public static final String FIND_KUNDEN_BY_PLZ = PREFIX + "findKundenByPlz";
	public static final String FIND_KUNDEN_BY_DATE = PREFIX + "findKundenByDate";
	public static final String FIND_PRIVATKUNDEN_FIRMENKUNDEN = PREFIX + "findPrivatkundenFirmenkunden";
	
	public static final String PARAM_KUNDE_ID = "kundeId";
	public static final String PARAM_KUNDE_ID_PREFIX = "idPrefix";
	public static final String PARAM_KUNDE_NACHNAME = "nachname";
	public static final String PARAM_KUNDE_VORNAME = "vorname";
	public static final String PARAM_KUNDE_NACHNAME_PREFIX = "nachnamePrefix";
	public static final String PARAM_KUNDE_ADRESSE_PLZ = "plz";
	public static final String PARAM_KUNDE_SEIT = "seit";
	public static final String PARAM_KUNDE_EMAIL = "email";
	
	public static final String GRAPH_BESTELLUNGEN = PREFIX + "bestellungen";
	
    @Id
    @GeneratedValue
    @Basic(optional = false)
    private Long id;
	
	@NotNull(message = "{kunde.nachname.notNull}")
    @Size(min = NAME_LENGTH_MIN, max = NAME_LENGTH_MAX, message = "{kunde.nachname.length}")
    @Pattern(regexp = NACHNAME_PATTERN, message = "{kunde.nachname.pattern}")
	private String nachname;
	
	@NotNull(message = "{kunde.vorname.notNull}")
    @Size(min = NAME_LENGTH_MIN, max = NAME_LENGTH_MAX, message = "{kunde.vorname.length}")
    @Pattern(regexp = VORNAME_PATTERN, message = "{kunde.vorname.pattern}")
	private String vorname;
	
//	@Email()
	@Column(unique = true)
	@NotNull(message = "{kunde.email.notNull}")
	@Size(max = EMAIL_LENGTH_MAX, message = "{kunde.email.length}")
	@Pattern(regexp = EMAIL_PATTERN, message = "{kunde.email.pattern}")
	private String email;
	
//	@OneToOne(cascade = {PERSIST, REMOVE}, mappedBy = "kunde")
	@OneToOne(mappedBy = "kunde", cascade = PERSIST)
	@NotNull(message = "{kunde.adresse.notNull}")
	@Valid
	private Adresse adresse;
	
	@OneToMany
	@JoinColumn(name = "kunde_fk", nullable = false)
	@OrderColumn(name = "idx", nullable = false)
	@XmlTransient
	private List<Bestellung> bestellungen;
	
	@ElementCollection(fetch = EAGER)
	@CollectionTable(name = "kunde_hobby",
	                 joinColumns = @JoinColumn(name = "kunde_fk", nullable = false),
	                 uniqueConstraints =  @UniqueConstraint(columnNames = { "kunde_fk", "hobby" }),
	                 indexes = @Index(columnList = "kunde_fk"))
	@Column(table = "kunde_hobby", name = "hobby", length = 2, nullable = false)
	private Set<HobbyType> hobbies;
	
	@Transient
	private URI bestellungenUri;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNachname() {
		return nachname;
	}
	public void setNachname(String nachname) {
		this.nachname = nachname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Adresse getAdresse() {
		return adresse;
	}
	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}
	public List<Bestellung> getBestellungen() {
		return bestellungen;
	}
	public void setBestellungen(List<Bestellung> bestellungen) {
		this.bestellungen = bestellungen;
	}
	public URI getBestellungenUri() {
		return bestellungenUri;
	}
	public void setBestellungenUri(URI bestellungenUri) {
		this.bestellungenUri = bestellungenUri;
	}
	public String getVorname() {
		return vorname;
	}
	public void setVorname(String vorname) {
		this.vorname = vorname;
	}
	public Set<HobbyType> getHobbies() {
		return hobbies;
	}
	public void setHobbies(Set<HobbyType> hobbies) {
		this.hobbies = hobbies;
	}
	
	public Kunde addBestellung(Bestellung bestellung) {
		if (bestellungen == null) {
			bestellungen = new ArrayList<>();
		}
		bestellungen.add(bestellung);
		return this;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((nachname == null) ? 0 : nachname.hashCode());
		result = prime * result + ((vorname == null) ? 0 : vorname.hashCode());
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
		Kunde other = (Kunde) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nachname == null) {
			if (other.nachname != null)
				return false;
		} else if (!nachname.equals(other.nachname))
			return false;
		if (vorname == null) {
			if (other.vorname != null)
				return false;
		} else if (!vorname.equals(other.vorname))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Kunde [id=" + id + ", nachname=" + nachname + ", vorname="
				+ vorname + ", email=" + email + ", adresse=" + adresse
				+ ", hobbies=" + hobbies + ", bestellungenUri="
				+ bestellungenUri + "]";
	}
}
