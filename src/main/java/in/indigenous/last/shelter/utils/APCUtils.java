package in.indigenous.last.shelter.utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import in.indigenous.common.util.io.FileReader;
import in.indigenous.last.shelter.constants.Troops;
import in.indigenous.last.shelter.models.APCInfo;
import in.indigenous.last.shelter.models.APCTroopsDetail;

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
	public List<APCInfo> getAPCInfo(String dataDir, String fileName, String sheetName, int account) {
		List<List<Object>> rawData = excelFileReader.readRows(dataDir, fileName, sheetName);
		Map<Integer, List<APCInfo>> apcInfoMap = new HashMap<>();
		for (List<Object> row : rawData) {
			int apcId = Double.valueOf(row.get(0).toString()).intValue();
			String apcName = row.get(1).toString();
			int layer = Double.valueOf(row.get(2).toString()).intValue();
			long marchingCapacity = Double.valueOf(row.get(3).toString()).longValue();
			String leadingTroop = row.get(4).toString().toUpperCase();
			APCTroopsDetail detail = new APCTroopsDetail();
			if (row.size() > 5) {
				String heroId = row.get(5).toString();
				if (StringUtils.isNotEmpty(heroId)) {
					detail.setHeroId(Double.valueOf(heroId).intValue());
				}
			}
			detail.setApcId(apcId);
			detail.setLayer(layer);
			detail.setTroops(Troops.valueOf(leadingTroop));
			detail.setMarchingCapacity(marchingCapacity);
			List<APCInfo> apcInfo = apcInfoMap.get(apcId);
			if (apcInfo == null) {
				apcInfo = new ArrayList<>();
				apcInfoMap.put(apcId, apcInfo);
			}
			APCInfo apc = new APCInfo();
			apc.setApcId(apcId);
			apc.setName(apcName);
			apcInfo.add(apc);
			apc.getDetails().add(detail);
		}
		List<APCInfo> result = apcInfoMap.values().stream().flatMap(List::stream).collect(Collectors.toList());
		result.sort(Comparator.comparing(APCInfo::getApcId));
		result.forEach(r -> r.getDetails().sort(Comparator.comparing(APCTroopsDetail::getLayer)));
		return result;
	}

}
