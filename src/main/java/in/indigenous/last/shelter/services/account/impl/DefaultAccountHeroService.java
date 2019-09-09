package in.indigenous.last.shelter.services.account.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import in.indigenous.last.shelter.models.hero.Hero;
import in.indigenous.last.shelter.models.hero.skills.HeroSkill;
import in.indigenous.last.shelter.services.account.AccountHeroService;
import in.indigenous.last.shelter.services.global.GlobalHeroService;
import in.indigenous.last.shelter.utils.LastShelterCache;
import in.indigenous.last.shelter.utils.SkillProcessorUtils;
import in.indigenous.last.shelter.view.CombatHero;

@Service
public class DefaultAccountHeroService implements AccountHeroService {

	@Resource
	private GlobalHeroService globalHeroService;

	@Resource
	private LastShelterCache lastShelterCache;

	@Resource
	private SkillProcessorUtils skillProcessorUtils;

	@Override
	public List<CombatHero> getAccountHeroDetails(int account) {
		List<CombatHero> result = new ArrayList<>();
		Map<Integer, List<HeroSkill>> accountHeroDetails = lastShelterCache.getAccountHeroData(account);
		List<Hero> glbHeroes = lastShelterCache.getGlobalHeroData();
		accountHeroDetails.keySet().forEach(id -> {
			CombatHero heroView = new CombatHero();
			Hero glbHero = glbHeroes.stream().filter(h -> {
				return h.getId() == id;
			}).findFirst().get();
			accountHeroDetails.get(id).forEach(s -> {
				HeroSkill sk = glbHero.getSkills().stream().filter(s1 -> {
					return s1.getSkillId() == s.getSkillId();
				}).filter(ps -> {
					return ps.getLevel() == s.getLevel();
				}).findFirst().get();
				skillProcessorUtils.processHeroView(sk, heroView, id);
				heroView.setId(id);
				heroView.setName(glbHero.getName());
				heroView.setHeroClass(glbHero.getHeroClass().name());
			});
			result.add(heroView);
		});
		return result;
	}

}
