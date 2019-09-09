package in.indigenous.last.shelter.models.hero.skills.combat;

import java.util.List;

import in.indigenous.last.shelter.models.troops.Troop;

public class CombatSkill extends CombatHeroSkill {

	private int range;

	private Troop applicableTroop;

	private double chance;

	private String damage;

	private int targetUnit;

	private int prepTurns;

	private List<Integer> turns;

	private MinusMight enemyMinusMight;

	private PlusDamage additionalDamage;

	private MinusResistance enemyMinusResistance;

	private MinusTurns enemyMinusTurns;

	private MinusDamage enemyMinusDamage;
	
	private LeadingUnit leadingUnit;
	
	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
	}

	public Troop getApplicableTroop() {
		return applicableTroop;
	}

	public void setApplicableTroop(Troop applicableTroop) {
		this.applicableTroop = applicableTroop;
	}

	public double getChance() {
		return chance;
	}

	public void setChance(double chance) {
		this.chance = chance;
	}

	public String getDamage() {
		return damage;
	}

	public void setDamage(String damage) {
		this.damage = damage;
	}

	public int getTargetUnit() {
		return targetUnit;
	}

	public void setTargetUnit(int targetUnit) {
		this.targetUnit = targetUnit;
	}

	public int getPrepTurns() {
		return prepTurns;
	}

	public void setPrepTurns(int prepTurns) {
		this.prepTurns = prepTurns;
	}

	public List<Integer> getTurns() {
		return turns;
	}

	public void setTurns(List<Integer> turns) {
		this.turns = turns;
	}

	public MinusMight getEnemyMinusMight() {
		return enemyMinusMight;
	}

	public void setEnemyMinusMight(MinusMight enemyMinusMight) {
		this.enemyMinusMight = enemyMinusMight;
	}

	public PlusDamage getAdditionalDamage() {
		return additionalDamage;
	}

	public void setAdditionalDamage(PlusDamage additionalDamage) {
		this.additionalDamage = additionalDamage;
	}

	public MinusResistance getEnemyMinusResistance() {
		return enemyMinusResistance;
	}

	public void setEnemyMinusResistance(MinusResistance enemyMinusResistance) {
		this.enemyMinusResistance = enemyMinusResistance;
	}

	public MinusTurns getEnemyMinusTurns() {
		return enemyMinusTurns;
	}

	public void setEnemyMinusTurns(MinusTurns enemyMinusTurns) {
		this.enemyMinusTurns = enemyMinusTurns;
	}

	public MinusDamage getEnemyMinusDamage() {
		return enemyMinusDamage;
	}

	public void setEnemyMinusDamage(MinusDamage enemyMinusDamage) {
		this.enemyMinusDamage = enemyMinusDamage;
	}
	
	public LeadingUnit getLeadingUnit() {
		return leadingUnit;
	}

	public void setLeadingUnit(LeadingUnit leadingUnit) {
		this.leadingUnit = leadingUnit;
	}
}
