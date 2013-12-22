package de.shop.bestellverwaltung.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static de.shop.util.Constants.KEINE_ID;

import javax.enterprise.context.Dependent;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.persistence.criteria.Path;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.Predicate;

import de.shop.artikelverwaltung.domain.Artikel;
import de.shop.bestellverwaltung.domain.Bestellung;
import de.shop.bestellverwaltung.domain.Posten;
import de.shop.kundenverwaltung.domain.Kunde;
import de.shop.kundenverwaltung.service.KundeService;
import de.shop.util.Mock;
import de.shop.util.interceptor.Log;

@Dependent
@Log
public class BestellungServiceImpl implements BestellungService, Serializable {
	private static final long serialVersionUID = -904844601529273628L;
	
	@Inject
	private transient EntityManager em;
	
	@Inject
	private KundeService ks;
	
	@Inject
	@NeueBestellung
	private transient Event<Bestellung> event;
	
	@Override
	@NotNull(message = "{bestellung.notFound.id}")
	public Bestellung findBestellungById(Long id, FetchType fetch) {
		if (id == null) {
			return null;
		}
		
		Bestellung bestellung;
		EntityGraph<?> entityGraph;
		Map<String, Object> props;
		switch (fetch) {
			case NUR_BESTELLUNG:
				bestellung = em.find(Bestellung.class, id);
				break;
				
//			case MIT_LIEFERUNGEN:
//				entityGraph = em.getEntityGraph(Bestellung.GRAPH_LIEFERUNGEN);
//				props = ImmutableMap.of(LOADGRAPH, (Object) entityGraph);
//				bestellung = em.find(Bestellung.class, id, props);
//				break;
				
			default:
				bestellung = em.find(Bestellung.class, id);
				break;
		}
		
		return bestellung;
	}
//	@Override
//	public Bestellung findBestellungById(Long id) {
//		// TODO Datenbanzugriffsschicht statt Mock
//        return Mock.findBestellungById(id);
//	}
	
	
	@Override
	@NotNull(message = "{bestellung.kunde.notFound.id}")
	public Kunde findKundeById(Long id) {
		try {
			return em.createNamedQuery(Bestellung.FIND_KUNDE_BY_ID, Kunde.class)
                     .setParameter(Bestellung.PARAM_ID, id)
					 .getSingleResult();
		}
		catch (NoResultException e) {
			return null;
		}
	}
//	@Override
//	public Kunde findKundeByBestellungId(Long id) {
//		// TODO Datenbanzugriffsschicht statt Mock
//        return Mock.findKundeByBestellungId(id);
//	}
	
	
	@Override
	@Size(min = 1, message = "{bestellung.notFound.kunde}")
	public List<Bestellung> findBestellungenByKunde(Kunde kunde) {
		if (kunde == null) {
			return Collections.emptyList();
		}
		return em.createNamedQuery(Bestellung.FIND_BESTELLUNGEN_BY_KUNDE, Bestellung.class)
                 .setParameter(Bestellung.PARAM_KUNDE, kunde)
				 .getResultList();
	}
//	@Override
//	@Size(min = 1, message = "{bestellung.notFound.kunde}")
//	public List<Bestellung> findBestellungenByKunde(Kunde kunde) {
//		// TODO Datenbanzugriffsschicht statt Mock
//		return Mock.findBestellungenByKunde(kunde);
//	}
	
	@Override
	@Size(min = 1, message = "{bestellung.notFound.ids}")
	public List<Bestellung> findBestellungenByIds(List<Long> ids, FetchType fetch) {
		if (ids == null || ids.isEmpty()) {
			return Collections.emptyList();
		}
		
		// SELECT b
		// FROM   Bestellung b
		// WHERE  b.id = <id> OR ...

		final CriteriaBuilder builder = em.getCriteriaBuilder();
		final CriteriaQuery<Bestellung> criteriaQuery  = builder.createQuery(Bestellung.class);
		final Root<Bestellung> b = criteriaQuery.from(Bestellung.class);
		
		// Die Vergleichen mit "=" als Liste aufbauen
		final Path<Long> idPath = b.get("id");
		final List<Predicate> predList = new ArrayList<>();
		for (Long id : ids) {
			final Predicate equal = builder.equal(idPath, id);
			predList.add(equal);
		}
		// Die Vergleiche mit "=" durch "or" verknuepfen
		final Predicate[] predArray = new Predicate[predList.size()];
		final Predicate pred = builder.or(predList.toArray(predArray));
		criteriaQuery.where(pred).distinct(true);

		final TypedQuery<Bestellung> query = em.createQuery(criteriaQuery);
//		if (FetchType.MIT_LIEFERUNGEN.equals(fetch)) {
//			final EntityGraph<?> entityGraph = em.getEntityGraph(Bestellung.GRAPH_LIEFERUNGEN);
//			query.setHint(LOADGRAPH, entityGraph);
//		}
				
		return query.getResultList();
	}
	
	
	@Override
	public Bestellung createBestellung(Bestellung bestellung, Long kundeId) {
		if (bestellung == null) {
			return null;
		}
		
		// Den persistenten Kunden mit der transienten Bestellung verknuepfen
		final Kunde kunde = ks.findKundeById(kundeId, KundeService.FetchType.MIT_BESTELLUNGEN);
		return createBestellung(bestellung, kunde);
	}
	
	@Override
	public Bestellung createBestellung(Bestellung bestellung, Kunde kunde) {
		if (bestellung == null) {
			return null;
		}
		
		// Den persistenten Kunden mit der transienten Bestellung verknuepfen
		if (!em.contains(kunde)) {
			kunde = ks.findKundeById(kunde.getId(), KundeService.FetchType.MIT_BESTELLUNGEN);
		}
		kunde.addBestellung(bestellung);
		bestellung.setKunde(kunde);
		
		// Vor dem Abspeichern IDs zuruecksetzen:
		// IDs koennten einen Wert != null haben, wenn sie durch einen Web Service uebertragen wurden
		bestellung.setId(KEINE_ID);
		for (Posten bp : bestellung.getPosten()) {
			bp.setId(KEINE_ID);
		}
		// FIXME JDK 8 hat Lambda-Ausdruecke
		//bestellung.getBestellpositionen()
		//          .forEach(bp -> bp.setId(KEINE_ID));
		
		em.persist(bestellung);
		event.fire(bestellung);

		return bestellung;
	}
//	@Override
//	public Bestellung createBestellung(Bestellung bestellung) {
//		// TODO Datenbanzugriffsschicht statt Mock
//		bestellung = Mock.createBestellung(bestellung);
//		//für email
//		event.fire(bestellung);
//		
//		return bestellung;
//	}


	@Override
	public List<Artikel> ladenhueter(int anzahl) {
		// TODO Auto-generated method stub
		return null;
	}
}
