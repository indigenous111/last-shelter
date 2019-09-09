package in.indigenous.last.shelter.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.configuration2.PropertiesConfiguration;
import org.springframework.stereotype.Component;

import in.indigenous.last.shelter.constants.LastShelterConstants;
import in.indigenous.last.shelter.models.apc.APC;
import in.indigenous.last.shelter.models.hero.Hero;
import in.indigenous.last.shelter.models.hero.skills.HeroSkill;
import in.indigenous.last.shelter.models.troops.Troop;

@Component
public class LastShelterCache {

	private final static int NUMBER_OF_ACCOUNTS = 4;

	private List<Hero> globalHeroData = new ArrayList<>();

	private Map<Integer, Map<Integer, List<HeroSkill>>> accountHeroData = new HashMap<>();

	private Map<Integer, List<Troop>> globalTroopDetails = new HashMap<>();

	private Map<Integer, List<APC>> apcInfos = new HashMap<>();

	@Resource
	private TroopUtils troopUtils;

	@Resource
	private APCUtils apcUtils;

	@Resource
	private HeroUtils heroUtils;

	@Resource
	private PropertiesConfiguration configuration;

	@PostConstruct
	public void init() {
		String globalHeroDataDir = configuration.getString(LastShelterConstants.LAST_SHELTER_GLOBAL_DATA_FILE_DIR);
		String globalHeroFileName = configuration.getString(LastShelterConstants.LAST_SHELTER_GLOBAL_HERO_FILE);
		String heroDetailsSheetName = configuration
				.getString(LastShelterConstants.LAST_SHELTER_GLOBAL_HERO_DETAILS_SHEET);
		String heroSkillsSheetName = configuration.getString(LastShelterConstants.LAST_SHELTER_GLOBAL_HERO_STATS_SHEET);
		try {
			globalHeroData = heroUtils.getGlobalHeroData(globalHeroDataDir, globalHeroFileName, heroDetailsSheetName,
					heroSkillsSheetName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String accountHeroDataDir = configuration.getString(LastShelterConstants.LAST_SHELTER_ACC_DATA_FILE_DIR) + "//";
		String accountHeroFileName = configuration.getString(LastShelterConstants.LAST_SHELTER_ACC_HERO_FILE);
		String accountHeroSheetName = configuration.getString(LastShelterConstants.LAST_SHELTER_ACC_HERO_DETAILS_SHEET);
		for (int i = 0; i < NUMBER_OF_ACCOUNTS; i++) {
			String calcAccountHeroDataDir = accountHeroDataDir + (i + 1);
			try {
				accountHeroData.put(i + 1, heroUtils.getAccountHeroSkill(calcAccountHeroDataDir, accountHeroFileName,
						accountHeroSheetName, i + 1));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		String apcDataDir = configuration.getString(LastShelterConstants.LAST_SHELTER_ACC_DATA_FILE_DIR) + "//";
		String apcFileName = configuration.getString(LastShelterConstants.LAST_SHELTER_ACC_APC_FILE);
		String apcSheetName = configuration.getString(LastShelterConstants.LAST_SHELTER_ACC_APC_INFO_SHEET);
		for (int i = 0; i < NUMBER_OF_ACCOUNTS; i++) {
			String calcApcDataDir = apcDataDir + (i + 1);
			apcInfos.put(i + 1, apcUtils.getAPCInfo(calcApcDataDir, apcFileName, apcSheetName, i + 1));
		}
		String troopsDataDir = configuration.getString(LastShelterConstants.LAST_SHELTER_GLOBAL_DATA_FILE_DIR);
		String troopsFileName = configuration.getString(LastShelterConstants.LAST_SHELTER_GLOBAL_TROOPS_DETAILS_FILE);
		String troopsSheetName = configuration.getString(LastShelterConstants.LAST_SHELTER_GLOBAL_TROOPS_DETAILS_SHEET);
		try {
			globalTroopDetails = troopUtils.getTroopInfo(troopsDataDir, troopsFileName, troopsSheetName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<Hero> getGlobalHeroData() {
		return globalHeroData;
	}

	public Map<Integer, List<HeroSkill>> getAccountHeroData(int account) {
		return accountHeroData.get(account);
	}

	public List<APC> getAPCInfo(int account) {
		return apcInfos.get(account);
	}

	public Map<Integer, List<Troop>> getTroopsInfo() {
		return globalTroopDetails;
	}
}
