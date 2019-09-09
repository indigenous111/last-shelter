package in.indigenous.last.shelter.models.hero;

import java.util.List;

import in.indigenous.last.shelter.models.hero.skills.HeroSkill;

public abstract class Hero {

	private int id;

	private String name;

	private HeroClass heroClass;

	private int level;

	private List<HeroSkill> skills;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public HeroClass getHeroClass() {
		return heroClass;
	}

	public void setHeroClass(HeroClass heroClass) {
		this.heroClass = heroClass;
	}

	public List<HeroSkill> getSkills() {
		return skills;
	}

	public void setSkills(List<HeroSkill> skills) {
		this.skills = skills;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

}
