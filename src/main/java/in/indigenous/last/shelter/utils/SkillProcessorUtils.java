package in.indigenous.last.shelter.utils;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import in.indigenous.last.shelter.models.hero.Hero;
import in.indigenous.last.shelter.models.hero.skills.HeroSkill;
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

}
