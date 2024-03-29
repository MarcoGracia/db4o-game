package es.mgj.base;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import es.mgj.base.GameUser;
import es.mgj.base.Magic;
import es.mgj.base.Object;
import es.mgj.base.Pet;
import es.mgj.base.Summoning;

/**
 * Character generated by hbm2java
 */

public class Character implements java.io.Serializable {
	
	private String id;
	
	private String name;
	private String race;
	private String class_;
	private Float level;
	private Date creationDate;
	private Float life;
	private Float mana;
	private Boolean gender;
	private GameUser gameUser;
	private Set<Pet> pets;
	private Set<Summoning> summonings;
	private Set<Magic> magics;
	private Set<es.mgj.base.Object> objects;
	
	public Character() {
	}

	public Character(String id) {
		this.id = id;
	}

	public Character(String id, GameUser gameUser, String name, String race,
			String class_, Float level, Date creationDate, Float life,
			Float mana, Boolean gender, Set<Pet> pets,
			Set<Summoning> summonings, Set<Object> objects, Set<Magic> magics) {
		
		this.id = id;
		this.gameUser = gameUser;
		this.name = name;
		this.race = race;
		this.class_ = class_;
		this.level = level;
		this.creationDate = creationDate;
		this.life = life;
		this.mana = mana;
		this.gender = gender;
		this.pets = pets;
		this.summonings = summonings;
		this.objects = objects;
		this.magics = magics;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public GameUser getGameUser() {
		return this.gameUser;
	}

	public void setGameUser(GameUser gameUser) {
		this.gameUser = gameUser;
	}


	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getRace() {
		return this.race;
	}

	public void setRace(String race) {
		this.race = race;
	}


	public String getClass_() {
		return this.class_;
	}

	public void setClass_(String class_) {
		this.class_ = class_;
	}


	public Float getLevel() {
		return this.level;
	}

	public void setLevel(Float level) {
		this.level = level;
	}



	public Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}


	public Float getLife() {
		return this.life;
	}

	public void setLife(Float life) {
		this.life = life;
	}


	public Float getMana() {
		return this.mana;
	}

	public void setMana(Float mana) {
		this.mana = mana;
	}


	public Boolean getGender() {
		return this.gender;
	}

	public void setGender(Boolean gender) {
		this.gender = gender;
	}


	public Set<Pet> getPets() {
		
		if(this.pets == null)
			pets = new HashSet<Pet>(0);
		
		return this.pets;
	}

	public void setPets(Set<Pet> pets) {
		this.pets = pets;
	}


	public Set<Summoning> getSummonings() {
		
		if(this.summonings == null)
			summonings = new HashSet<Summoning>(0);
		
		return this.summonings;
	}

	public void setSummonings(Set<Summoning> summonings) {
		this.summonings = summonings;
	}


	public Set<Object> getObjects() {
		if(this.objects == null)
			objects = new HashSet<es.mgj.base.Object>(0);
		return this.objects;
	}

	public void setObjects(Set<Object> objects) {
		this.objects = objects;
	}

	
	public Set<Magic> getMagics() {
		
		if(this.magics == null)
			magics = new HashSet<Magic>(0);
		
		return this.magics;
	}

	public void setMagics(Set<Magic> magics) {
		this.magics = magics;
	}

}
