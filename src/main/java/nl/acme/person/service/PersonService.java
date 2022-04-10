package nl.acme.person.service;

import nl.acme.person.domain.Person;
import nl.acme.person.persistence.PersonRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Service
// rloman later import and add to POST and PUT @Transactional
public class PersonService {

	@Autowired
	private PersonRepository personRepository;

	public Iterable<Person> findAll() {
		return personRepository.findAll();
	}

	public Person save(Person person) {
		return personRepository.save(person);
	}

	public Optional<Person> findById(long id) {
		return personRepository.findById(id);
	}

	public void deleteById(long id) {
		personRepository.deleteById(id);
	}
}
