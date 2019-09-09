package in.indigenous.last.shelter.view.account;

import java.util.List;

public class APC {

	private int id;

	private String name;

	private List<APCLayer> layer;

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

	public List<APCLayer> getLayer() {
		return layer;
	}

	public void setLayer(List<APCLayer> layer) {
		this.layer = layer;
	}

}
