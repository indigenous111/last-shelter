package in.indigenous.last.shelter.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import in.indigenous.common.util.io.FileReader;
import in.indigenous.last.shelter.models.Troops;

@Component
public class TroopsUtils {

	@Resource
	private FileReader excelFileReader;

	public Map<Integer, List<Troops>> getTroopsInfo(String dataDir, String fileName, String sheetName) throws IOException {
		Map<Integer, List<Troops>> result = new HashMap<>();
		List<List<Object>> rawData = excelFileReader.readRows(dataDir, fileName, sheetName);
		for(List<Object> row: rawData)
		{
			int id = Double.valueOf(row.get(0).toString()).intValue();
			String name = row.get(1).toString();
			int level = Double.valueOf(row.get(2).toString()).intValue();
			int attack = Double.valueOf(row.get(3).toString()).intValue();
			int speed = Double.valueOf(row.get(4).toString()).intValue();
			int defense = Double.valueOf(row.get(5).toString()).intValue();
			int load = Double.valueOf(row.get(6).toString()).intValue();
			int hp = Double.valueOf(row.get(7).toString()).intValue();
			double foodCost = Double.valueOf(row.get(8).toString());
			int combat = Double.valueOf(row.get(9).toString()).intValue();
			List<Troops> troops = result.get(level);
			if(troops == null) {
				troops = new ArrayList<>();
				result.put(level, troops);
			}
			Troops troop = new Troops();
			troop.setId(id);
			troop.setName(name);
			troop.setLevel(level);
			troop.setAttack(attack);
			troop.setSpeed(speed);
			troop.setDefense(defense);
			troop.setLoad(load);
			troop.setHp(hp);
			troop.setFood(foodCost);
			troop.setCombat(combat);
			troops.add(troop);
		}
		return result;
	}
}
