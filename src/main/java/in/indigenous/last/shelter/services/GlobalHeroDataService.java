package in.indigenous.last.shelter.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import in.indigenous.last.shelter.constants.LastShelterConstants;
import in.indigenous.last.shelter.models.GlobalHeroData;
import in.indigenous.last.shelter.models.GlobalHeroDetails;
import in.indigenous.last.shelter.models.GlobalHeroStats;
import in.indigenous.last.shelter.models.HeroSkills;
import in.indigenous.last.shelter.models.MinusMight;
import in.indigenous.last.shelter.utils.GlobalHeroUtils;

@Service
public class GlobalHeroDataService {

	@Resource
	private GlobalHeroUtils globalHeroUtils;

	@Resource
	private PropertiesConfiguration configuration;

	public List<GlobalHeroData> getGlobalHeroData() {
		String dataDir = configuration.getString(LastShelterConstants.LAST_SHELTER_GLOBAL_DATA_FILE_DIR);
		String fileName = configuration.getString(LastShelterConstants.LAST_SHELTER_GLOBAL_HERO_FILE);
		String heroDetailsSheetName = configuration
				.getString(LastShelterConstants.LAST_SHELTER_GLOBAL_HERO_DETAILS_SHEET);
		String heroSkillsSheetName = configuration.getString(LastShelterConstants.LAST_SHELTER_GLOBAL_HERO_STATS_SHEET);
		List<GlobalHeroDetails> details = new ArrayList<>();
		try {
			details = globalHeroUtils.getHeroDetails(dataDir, fileName, heroDetailsSheetName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		List<GlobalHeroData> data = details.stream().map(det -> {
			GlobalHeroData dt = new GlobalHeroData();
			dt.setDetails(det);
			try {
				dt.setStats(globalHeroUtils.getHeroStats(dataDir, fileName, heroSkillsSheetName, det.getId()));
			} catch (IOException e) {
				e.printStackTrace();
			}
			processGlobalHeroStats(dt);
			return dt;
		}).collect(Collectors.toList());
		data.sort((g1, g2) -> g2.getStats().getDamage().compareTo(g1.getStats().getDamage()));
		return data;
	}

	/**
	 * Process Global Hero Stats.
	 * 
	 * @param data
	 */
	private void processGlobalHeroStats(GlobalHeroData data) {
		if (CollectionUtils.isNotEmpty(data.getStats().getSkills())) {
			List<HeroSkills> skills = data.getStats().getSkills();
			GlobalHeroStats stats = data.getStats();
			stats.setMarchingCapacity(processMarchingCapacity(skills));
			stats.setDamage(processDamage(skills));
			stats.setMight(processMight(skills));
			stats.setResitance(processResistance(skills));
			stats.setLowerResistance(processLowerResistance(skills));
			stats.setRange(processRange(skills));
			stats.setLeadingUnit(processLeadingUnit(skills));
			stats.setHp(processHp(skills));
			stats.setMinusEnemyTurns(processMinusEnemyTurns(skills));
			stats.setLowerDamage(processLowerDamage(skills));
			stats.setCombatSpeed(processCombatSpeed(skills));
			stats.setSeigeMight(processSeigeMight(skills));
			stats.setLowerMight(processLowerMight(skills));
		}
	}

	private double processMarchingCapacity(List<HeroSkills> skills) {
		List<String> marchingCapacityWithHL = new ArrayList<>();
		List<String> marchingCapacityWithML = new ArrayList<>();
		double marchc = 0.0d;
		skills.sort(Comparator.comparing(HeroSkills::getLevel));
		ScriptEngineManager mgr = new ScriptEngineManager();
		ScriptEngine engine = mgr.getEngineByName("JavaScript");
		for (HeroSkills skill : skills) {
			if (StringUtils.isNotEmpty(skill.getMarchingCapacity())) {
				if (skill.getLevel() == 10) {
					String marchingCapacity = skill.getMarchingCapacity();
					if (marchingCapacity.contains("HL")) {
						marchingCapacityWithHL.add(marchingCapacity);
					} else if (marchingCapacity.contains("ML")) {
						marchingCapacityWithML.add(marchingCapacity);
					}
				}
			}
		}
		for (String marchCap : marchingCapacityWithHL) {
			marchCap = StringUtils.replace(marchCap, "HL", "50");
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
		return marchc;
	}

	/**
	 * Process damage
	 * 
	 * @param skills
	 * @return
	 */
	private double processDamage(List<HeroSkills> skills) {
		double damage = 0.0d;
		ScriptEngineManager mgr = new ScriptEngineManager();
		ScriptEngine engine = mgr.getEngineByName("JavaScript");
		for (HeroSkills skill : skills) {
			if (skill.getLevel() == 10) {
				if (skill.getDamage() != null && StringUtils.isNotEmpty(skill.getDamage().getDamage())) {
					String damageStr = skill.getDamage().getDamage();
					damageStr = StringUtils.replace(damageStr, "HL", "50");
					try {
						double damageUnit = Double.valueOf(engine.eval(damageStr).toString());
						if (CollectionUtils.isNotEmpty(skill.getDamage().getTurns())) {
							int turns = skill.getDamage().getTurns().size();
							damageUnit *= turns;
						}
						if (skill.getDamage().getTargetUnit() > 0) {
							damageUnit *= skill.getDamage().getTargetUnit();
						}
						if (skill.getDamage().getMinusTurns() > 0) {
							damageUnit /= (skill.getDamage().getMinusTurns() + 1);
						}
						double additionalDamage = 0;
						if (skill.getAdditionalDamage() != null) {
							additionalDamage = skill.getAdditionalDamage().getDamage();
							if (skill.getAdditionalDamage().getTurns() > 0) {
								additionalDamage *= skill.getAdditionalDamage().getTurns();
							}
						}
						damage += damageUnit + additionalDamage;
					} catch (NumberFormatException | ScriptException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return damage;
	}

	/**
	 * Process Resistance
	 * 
	 * @param skills
	 * @return
	 */
	private double processResistance(List<HeroSkills> skills) {
		double resistance = 0.0d;
		for (HeroSkills skill : skills) {
			if (skill.getLevel() == 10) {
				resistance += skill.getResitance();
			}
		}
		return resistance;
	}

	/**
	 * Process Might.
	 * 
	 * @param skills
	 * @return
	 */
	private double processMight(List<HeroSkills> skills) {
		double might = 0.0d;
		for (HeroSkills skill : skills) {
			if (skill.getLevel() == 10) {
				might += skill.getMight();
			}
		}
		return might;
	}

	/**
	 * Process Lower Resistance.
	 * 
	 * @param skills
	 * @return
	 */
	private double processLowerResistance(List<HeroSkills> skills) {
		double lower = 0.0d;
		for (HeroSkills skill : skills) {
			if (skill.getLevel() == 10) {
				if (skill.getLowerResistance() != null) {
					double lowerUnit = skill.getLowerResistance().getLowerResistance();
					if (skill.getLowerResistance().getTurns() > 0) {
						lowerUnit *= skill.getLowerResistance().getTurns();
					}
					lower += lowerUnit;
				}
			}
		}
		return lower;
	}

	/**
	 * Process Range
	 * 
	 * @param skills
	 * @return
	 */
	private int processRange(List<HeroSkills> skills) {
		int skillVal = 0;
		for (HeroSkills skill : skills) {
			if (skillVal == 0) {
				skillVal = skill.getRange();
			} else if (skill.getRange() < skillVal && skill.getRange() != 0) {
				skillVal = skill.getRange();
			}
		}
		return skillVal;
	}

	/**
	 * Process Leading Unit.
	 * 
	 * @param skills
	 * @return
	 */
	private String processLeadingUnit(List<HeroSkills> skills) {
		for (HeroSkills skill : skills) {
			if (StringUtils.isNotEmpty(skill.getLeadingUnit())) {
				return skill.getLeadingUnit();
			}
		}
		return null;
	}

	private double processHp(List<HeroSkills> skills) {
		double hp = 0.0d;
		for (HeroSkills skill : skills) {
			if (skill.getLevel() == 10) {
				if (skill.getHp() > 0) {
					hp += skill.getHp();
				}
			}
		}
		return hp;
	}

	private double processMinusEnemyTurns(List<HeroSkills> skills) {
		for (HeroSkills skill : skills) {
			if (skill.getLevel() == 10 && skill.getMinusEnemyTurns() != null) {
				double mt = skill.getMinusEnemyTurns().getMinusTurns();
				if (skill.getMinusEnemyTurns().getChance() > 0) {
					mt *= skill.getMinusEnemyTurns().getChance();
				}
				if (skill.getMinusEnemyTurns().getUnits() > 0) {
					mt *= skill.getMinusEnemyTurns().getUnits();
				}
				return mt;
			}
		}
		return 0.0d;
	}

	private double processLowerDamage(List<HeroSkills> skills) {
		double ld = 0.0d;
		ScriptEngineManager mgr = new ScriptEngineManager();
		ScriptEngine engine = mgr.getEngineByName("JavaScript");
		for (HeroSkills skill : skills) {
			if (skill.getLevel() == 10) {
				if (skill.getLowerDamage() != null) {
					double ldUnit = 0.0d;
					try {
						ldUnit = (double) engine.eval(skill.getLowerDamage().getDamage());
					} catch (ScriptException e) {
						e.printStackTrace();
					}
					if(skill.getLowerDamage().getUnits() > 0) {
						ldUnit *= skill.getLowerDamage().getUnits();
					}
					if (skill.getLowerDamage().getTurns() > 0) {
						ldUnit *= skill.getLowerDamage().getTurns();
					}
					ld += ldUnit;
				}
			}
		}
		return ld;
	}

	private int processCombatSpeed(List<HeroSkills> skills) {
		List<HeroSkills> relvSkills = skills.stream().filter(t -> {
			return t.getLevel() == 10 && t.getCombatSpeed() > 0;
		}).collect(Collectors.toList());
		for (HeroSkills skill : relvSkills) {
			if (skill.getLevel() == 10) {
				return skill.getCombatSpeed();
			}
		}
		return 0;
	}

	private double processSeigeMight(List<HeroSkills> skills) {
		List<HeroSkills> relvSkills = skills.stream().filter(t -> {
			return t.getLevel() == 10 && t.getSeigeMight() > 0;
		}).collect(Collectors.toList());
		for (HeroSkills skill : relvSkills) {
			if (skill.getLevel() == 10) {
				return skill.getSeigeMight();
			}
		}
		return 0.0d;
	}

	private double processLowerMight(List<HeroSkills> skills) {
		double val = 0.0d;
		for (HeroSkills skill : skills) {
			if (skill.getLevel() == 10 && skill.getDamage() != null) {
				MinusMight mm = skill.getDamage().getMinusMight();
				if (mm != null) {
					if (mm.getTurns() == 0) {
						val += mm.getMinusMight();
					} else {
						val += mm.getMinusMight() * mm.getTurns();
					}

				}
			}
		}
		return val;
	}

}
