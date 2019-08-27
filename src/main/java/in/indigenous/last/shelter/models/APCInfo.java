package in.indigenous.last.shelter.models;

import java.util.ArrayList;
import java.util.List;

public class APCInfo {

	private int apcId;

	private String name;

	private List<APCTroopsDetail> details = new ArrayList<>();

	public int getApcId() {
		return apcId;
	}

	public void setApcId(int apcId) {
		this.apcId = apcId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<APCTroopsDetail> getDetails() {
		return details;
	}

	public void setDetails(List<APCTroopsDetail> details) {
		this.details = details;
	}

}
