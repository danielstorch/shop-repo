package de.shop.bestellverwaltung.domain;

import java.io.Serializable;
import java.net.URI;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import de.shop.kundenverwaltung.domain.AbstractKunde;

@XmlRootElement
public class Bestellung {
	private static final long serialVersionUID = 1618359234119003714L;
	
	private Long id;
	private boolean ausgeliefert;
	
	@XmlTransient
	private AbstractKunde kunde;
	
	private URI kundeUri;

}
