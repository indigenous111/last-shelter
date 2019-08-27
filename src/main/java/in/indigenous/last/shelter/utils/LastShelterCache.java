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
import in.indigenous.last.shelter.models.APCInfo;
import in.indigenous.last.shelter.models.AccountHeroData;
import in.indigenous.last.shelter.models.HeroData;
import in.indigenous.last.shelter.models.Troops;

@Component
public class LastShelterCache {

	private final static int accounts = 4;

	private List<HeroData> globalHeroData = new ArrayList<>();

	private Map<Integer, Map<Integer, List<AccountHeroData>>> accountHeroData = new HashMap<>();

	private Map<Integer, List<Troops>> globalTroopDetails = new HashMap<>();

	private Map<Integer, List<APCInfo>> apcInfos = new HashMap<>();

	@Resource
	private TroopsUtils troopsUtils;

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
		for (int i = 0; i < accounts; i++) {
			String calcAccountHeroDataDir = accountHeroDataDir + (i + 1);
			try {
				accountHeroData.put(i + 1, heroUtils.getAccountHeroData(calcAccountHeroDataDir, accountHeroFileName,
						accountHeroSheetName, i + 1));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		String apcDataDir = configuration.getString(LastShelterConstants.LAST_SHELTER_ACC_DATA_FILE_DIR) + "//";
		String apcFileName = configuration.getString(LastShelterConstants.LAST_SHELTER_ACC_APC_FILE);
		String apcSheetName = configuration.getString(LastShelterConstants.LAST_SHELTER_ACC_APC_INFO_SHEET);
		for (int i = 0; i < accounts; i++) {
			String calcApcDataDir = apcDataDir +(i + 1);
			apcInfos.put(i + 1, apcUtils.getAPCInfo(calcApcDataDir, apcFileName, apcSheetName, i + 1));
		}
		String troopsDataDir = configuration.getString(LastShelterConstants.LAST_SHELTER_GLOBAL_DATA_FILE_DIR);
		String troopsFileName = configuration.getString(LastShelterConstants.LAST_SHELTER_GLOBAL_TROOPS_DETAILS_FILE);
		String troopsSheetName = configuration.getString(LastShelterConstants.LAST_SHELTER_GLOBAL_TROOPS_DETAILS_SHEET);
		try {
			globalTroopDetails = troopsUtils.getTroopsInfo(troopsDataDir, troopsFileName, troopsSheetName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<HeroData> getGlobalHeroData() {
		return globalHeroData;
	}

	public Map<Integer, List<AccountHeroData>> getAccountHeroData(int account) {
		return accountHeroData.get(account);
	}

	public List<APCInfo> getAPCInfo(int account) {
		return apcInfos.get(account);
	}

	public Map<Integer, List<Troops>> getTroopsInfo() {
		return globalTroopDetails;
	}
}
