package in.indigenous.last.shelter.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Troop {

	private int id;

	private String name;

	private int level;

	private long attack;

	private long speed;

	private long defense;

	private long load;

	private long hp;

	private long foodConsumption;

	private long combat;

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

	public long getAttack() {
		return attack;
	}

	public void setAttack(long attack) {
		this.attack = attack;
	}

	public long getSpeed() {
		return speed;
	}

	public void setSpeed(long speed) {
		this.speed = speed;
	}

	public long getDefense() {
		return defense;
	}

	public void setDefense(long defense) {
		this.defense = defense;
	}

	public long getLoad() {
		return load;
	}

	public void setLoad(long load) {
		this.load = load;
	}

	public long getHp() {
		return hp;
	}

	public void setHp(long hp) {
		this.hp = hp;
	}

	public long getFoodConsumption() {
		return foodConsumption;
	}

	public void setFoodConsumption(long foodConsumption) {
		this.foodConsumption = foodConsumption;
	}

	public long getCombat() {
		return combat;
	}

	public void setCombat(long combat) {
		this.combat = combat;
	}

}
