package org.hibernate.orm.test.jcache.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class EventManager {

	private final SessionFactory sessionFactory;

	public EventManager(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public List listEmailsOfEvent(Long eventId) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();

		List emailList = new ArrayList();
		Event event = session.getReference(Event.class, eventId);
		for (Iterator it = event.getParticipants().iterator(); it.hasNext(); ) {
			Person person = (Person)it.next();
			emailList.addAll(person.getEmailAddresses());
		}

		session.getTransaction().commit();
		return emailList;
	}

	public Long createAndStoreEvent(String title, Person organizer, Date theDate) {

		Session session = sessionFactory.getCurrentSession();

		session.beginTransaction();

		Event theEvent = new Event();
		theEvent.setTitle(title);
		theEvent.setDate(theDate);
		theEvent.setOrganizer(organizer);

		session.persist( theEvent );

		session.getTransaction().commit();
		return theEvent.getId();
	}

	public Long createAndStorePerson(String firstName, String lastName) {

		Session session = sessionFactory.getCurrentSession();

		session.beginTransaction();

		Person person = new Person();
		person.setFirstname(firstName);
		person.setLastname(lastName);

		session.persist(person);

		session.getTransaction().commit();
		return person.getId();
	}

	public Long createAndStorePerson(Person person) {

		Session session = sessionFactory.getCurrentSession();

		session.beginTransaction();

		session.persist(person);

		session.getTransaction().commit();
		return person.getId();
	}

	public List<Event> listEvents() {

		Session session = sessionFactory.getCurrentSession();

		session.beginTransaction();

		List<Event> result = session.createQuery("from Event", Event.class).setCacheable(true).list();

		session.getTransaction().commit();

		return result;
	}

	public void addPersonToEvent(Long personId, Long eventId) {

		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();

		Person aPerson = session.getReference(Person.class, personId);
		Event anEvent = session.getReference(Event.class, eventId);

		aPerson.getEvents().add(anEvent);

		session.getTransaction().commit();
	}

	public void addEmailToPerson(Long personId, String emailAddress) {

		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();

		Person aPerson = session.getReference(Person.class, personId);

		// The getEmailAddresses() might trigger a lazy load of the collection
		aPerson.getEmailAddresses().add(emailAddress);

		session.getTransaction().commit();
	}

	public void addPhoneNumberToPerson(Long personId, PhoneNumber pN) {

		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();

		Person aPerson = session.getReference(Person.class, personId);
		pN.setPersonId(personId.longValue());
		aPerson.getPhoneNumbers().add(pN);

		session.getTransaction().commit();
	}

	public void addTalismanToPerson(Long personId, String talisman) {

		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();

		Person aPerson = session.getReference(Person.class, personId);
		aPerson.addTalisman(talisman);

		session.getTransaction().commit();
	}
}
