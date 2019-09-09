package in.indigenous.last.shelter.utils;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import in.indigenous.last.shelter.models.hero.Hero;
import in.indigenous.last.shelter.models.hero.skills.HeroSkill;
import in.indigenous.last.shelter.models.hero.skills.HeroSkillClass;
import in.indigenous.last.shelter.models.hero.skills.combat.CombatHeroSkill;
import in.indigenous.last.shelter.models.hero.skills.combat.CombatSkill;
import in.indigenous.last.shelter.models.hero.skills.combat.CombatSkillType;
import in.indigenous.last.shelter.processors.skills.SkillProcessor;
import in.indigenous.last.shelter.view.CombatHero;

/**
 * The Class SkillProcessorUtils.
 */
@Component
public class SkillProcessorUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(SkillProcessorUtils.class);

	@Resource
	private LastShelterCache lastShelterCache;
	
	@Resource
	private List<SkillProcessor> processors;

	/**
	 * Process Hero Stats.
	 *
	 * @param hero     the hero
	 * @param heroView the hero view
	 */
	public void processHeroView(HeroSkill skill, CombatHero heroView, int heroId) {
		List<Hero> glbHeroes = lastShelterCache.getGlobalHeroData();
		Hero glbHero = glbHeroes.stream().filter(h -> {
			return heroId == h.getId();
		}).findFirst().get();
		List<HeroSkill> skills = glbHero.getSkills();
		processors.forEach(p -> {
			if(p.isApplicable(skill)) {
				p.processCombatHero(skills, skill, heroView);
			}
		});
	}


	/**
	 * Process HP.
	 * 
	 * @param skills
	 * @param skillLevel
	 * @return
	 */
	private double processHp(List<HeroSkill> glbHeroSkills, HeroSkill targetSkill, CombatHero heroView) {
		double hp = 0.0d;
		HeroSkill skill = glbHeroSkills.stream().filter(sk -> {
			return sk.getSkillId() == targetSkill.getSkillId();
		}).filter(sk -> {
			return sk.getLevel() == targetSkill.getLevel();
		}).findFirst().get();
		if (HeroSkillClass.COMBAT.equals(skill.getSkillClass())) {
			CombatHeroSkill combatSkill = (CombatHeroSkill) skill;
			if (CombatSkillType.COMBAT.equals(combatSkill.getType())) {
				CombatSkill cskill = (CombatSkill) skill;
				// TODO - Update hp.
				/*
				 * if (skill.getHp() > 0) { hp += skill.getHp(); }
				 */
			}
		}
		return hp;
	}

	/**
	 * Process Combat Speed.
	 * 
	 * @param skills
	 * @param skillLevel
	 * @return
	 */
	private int processCombatSpeed(List<HeroSkill> glbHeroSkills, HeroSkill targetSkill, CombatHero heroView) {
		HeroSkill skill = glbHeroSkills.stream().filter(sk -> {
			return sk.getSkillId() == targetSkill.getSkillId();
		}).filter(sk -> {
			return sk.getLevel() == targetSkill.getLevel();
		}).findFirst().get();
		if (HeroSkillClass.COMBAT.equals(skill.getSkillClass())) {
			CombatHeroSkill combatSkill = (CombatHeroSkill) skill;
			if (CombatSkillType.COMBAT.equals(combatSkill.getType())) {
				CombatSkill cskill = (CombatSkill) skill;
				// Update combat speed.
				// return cskill.get
			}
		}
		return 0;
	}

}
