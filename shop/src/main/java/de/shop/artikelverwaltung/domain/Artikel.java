package de.shop.artikelverwaltung.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Artikel implements Serializable {
	private static final long serialVersionUID = 1472129607838538329L;
	
	private static final int BEZEICHNUNG_LENGTH_MIN = 2;
	private static final int BEZEICHNUNG_LENGTH_MAX = 32;
	public static final String BEZEICHNUNG_PATTERN = "[A-Z\u00C4\u00D6\u00DC][a-z\u00E4\u00F6\u00FC\u00DF0-9]+"
            + "(-[A-Z\u00C4\u00D6\u00DC][a-z\u00E4\u00F6\u00FC\u00DF0-9]+)?";
	
	
	//@NotNull(message = "{artikel.id.notNull}")
	private Long id;
	
	@NotNull(message = "{artikel.preis.notNull}")
	@DecimalMin(value = "0.00", message = "{artikel.preis.min}")
	private BigDecimal preis;
	
	@NotNull(message = "{artikel.bezeichnung.notNull}")
	@Size(min = BEZEICHNUNG_LENGTH_MIN, max = BEZEICHNUNG_LENGTH_MAX, message = "{artikel.bezeichnung.length}")
	//@Pattern(regexp = BEZEICHNUNG_PATTERN, message = "{artikel.bezeichnung.pattern}")
	private String bezeichnung = "";
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getBezeichnung() {
		return bezeichnung;
	}
	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}
	public BigDecimal getPreis() {
		return preis;
	}
	public void setPreis(BigDecimal preis) {
		this.preis = preis;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((bezeichnung == null) ? 0 : bezeichnung.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((preis == null) ? 0 : preis.hashCode());
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
		
		final Artikel other = (Artikel) obj;
		if (bezeichnung == null) {
		  if (other.bezeichnung != null)
			return false;
		  }
		else if (!bezeichnung.equals(other.bezeichnung))
			return false;
		
		if (id == null) {
			if (other.id != null)
				return false;
		} 
		else if (!id.equals(other.id))
			return false;
		
		if (preis == null) {
			if (other.preis != null)
				return false;
		} 
		else if (!preis.equals(other.preis))
			return false;
		
		return true;
	}

	@Override
	public String toString() {
		return "Artikel [id=" + id + ", preis=" + preis + ", bezeichnung="
				+ bezeichnung + "]";
	}
}
