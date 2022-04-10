package nl.acme.person.api;

import nl.acme.person.domain.Person;
import nl.acme.person.service.PersonService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("api/persons")
public class PersonController {

	@Autowired
	private PersonService personService;

	@GetMapping
	public ResponseEntity<Iterable<Person>> list() {
		return ResponseEntity.ok(personService.findAll());
	}

	@PostMapping
	public ResponseEntity<Person> create(@RequestBody Person person) {
		return ResponseEntity.ok(this.personService.save(person));
	}

	@GetMapping("{id}")
	public ResponseEntity<Person> findById(@PathVariable long id) {
		Optional<Person> optionalPerson = this.personService.findById(id);
		if(optionalPerson.isPresent()) {
			return ResponseEntity.ok(optionalPerson.get());
		}
		else {
			return ResponseEntity.notFound().build();
		}
	}

	@PutMapping("{id}")
	public ResponseEntity<Person> updateById(@PathVariable long id, @RequestBody Person source) {
		Optional<Person> optionalPerson = this.personService.findById(id);
		if(optionalPerson.isPresent()) {
			Person target = optionalPerson.get();
			target.setName(source.getName());
			target.setAge(source.getAge());

			return ResponseEntity.ok(this.personService.save(target));
		}
		else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Void> deleteById(@PathVariable long id) {
		Optional<Person> optionalPerson = this.personService.findById(id);
		if(optionalPerson.isPresent()) {
			this.personService.deleteById(id);

			return ResponseEntity.noContent().build();
		}
		else {
			return ResponseEntity.notFound().build();
		}
	}
}
