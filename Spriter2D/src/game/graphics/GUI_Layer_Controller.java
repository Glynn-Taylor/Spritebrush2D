package game.graphics;

import java.util.ArrayList;
import java.util.HashMap;

public class GUI_Layer_Controller {
	private HashMap<String,Integer> LayerNameToIndex = new HashMap<String, Integer>();
	private ArrayList<GUI_Layer> Layers = new ArrayList<GUI_Layer>();
	
	public void Render() {
		for (int i = 0; i < Layers.size(); i++) {
			Layers.get(i).Render();
		}
	}
	public void addLayer(GUI_Layer l, String name){
		if(l!=null){
			LayerNameToIndex.put(name, Layers.size());
			l.setController(this);
			Layers.add(l);
		}
	}
}
