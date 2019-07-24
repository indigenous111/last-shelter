package in.indigenous.last.shelter.models;

public class HeroSkills {

	private int id;

	private String name;

	private int level;

	private int range;

	private String leadingUnit;

	private String marchingCapacity;

	private Damage damage;

	private AdditionalDamage additionalDamage;

	private double resitance;

	private double might;

	private LowerResistance lowerResistance;

	private double hp;

	private MinusEnemyTurns minusEnemyTurns;

	private LowerDamage lowerDamage;

	private int combatSpeed;

	private double seigeMight;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMarchingCapacity() {
		return marchingCapacity;
	}

	public void setMarchingCapacity(String marchingCapacity) {
		this.marchingCapacity = marchingCapacity;
	}

	public Damage getDamage() {
		return damage;
	}

	public void setDamage(Damage damage) {
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

	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getLeadingUnit() {
		return leadingUnit;
	}

	public void setLeadingUnit(String leadingUnit) {
		this.leadingUnit = leadingUnit;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getHp() {
		return hp;
	}

	public void setHp(double hp) {
		this.hp = hp;
	}

	public LowerResistance getLowerResistance() {
		return lowerResistance;
	}

	public void setLowerResistance(LowerResistance lowerResistance) {
		this.lowerResistance = lowerResistance;
	}

	public LowerDamage getLowerDamage() {
		return lowerDamage;
	}

	public void setLowerDamage(LowerDamage lowerDamage) {
		this.lowerDamage = lowerDamage;
	}

	public AdditionalDamage getAdditionalDamage() {
		return additionalDamage;
	}

	public void setAdditionalDamage(AdditionalDamage additionalDamage) {
		this.additionalDamage = additionalDamage;
	}

	public MinusEnemyTurns getMinusEnemyTurns() {
		return minusEnemyTurns;
	}

	public void setMinusEnemyTurns(MinusEnemyTurns minusEnemyTurns) {
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

}
