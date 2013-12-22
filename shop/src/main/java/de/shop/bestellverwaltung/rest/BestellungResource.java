//package de.shop.bestellverwaltung.rest;
//
//import static de.shop.util.Constants.SELF_LINK;
//import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
//import static javax.ws.rs.core.MediaType.APPLICATION_XML;
//import static javax.ws.rs.core.MediaType.TEXT_XML;
//
//import java.net.URI;
//
//import javax.enterprise.context.RequestScoped;
//import javax.inject.Inject;
//import javax.validation.Valid;
//import javax.ws.rs.Consumes;
//import javax.ws.rs.GET;
//import javax.ws.rs.POST;
//import javax.ws.rs.Path;
//import javax.ws.rs.PathParam;
//import javax.ws.rs.Produces;
//import javax.ws.rs.core.Context;
//import javax.ws.rs.core.Link;
//import javax.ws.rs.core.Response;
//import javax.ws.rs.core.UriInfo;
//
//import de.shop.artikelverwaltung.rest.ArtikelResource;
//import de.shop.bestellverwaltung.domain.Bestellung;
//import de.shop.bestellverwaltung.domain.Posten;
//import de.shop.bestellverwaltung.service.BestellungService;
//import de.shop.kundenverwaltung.domain.Kunde;
//import de.shop.kundenverwaltung.rest.KundeResource;
//import de.shop.util.interceptor.Log;
//import de.shop.util.rest.UriHelper;
//import de.shop.util.rest.NotFoundException;
//
//@Path("/bestellung")
//@Produces({ APPLICATION_JSON, APPLICATION_XML + ";qs=0.75", TEXT_XML + ";qs=0.5" })
//@Consumes
//@RequestScoped
//@Log
//public class BestellungResource {
//	@Context
//	private UriInfo uriInfo;
//	
//	@Inject
//	private UriHelper uriHelper;
//	
//	@Inject
//	private KundeResource kundeResource;
//	
//	@Inject
//    private BestellungService bs;
//	
//	@Inject
//	private ArtikelResource artikelResource;
//	
//	@GET
//	@Path("{id:[1-9][0-9]*}")
//	public Response findBestellungById(@PathParam("id") Long id) {
//		final Bestellung bestellung = bs.findBestellungById(id);
//		if (bestellung == null) {
//			throw new NotFoundException("Keine Bestellung mit der ID " + id + " gefunden.");
//		}
//		
//		// URIs innerhalb der gefundenen Bestellung anpassen
//		setStructuralLinks(bestellung, uriInfo);
//		
//		// Link-Header setzen
//		final Response response = Response.ok(bestellung)
//                                          .links(getTransitionalLinks(bestellung, uriInfo))
//                                          .build();
//		return response;
//	}
//	
//	public void setStructuralLinks(Bestellung bestellung, UriInfo uriInfo) {
//		// URI fuer Kunde setzen
//		final Kunde kunde = bestellung.getKunde();
//		if (kunde != null) {
//			final URI kundeUri = kundeResource.getUriKunde(bestellung.getKunde(), uriInfo);
//			bestellung.setKundeUri(kundeUri);
//		}
//		
//		// URI fuer Artikel in den Bestellpositionen setzen
//		for (Posten p : bestellung.getPosten()) {
//			if (p != null) {
//				final URI artikelURI = artikelResource.getUriArtikel(p.getArtikel(), uriInfo);
//				p.setArtikelURI(artikelURI);
//			}
//		}
//	}
//	
//	private Link[] getTransitionalLinks(Bestellung bestellung, UriInfo uriInfo) {
//		final Link self = Link.fromUri(getUriBestellung(bestellung, uriInfo))
//                              .rel(SELF_LINK)
//                              .build();
//		return new Link[] {self};
//	}
//	
//	public URI getUriBestellung(Bestellung bestellung, UriInfo uriInfo) {
//		return uriHelper.getUri(BestellungResource.class, "findBestellungById", bestellung.getId(), uriInfo);
//	}
//	
//	// Erzeugt neue Bestellungen für Kunde
//	@POST
//	@Consumes({APPLICATION_JSON, APPLICATION_XML, TEXT_XML })
//	@Produces
//	public Response createBestellung(@Valid Bestellung bestellung) {
//		// TODO Anwendungskern statt Mock, Verwendung von Locale
//		bestellung = bs.createBestellung(bestellung);
//		setStructuralLinks(bestellung, uriInfo);
//		System.out.println("Neue Bestellung: " + bestellung);
//		return Response.created(getUriBestellung(bestellung, uriInfo))
//			           .build();
//	}
//	
//	@GET
//    @Path("{id:[1-9][0-9]*}/kunde")
//    public Response findKundeByBestellungId(@PathParam("id") Long id) {
//            final Kunde kunde = bs.findKundeByBestellungId(id);
//            if (kunde == null) {
//            		throw new NotFoundException("Kein Kunde zu der Bestellung mit der ID " + id + " gefunden.");
//            }
//
//            kundeResource.setStructuralLinks(kunde, uriInfo);
//
//            // Link Header setzen
//            return Response.ok(kunde).links(kundeResource.getTransitionalLinks(kunde, uriInfo)).build();
//    }
//}
