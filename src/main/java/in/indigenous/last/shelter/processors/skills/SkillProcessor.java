package in.indigenous.last.shelter.processors.skills;

import java.util.List;

import in.indigenous.last.shelter.models.apc.APC;
import in.indigenous.last.shelter.models.hero.skills.HeroSkill;
import in.indigenous.last.shelter.view.CombatHero;

public interface SkillProcessor {
	
	boolean isApplicable(HeroSkill skill);
	
	void processCombatHero(List<HeroSkill> glbHeroSkills, HeroSkill targetSkill, CombatHero heroView);
	
	void processAPC(CombatHero hero, HeroSkill skill, APC own, APC enemy);

}
