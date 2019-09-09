package in.indigenous.last.shelter.services.global.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import in.indigenous.last.shelter.models.hero.Hero;
import in.indigenous.last.shelter.services.global.GlobalHeroService;
import in.indigenous.last.shelter.utils.LastShelterCache;
import in.indigenous.last.shelter.utils.SkillProcessorUtils;
import in.indigenous.last.shelter.view.CombatHero;

@Service
public class DefaultGlobalHeroService implements GlobalHeroService {

	@Resource
	private LastShelterCache lastShelterCache;

	@Resource
	private SkillProcessorUtils skillProcessorUtils;

	@Override
	public List<CombatHero> getGlobalHeroDetails() {
		List<CombatHero> result = new ArrayList<>();
		List<Hero> glbHeroes = lastShelterCache.getGlobalHeroData();
		glbHeroes.forEach(h -> {
			CombatHero view = new CombatHero();
			h.getSkills().forEach(sk -> {
				if (sk.getLevel() == 10) {
					skillProcessorUtils.processHeroView(sk, view, h.getId());
					view.setId(h.getId());
					view.setName(h.getName());
					view.setHeroClass(h.getHeroClass().name());
				}
			});
			result.add(view);
		});
		return result;
	}

}
