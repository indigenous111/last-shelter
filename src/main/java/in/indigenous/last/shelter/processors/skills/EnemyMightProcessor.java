package in.indigenous.last.shelter.processors.skills;

import java.util.List;

import in.indigenous.last.shelter.models.apc.APC;
import in.indigenous.last.shelter.models.hero.skills.HeroSkill;
import in.indigenous.last.shelter.models.hero.skills.HeroSkillClass;
import in.indigenous.last.shelter.models.hero.skills.combat.CombatHeroSkill;
import in.indigenous.last.shelter.models.hero.skills.combat.CombatSkill;
import in.indigenous.last.shelter.models.hero.skills.combat.CombatSkillType;
import in.indigenous.last.shelter.view.CombatHero;

public class EnemyMightProcessor implements SkillProcessor {

	@Override
	public boolean isApplicable(HeroSkill skill) {
		boolean result = false;
		if (HeroSkillClass.COMBAT.equals(skill.getSkillClass())) {
			CombatHeroSkill combatSkill = (CombatHeroSkill) skill;
			if (CombatSkillType.COMBAT.equals(combatSkill.getType())) {
				result = true;
			}
		}
		return result;
	}

	@Override
	public void processCombatHero(List<HeroSkill> glbHeroSkills, HeroSkill targetSkill, CombatHero heroView) {
		double val = 0.0d;
		HeroSkill skill = glbHeroSkills.stream().filter(sk -> {
			return sk.getSkillId() == targetSkill.getSkillId();
		}).filter(sk -> {
			return sk.getLevel() == targetSkill.getLevel();
		}).findFirst().get();
		CombatSkill cskill = (CombatSkill) skill;
		if (cskill.getEnemyMinusMight() != null) {
			double mm = cskill.getEnemyMinusMight().getMight();
			if (cskill.getEnemyMinusMight().getTurns() == 0) {
				val += mm;
			} else {
				val += mm * cskill.getEnemyMinusMight().getTurns();
			}
		}
		heroView.setLowerMight(heroView.getLowerMight() + val);
	}

	@Override
	public void processAPC(CombatHero hero, HeroSkill skill, APC own, APC enemy) {
		// TODO Auto-generated method stub

	}

}
