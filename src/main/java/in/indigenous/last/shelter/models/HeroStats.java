package in.indigenous.last.shelter.models;

import java.util.ArrayList;
import java.util.List;

public class HeroStats {

	private int range;

	private String leadingUnit;

	private double marchingCapacity;

	private Double damage = 0.0d;

	private double resitance;

	private double might;

	private double lowerResistance;

	private double hp;

	private double minusEnemyTurns;

	private double lowerDamage;

	private List<HeroSkills> skills = new ArrayList<>();

	private int combatSpeed;

	private double seigeMight;

	private double lowerMight;

	private double seigeDefenseMight;

	private double bulwark;

	public double getMarchingCapacity() {
		return marchingCapacity;
	}

	public void setMarchingCapacity(double marchingCapacity) {
		this.marchingCapacity = marchingCapacity;
	}

	public Double getDamage() {
		return damage;
	}

	public void setDamage(Double damage) {
		this.damage = damage;
	}

	public double getResitance() {
		return resitance;
	}

	public void setResitance(double resitance) {
		this.resitance = resitance;
	}

	public double getMight() {
		return might;
	}

	public void setMight(double might) {
		this.might = might;
	}

	public double getLowerResistance() {
		return lowerResistance;
	}

	public void setLowerResistance(double lowerResistance) {
		this.lowerResistance = lowerResistance;
	}

	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
	}

	public List<HeroSkills> getSkills() {
		return skills;
	}

	public void setSkills(List<HeroSkills> skills) {
		this.skills = skills;
	}

	public String getLeadingUnit() {
		return leadingUnit;
	}

	public void setLeadingUnit(String leadingUnit) {
		this.leadingUnit = leadingUnit;
	}

	public double getHp() {
		return hp;
	}

	public void setHp(double hp) {
		this.hp = hp;
	}

	public double getLowerDamage() {
		return lowerDamage;
	}

	public void setLowerDamage(double lowerDamage) {
		this.lowerDamage = lowerDamage;
	}

	public double getMinusEnemyTurns() {
		return minusEnemyTurns;
	}

	public void setMinusEnemyTurns(double minusEnemyTurns) {
		this.minusEnemyTurns = minusEnemyTurns;
	}

	public int getCombatSpeed() {
		return combatSpeed;
	}

	public void setCombatSpeed(int combatSpeed) {
		this.combatSpeed = combatSpeed;
	}

	public double getSeigeMight() {
		return seigeMight;
	}

	public void setSeigeMight(double seigeMight) {
		this.seigeMight = seigeMight;
	}

	public double getLowerMight() {
		return lowerMight;
	}

	public void setLowerMight(double lowerMight) {
		this.lowerMight = lowerMight;
	}

	public double getSeigeDefenseMight() {
		return seigeDefenseMight;
	}

	public void setSeigeDefenseMight(double seigeDefenseMight) {
		this.seigeDefenseMight = seigeDefenseMight;
	}

	public double getBulwark() {
		return bulwark;
	}

	public void setBulwark(double bulwark) {
		this.bulwark = bulwark;
	}

}
