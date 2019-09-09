package in.indigenous.last.shelter.services.global.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import in.indigenous.last.shelter.services.global.GlobalTroopService;
import in.indigenous.last.shelter.utils.LastShelterCache;
import in.indigenous.last.shelter.view.Troop;

/**
 * The Class DefaultGlobalTroopService.
 */
@Service
public class DefaultGlobalTroopService implements GlobalTroopService {

	@Resource
	private LastShelterCache lastShelterCache;

	/*
	 * (non-Javadoc)
	 * 
	 * @see in.indigenous.last.shelter.services.global.GlobalTroopService#
	 * getGlobalTroopDetails()
	 */
	@Override
	public Map<Integer, List<Troop>> getGlobalTroopDetails() {
		ObjectMapper mapper = new ObjectMapper();
		List<Troop> allTroops = lastShelterCache.getTroopsInfo().values().stream().flatMap(List::stream).map(t -> {
			return mapper.convertValue(t, Troop.class);
		}).collect(Collectors.toList());

		return allTroops.stream().collect(Collectors.groupingBy(Troop::getLevel));
	}

}
