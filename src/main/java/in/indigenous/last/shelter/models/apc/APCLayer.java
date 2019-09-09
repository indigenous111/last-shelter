package in.indigenous.last.shelter.models.apc;

import in.indigenous.last.shelter.models.hero.Hero;
import in.indigenous.last.shelter.models.troops.TroopDetails;

public class APCLayer {

	private int id;
	
	private Hero hero;
	
	private TroopDetails troopDetails;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Hero getHero() {
		return hero;
	}

	public void setHero(Hero hero) {
		this.hero = hero;
	}

	public TroopDetails getTroopDetails() {
		return troopDetails;
	}

	public void setTroopDetails(TroopDetails troopDetails) {
		this.troopDetails = troopDetails;
	}
}
