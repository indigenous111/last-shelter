package in.indigenous.last.shelter.models.troops;

/**
 * Troop stats details of a troop.
 * 
 * @author sarkh
 *
 */
public class Troop {

	private TroopClass troopClass;

	private int id;

	private String name;

	private int level;

	private int attack;

	private int speed;

	private int defense;

	private int load;

	private int hp;

	private double foodConsumption;

	private int combat;

	public TroopClass getTroopClass() {
		return troopClass;
	}

	public void setTroopClass(TroopClass troopClass) {
		this.troopClass = troopClass;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getDefense() {
		return defense;
	}

	public void setDefense(int defense) {
		this.defense = defense;
	}

	public int getLoad() {
		return load;
	}

	public void setLoad(int load) {
		this.load = load;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public double getFoodConsumption() {
		return foodConsumption;
	}

	public void setFoodConsumption(double foodConsumption) {
		this.foodConsumption = foodConsumption;
	}

	public int getCombat() {
		return combat;
	}

	public void setCombat(int combat) {
		this.combat = combat;
	}

}
