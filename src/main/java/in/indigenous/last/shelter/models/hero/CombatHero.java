package in.indigenous.last.shelter.models.hero;

import in.indigenous.last.shelter.models.troops.Troop;

public class CombatHero extends Hero {

	private Troop leadingTroop;

	public Troop getLeadingTroop() {
		return leadingTroop;
	}

	public void setLeadingTroop(Troop leadingTroop) {
		this.leadingTroop = leadingTroop;
	}
	
	
}
