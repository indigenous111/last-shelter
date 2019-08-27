package in.indigenous.last.shelter.models;

import in.indigenous.last.shelter.constants.Troops;

public class APCTroopsDetail {

	private int apcId;

	private String name;

	private int layer;

	private int heroId;

	private String heroName;

	private Troops troops;

	private long marchingCapacity;

	private long netMarchingCapacity;

	public int getApcId() {
		return apcId;
	}

	public void setApcId(int apcId) {
		this.apcId = apcId;
	}

	public int getLayer() {
		return layer;
	}

	public void setLayer(int layer) {
		this.layer = layer;
	}

	public Troops getTroops() {
		return troops;
	}

	public void setTroops(Troops troops) {
		this.troops = troops;
	}

	public long getMarchingCapacity() {
		return marchingCapacity;
	}

	public void setMarchingCapacity(long marchingCapacity) {
		this.marchingCapacity = marchingCapacity;
	}

	public int getHeroId() {
		return heroId;
	}

	public void setHeroId(int heroId) {
		this.heroId = heroId;
	}

	public String getHeroName() {
		return heroName;
	}

	public void setHeroName(String heroName) {
		this.heroName = heroName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getNetMarchingCapacity() {
		return netMarchingCapacity;
	}

	public void setNetMarchingCapacity(long netMarchingCapacity) {
		this.netMarchingCapacity = netMarchingCapacity;
	}

}
