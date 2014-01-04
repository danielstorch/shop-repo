package de.shop.bestellverwaltung.rest;

import static de.shop.util.Constants.SELF_LINK;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;
import static javax.ws.rs.core.MediaType.TEXT_XML;
import static de.shop.bestellverwaltung.service.BestellungService.FetchType.NUR_BESTELLUNG;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import de.shop.artikelverwaltung.domain.Artikel;
import de.shop.artikelverwaltung.rest.ArtikelResource;
import de.shop.artikelverwaltung.service.ArtikelService;
import de.shop.bestellverwaltung.domain.Bestellung;
import de.shop.bestellverwaltung.domain.Posten;
import de.shop.bestellverwaltung.service.BestellungService;
import de.shop.kundenverwaltung.domain.Kunde;
import de.shop.kundenverwaltung.rest.KundeResource;
import de.shop.util.interceptor.Log;
import de.shop.util.rest.UriHelper;
import de.shop.util.rest.NotFoundException;

@Path("/bestellung")
@Produces({ APPLICATION_JSON, APPLICATION_XML + ";qs=0.75", TEXT_XML + ";qs=0.5" })
@Consumes
@RequestScoped
@Transactional
@Log
public class BestellungResource {
	@Context
	private UriInfo uriInfo;
	
	@Inject
	private UriHelper uriHelper;
	
	@Inject
	private KundeResource kundeResource;
	
	@Inject
    private BestellungService bs;
	
	@Inject
	private ArtikelResource artikelResource;
	
	@Inject
	private ArtikelService as;
	
	@GET
	@Path("{id:[1-9][0-9]*}")
	public Response findBestellungById(@PathParam("id") Long id) {
		final Bestellung bestellung = bs.findBestellungById(id, NUR_BESTELLUNG);
		if (bestellung == null) {
			throw new NotFoundException("Keine Bestellung mit der ID " + id + " gefunden.");
		}
		
		// URIs innerhalb der gefundenen Bestellung anpassen
		setStructuralLinks(bestellung, uriInfo);
		
		// Link-Header setzen
		final Response response = Response.ok(bestellung)
                                          .links(getTransitionalLinks(bestellung, uriInfo))
                                          .build();
		return response;
	}
	
	public void setStructuralLinks(Bestellung bestellung, UriInfo uriInfo) {
		// URI fuer Kunde setzen
		final Kunde kunde = bestellung.getKunde();
		if (kunde != null) {
			final URI kundeUri = kundeResource.getUriKunde(bestellung.getKunde(), uriInfo);
			bestellung.setKundeUri(kundeUri);
		}
		
		// URI fuer Artikel in den Bestellpositionen setzen
		for (Posten p : bestellung.getPosten()) {
			if (p != null) {
				final URI artikelURI = artikelResource.getUriArtikel(p.getArtikel(), uriInfo);
				p.setArtikelURI(artikelURI);
			}
		}
	}
	
	private Link[] getTransitionalLinks(Bestellung bestellung, UriInfo uriInfo) {
		final Link self = Link.fromUri(getUriBestellung(bestellung, uriInfo))
                              .rel(SELF_LINK)
                              .build();
		return new Link[] {self};
	}
	
