package in.indigenous.last.shelter.models.apc;

import java.util.ArrayList;
import java.util.List;

public class APC {

	private int id;

	private String name;

	private boolean classApc;

	private List<APCLayer> layers = new ArrayList<>();

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

	public boolean isClassApc() {
		return classApc;
	}

	public void setClassApc(boolean classApc) {
		this.classApc = classApc;
	}

	public List<APCLayer> getLayers() {
		return layers;
	}

	public void setLayers(List<APCLayer> layers) {
		this.layers = layers;
	}

}
