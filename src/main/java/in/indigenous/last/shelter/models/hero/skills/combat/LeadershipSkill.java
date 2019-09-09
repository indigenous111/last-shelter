package in.indigenous.last.shelter.models.hero.skills.combat;

public class LeadershipSkill extends CombatHeroSkill {

	private String marchingCapacity;

	private double resistance;

	private double might;

	private double seigeMight;

	private double seigeDefenseMight;

	private double bulwark;

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
