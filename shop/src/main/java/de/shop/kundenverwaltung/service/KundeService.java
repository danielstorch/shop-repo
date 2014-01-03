package de.shop.kundenverwaltung.service;

import static de.shop.util.Constants.LOADGRAPH;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.google.common.collect.ImmutableMap;

import de.shop.kundenverwaltung.domain.Adresse;
import de.shop.kundenverwaltung.domain.Kunde;
import de.shop.bestellverwaltung.domain.Bestellung;
import de.shop.bestellverwaltung.domain.Posten;
import de.shop.bestellverwaltung.domain.Bestellung_;
import de.shop.bestellverwaltung.domain.Posten_;
//import de.shop.util.Mock;
import de.shop.util.interceptor.Log;

@Dependent
@Log
public class KundeService implements Serializable {
	private static final long serialVersionUID = 4360325837484294309L;
	
	public enum FetchType {
		NUR_KUNDE,
		MIT_BESTELLUNGEN,
		MIT_WARTUNGSVERTRAEGEN
	}
	
	public enum OrderType {
		KEINE,
		ID
	}
	
	@Inject
	private transient EntityManager em;
	
	
	@NotNull(message = "{kunde.notFound.id}")
	public Kunde findKundeById(Long id, FetchType fetch) {
		if (id == null) {
			return null;
		}
		
		Kunde kunde;
		EntityGraph<?> entityGraph;
		Map<String, Object> props;
		switch (fetch) {
			case NUR_KUNDE:
				kunde = em.find(Kunde.class, id);
				break;
			
			case MIT_BESTELLUNGEN:
				entityGraph = em.getEntityGraph(Kunde.GRAPH_BESTELLUNGEN);
				props = ImmutableMap.of(LOADGRAPH, (Object) entityGraph);
				kunde = em.find(Kunde.class, id, props);
				break;
				
//			case MIT_WARTUNGSVERTRAEGEN:
//				entityGraph = em.getEntityGraph(AbstractKunde.GRAPH_WARTUNGSVERTRAEGE);
//				props = ImmutableMap.of(LOADGRAPH, (Object) entityGraph);
//				kunde = em.find(AbstractKunde.class, id, props);
//				break;

			default:
				kunde = em.find(Kunde.class, id);
				break;
		}
		
		return kunde;
	}
//	@NotNull(message = "{kunde.notFound.id}")
//	public Kunde findKundeById(Long id) {
//		if (id == null) {
//			return null;
//		}
//		// TODO Datenbanzugriffsschicht statt Mock
//		return Mock.findKundeById(id);
//	}
	
	
	public List<Long> findIdsByPrefix(String idPrefix) {
		return em.createNamedQuery(Kunde.FIND_IDS_BY_PREFIX, Long.class)
				 .setParameter(Kunde.PARAM_KUNDE_ID_PREFIX, idPrefix + '%')
				 .getResultList();
	}
	
	
	@NotNull(message = "{kunde.notFound.email}")
	public Kunde findKundeByEmail(String email) {
		try {
			return em.createNamedQuery(Kunde.FIND_KUNDE_BY_EMAIL, Kunde.class)
					 .setParameter(Kunde.PARAM_KUNDE_EMAIL, email)
					 .getSingleResult();
		}
		catch (NoResultException e) {
			return null;
		}
	}
	
	
	public List<Kunde> findAllKunden(FetchType fetch, OrderType order) {
		final TypedQuery<Kunde> query = OrderType.ID.equals(order)
		                                        ? em.createNamedQuery(Kunde.FIND_KUNDEN_ORDER_BY_ID,
		                                        		Kunde.class)
		                                        : em.createNamedQuery(Kunde.FIND_KUNDEN, Kunde.class);
		
		EntityGraph<?> entityGraph;
		switch (fetch) {
			case NUR_KUNDE:
				break;
			
			case MIT_BESTELLUNGEN:
				entityGraph = em.getEntityGraph(Kunde.GRAPH_BESTELLUNGEN);
				query.setHint(LOADGRAPH, entityGraph);
				break;
				
//			case MIT_WARTUNGSVERTRAEGEN:
//				entityGraph = em.getEntityGraph(Kunde.GRAPH_WARTUNGSVERTRAEGE);
//				query.setHint(LOADGRAPH, entityGraph);
//				break;

			default:
				break;
		}
		return query.getResultList();
	}
//	public List<Kunde> findAllKunden() {
//		// TODO Datenbanzugriffsschicht statt Mock
//		return Mock.findAllKunden();
//	}

	
	@Size(min = 1, message = "{kunde.notFound.nachname}")
	public List<Kunde> findKundenByNachname(String nachname, FetchType fetch) {
		final TypedQuery<Kunde> query = em.createNamedQuery(Kunde.FIND_KUNDEN_BY_NACHNAME,
				Kunde.class).setParameter(Kunde.PARAM_KUNDE_NACHNAME, nachname);
		
		EntityGraph<?> entityGraph;
		switch (fetch) {
			case NUR_KUNDE:
				break;
				
			case MIT_BESTELLUNGEN:
				entityGraph = em.getEntityGraph(Kunde.GRAPH_BESTELLUNGEN);
				query.setHint(LOADGRAPH, entityGraph);
				break;
				
//			case MIT_WARTUNGSVERTRAEGEN:
//				entityGraph = em.getEntityGraph(AbstractKunde.GRAPH_WARTUNGSVERTRAEGE);
//				query.setHint(LOADGRAPH, entityGraph);
//				break;
				
			default:
				break;
		}
		return query.getResultList();
	}
//	@Size(min = 1, message = "{kunde.notFound.nachname}")
//	public List<Kunde> findKundenByNachname(String nachname) {
//		// TODO Datenbanzugriffsschicht statt Mock
//		return Mock.findKundenByNachname(nachname);
//	}
	
	
	@Size(min = 1, message = "{kunde.notFound.vorname}")
	public List<Kunde> findKundenByVorname(String vorname, FetchType fetch) {
		final TypedQuery<Kunde> query = em.createNamedQuery(Kunde.FIND_KUNDEN_BY_VORNAME,
				Kunde.class).setParameter(Kunde.PARAM_KUNDE_VORNAME, vorname);
		
		EntityGraph<?> entityGraph;
		switch (fetch) {
			case NUR_KUNDE:
				break;
				
			case MIT_BESTELLUNGEN:
				entityGraph = em.getEntityGraph(Kunde.GRAPH_BESTELLUNGEN);
				query.setHint(LOADGRAPH, entityGraph);
				break;
				
//			case MIT_WARTUNGSVERTRAEGEN:
//				entityGraph = em.getEntityGraph(AbstractKunde.GRAPH_WARTUNGSVERTRAEGE);
//				query.setHint(LOADGRAPH, entityGraph);
//				break;
				
			default:
				break;
		}
		return query.getResultList();
	}
//	@Size(min = 1, message = "{kunde.notFound.nachname}")
//	public List<Kunde> findKundenByVorname(String vorname) {
//		// TODO Datenbanzugriffsschicht statt Mock
//		return Mock.findKundenByNachname(vorname);
//	}
	
