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
import org.springframework.stereotype.Component;

import in.indigenous.common.util.io.FileReader;
import in.indigenous.last.shelter.models.AccountHeroData;
import in.indigenous.last.shelter.models.AdditionalDamage;
import in.indigenous.last.shelter.models.Damage;
import in.indigenous.last.shelter.models.HeroData;
import in.indigenous.last.shelter.models.HeroDetails;
import in.indigenous.last.shelter.models.HeroSkills;
import in.indigenous.last.shelter.models.HeroStats;
import in.indigenous.last.shelter.models.LowerDamage;
import in.indigenous.last.shelter.models.LowerResistance;
import in.indigenous.last.shelter.models.MinusEnemyTurns;
import in.indigenous.last.shelter.models.MinusMight;

@Component
public class HeroUtils {

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
	public List<HeroData> getGlobalHeroData(String dataDir, String fileName, String heroSheetName,
			String heroSkillsSheetName) throws IOException {
		Map<Object, List<Object>> rawData = excelFileReader.readExcelSheet(dataDir, fileName, heroSheetName);
		List<String> keys = new ArrayList<>(Arrays.asList(StringUtils.split(HERO_DETAILS_KEYS, ",")));

		Map<Integer, HeroData> heroDataMap = new HashMap<>();
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
			HeroDetails detail = new HeroDetails();
			int id = Double.valueOf(idValues.get(index)).intValue();
			detail.setId(id);
			HeroData data = new HeroData();
			data.setDetails(detail);
			heroDataMap.put(id, data);
		});
		idValues.stream().forEach(idValue -> {
			int index = idValues.indexOf(idValue);
			heroDataMap.get(index + 1).getDetails().setName(nameValues.get(index));
		});
		idValues.stream().forEach(idValue -> {
			int index = idValues.indexOf(idValue);
			heroDataMap.get(index + 1).getDetails().setColor(colorValues.get(index));
		});
		for (int id : heroDataMap.keySet()) {
			heroDataMap.get(id).setStats(getHeroStats(dataDir, fileName, heroSkillsSheetName, id));
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
	public Map<Integer, List<AccountHeroData>> getAccountHeroData(String dataDir, String fileName, String sheetName,
			int account) throws IOException {
		List<List<Object>> rawData = excelFileReader.readRows(dataDir, fileName, sheetName);
		Map<Integer, List<AccountHeroData>> accountHeroDataMap = new HashMap<>();
		if (CollectionUtils.isNotEmpty(rawData)) {
			for (List<Object> row : rawData) {
				int heroId = Double.valueOf(row.get(0).toString()).intValue();
				int heroLvl = Double.valueOf(row.get(1).toString()).intValue();
				int skillId = Double.valueOf(row.get(2).toString()).intValue();
				int skillLvl = Double.valueOf(row.get(3).toString()).intValue();
				List<AccountHeroData> list = accountHeroDataMap.get(heroId);
				if (list == null) {
					list = new ArrayList<>();
					accountHeroDataMap.put(heroId, list);
				}
				AccountHeroData data = new AccountHeroData();
				data.setHeroId(heroId);
				data.setHeroLevel(heroLvl);
				data.setSkillId(skillId);
				data.setSkillLevel(skillLvl);
				list.add(data);
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
	private HeroStats getHeroStats(String dataDir, String fileName, String skillSheetName, int heroId)
			throws IOException {
		HeroStats stats = new HeroStats();
		List<List<Object>> rawData = excelFileReader.readRows(dataDir, fileName, skillSheetName);
		if (CollectionUtils.isNotEmpty(rawData)) {
			List<List<Object>> skillset = new ArrayList<>();
			for (List<Object> row : rawData) {
				if (Double.valueOf(row.get(0).toString()).intValue() == heroId) {
					skillset.add(row);
				}
			}
			List<HeroSkills> skills = new ArrayList<>();
			Map<Integer, HeroSkills> skillMap = new HashMap<>();
			for (List<Object> obj : skillset) {
				HeroSkills skill = skillMap.get(obj.get(1));
				if (skill == null) {
					skill = new HeroSkills();
					skillMap.put(Double.valueOf(obj.get(1).toString()).intValue(), skill);
				}
				if (obj.size() > 2 && obj.get(1) != null && StringUtils.isNotEmpty(obj.get(1).toString())) {
					skill.setId(Double.valueOf(obj.get(1).toString()).intValue());
				}
				if (obj.size() > 3 && obj.get(2) != null && StringUtils.isNotEmpty(obj.get(2).toString())) {
					skill.setName(obj.get(2).toString());
				}
				skill.setLevel(Double.valueOf(obj.get(3).toString()).intValue());
				if (obj.size() > 4 && obj.get(4) != null && StringUtils.isNotEmpty(obj.get(4).toString())) {
					skill.setRange(Double.valueOf(obj.get(4).toString()).intValue());
				}
				if (obj.size() > 5 && obj.get(5) != null && StringUtils.isNotEmpty(obj.get(5).toString())) {
					skill.setLeadingUnit(obj.get(5).toString());
				}
				if (obj.size() > 7 && obj.get(7) != null && StringUtils.isNotEmpty(obj.get(7).toString())) {
					skill.setMarchingCapacity(obj.get(7).toString());
				}
				if (obj.size() > 8 && obj.get(8) != null && StringUtils.isNotEmpty(obj.get(8).toString())) {
					String damageStr = obj.get(8).toString();
					String turnString = "";
					if (StringUtils.contains(damageStr, "turn")) {
						String[] placeholder = damageStr.split("turn");
						damageStr = placeholder[0].trim();
						turnString = placeholder[1].trim();
					}
					Damage damage = new Damage();
					damage.setDamage(damageStr);
					if (StringUtils.isNotEmpty(turnString)) {
						damage.setTurns(new ArrayList<>(Arrays.asList(turnString.split(","))).stream().map(t -> {
							return Integer.parseInt(t);
						}).collect(Collectors.toList()));
					}
					if (obj.size() > 9 && obj.get(9) != null && StringUtils.isNotEmpty(obj.get(9).toString())) {
						damage.setMinusTurns(Double.valueOf(obj.get(9).toString()).intValue());
					}
					if (obj.size() > 6 && obj.get(6) != null && StringUtils.isNotEmpty(obj.get(6).toString())) {
						damage.setTargetUnit(Double.valueOf(obj.get(6).toString()).intValue());
					}
					if (obj.size() > 10 && obj.get(10) != null && StringUtils.isNotEmpty(obj.get(10).toString())) {
						MinusMight minusMight = new MinusMight();
						minusMight.setMinusMight(Double.valueOf(obj.get(10).toString()));
						if (obj.size() > 11 && obj.get(11) != null && StringUtils.isNotEmpty(obj.get(11).toString())) {
							minusMight.setTurns(Double.valueOf(obj.get(11).toString()).intValue());
						}
						damage.setMinusMight(minusMight);
					}
					skill.setDamage(damage);
				}
				if (obj.size() > 12 && obj.get(12) != null && StringUtils.isNotEmpty(obj.get(12).toString())) {
					skill.setResitance(Double.valueOf(obj.get(12).toString()));
				}
				if (obj.size() > 13 && obj.get(13) != null && StringUtils.isNotEmpty(obj.get(13).toString())) {
					skill.setMight(Double.valueOf(obj.get(13).toString()));
				}
				if (obj.size() > 14 && obj.get(14) != null && StringUtils.isNotEmpty(obj.get(14).toString())) {
					LowerResistance lowerResistance = new LowerResistance();
					lowerResistance.setLowerResistance(Double.valueOf(obj.get(14).toString()));
					if (obj.size() > 15 && obj.get(15) != null && StringUtils.isNotEmpty(obj.get(15).toString())) {
						lowerResistance.setTurns(Double.valueOf(obj.get(15).toString()).intValue());
					}
					skill.setLowerResistance(lowerResistance);
				}
				if (obj.size() > 16 && obj.get(16) != null && StringUtils.isNotEmpty(obj.get(16).toString())) {
					skill.setHp(Double.valueOf(obj.get(16).toString()));
				}
				if (obj.size() > 17 && obj.get(17) != null && StringUtils.isNotEmpty(obj.get(17).toString())) {
					MinusEnemyTurns minusEnemyTurns = new MinusEnemyTurns();
					String minusEnemyStr = obj.get(17).toString();
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
							List<Integer> turns = new ArrayList<>(Arrays.asList(turnStr[1].trim().split(","))).stream()
									.map(t -> {
										return Integer.valueOf(t);
									}).collect(Collectors.toList());
							minusEnemyTurns.setTurns(turns);
							if (obj.size() > 18 && obj.get(18) != null
									&& StringUtils.isNotEmpty(obj.get(18).toString())) {
								minusEnemyTurns.setMinusTurns(Double.valueOf(obj.get(18).toString()).intValue());
							}
						}
					} else {
						minusEnemyTurns.setMinusTurns(Double.valueOf(minusEnemyStr).intValue());
					}
					skill.setMinusEnemyTurns(minusEnemyTurns);
				}
				if (obj.size() > 19 && obj.get(19) != null && StringUtils.isNotEmpty(obj.get(19).toString())) {
					LowerDamage lowerDamage = new LowerDamage();
					lowerDamage.setDamage(obj.get(19).toString());
					if (obj.size() > 20 && obj.get(20) != null && StringUtils.isNotEmpty(obj.get(20).toString())) {
						lowerDamage.setUnits(Double.valueOf(obj.get(20).toString()).intValue());
					}
					if (obj.size() > 21 && obj.get(21) != null && StringUtils.isNotEmpty(obj.get(21).toString())) {
						lowerDamage.setTurns(Double.valueOf(obj.get(21).toString()).intValue());
					}
					skill.setLowerDamage(lowerDamage);
				}
				if (obj.size() > 22 && obj.get(22) != null && StringUtils.isNotEmpty(obj.get(22).toString())) {
					AdditionalDamage additionalDamage = new AdditionalDamage();
					additionalDamage.setDamage(Double.valueOf(obj.get(22).toString()).intValue());
					if (obj.size() > 23 && obj.get(23) != null && StringUtils.isNotEmpty(obj.get(23).toString())) {
						additionalDamage.setTurns(Double.valueOf(obj.get(23).toString()).intValue());
					}
					skill.setAdditionalDamage(additionalDamage);
				}
				if (obj.size() > 24 && obj.get(24) != null && StringUtils.isNotEmpty(obj.get(24).toString())) {
					skill.setCombatSpeed(Double.valueOf(obj.get(24).toString()).intValue());
				}
				if (obj.size() > 25 && obj.get(25) != null && StringUtils.isNotEmpty(obj.get(25).toString())) {
					skill.setSeigeMight(Double.valueOf(obj.get(25).toString()));
				}
				if (obj.size() > 26 && obj.get(26) != null && StringUtils.isNotEmpty(obj.get(26).toString())) {
					skill.setSeigeDefenseMight(Double.valueOf(obj.get(26).toString()));
				}
				if (obj.size() > 27 && obj.get(27) != null && StringUtils.isNotEmpty(obj.get(27).toString())) {
					skill.setBulwark(Double.valueOf(obj.get(27).toString()));
				}
				skills.add(skill);
			}
			stats.setSkills(skills);
		}
		return stats;
	}
}
