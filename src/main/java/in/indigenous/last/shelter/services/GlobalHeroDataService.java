package in.indigenous.last.shelter.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import in.indigenous.last.shelter.models.HeroData;
import in.indigenous.last.shelter.utils.LastShelterCache;
import in.indigenous.last.shelter.utils.SkillProcessorUtils;

@Service
public class GlobalHeroDataService {

	@Resource
	private LastShelterCache lastShelterCache;

	@Resource
	private SkillProcessorUtils skillProcessorUtils;

	public List<HeroData> getGlobalHeroData() {
		List<HeroData> data = new ArrayList<>();
		data = lastShelterCache.getGlobalHeroData();
		data.stream().map(dt -> {
			int skillNumbers = 0;
			if (StringUtils.equalsIgnoreCase("orange", dt.getDetails().getColor())) {
				skillNumbers = 8;
			} else if (StringUtils.equalsIgnoreCase("purple", dt.getDetails().getColor())) {
				skillNumbers = 6;
			}
			for (int i = 0; i < skillNumbers; i++) {
				skillProcessorUtils.processHeroStats(dt, i + 1, 10, "50", 0.0d);
			}
			return dt;
		}).collect(Collectors.toList());
		data.sort((g1, g2) -> g2.getStats().getDamage().compareTo(g1.getStats().getDamage()));
		return data;
	}

}
