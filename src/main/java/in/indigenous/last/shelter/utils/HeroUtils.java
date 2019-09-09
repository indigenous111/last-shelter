package in.indigenous.last.shelter.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import in.indigenous.common.util.io.FileReader;
import in.indigenous.last.shelter.models.hero.CombatHero;
import in.indigenous.last.shelter.models.hero.Hero;
import in.indigenous.last.shelter.models.hero.HeroClass;
import in.indigenous.last.shelter.models.hero.skills.HeroSkill;
import in.indigenous.last.shelter.models.hero.skills.HeroSkillClass;
import in.indigenous.last.shelter.models.hero.skills.combat.CombatHeroSkill;
import in.indigenous.last.shelter.models.hero.skills.combat.CombatSkill;
import in.indigenous.last.shelter.models.hero.skills.combat.CombatSkillType;
import in.indigenous.last.shelter.models.hero.skills.combat.LeadershipSkill;
import in.indigenous.last.shelter.models.hero.skills.combat.LeadingUnit;
import in.indigenous.last.shelter.models.hero.skills.combat.MinusDamage;
import in.indigenous.last.shelter.models.hero.skills.combat.MinusMight;
import in.indigenous.last.shelter.models.hero.skills.combat.MinusResistance;
import in.indigenous.last.shelter.models.hero.skills.combat.MinusTurns;
import in.indigenous.last.shelter.models.hero.skills.combat.PlusDamage;

