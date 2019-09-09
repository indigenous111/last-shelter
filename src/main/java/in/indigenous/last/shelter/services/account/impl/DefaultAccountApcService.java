package in.indigenous.last.shelter.services.account.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import in.indigenous.last.shelter.services.account.AccountApcService;
import in.indigenous.last.shelter.services.account.AccountHeroService;
import in.indigenous.last.shelter.utils.LastShelterCache;
import in.indigenous.last.shelter.view.CombatHero;
import in.indigenous.last.shelter.view.Troop;
import in.indigenous.last.shelter.view.account.APC;
import in.indigenous.last.shelter.view.account.APCLayer;

/**
 * The Class DefaultAccountApcService.
 */
@Service
public class DefaultAccountApcService implements AccountApcService {

	/** The last shelter cache. */
	@Resource
	private LastShelterCache lastShelterCache;

	/** The account hero service. */
	@Resource
	private AccountHeroService accountHeroService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see in.indigenous.last.shelter.services.account.AccountApcService#
	 * getAccountAPCDetails(int)
	 */
	@Override
	public List<APC> getAccountAPCDetails(int account) {
		List<in.indigenous.last.shelter.models.apc.APC> apcInfo = lastShelterCache.getAPCInfo(account);
		List<CombatHero> heroes = accountHeroService.getAccountHeroDetails(account);

		// Loading APC layers info.
		List<APC> apcView = new ArrayList<>();
		apcInfo.forEach(apc -> {
			APC apcV = new APC();
			apcV.setId(apc.getId());
			apcV.setName(apc.getName());
			List<APCLayer> layers = new ArrayList<>();
			apc.getLayers().forEach(layer -> {
				APCLayer layerV = new APCLayer();
				layerV.setId(layer.getId());
				Troop troop = new Troop();
				troop.setName(layer.getTroopDetails().getTroop().getTroopClass().name());
				layerV.setLeadingUnit(troop);
				layerV.setMarchingCapacity(layer.getTroopDetails().getAmount());
				int heroId = layer.getHero().getId();
				Optional<CombatHero> hero = heroes.stream().filter(h -> {
					return h.getId() == heroId;
				}).findFirst();
				if (hero.isPresent()) {
					layerV.setHero(hero.get());
				}
				layers.add(layerV);

			});
			apcV.setLayer(layers);
			apcView.add(apcV);
		});

		// TODO - Load Hero details.

		// TODO - Load Troop details.

		// TODO - Load marching capacity.

		return apcView;
	}

}
