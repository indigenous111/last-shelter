package in.indigenous.last.shelter.utils;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import in.indigenous.common.util.io.FileReader;
import in.indigenous.last.shelter.models.apc.APC;
import in.indigenous.last.shelter.models.apc.APCLayer;
import in.indigenous.last.shelter.models.hero.CombatHero;
import in.indigenous.last.shelter.models.hero.Hero;
import in.indigenous.last.shelter.models.troops.Troop;
import in.indigenous.last.shelter.models.troops.TroopClass;
import in.indigenous.last.shelter.models.troops.TroopDetails;

@Component
public class APCUtils {

	@Resource
	private FileReader excelFileReader;

	/**
	 * Gets APC info.
	 * 
	 * @param dataDir
	 * @param fileName
	 * @param sheetName
	 * @param account
	 */
	public List<APC> getAPCInfo(String dataDir, String fileName, String sheetName, int account) {
		List<List<Object>> rawData = excelFileReader.readRows(dataDir, fileName, sheetName);
		Map<Integer, APC> apcInfoMap = new HashMap<>();
		for (List<Object> row : rawData) {
			int apcId = Double.valueOf(row.get(0).toString()).intValue();
			String apcName = row.get(1).toString();
			int layer = Double.valueOf(row.get(2).toString()).intValue();
			long marchingCapacity = Double.valueOf(row.get(3).toString()).longValue();
			TroopClass leadingTroop = TroopClass.valueOf(row.get(4).toString().toUpperCase());
			Hero apcLayerHero = new CombatHero();
			if (row.size() > 5) {
				String heroId = row.get(5).toString();
				if (StringUtils.isNotEmpty(heroId)) {
					apcLayerHero.setId(Double.valueOf(heroId).intValue());
				}
			}
			APCLayer apcLayer = new APCLayer();
			apcLayer.setId(layer);
			Troop troop = new Troop();
			troop.setTroopClass(leadingTroop);
			TroopDetails troopDetails = new TroopDetails();
			troopDetails.setTroop(troop);
			troopDetails.setAmount(marchingCapacity);
			apcLayer.setTroopDetails(troopDetails);
			APC apc = apcInfoMap.get(apcId);
			if (apc == null) {
				apc = new APC();
				apcInfoMap.put(apcId, apc);
			}
			apc.setId(apcId);
			apc.setName(apcName);
			apc.getLayers().add(apcLayer);
		}
		List<APC> result = apcInfoMap.values().stream().collect(Collectors.toList());
		result.sort(Comparator.comparing(APC::getId));
		result.forEach(r -> r.getLayers().sort(Comparator.comparing(APCLayer::getId)));
		return result;
	}

}