	public URI getUriBestellung(Bestellung bestellung, UriInfo uriInfo) {
		return uriHelper.getUri(BestellungResource.class, "findBestellungById", bestellung.getId(), uriInfo);
	}
	
	
	/**
	 * Mit der URL /bestellungen eine neue Bestellung anlegen
	 * @param bestellung die neue Bestellung
	 * @return Objekt mit Bestelldaten, falls die ID vorhanden ist
	 */
	@POST
	@Consumes({ APPLICATION_JSON, APPLICATION_XML, TEXT_XML })
	@Produces
	public Response createBestellung(@Valid Bestellung bestellung) {
		// TODO eingeloggter Kunde wird durch die URI im Attribut "kundeUri" emuliert
		final String kundeUriStr = bestellung.getKundeUri().toString();
		int startPos = kundeUriStr.lastIndexOf('/') + 1;
		final String kundeIdStr = kundeUriStr.substring(startPos);
		Long kundeId = null;
		try {
			kundeId = Long.valueOf(kundeIdStr);
		}
		catch (NumberFormatException e) {
			kundeIdInvalid();
		}
		
		// IDs der (persistenten) Artikel ermitteln
		final Collection<Posten> bestellpositionen = bestellung.getPosten();
		final List<Long> artikelIds = new ArrayList<>(bestellpositionen.size());
		for (Posten bp : bestellpositionen) {
			final URI artikelUri = bp.getArtikelURI();
			if (artikelUri == null) {
				continue;
			}
			final String artikelUriStr = artikelUri.toString();
			startPos = artikelUriStr.lastIndexOf('/') + 1;
			final String artikelIdStr = artikelUriStr.substring(startPos);
			Long artikelId = null;
			try {
				artikelId = Long.valueOf(artikelIdStr);
			}
			catch (NumberFormatException e) {
				// Ungueltige Artikel-ID: wird nicht beruecksichtigt
				continue;
			}
			artikelIds.add(artikelId);
		}
		
		if (artikelIds.isEmpty()) {
			// keine einzige Artikel-ID als gueltige Zahl
			artikelIdsInvalid();
		}

		final Collection<Artikel> gefundeneArtikel = as.findArtikelByIds(artikelIds);
		
		// Bestellpositionen haben URLs fuer persistente Artikel.
		// Diese persistenten Artikel wurden in einem DB-Zugriff ermittelt (s.o.)
		// Fuer jede Bestellposition wird der Artikel passend zur Artikel-URL bzw. Artikel-ID gesetzt.
		// Bestellpositionen mit nicht-gefundene Artikel werden eliminiert.
		int i = 0;
		final Set<Posten> neueBestellpositionen = new HashSet<>();
		for (Posten bp : bestellpositionen) {
			// Artikel-ID der aktuellen Bestellposition (s.o.):
			// artikelIds haben gleiche Reihenfolge wie bestellpositionen
			final long artikelId = artikelIds.get(i++);
			
			// Wurde der Artikel beim DB-Zugriff gefunden?
			for (Artikel artikel : gefundeneArtikel) {
				if (artikel.getId().longValue() == artikelId) {
					// Der Artikel wurde gefunden
					bp.setArtikel(artikel);
					neueBestellpositionen.add(bp);
					break;					
				}
			}
			// FIXME JDK 8 hat Lambda-Ausdruecke
			//final Artikel artikel = gefundeneArtikel.stream()
			//                                        .filter(a -> a.getId() == artikelId)
			//                                        .findAny();
			//if (artikel != null) {
			//	bp.setArtikel(artikel);
			//	neueBestellpositionen.add(bp);				
			//}
		}
		bestellung.setPosten(neueBestellpositionen);
		
		bestellung = bs.createBestellung(bestellung, kundeId);

		final URI bestellungUri = getUriBestellung(bestellung, uriInfo);
		return Response.created(bestellungUri).build();
	}
	// Erzeugt neue Bestellungen für Kunde.....
//	@POST
//	@Consumes({APPLICATION_JSON, APPLICATION_XML, TEXT_XML })
//	@Produces
//	public Response createBestellung(@Valid Bestellung bestellung, @Valid Long kundeId) {
//		// TODO Anwendungskern statt Mock, Verwendung von Locale
//		bestellung = bs.createBestellung(bestellung, kundeId);
//		setStructuralLinks(bestellung, uriInfo);
//		System.out.println("Neue Bestellung: " + bestellung);
//		return Response.created(getUriBestellung(bestellung, uriInfo))
//			           .build();
//	}
	
	@GET
    @Path("{id:[1-9][0-9]*}/kunde")
    public Response findKundeByBestellungId(@PathParam("id") Long id) {
            final Kunde kunde = bs.findKundeById(id);
            if (kunde == null) {
            		throw new NotFoundException("Kein Kunde zu der Bestellung mit der ID " + id + " gefunden.");
            }

            kundeResource.setStructuralLinks(kunde, uriInfo);

            // Link Header setzen
            return Response.ok(kunde).links(kundeResource.getTransitionalLinks(kunde, uriInfo)).build();
    }
	
	
	/**
	 * @NotNull verletzen, um die entsprechende Meldung zu verursachen, weil keine einzige Artikel-ID
	 *          eine gueltige Zahl war.
	 * @return null
	 */
	@NotNull(message = "{bestellung.artikelIds.invalid}")
	public List<Long> artikelIdsInvalid() {
		return null;
	}
	/**
	 * @NotNull verletzen, um die entsprechende Meldung zu verursachen, weil die Kunde-Id ungueltig ist.
	 * @return null
	 */
	@NotNull(message = "{bestellung.kunde.id.invalid}")
	public Long kundeIdInvalid() {
		return null;
	}
}
