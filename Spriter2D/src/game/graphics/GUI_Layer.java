/*******************************************************************************
 * Copyright (c) 2013 Glynn Taylor.
 * All rights reserved. This program and the accompanying materials, 
 * (excluding imported libraries, such as LWJGL and Slick2D)
 * are made available under the terms of the GNU Public License
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *     Glynn Taylor - initial API and implementation
 ******************************************************************************/
/*
 * Handles GUI elements, allows them to be added and 
 taken away easily using methods, bulk processes input 
 and handles mouse click events
 */
package game.graphics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

public class GUI_Layer {
	private final ArrayList<GUI_Button> Buttons = new ArrayList<GUI_Button>();
	private final ArrayList<Boolean> ObjectPressed = new ArrayList<Boolean>();
	private final ArrayList<GUI_Object> objects = new ArrayList<GUI_Object>();
	private final long RegisterClickDelay = 300;
	
	private HashMap<String,Integer> ButtonNameToIndex = new HashMap<String, Integer>();
	private HashMap<String,Integer> ObjectNameToIndex = new HashMap<String, Integer>();
	
	private boolean[] ButtonClicked;
	private static long LastClickTime;
	private boolean Enabled = true;
	private boolean UseDelay = true;
	
	private GUI_Layer_Controller Controller;
	//private boolean AutoFlush=false;
	
	public void Render() {
		if (Enabled) {

			for (int i = 0; i < objects.size(); i++) {
				if (objects.get(i).Enabled)
					objects.get(i).Render();
			}
			for (int i = 0; i < Buttons.size(); i++) {
				Buttons.get(i).Render();
			}
		}

	}

	public void toggleObjectEnabled(String object) {
		objects.get(ObjectNameToIndex.get(object)).toggleEnabled();
	}

	public void setObjectEnabled(String object, boolean b) {
		objects.get(ObjectNameToIndex.get(object)).SetEnabled(b);
	}

	public boolean ProcessInput(int mouseX, int mouseY, boolean mouseDown) {
		if (Enabled) {
			for (int i = 0; i < Buttons.size(); i++) {
				if (Buttons.get(i).InsideButton(mouseX, mouseY)
						&& mouseDown
						&& System.currentTimeMillis() - LastClickTime > RegisterClickDelay) {
					ButtonClicked[i] = true;
					LastClickTime = System.currentTimeMillis();
					return true;
				} else {
					ButtonClicked[i] = false;
				}
			}
			for (int i = 0; i < objects.size(); i++) {
				if (objects.get(i).Enabled)
					if ((!UseDelay || System.currentTimeMillis() - LastClickTime > RegisterClickDelay)
							&& objects.get(i).InsideObject(mouseX, mouseY)) {
						if (objects.get(i).ProcessInput(mouseX, mouseY,
								mouseDown)) {
							LastClickTime = System.currentTimeMillis();
							return true;
						} else {
							objects.get(i).ReleaseClicks();

						}

					}

			}
		}
		return false;
	}

	public void AddButton(String name,RelativeDimensions absoluteDimensions, Texture t) {
		if(!ButtonNameToIndex.containsKey(name)){
		ButtonNameToIndex.put(name, Buttons.size());
		Buttons.add(new GUI_Button(absoluteDimensions, t));
		ButtonClicked = new boolean[Buttons.size()];
		}
	}

	public void addObject(String name,GUI_Object obj) {
		if(!ObjectNameToIndex.containsKey(name)){
			ObjectNameToIndex.put(name, objects.size());
			objects.add(obj);
		}
	}
	public void destroyObject(String name){
		if(ObjectNameToIndex.containsKey(name)){
			int index = ObjectNameToIndex.get(name);
			ObjectNameToIndex.remove(name);
			for (Map.Entry<String,Integer> entry : ObjectNameToIndex.entrySet()) {
				if(entry.getValue()>index)
					entry.setValue(entry.getValue()-1);
			}
			objects.remove(index);
		}
	}
	
	public void flushObject(String object) {
		objects.get(ObjectNameToIndex.get(object)).ReleaseClicks();
		objects.get(ObjectNameToIndex.get(object)).OnEndActivation();
	}
	private void flushObject(int i) {
		objects.get(i).ReleaseClicks();
		objects.get(i).OnEndActivation();
	}
	public void flushAll() {
		if (Enabled) {
			for (int i = 0; i < Buttons.size(); i++) {
				
			}
			for (int i = 0; i < objects.size(); i++) {
				flushObject(i);
			}
		}
	}

	public void SetEnabled(boolean i) {
		Enabled = i;
	}

	public void toggleEnabled() {
		Enabled = !Enabled;
	}

	public boolean isButtonDown(String object) {
		if (Enabled) {
			if (ButtonClicked[ButtonNameToIndex.get(object)] == true) {
				ButtonClicked[ButtonNameToIndex.get(object)] = false;
				return true;
			}
		}
		return false;
	}

	public boolean isElementDown(String object, int element) {
		//if(AutoFlush)
			//flushAll();
		return objects.get(ObjectNameToIndex.get(object)).isElementDown(element);

	}

	public GUI_Object_Element getElementDown(String object) {

		return objects.get(ObjectNameToIndex.get(object)).getElementDown();

	}

	public int getIndexDown(String object) {

		return objects.get(ObjectNameToIndex.get(object)).getIndexDown();

	}

	public String getElementName(String object, int element) {
		return objects.get(ObjectNameToIndex.get(object)).getElementName(element);
	}

	public void addElement(String object, GUI_Object_Element element) {
		objects.get(ObjectNameToIndex.get(object)).addElement(element);

	}
	public void setUseDelay(boolean b){
		UseDelay=b;
	}

	public void Box(int x, int y, int w, int h, Color c) {
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		c.bind();
		GL11.glBegin(GL11.GL_QUADS);

		// GL11.glTexCoord2f(0, 0);
		GL11.glVertex3f(x, y, 0);

		// GL11.glTexCoord2f(0, 1);
		GL11.glVertex3f(x, y + h, 0);

		// GL11.glTexCoord2f(1, 1);
		GL11.glVertex3f(x + w, y + h, 0);

		// GL11.glTexCoord2f(1, 0);
		GL11.glVertex3f(x + w, y, 0);

		GL11.glEnd();
		Color.white.bind();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}

	public void onResize() {
		for(int i=0;i<Buttons.size();i++){
			Buttons.get(i).onResize();
		}
		for(int i=0;i<objects.size();i++){
			objects.get(i).onResize();
		}
		
	}
	public void setController(GUI_Layer_Controller lc){
		Controller=lc;
	}
	/*public void setAutoFlush(boolean b){
		AutoFlush=b;
	}*/
}
