package in.indigenous.last.shelter.models;

import java.util.List;

public class MinusEnemyTurns {

	private double chance;

	private List<Integer> turns;

	private int units;

	private int minusTurns;

	public double getChance() {
		return chance;
	}

	public void setChance(double chance) {
		this.chance = chance;
	}

	public List<Integer> getTurns() {
		return turns;
	}

	public void setTurns(List<Integer> turns) {
		this.turns = turns;
	}

	public int getUnits() {
		return units;
	}

	public void setUnits(int units) {
		this.units = units;
	}

	public int getMinusTurns() {
		return minusTurns;
	}

	public void setMinusTurns(int minusTurns) {
		this.minusTurns = minusTurns;
	}

}