	public List<String> findNachnamenByPrefix(String nachnamePrefix) {
		return em.createNamedQuery(Kunde.FIND_NACHNAMEN_BY_PREFIX, String.class)
				 .setParameter(Kunde.PARAM_KUNDE_NACHNAME_PREFIX, nachnamePrefix + '%')
				 .getResultList();
	}
	
	
	@Size(min = 1, message = "{kunde.notFound.plz}")
	public List<Kunde> findKundenByPLZ(String plz) {
		return em.createNamedQuery(Kunde.FIND_KUNDEN_BY_PLZ, Kunde.class)
                 .setParameter(Kunde.PARAM_KUNDE_ADRESSE_PLZ, plz)
                 .getResultList();
	}
	
	
	@Size(min = 1, message = "{kunde.notFound.nachname}")
	public List<Kunde> findKundenByNachnameCriteria(String nachname) {
		final CriteriaBuilder builder = em.getCriteriaBuilder();
		final CriteriaQuery<Kunde> criteriaQuery = builder.createQuery(Kunde.class);
		final Root<Kunde> k = criteriaQuery.from(Kunde.class);

//		final Path<String> nachnamePath = k.get(Kunde.nachname);
		final Path<String> nachnamePath = k.get("nachname");
		
		final Predicate pred = builder.equal(nachnamePath, nachname);
		criteriaQuery.where(pred);
		
		// Ausgabe des komponierten Query-Strings. Voraussetzung: das Modul "org.hibernate" ist aktiviert
		//LOGGER.tracef("", em.createQuery(criteriaQuery).unwrap(org.hibernate.Query.class).getQueryString());
		return em.createQuery(criteriaQuery).getResultList();
	}
	
	
	/**
	 * Die Kunden mit einer bestimmten Mindestbestellmenge suchen.
	 * @param minMenge Die Mindestbestellmenge
	 * @return Liste der passenden Kunden
	 * @exception ConstraintViolationException zu @Size, falls die Liste leer ist
	 *///syntax error
//	@Size(min = 1, message = "{kunde.notFound.minBestMenge}")
//	public List<Kunde> findKundenMitMinBestMenge(short minMenge) {
//		final CriteriaBuilder builder = em.getCriteriaBuilder();
//		final CriteriaQuery<Kunde> criteriaQuery  = builder.createQuery(Kunde.class);
//		final Root<Kunde> k = criteriaQuery.from(Kunde.class);
//
//		final Join<Kunde, Bestellung> b = k.join(Kunde.bestellungen);
//		final Join<Bestellung, Posten> bp = b.join(Bestellung_.posten);
//		criteriaQuery.where(builder.gt(bp.<Short>get(Posten.anzahl), minMenge))
//		             .distinct(true);
//		
//		return em.createQuery(criteriaQuery)
//		         .getResultList();
//	}
	
	
	/**
	 * Kunden zu den Suchkriterien suchen
	 * @param email Email-Adresse
	 * @param nachname Nachname
	 * @param plz Postleitzahl
	 * @param minBestMenge Mindestbestellmenge
	 * @return Die gefundenen Kunden
	 * @exception ConstraintViolationException zu @Size, falls die Liste leer ist
	 */
//	@NotNull(message = "{kunde.notFound.criteria}")
//	public List<Kunde> findKundenByCriteria(String email, String nachname, String plz, Short minBestMenge) {
//		// SELECT DISTINCT k
//		// FROM   AbstractKunde k
//		// WHERE  email = ? AND nachname = ? AND k.adresse.plz = ? and seit = ?
//		
//		final CriteriaBuilder builder = em.getCriteriaBuilder();
//		final CriteriaQuery<Kunde> criteriaQuery  = builder.createQuery(Kunde.class);
//		final Root<? extends Kunde> k = criteriaQuery.from(Kunde.class);
//		
//		Predicate pred = null;
//		if (email != null) {
//			final Path<String> emailPath = k.get(Kunde.email);
//			pred = builder.equal(emailPath, email);
//		}
//		if (nachname != null) {
//			final Path<String> nachnamePath = k.get(Kunde.nachname);
//			final Predicate tmpPred = builder.equal(nachnamePath, nachname);
//			pred = pred == null ? tmpPred : builder.and(pred, tmpPred);
//		}
//		if (plz != null) {
//			final Path<String> plzPath = k.get(AbstractKunde_.adresse)
//                                          .get(Adresse_.plz);
//			final Predicate tmpPred = builder.equal(plzPath, plz);
//			pred = pred == null ? tmpPred : builder.and(pred, tmpPred);
//		}
//		if (seit != null) {
//			final Path<Date> seitPath = k.get(AbstractKunde_.seit);
//			final Predicate tmpPred = builder.equal(seitPath, seit);
//			pred = pred == null ? tmpPred : builder.and(pred, tmpPred);
//		}
//		if (minBestMenge != null) {
//			final Path<Short> anzahlPath = k.join(AbstractKunde_.bestellungen)
//                                            .join(Bestellung_.bestellpositionen)
//                                            .get(Bestellposition_.anzahl);
//			final Predicate tmpPred = builder.gt(anzahlPath, minBestMenge);
//			pred = pred == null ? tmpPred : builder.and(pred, tmpPred);
//		}
//		
//		criteriaQuery.where(pred)
//		             .distinct(true);
//		return em.createQuery(criteriaQuery).getResultList();
//	}
	
