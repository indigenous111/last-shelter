package in.indigenous.last.shelter.services;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import in.indigenous.last.shelter.models.APCInfo;
import in.indigenous.last.shelter.models.APCTroopsDetail;
import in.indigenous.last.shelter.models.AccountHeroData;
import in.indigenous.last.shelter.models.HeroData;
import in.indigenous.last.shelter.models.HeroSkills;
import in.indigenous.last.shelter.utils.LastShelterCache;
import in.indigenous.last.shelter.utils.SkillProcessorUtils;

@Service
public class AccountHeroDataService {

	@Resource
	private GlobalHeroDataService globalHeroDataService;

	@Resource
	private PropertiesConfiguration configuration;

	@Resource
	private SkillProcessorUtils skillProcessorUtils;

	@Resource
	private LastShelterCache lastShelterCache;

	/**
	 * 
	 * @param account
	 * @return
	 */
	public List<HeroData> getAccountHeroData(int account) {
		List<HeroData> globalHeroData = globalHeroDataService.getGlobalHeroData();
		List<HeroData> data = new ArrayList<>();
		Map<Integer, List<AccountHeroData>> accountHeroData = lastShelterCache.getAccountHeroData(account);
		data = globalHeroData.stream().filter(t -> {
			return accountHeroData.containsKey(t.getDetails().getId());
		}).collect(Collectors.toList());
		List<Integer> applicableSkills = accountHeroData.values().stream().flatMap(List::stream).map(h -> {
			return h.getSkillId();
		}).collect(Collectors.toList());
		data.forEach(d -> {
			List<HeroSkills> skills = d.getStats().getSkills().stream().filter(s -> {
				return applicableSkills.contains(s.getId());
			}).collect(Collectors.toList());
			d.getStats().setSkills(skills);
		});
		for (HeroData heroData : data) {
			for (AccountHeroData accData : accountHeroData.get(heroData.getDetails().getId())) {
				skillProcessorUtils.processHeroStats(heroData, accData.getSkillId(), accData.getSkillLevel(),
						String.valueOf(accData.getHeroLevel()), 0.0d);
				heroData.getDetails().setLevel(accData.getHeroLevel());
			}
		}
		data.sort((g1, g2) -> g2.getStats().getDamage().compareTo(g1.getStats().getDamage()));
		return data;
	}

	/**
	 * Gets the APC info of an account.
	 * 
	 * @param account
	 * @return
	 */
	public List<APCInfo> getAPCInfo(int account) {
		List<APCInfo> result = lastShelterCache.getAPCInfo(account);
		List<HeroData> globalHeroData = getAccountHeroData(account);
		Map<Integer, List<AccountHeroData>> accountHeroData = lastShelterCache.getAccountHeroData(account);
		Set<Integer> applicableSkills = accountHeroData.values().stream().flatMap(List::stream).map(h -> {
			return h.getSkillId();
		}).collect(Collectors.toSet());
		List<Integer> applicable = new ArrayList<>(Arrays.asList(1, 6));
		applicableSkills.stream().filter(s -> {
			return applicable.contains(s);
		}).collect(Collectors.toList());
		applicable.forEach(a -> {
			result.forEach(r -> {
				r.getDetails().forEach(d -> {
					int heroId = d.getHeroId();
					d.setName(r.getName());
					if (heroId > 0) {
						List<AccountHeroData> hdata = accountHeroData.get(heroId);
						List<AccountHeroData> fhdata = hdata.stream().filter(h -> {
							return h.getSkillId() == a;
						}).collect(Collectors.toList());
						fhdata.stream().forEach(s -> {
							if (Objects.isNull(s)) {
								fhdata.remove(s);
							}
						});
						List<HeroData> glb = globalHeroData;
						List<HeroData> glb1 = glb = glb.stream().filter(g -> {
							return g.getDetails().getId() == d.getHeroId();
						}).collect(Collectors.toList());
						d.setHeroName(glb1.get(0).getDetails().getName());
						glb.stream().filter(gt -> {
							return gt.getDetails().getId() == d.getHeroId();
						});
						List<HeroSkills> sk = glb.stream().filter(gt -> {
							return gt.getDetails().getId() == d.getHeroId();
						}).map(gt -> {
							return gt.getStats().getSkills();
						}).flatMap(List::stream).filter(gter -> {
							return fhdata.size() > 0 && gter.getId() == fhdata.get(0).getSkillId()
									&& gter.getLevel() == fhdata.get(0).getSkillLevel();
						}).collect(Collectors.toList());
						setMarchingCapacity(d, sk, hdata.get(0).getHeroLevel());
					} else {
						d.setNetMarchingCapacity(d.getMarchingCapacity());
					}
				});
			});
		});
		return result;
	}

	private void setMarchingCapacity(APCTroopsDetail detail, List<HeroSkills> skills, int heroLvl) {
		long marchingCapacity = detail.getMarchingCapacity();
		List<String> marchingCapacityWithHL = new ArrayList<>();
		List<String> marchingCapacityWithML = new ArrayList<>();
		ScriptEngineManager mgr = new ScriptEngineManager();
		ScriptEngine engine = mgr.getEngineByName("JavaScript");
		for (HeroSkills skill : skills) {
			if (StringUtils.isNotEmpty(skill.getMarchingCapacity())) {
				String mch = skill.getMarchingCapacity();
				if (mch.contains("HL")) {
					marchingCapacityWithHL.add(mch);
				} else if (mch.contains("ML")) {
					marchingCapacityWithML.add(mch);
				}
			}
		}
		for (String marchCap : marchingCapacityWithHL) {
			marchCap = StringUtils.replace(marchCap, "HL", String.valueOf(heroLvl));
			try {
				double marchu = Double.valueOf(engine.eval(marchCap).toString());
				marchingCapacity += marchu;
			} catch (NumberFormatException | ScriptException e) {
				e.printStackTrace();
			}
			detail.setNetMarchingCapacity(marchingCapacity);
		}
		for (String marchCap : marchingCapacityWithML) {
			marchCap = StringUtils.replace(marchCap, "ML", String.valueOf(marchCap));
			try {
				double marchu = Double.valueOf(engine.eval(marchCap).toString());
				marchingCapacity += marchu;
			} catch (NumberFormatException | ScriptException e) {
				e.printStackTrace();
			}
			detail.setNetMarchingCapacity(marchingCapacity);
		}
	}

}