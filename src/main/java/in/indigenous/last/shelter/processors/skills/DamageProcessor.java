package in.indigenous.last.shelter.processors.skills;

import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import in.indigenous.last.shelter.models.apc.APC;
import in.indigenous.last.shelter.models.hero.skills.HeroSkill;
import in.indigenous.last.shelter.models.hero.skills.HeroSkillClass;
import in.indigenous.last.shelter.models.hero.skills.combat.CombatHeroSkill;
import in.indigenous.last.shelter.models.hero.skills.combat.CombatSkill;
import in.indigenous.last.shelter.models.hero.skills.combat.CombatSkillType;
import in.indigenous.last.shelter.view.CombatHero;

public class DamageProcessor implements SkillProcessor {

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
		double damage = 0.0d;
		double ld = 0.0d;
		ScriptEngineManager mgr = new ScriptEngineManager();
		ScriptEngine engine = mgr.getEngineByName("JavaScript");
		HeroSkill skill = glbHeroSkills.stream().filter(sk -> {
			return sk.getSkillId() == targetSkill.getSkillId();
		}).filter(sk -> {
			return sk.getLevel() == targetSkill.getLevel();
		}).findFirst().get();
		CombatSkill cskill = (CombatSkill) skill;
		String damageStr = cskill.getDamage();
		damageStr = StringUtils.replace(damageStr, "HL", String.valueOf(heroView.getLevel()));
		if (StringUtils.isNotBlank(damageStr)) {
			try {
				double damageUnit = Double.valueOf(engine.eval(damageStr).toString());
				if (CollectionUtils.isNotEmpty(cskill.getTurns())) {
					int turns = cskill.getTurns().size();
					damageUnit *= turns;
				}
				if (cskill.getTargetUnit() > 0) {
					damageUnit *= cskill.getTargetUnit();
				}
				if (cskill.getPrepTurns() > 0) {
					damageUnit /= (cskill.getPrepTurns() + 1);
				}
				double additionalDamage = 0;
				if (cskill.getAdditionalDamage() != null) {
					additionalDamage = cskill.getAdditionalDamage().getDamage();
					if (cskill.getAdditionalDamage().getTurns() > 0) {
						additionalDamage *= cskill.getAdditionalDamage().getTurns();
					}
				}
				damage += damageUnit + additionalDamage;
			} catch (NumberFormatException | ScriptException e) {
				e.printStackTrace();
			}
		}
		heroView.setDamage(heroView.getDamage() + damage);
		heroView.setRange(heroView.getRange() + cskill.getRange());
		if (StringUtils.isEmpty(heroView.getLeadingUnit()) && cskill.getLeadingUnit() != null
				&& StringUtils.isNotEmpty(cskill.getLeadingUnit().name())) {
			heroView.setLeadingUnit(cskill.getLeadingUnit().name());
		}
		if (cskill.getEnemyMinusTurns() != null) {
			int mt = cskill.getEnemyMinusTurns().getMinusTurns();
			if (cskill.getEnemyMinusTurns().getChance() > 0) {
				mt *= cskill.getEnemyMinusTurns().getChance();
			}
			if (cskill.getEnemyMinusTurns().getUnits() > 0) {
				mt *= cskill.getEnemyMinusTurns().getUnits();
			}
			heroView.setMinusEnemyTurns(heroView.getMinusEnemyTurns() + mt);
		}
		if (cskill.getEnemyMinusDamage() != null) {
			double ldUnit = 0.0d;
			try {
				ldUnit = (double) engine.eval(cskill.getEnemyMinusDamage().getDamage());
			} catch (ScriptException e) {
				e.printStackTrace();
			}
			if (cskill.getEnemyMinusDamage().getUnits() > 0) {
				ldUnit *= cskill.getEnemyMinusDamage().getUnits();
			}
			if (cskill.getEnemyMinusDamage().getTurns() > 0) {
				ldUnit *= cskill.getEnemyMinusDamage().getTurns();
			}
			ld += ldUnit;
			heroView.setLowerEnemyDamage(heroView.getLowerEnemyDamage() + ld);
		}

	}

	@Override
	public void processAPC(CombatHero hero, HeroSkill skill, APC own, APC enemy) {
		// TODO Auto-generated method stub

	}

}
