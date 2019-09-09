package in.indigenous.last.shelter.models.hero.skills.combat;

import in.indigenous.last.shelter.models.hero.skills.HeroSkill;

/**
 * Represents a hero skill.
 * 
 * @author sarkh
 *
 */
public abstract class CombatHeroSkill extends HeroSkill {

	/**
	 * The skill type.
	 */
	private CombatSkillType type;
	
	public CombatSkillType getType() {
		return type;
	}

	public void setType(CombatSkillType type) {
		this.type = type;
	}

}
