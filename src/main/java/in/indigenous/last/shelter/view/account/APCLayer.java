package in.indigenous.last.shelter.view.account;

import in.indigenous.last.shelter.view.CombatHero;
import in.indigenous.last.shelter.view.Troop;

public class APCLayer {

	private int id;

	private long marchingCapacity;

	private Troop leadingUnit;

	private CombatHero hero;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getMarchingCapacity() {
		return marchingCapacity;
	}

	public void setMarchingCapacity(long marchingCapacity) {
		this.marchingCapacity = marchingCapacity;
	}

	public Troop getLeadingUnit() {
		return leadingUnit;
	}

	public void setLeadingUnit(Troop leadingUnit) {
		this.leadingUnit = leadingUnit;
	}

	public CombatHero getHero() {
		return hero;
	}

	public void setHero(CombatHero hero) {
		this.hero = hero;
	}

}
