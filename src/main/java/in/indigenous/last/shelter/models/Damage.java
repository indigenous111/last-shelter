package in.indigenous.last.shelter.models;

import java.util.List;

public class Damage {

	private String damage;

	private int minusTurns;

	private List<Integer> turns;

	private int targetUnit;

	private MinusMight minusMight;

	public String getDamage() {
		return damage;
	}

	public void setDamage(String damage) {
		this.damage = damage;
	}

	public int getMinusTurns() {
		return minusTurns;
	}

	public void setMinusTurns(int minusTurns) {
		this.minusTurns = minusTurns;
	}

	public List<Integer> getTurns() {
		return turns;
	}

	public void setTurns(List<Integer> turns) {
		this.turns = turns;
	}

	public int getTargetUnit() {
		return targetUnit;
	}

	public void setTargetUnit(int targetUnit) {
		this.targetUnit = targetUnit;
	}

	public MinusMight getMinusMight() {
		return minusMight;
	}

	public void setMinusMight(MinusMight minusMight) {
		this.minusMight = minusMight;
	}

}
