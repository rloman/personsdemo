package nl.acme.person.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
public class Person implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String name;
	private int age;


	public long getId(){ 
		return this.id;
	}

	public void setId(long id){ 
		this.id=id;
	}

	public String getName(){ 
		return this.name;
	}

	public void setName(String name){ 
		this.name=name;
	}

	public int getAge(){ 
		return this.age;
	}

	public void setAge(int age){ 
		this.age=age;
	}

}
