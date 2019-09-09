package in.indigenous.last.shelter.services.global;

import java.util.List;
import java.util.Map;

import in.indigenous.last.shelter.view.Troop;

public interface GlobalTroopService {

	Map<Integer, List<Troop>> getGlobalTroopDetails();
}
