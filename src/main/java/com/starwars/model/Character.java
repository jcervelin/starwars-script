package com.starwars.model;

import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Character {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String name;
	
	//Map created to group words more efficiently
	@JsonIgnore
	@Transient
	private Map<String, WordCount> wordCountsMap;
	@OneToMany(cascade={CascadeType.ALL},mappedBy="character",targetEntity=WordCount.class)
	private Set<WordCount> wordCounts;
	
	public Map<String, WordCount> getWordCountsMap() {
		return wordCountsMap;
	}

	public void setWordCountsMap(Map<String, WordCount> wordCountsMap) {
		this.wordCountsMap = wordCountsMap;
	}

	public Set<WordCount> getWordCounts() {
		return wordCounts;
	}

	public void setWordCounts(Set<WordCount> wordCounts) {
		this.wordCounts = wordCounts;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Character other = (Character) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Character [id=" + id + ", name=" + name + ", wordCountsMap="
				+ wordCountsMap + ", wordCounts=" + wordCounts + "]";
	}
	
}