	/**
	 * Einen neuen Kunden in der DB anlegen.
	 * @param kunde Der anzulegende Kunde.
	 * @return Der neue Kunde einschliesslich generierter ID.
	 */
	public <T extends Kunde> T createKunde(T kunde) {
		if (kunde == null) {
			return kunde;
		}
		
//		em.detach(kunde);

		// Pruefung, ob die Email-Adresse schon existiert
		final Kunde tmp = findKundeByEmail(kunde.getEmail());   // Kein Aufruf als Business-Methode
		if (tmp != null) {
			throw new EmailExistsException(kunde.getEmail());
		}
		
//		kunde.setId(null);
		em.persist(kunde);
		return kunde;		
	}
	
	/**
	 * Einen vorhandenen Kunden aktualisieren
	 * @param kunde Der Kunde mit aktualisierten Attributwerten
	 * @return Der aktualisierte Kunde
	 */
	public <T extends Kunde> T updateKunde(T kunde) {
		if (kunde == null) {
			return null;
		}
		
		// kunde vom EntityManager trennen, weil anschliessend z.B. nach Id und Email gesucht wird
		em.detach(kunde);
		
		// Gibt es ein anderes Objekt mit gleicher Email-Adresse?
		final Kunde tmp = findKundeByEmail(kunde.getEmail());  // Kein Aufruf als Business-Methode
//		final Kunde tmp = findKundeById(kunde.getId(), FetchType.NUR_KUNDE);
		if (tmp != null) {
			em.detach(tmp);
			if (tmp.getId().longValue() != kunde.getId().longValue()) {
				// anderes Objekt mit gleichem Attributwert fuer email
				throw new EmailExistsException(kunde.getEmail());
			}
		}

		em.merge(kunde);
		return kunde;
	}
	
	/**
	 * Einen Kunden aus der DB loeschen, falls er existiert.
	 * @param kunde Der zu loeschende Kunde.
	 */
	public void deleteKunde(Long kundeId) {	//Kunde
		if (kundeId == null) {
			return;
		}
		
		// Bestellungen laden, damit sie anschl. ueberprueft werden koennen
		final Kunde kunde = findKundeById(kundeId, FetchType.MIT_BESTELLUNGEN);  // Kein Aufruf als Business-Methode //kunde.getId()
		if (kunde == null) {
			return;
		}
		
		// Gibt es Bestellungen?
		if (!kunde.getBestellungen().isEmpty()) {
			throw new KundeDeleteBestellungException(kunde);
		}
		em.remove(kunde);
	}
//	public void deleteKunde(Long kundeId) {
//		final Kunde kunde = findKundeById(kundeId);  // Kein Aufruf als Business-Methode
//		if (kunde == null) {
//			return;
//		}
//		
//		// TODO Datenbanzugriffsschicht statt Mock
//		Mock.deleteKunde(kundeId);
//	}
	
}