@Component
public class HeroUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(HeroUtils.class);

	@Resource
	private FileReader excelFileReader;

	private final static String HERO_DETAILS_KEYS = "Id,Name,Color";

	/**
	 * Get global hero data.
	 * 
	 * @param dataDir
	 * @param fileName
	 * @param heroSheetName
	 * @param heroSkillsSheetName
	 * @return
	 * @throws IOException
	 */
	public List<Hero> getGlobalHeroData(String dataDir, String fileName, String heroSheetName,
			String heroSkillsSheetName) throws IOException {
		Map<Object, List<Object>> rawData = excelFileReader.readExcelSheet(dataDir, fileName, heroSheetName);
		List<String> keys = new ArrayList<>(Arrays.asList(StringUtils.split(HERO_DETAILS_KEYS, ",")));

		Map<Integer, Hero> heroDataMap = new HashMap<>();
		List<String> idValues = rawData.get(keys.get(0)).stream().map(el -> {
			return String.valueOf(el);
		}).collect(Collectors.toList());
		List<String> nameValues = rawData.get(keys.get(1)).stream().map(el -> {
			return String.valueOf(el);
		}).collect(Collectors.toList());
		List<String> colorValues = rawData.get(keys.get(2)).stream().map(el -> {
			return String.valueOf(el);
		}).collect(Collectors.toList());

		idValues.stream().forEach(idValue -> {
			int index = idValues.indexOf(idValue);
			Hero hero = new CombatHero();
			int id = Double.valueOf(idValues.get(index)).intValue();
			hero.setId(id);
			heroDataMap.put(id, hero);
		});
		idValues.stream().forEach(idValue -> {
			int index = idValues.indexOf(idValue);
			heroDataMap.get(index + 1).setName(nameValues.get(index));
		});
		idValues.stream().forEach(idValue -> {
			int index = idValues.indexOf(idValue);
			heroDataMap.get(index + 1).setHeroClass(HeroClass.valueOf(colorValues.get(index).toUpperCase()));
		});
		for (int id : heroDataMap.keySet()) {
			Hero hero = heroDataMap.get(id);
			if (hero == null) {
				hero = new CombatHero();
				heroDataMap.put(id, hero);
			}
			getCombatHeroStats(dataDir, fileName, heroSkillsSheetName, id, hero);
		}
		return new ArrayList<>(heroDataMap.values());
	}

	/**
	 * Get Account Hero Data.
	 * 
	 * @param dataDir
	 * @param fileName
	 * @param sheetName
	 * @param account
	 * @return
	 * @throws IOException
	 */
	public Map<Integer, List<HeroSkill>> getAccountHeroSkill(String dataDir, String fileName, String sheetName,
			int account) throws IOException {
		List<List<Object>> rawData = excelFileReader.readRows(dataDir, fileName, sheetName);
		Map<Integer, List<HeroSkill>> accountHeroDataMap = new HashMap<>();
		if (CollectionUtils.isNotEmpty(rawData)) {
			for (List<Object> row : rawData) {
				int heroId = Double.valueOf(row.get(0).toString()).intValue();
				// Check how to update hero level.
				int heroLvl = Double.valueOf(row.get(1).toString()).intValue();
				int skillId = Double.valueOf(row.get(2).toString()).intValue();
				int skillLvl = Double.valueOf(row.get(3).toString()).intValue();
				List<HeroSkill> list = accountHeroDataMap.get(heroId);
				if (list == null) {
					list = new ArrayList<>();
					accountHeroDataMap.put(heroId, list);
				}
				HeroSkill skill = new CombatSkill();
				skill.setSkillId(skillId);
				skill.setLevel(skillLvl);
				skill.setHeroLevel(heroLvl);
				list.add(skill);
			}
		}
		return accountHeroDataMap;
	}

	/**
	 * Get hero stats.
	 * 
	 * @param dataDir
	 * @param fileName
	 * @param skillSheetName
	 * @param heroId
	 * @return
	 * @throws IOException
	 */
	private void getCombatHeroStats(String dataDir, String fileName, String skillSheetName, int heroId, Hero hero)
			throws IOException {
		List<List<Object>> rawData = excelFileReader.readRows(dataDir, fileName, skillSheetName);
		if (CollectionUtils.isNotEmpty(rawData)) {
			List<List<Object>> skillset = new ArrayList<>();
			for (List<Object> row : rawData) {
				if (Double.valueOf(row.get(0).toString()).intValue() == heroId) {
					skillset.add(row);
				}
			}
			List<HeroSkill> skills = new ArrayList<>();
			Map<Integer, CombatHeroSkill> skillMap = new HashMap<>();
			for (List<Object> obj : skillset) {
				CombatHeroSkill skill = skillMap.get(obj.get(0));
				CombatSkillType skillType = CombatSkillType.valueOf(obj.get(2).toString().toUpperCase());
				if (skill == null) {
					if (CombatSkillType.COMBAT.equals(skillType)) {
						skill = new CombatSkill();
					}
					if (CombatSkillType.LEADERSHIP.equals(skillType)) {
						skill = new LeadershipSkill();
					}
					skillMap.put(Double.valueOf(obj.get(0).toString()).intValue(), skill);
				}
				if (obj.size() > 1 && obj.get(1) != null && StringUtils.isNotEmpty(obj.get(1).toString())) {
					skill.setSkillId(Double.valueOf(obj.get(1).toString()).intValue());
				}
				if (obj.size() > 2 && obj.get(2) != null && StringUtils.isNotEmpty(obj.get(2).toString())) {
					skill.setType(CombatSkillType.valueOf(obj.get(2).toString()));
				}
				if (obj.size() > 3 && obj.get(3) != null && StringUtils.isNotEmpty(obj.get(3).toString())) {
					skill.setName(obj.get(3).toString());
				}
				skill.setLevel(Double.valueOf(obj.get(4).toString()).intValue());
				if (CombatSkillType.COMBAT.equals(skillType)) {
					CombatSkill cSkill = (CombatSkill) skill;
					cSkill.setSkillClass(HeroSkillClass.COMBAT);
					if (obj.size() > 5 && obj.get(5) != null && StringUtils.isNotEmpty(obj.get(5).toString())) {
						cSkill.setRange(Double.valueOf(obj.get(5).toString()).intValue());
					}
					if (obj.size() > 6 && obj.get(6) != null && StringUtils.isNotEmpty(obj.get(6).toString())) {
						cSkill.setLeadingUnit(LeadingUnit.valueOf(obj.get(6).toString().toUpperCase()));
					}
					if (obj.size() > 7 && obj.get(7) != null && StringUtils.isNotEmpty(obj.get(7).toString())) {
						cSkill.setTargetUnit(Double.valueOf((obj.get(7).toString().toUpperCase())).intValue());
					}
					if (obj.size() > 9 && obj.get(9) != null && StringUtils.isNotEmpty(obj.get(9).toString())) {
						String damageStr = obj.get(9).toString();
						String turnString = "";
						if (StringUtils.contains(damageStr, "turn")) {
							String[] placeholder = damageStr.split("turn");
							damageStr = placeholder[0].trim();
							turnString = placeholder[1].trim();
						}
						cSkill.setDamage(damageStr);
						if (StringUtils.isNotEmpty(turnString)) {
							cSkill.setTurns(new ArrayList<>(Arrays.asList(turnString.split(","))).stream().map(t -> {
								return Integer.parseInt(t);
							}).collect(Collectors.toList()));
						}
						if (obj.size() > 10 && obj.get(10) != null && StringUtils.isNotEmpty(obj.get(10).toString())) {
							MinusMight minusMight = new MinusMight();
							minusMight.setMight(Double.valueOf(obj.get(10).toString()));
							if (obj.size() > 11 && obj.get(11) != null
									&& StringUtils.isNotEmpty(obj.get(11).toString())) {
								minusMight.setTurns(Double.valueOf(obj.get(11).toString()).intValue());
							}
							cSkill.setEnemyMinusMight(minusMight);
						}
					}
					if (obj.size() > 15 && obj.get(15) != null && StringUtils.isNotEmpty(obj.get(15).toString())) {
						MinusResistance minusResistance = new MinusResistance();
						minusResistance.setResistance(Double.valueOf(obj.get(15).toString()));
						if (obj.size() > 16 && obj.get(16) != null && StringUtils.isNotEmpty(obj.get(16).toString())) {
							minusResistance.setTurns(Double.valueOf(obj.get(16).toString()).intValue());
						}
						cSkill.setEnemyMinusResistance(minusResistance);
					}
					if (obj.size() > 18 && obj.get(18) != null && StringUtils.isNotEmpty(obj.get(18).toString())) {
						MinusTurns minusEnemyTurns = new MinusTurns();
						String minusEnemyStr = obj.get(18).toString();
						if (minusEnemyStr.contains("chance") || minusEnemyStr.contains("turn")) {
							if (minusEnemyStr.contains("chance")) {
								String[] chanceStr = minusEnemyStr.split("chance");
								Double chance = Double.valueOf(chanceStr[0].trim());
								minusEnemyTurns.setChance(chance);
								minusEnemyStr = chanceStr[1].trim();
							}
							if (minusEnemyStr.contains("turn")) {
								String[] turnStr = minusEnemyStr.split("turn");
								Integer unit = Integer.valueOf(turnStr[0].trim());
								minusEnemyTurns.setUnits(unit);
								List<Integer> turns = new ArrayList<>(Arrays.asList(turnStr[1].trim().split(",")))
										.stream().map(t -> {
											return Integer.valueOf(t);
										}).collect(Collectors.toList());
								minusEnemyTurns.setTurns(turns);
								if (obj.size() > 19 && obj.get(19) != null
										&& StringUtils.isNotEmpty(obj.get(19).toString())) {
									minusEnemyTurns.setMinusTurns(Double.valueOf(obj.get(19).toString()).intValue());
								}
							}
						} else {
							minusEnemyTurns.setMinusTurns(Double.valueOf(minusEnemyStr).intValue());
						}
						cSkill.setEnemyMinusTurns(minusEnemyTurns);
					}
					if (obj.size() > 20 && obj.get(20) != null && StringUtils.isNotEmpty(obj.get(20).toString())) {
						MinusDamage minusDamage = new MinusDamage();
						minusDamage.setDamage(obj.get(20).toString());
						if (obj.size() > 21 && obj.get(21) != null && StringUtils.isNotEmpty(obj.get(21).toString())) {
							minusDamage.setUnits(Double.valueOf(obj.get(21).toString()).intValue());
						}
						if (obj.size() > 22 && obj.get(22) != null && StringUtils.isNotEmpty(obj.get(22).toString())) {
							minusDamage.setTurns(Double.valueOf(obj.get(22).toString()).intValue());
						}
						cSkill.setEnemyMinusDamage(minusDamage);
					}
					if (obj.size() > 23 && obj.get(23) != null && StringUtils.isNotEmpty(obj.get(23).toString())) {
						PlusDamage additionalDamage = new PlusDamage();
						additionalDamage.setDamage(Double.valueOf(obj.get(23).toString()).intValue());
						if (obj.size() > 24 && obj.get(24) != null && StringUtils.isNotEmpty(obj.get(24).toString())) {
							additionalDamage.setTurns(Double.valueOf(obj.get(24).toString()).intValue());
						}
						cSkill.setAdditionalDamage(additionalDamage);
					}
					if (obj.size() > 27 && obj.get(27) != null && StringUtils.isNotEmpty(obj.get(27).toString())) {
						cSkill.setSeigeDefenseMight(Double.valueOf(obj.get(27).toString()));
					}
					if (obj.size() > 28 && obj.get(28) != null && StringUtils.isNotEmpty(obj.get(28).toString())) {
						cSkill.setBulwark(Double.valueOf(obj.get(28).toString()));
					}
				} else if (CombatSkillType.LEADERSHIP.equals(skillType)) {
					skill.setSkillClass(HeroSkillClass.COMBAT);
					LeadershipSkill lskill = (LeadershipSkill) skill;
					if (obj.size() > 8 && obj.get(8) != null && StringUtils.isNotEmpty(obj.get(8).toString())) {
						lskill.setMarchingCapacity(obj.get(8).toString());
					}
					if (obj.size() > 13 && obj.get(13) != null && StringUtils.isNotEmpty(obj.get(13).toString())) {
						lskill.setResistance(Double.valueOf(obj.get(13).toString()).doubleValue());
					}
					if (obj.size() > 14 && obj.get(14) != null && StringUtils.isNotEmpty(obj.get(14).toString())) {
						lskill.setMight(Double.valueOf(obj.get(14).toString()).doubleValue());
					}
					if (obj.size() > 17 && obj.get(17) != null && StringUtils.isNotEmpty(obj.get(17).toString())) {
						lskill.setHp(Double.valueOf(obj.get(17).toString()));
					}
					if (obj.size() > 25 && obj.get(25) != null && StringUtils.isNotEmpty(obj.get(25).toString())) {
						lskill.setCombatSpeed(Double.valueOf(obj.get(25).toString()).intValue());
					}
					if (obj.size() > 26 && obj.get(26) != null && StringUtils.isNotEmpty(obj.get(26).toString())) {
						lskill.setSeigeMight(Double.valueOf(obj.get(26).toString()));
					}
				}
				skills.add(skill);
			}
			hero.setSkills(skills);
		}
	}
}
