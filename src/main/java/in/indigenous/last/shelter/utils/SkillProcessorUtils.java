package in.indigenous.last.shelter.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import in.indigenous.last.shelter.models.HeroData;
import in.indigenous.last.shelter.models.HeroSkills;
import in.indigenous.last.shelter.models.HeroStats;
import in.indigenous.last.shelter.models.MinusMight;

@Component
public class SkillProcessorUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(SkillProcessorUtils.class);

	/**
	 * Process Hero Stats.
	 * 
	 * @param data
	 */
	public void processHeroStats(HeroData data, int skillId, int skillLevel, String heroLvl, double mc) {
		LOGGER.debug(
				"Processing hero " + data.getDetails().getName() + ", skill " + skillId + ", skillLevel " + skillLevel);
		if (CollectionUtils.isNotEmpty(data.getStats().getSkills())) {
			List<HeroSkills> skills = data.getStats().getSkills();
			HeroStats stats = data.getStats();
			stats.setMarchingCapacity(
					stats.getMarchingCapacity() + processMarchingCapacity(skills, skillId, skillLevel, heroLvl, mc));
			stats.setDamage(processDamage(skills, skillId, skillLevel, heroLvl));
			stats.setMight(processMight(skills, skillId, skillLevel));
			stats.setResitance(processResistance(skills, skillId, skillLevel));
			stats.setLowerResistance(processLowerResistance(skills, skillId, skillLevel));
			stats.setRange(processRange(skills, skillId, skillLevel));
			stats.setLeadingUnit(processLeadingUnit(skills, skillId, skillLevel));
			stats.setHp(processHp(skills, skillId, skillLevel));
			stats.setMinusEnemyTurns(processMinusEnemyTurns(skills, skillId, skillLevel));
			stats.setLowerDamage(processLowerDamage(skills, skillId, skillLevel));
			stats.setCombatSpeed(processCombatSpeed(skills, skillId, skillLevel));
			stats.setSeigeMight(processSeigeMight(skills, skillId, skillLevel));
			stats.setLowerMight(processLowerMight(skills, skillId, skillLevel));
			stats.setSeigeDefenseMight(processSeigeDefenseMight(skills, skillId, skillLevel));
			stats.setBulwark(processBulwark(skills, skillId, skillLevel));
		}
	}

	/**
	 * Process marching capacity.
	 * 
	 * @param skills
	 * @param skillLevel
	 * @return
	 */
	private double processMarchingCapacity(List<HeroSkills> skills, int skillId, int skillLevel, String heroLvl,
			double mc) {
		List<Integer> applicableSkills = new ArrayList<>(Arrays.asList(1, 6));
		double marchc = 0.0d;
		if (applicableSkills.contains(skillId)) {
			List<String> marchingCapacityWithHL = new ArrayList<>();
			List<String> marchingCapacityWithML = new ArrayList<>();
			skills.sort(Comparator.comparing(HeroSkills::getLevel));
			ScriptEngineManager mgr = new ScriptEngineManager();
			ScriptEngine engine = mgr.getEngineByName("JavaScript");
			for (HeroSkills skill : skills) {
				if (StringUtils.isNotEmpty(skill.getMarchingCapacity())) {
					if (skill.getLevel() == skillLevel) {
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
				marchCap = StringUtils.replace(marchCap, "HL", heroLvl);
				try {
					double marchu = Double.valueOf(engine.eval(marchCap).toString());
					LOGGER.debug("Processing hero with level = " + heroLvl
							+ ", march capacity HL skill value calcualated as: " + marchu);
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
		}
		return marchc;
	}

	/**
	 * Process damage
	 * 
	 * @param skills
	 * @return
	 */
	private double processDamage(List<HeroSkills> skills, int skillId, int skillLevel, String heroLvl) {
		double damage = 0.0d;
		ScriptEngineManager mgr = new ScriptEngineManager();
		ScriptEngine engine = mgr.getEngineByName("JavaScript");
		for (HeroSkills skill : skills) {
			if (skill.getLevel() == skillLevel) {
				if (skill.getDamage() != null && StringUtils.isNotEmpty(skill.getDamage().getDamage())) {
					String damageStr = skill.getDamage().getDamage();
					damageStr = StringUtils.replace(damageStr, "HL", heroLvl);
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
	private double processResistance(List<HeroSkills> skills, int skillId, int skillLevel) {
		double resistance = 0.0d;
		for (HeroSkills skill : skills) {
			if (skill.getLevel() == skillLevel) {
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
	private double processMight(List<HeroSkills> skills, int skillId, int skillLevel) {
		double might = 0.0d;
		for (HeroSkills skill : skills) {
			if (skill.getLevel() == skillLevel) {
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
	private double processLowerResistance(List<HeroSkills> skills, int skillId, int skillLevel) {
		double lower = 0.0d;
		for (HeroSkills skill : skills) {
			if (skill.getLevel() == skillLevel) {
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
	private int processRange(List<HeroSkills> skills, int skillId, int skillLevel) {
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
	private String processLeadingUnit(List<HeroSkills> skills, int skillId, int skillLevel) {
		for (HeroSkills skill : skills) {
			if (StringUtils.isNotEmpty(skill.getLeadingUnit())) {
				return skill.getLeadingUnit();
			}
		}
		return null;
	}

	/**
	 * Process HP.
	 * 
	 * @param skills
	 * @param skillLevel
	 * @return
	 */
	private double processHp(List<HeroSkills> skills, int skillId, int skillLevel) {
		double hp = 0.0d;
		for (HeroSkills skill : skills) {
			if (skill.getLevel() == skillLevel) {
				if (skill.getHp() > 0) {
					hp += skill.getHp();
				}
			}
		}
		return hp;
	}

	/**
	 * Process minus enemy turs.
	 * 
	 * @param skills
	 * @param skillLevel
	 * @return
	 */
	private double processMinusEnemyTurns(List<HeroSkills> skills, int skillId, int skillLevel) {
		for (HeroSkills skill : skills) {
			if (skill.getLevel() == skillLevel && skill.getMinusEnemyTurns() != null) {
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

	/**
	 * Process lower damage.
	 * 
	 * @param skills
	 * @param skillLevel
	 * @return
	 */
	private double processLowerDamage(List<HeroSkills> skills, int skillId, int skillLevel) {
		double ld = 0.0d;
		ScriptEngineManager mgr = new ScriptEngineManager();
		ScriptEngine engine = mgr.getEngineByName("JavaScript");
		for (HeroSkills skill : skills) {
			if (skill.getLevel() == skillLevel) {
				if (skill.getLowerDamage() != null) {
					double ldUnit = 0.0d;
					try {
						ldUnit = (double) engine.eval(skill.getLowerDamage().getDamage());
					} catch (ScriptException e) {
						e.printStackTrace();
					}
					if (skill.getLowerDamage().getUnits() > 0) {
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

	/**
	 * Process Combat Speed.
	 * 
	 * @param skills
	 * @param skillLevel
	 * @return
	 */
	private int processCombatSpeed(List<HeroSkills> skills, int skillId, int skillLevel) {
		List<HeroSkills> relvSkills = skills.stream().filter(t -> {
			return t.getLevel() == skillLevel && t.getCombatSpeed() > 0;
		}).collect(Collectors.toList());
		for (HeroSkills skill : relvSkills) {
			if (skill.getLevel() == 10) {
				return skill.getCombatSpeed();
			}
		}
		return 0;
	}

	/**
	 * Process seige might.
	 * 
	 * @param skills
	 * @param skillLevel
	 * @return
	 */
	private double processSeigeMight(List<HeroSkills> skills, int skillId, int skillLevel) {
		List<HeroSkills> relvSkills = skills.stream().filter(t -> {
			return t.getLevel() == skillLevel && t.getSeigeMight() > 0;
		}).collect(Collectors.toList());
		for (HeroSkills skill : relvSkills) {
			if (skill.getLevel() == 10) {
				return skill.getSeigeMight();
			}
		}
		return 0.0d;
	}

	/**
	 * Process lower might.
	 * 
	 * @param skills
	 * @param skillLevel
	 * @return
	 */
	private double processLowerMight(List<HeroSkills> skills, int skillId, int skillLevel) {
		double val = 0.0d;
		for (HeroSkills skill : skills) {
			if (skill.getLevel() == skillLevel && skill.getDamage() != null) {
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

	private double processSeigeDefenseMight(List<HeroSkills> skills, int skillId, int skillLevel) {
		List<HeroSkills> relvSkills = skills.stream().filter(t -> {
			return t.getLevel() == skillLevel && t.getSeigeDefenseMight() > 0;
		}).collect(Collectors.toList());
		for (HeroSkills skill : relvSkills) {
			if (skill.getLevel() == 10) {
				return skill.getSeigeDefenseMight();
			}
		}
		return 0.0d;
	}

	private double processBulwark(List<HeroSkills> skills, int skillId, int skillLevel) {
		List<HeroSkills> relvSkills = skills.stream().filter(t -> {
			return t.getLevel() == skillLevel && t.getBulwark() > 0;
		}).collect(Collectors.toList());
		for (HeroSkills skill : relvSkills) {
			if (skill.getLevel() == 10) {
				return skill.getBulwark();
			}
		}
		return 0.0d;
	}
}
