package in.indigenous.last.shelter.models.hero.skills;

public abstract class HeroSkill {

	/**
	 * The skill id.
	 */
	private int skillId;

	/**
	 * The name of the skill.
	 */
	private String name;

	/**
	 * The skill level.
	 */
	private int level;

	/**
	 * Heroe's level.
	 */
	private int heroLevel;

	/**
	 * Skill class.
	 */
	private HeroSkillClass skillClass;

	public int getSkillId() {
		return skillId;
	}

	public void setSkillId(int skillId) {
		this.skillId = skillId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public HeroSkillClass getSkillClass() {
		return skillClass;
	}

	public void setSkillClass(HeroSkillClass skillClass) {
		this.skillClass = skillClass;
	}

	public int getHeroLevel() {
		return heroLevel;
	}

	public void setHeroLevel(int heroLevel) {
		this.heroLevel = heroLevel;
	}

}
