package de.shop.kundenverwaltung.domain;

import java.io.Serializable;
import java.net.URI;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import de.shop.bestellverwaltung.domain.Bestellung;

@XmlRootElement
public class Kunde implements Serializable {
	private static final long serialVersionUID = -8477316271106761155L;
	
	private static final String NAME_PATTERN = "[A-Z\u00C4\u00D6\u00DC][a-z0-9+-_\u00E4\u00F6\u00FC\u00DF]+";
	private static final String EMAIL_PATTERN = "[\\w.%-]+@[\\w.%-]+\\.[A-Za-z]{2,4}";
    private static final String PREFIX_ADEL = "(o'|von|von der|von und zu|van)?";

    public static final String NACHNAME_PATTERN = PREFIX_ADEL + NAME_PATTERN + "(-" + NAME_PATTERN + ")?";
    public static final String VORNAME_PATTERN = NAME_PATTERN + "(-" + NAME_PATTERN + ")?";

    public static final int NAME_LENGTH_MIN = 2;
    public static final int NAME_LENGTH_MAX = 32;
    public static final int EMAIL_LENGTH_MAX = 32;
	
    private Long id;
	
	@NotNull(message = "{kunde.nachname.notNull}")
    @Size(min = NAME_LENGTH_MIN, max = NAME_LENGTH_MAX, message = "{kunde.nachname.length}")
    @Pattern(regexp = NACHNAME_PATTERN, message = "{kunde.nachname.pattern}")
	private String nachname;
	
	@NotNull(message = "{kunde.vorname.notNull}")
    @Size(min = NAME_LENGTH_MIN, max = NAME_LENGTH_MAX, message = "{kunde.vorname.length}")
    @Pattern(regexp = VORNAME_PATTERN, message = "{kunde.vorname.pattern}")
	private String vorname;
	
	@NotNull(message = "{kunde.email.notNull}")
	@Size(max = EMAIL_LENGTH_MAX, message = "{kunde.email.length}")
	@Pattern(regexp = EMAIL_PATTERN, message = "{kunde.email.pattern}")
	private String email;
	
	@NotNull(message = "{kunde.adresse.notNull}")
	@Valid
	private Adresse adresse;
	
	@XmlTransient
	private List<Bestellung> bestellungen;
	
	private Set<HobbyType> hobbies;
	
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((adresse == null) ? 0 : adresse.hashCode());
		result = prime * result
				+ ((bestellungen == null) ? 0 : bestellungen.hashCode());
		result = prime * result
				+ ((bestellungenUri == null) ? 0 : bestellungenUri.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((hobbies == null) ? 0 : hobbies.hashCode());
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
		final Kunde other = (Kunde) obj;
		if (adresse == null) {
			if (other.adresse != null)
				return false;
		} 
		else if (!adresse.equals(other.adresse))
			return false;
		
		if (bestellungen == null) {
			if (other.bestellungen != null)
				return false;
		} 
		else if (!bestellungen.equals(other.bestellungen))
			return false;
		
		if (bestellungenUri == null) {
			if (other.bestellungenUri != null)
				return false;
		} 
		else if (!bestellungenUri.equals(other.bestellungenUri))
			return false;
		
		if (email == null) {
			if (other.email != null)
				return false;
		} 
		else if (!email.equals(other.email))
			return false;
		
		if (hobbies == null) {
			if (other.hobbies != null)
				return false;
		} 
		else if (!hobbies.equals(other.hobbies))
			return false;
		
		if (id == null) {
			if (other.id != null)
				return false;
		} 
		else if (!id.equals(other.id))
			return false;
		
		if (nachname == null) {
			if (other.nachname != null)
				return false;
		} 
		else if (!nachname.equals(other.nachname))
			return false;
		
		if (vorname == null) {
			if (other.vorname != null)
				return false;
		} 
		else if (!vorname.equals(other.vorname))
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
