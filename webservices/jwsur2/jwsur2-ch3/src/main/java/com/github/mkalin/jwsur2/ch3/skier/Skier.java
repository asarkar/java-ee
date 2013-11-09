package com.github.mkalin.jwsur2.ch3.skier;

import java.util.Collection;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Skier {
	private Person person;
	private String nationalTeam;
	private Collection<String> majorAchievements;

	// constructors
	public Skier() {
	} // required for unmarshaling

	public Skier(Person person, String nationalTeam,
			Collection<String> majorAchievements) {
		setPerson(person);
		setNationalTeam(nationalTeam);
		setMajorAchievements(majorAchievements);
	}

	// properties
	public Person getPerson() {
		return this.person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public String getNationalTeam() {
		return this.nationalTeam;
	}

	public void setNationalTeam(String nationalTeam) {
		this.nationalTeam = nationalTeam;
	}

	public Collection<String> getMajorAchievements() {
		return majorAchievements;
	}

	public void setMajorAchievements(Collection<String> majorAchievements) {
		this.majorAchievements = majorAchievements;
	}
}
