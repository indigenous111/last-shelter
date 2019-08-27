package in.indigenous.last.shelter.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import in.indigenous.last.shelter.models.Troops;
import in.indigenous.last.shelter.utils.LastShelterCache;

@Service
public class TroopsService {

	@Resource
	private LastShelterCache lastShelterCache;

	public Map<Integer, List<Troops>> getTroopsInfo() {
		Map<Integer, List<Troops>> result = new HashMap<>();
		result = lastShelterCache.getTroopsInfo();
		return result;
	}
}
