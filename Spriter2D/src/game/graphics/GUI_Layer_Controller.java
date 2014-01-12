package game.graphics;

import java.util.ArrayList;
import java.util.HashMap;

public class GUI_Layer_Controller {
	private HashMap<String,Integer> LayerNameToIndex = new HashMap<String, Integer>();
	private ArrayList<GUI_Layer> Layers = new ArrayList<GUI_Layer>();
	
	public void Render() {
		for (int i = 0; i < Layers.size(); i++) {
			Layers.get((Layers.size()-1)-i).Render();
		}
	}
	public void addLayer(GUI_Layer l, String name){
		if(l!=null){
			LayerNameToIndex.put(name, Layers.size());
			l.setController(this);
			Layers.add(l);
		}
	}
	public void ProcessInput(int mouseX, int mouseY, boolean mouseDown) {
		for (int i = 0; i < Layers.size(); i++) {
			if(Layers.get(i).ProcessInput(mouseX, mouseY, mouseDown))
				break;
		}
	}
	
	public boolean isButtonDown(String layername,String object) {
		return Layers.get(LayerNameToIndex.get(layername)).isButtonDown(object);
	}

	public boolean isElementDown(String layername,String object, int element) {
		return Layers.get(LayerNameToIndex.get(layername)).isElementDown(object, element);

	}

	public GUI_Object_Element getElementDown(String layername,String object) {
		return Layers.get(LayerNameToIndex.get(layername)).getElementDown(object);
	}

	public int getIndexDown(String layername,String object) {
		return Layers.get(LayerNameToIndex.get(layername)).getIndexDown(object);
	}

	public String getElementName(String layername,String object, int element) {
		return Layers.get(LayerNameToIndex.get(layername)).getElementName(object, element);
	}
	public void flushObject(String layername, String object) {
		Layers.get(LayerNameToIndex.get(layername)).flushObject(object);
		
	}
	public void onResize() {
		for (int i = 0; i < Layers.size(); i++) {
			Layers.get(i).onResize();
		}
	}
}
