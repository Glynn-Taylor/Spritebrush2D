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

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

public class GUI_Layer {
	private final ArrayList<GUI_Button> Buttons = new ArrayList<GUI_Button>();
	private boolean[] ButtonClicked;
	private final ArrayList<Boolean> ObjectPressed = new ArrayList<Boolean>();
	private static long LastClickTime;
	private final long RegisterClickDelay = 300;
	private boolean Enabled = true;
	private final ArrayList<GUI_Object> objects = new ArrayList<GUI_Object>();
	private GUI_Layer_Controller Controller;
	
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

	public void toggleObjectEnabled(int i) {
		objects.get(i).toggleEnabled();
	}

	public void setObjectEnabled(int i, boolean b) {
		objects.get(i).SetEnabled(b);
	}

	public void ProcessInput(int mouseX, int mouseY, boolean mouseDown) {
		if (Enabled) {
			for (int i = 0; i < Buttons.size(); i++) {
				if (Buttons.get(i).InsideButton(mouseX, mouseY)
						&& mouseDown
						&& System.currentTimeMillis() - LastClickTime > RegisterClickDelay) {
					ButtonClicked[i] = true;
					LastClickTime = System.currentTimeMillis();
				} else {
					ButtonClicked[i] = false;
				}
			}
			for (int i = 0; i < objects.size(); i++) {
				if (objects.get(i).Enabled)
					if (System.currentTimeMillis() - LastClickTime > RegisterClickDelay
							&& objects.get(i).InsideObject(mouseX, mouseY)) {
						if (objects.get(i).ProcessInput(mouseX, mouseY,
								mouseDown)) {
							LastClickTime = System.currentTimeMillis();
						} else {
							objects.get(i).ReleaseClicks();

						}

					}

			}
		}
	}

	public void AddButton(RelativeDimensions absoluteDimensions, Texture t) {
		Buttons.add(new GUI_Button(absoluteDimensions, t));
		ButtonClicked = new boolean[Buttons.size()];
	}

	public void AddObject(GUI_Object obj) {
		objects.add(obj);

	}

	public void flushObject(int i) {
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

	public boolean isButtonDown(int i) {
		if (Enabled) {
			if (ButtonClicked[i] == true) {
				ButtonClicked[i] = false;
				return true;
			}
		}
		return false;
	}

	public boolean isElementDown(int object, int element) {
		if(objects.get(object).isElementDown(element))
			flushAll();
		return objects.get(object).isElementDown(element);

	}

	public GUI_Object_Element getElementDown(int object) {

		return objects.get(object).getElementDown();

	}

	public int getIndexDown(int object) {

		return objects.get(object).getIndexDown();

	}

	public String getElementName(int object, int element) {
		return objects.get(object).getElementName(element);
	}

	public void addElement(int object, GUI_Object_Element element) {
		objects.get(object).addElement(element);

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
}
