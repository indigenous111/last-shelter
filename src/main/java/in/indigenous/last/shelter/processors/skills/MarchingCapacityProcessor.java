package in.indigenous.last.shelter.processors.skills;

import java.util.ArrayList;
import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.commons.lang3.StringUtils;

import in.indigenous.last.shelter.models.apc.APC;
import in.indigenous.last.shelter.models.hero.skills.HeroSkill;
import in.indigenous.last.shelter.models.hero.skills.HeroSkillClass;
import in.indigenous.last.shelter.models.hero.skills.combat.CombatHeroSkill;
import in.indigenous.last.shelter.models.hero.skills.combat.CombatSkillType;
import in.indigenous.last.shelter.models.hero.skills.combat.LeadershipSkill;
import in.indigenous.last.shelter.view.CombatHero;

public class MarchingCapacityProcessor implements SkillProcessor {

	@Override
	public boolean isApplicable(HeroSkill skill) {
		boolean result = false;
		if (HeroSkillClass.COMBAT.equals(skill.getSkillClass())) {
			CombatHeroSkill combatSkill = (CombatHeroSkill) skill;
			if (CombatSkillType.LEADERSHIP.equals(combatSkill.getType())) {
				result = true;
			}
		}
		return result;
	}

	@Override
	public void processCombatHero(List<HeroSkill> glbHeroSkills, HeroSkill targetSkill, CombatHero heroView) {
		double marchc = 0.0d;
		List<String> marchingCapacityWithHL = new ArrayList<>();
		List<String> marchingCapacityWithML = new ArrayList<>();
		ScriptEngineManager mgr = new ScriptEngineManager();
		ScriptEngine engine = mgr.getEngineByName("JavaScript");
		HeroSkill skill = glbHeroSkills.stream().filter(sk -> {
			return sk.getSkillId() == targetSkill.getSkillId();
		}).filter(sk -> {
			return sk.getLevel() == targetSkill.getLevel();
		}).findFirst().get();
		LeadershipSkill lskill = (LeadershipSkill) skill;
		if (StringUtils.isNotEmpty(lskill.getMarchingCapacity())) {
			String marchingCapacity = lskill.getMarchingCapacity();
			if (marchingCapacity.contains("HL")) {
				marchingCapacityWithHL.add(marchingCapacity);
			} else if (marchingCapacity.contains("ML")) {
				marchingCapacityWithML.add(marchingCapacity);
			}
		}

		for (String marchCap : marchingCapacityWithHL) {
			marchCap = StringUtils.replace(marchCap, "HL", String.valueOf(heroView.getLevel()));
			try {
				double marchu = Double.valueOf(engine.eval(marchCap).toString());
				marchc += marchu;
			} catch (NumberFormatException | ScriptException e) {
				e.printStackTrace();
			}
		}
		for (String marchCap : marchingCapacityWithML) {
			marchCap = StringUtils.replace(marchCap, "ML", String.valueOf(marchc));
			try {
				double marchu = Double.valueOf(engine.eval(marchCap).toString());
				marchc += marchu;
			} catch (NumberFormatException | ScriptException e) {
				e.printStackTrace();
			}
		}
		heroView.setMarchingCapacity(heroView.getMarchingCapacity() + marchc);
	}

	@Override
	public void processAPC(CombatHero hero, HeroSkill skill, APC own, APC enemy) {
		// TODO Auto-generated method stub

	}

}
