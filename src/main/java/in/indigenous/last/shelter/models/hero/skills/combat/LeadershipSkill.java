package in.indigenous.last.shelter.models.hero.skills.combat;

public class LeadershipSkill extends CombatHeroSkill {

	private String marchingCapacity;

	private double resistance;

	private double might;

	private double seigeMight;

	private double hp;

	private int combatSpeed;

	public String getMarchingCapacity() {
		return marchingCapacity;
	}

	public void setMarchingCapacity(String marchingCapacity) {
		this.marchingCapacity = marchingCapacity;
	}

	public double getResistance() {
		return resistance;
	}

	public void setResistance(double resistance) {
		this.resistance = resistance;
	}

	public double getMight() {
		return might;
	}

	public void setMight(double might) {
		this.might = might;
	}

	public double getSeigeMight() {
		return seigeMight;
	}

	public void setSeigeMight(double seigeMight) {
		this.seigeMight = seigeMight;
	}

	public double getHp() {
		return hp;
	}

	public void setHp(double hp) {
		this.hp = hp;
	}

	public int getCombatSpeed() {
		return combatSpeed;
	}

	public void setCombatSpeed(int combatSpeed) {
		this.combatSpeed = combatSpeed;
	}

}
