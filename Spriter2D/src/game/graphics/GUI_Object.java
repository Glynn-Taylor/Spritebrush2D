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
 * Abstract base class for GUI elements
 */
package game.graphics;

import game.states.Game;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;

public abstract class GUI_Object extends GUI_Entity{
	
	// private final float MouseOverMultiplier = 1.1f;
	
	protected final ArrayList<GUI_Object_Element> Elements = new ArrayList<GUI_Object_Element>();

	public abstract boolean ProcessInput(int mouseX, int mouseY,
			boolean mouseDown);

	
	public GUI_Object(){
		//System.out.println("object got called");
	}
	
	public boolean InsideObject(int mouseX, int mouseY) {
		if (mouseX > EntityWindowX && mouseX < EntityWindowX + EntityWidth) {
			if (SCREEN_HEIGHT - mouseY > EntityWindowY
					&& SCREEN_HEIGHT - mouseY < EntityWindowY + EntityHeight) {
				MouseInsideMe = true;

				return true;
				// EDIT ME
			}

		}
		MouseInsideMe = false;
		return false;
	}

	public void Render() {
		if (texture != null)
			RenderBackground();
		RenderElements();
	}

	protected abstract void RenderElements();

	protected void RenderElementsTruePosition() {
		for (int i = 0; i < Elements.size(); i++) {
			Elements.get(i).Render();
		}

	}

	private void RenderBackground() {
		texture.bind();
		GL11.glBegin(GL11.GL_QUADS);

		GL11.glTexCoord2f(0, 0);
		GL11.glVertex3f(EntityWindowX, EntityWindowY, 0);

		GL11.glTexCoord2f(0, 1);
		GL11.glVertex3f(EntityWindowX, EntityWindowY + EntityHeight, 0);

		GL11.glTexCoord2f(1, 1);
		GL11.glVertex3f(EntityWindowX + EntityWidth, EntityWindowY
				+ EntityHeight, 0);

		GL11.glTexCoord2f(1, 0);
		GL11.glVertex3f(EntityWindowX + EntityWidth, EntityWindowY, 0);

		GL11.glEnd();
	}

	public void SetEnabled(boolean i) {
		Enabled = i;
	}

	public void toggleEnabled() {
		Enabled = !Enabled;

	}

	public void ReleaseClicks() {
		for (int i = 0; i < Elements.size(); i++) {
			Elements.get(i).Clicked = false;
		}
	}

	public boolean isElementDown(int e) {
		if (e < Elements.size()) {
			return Elements.get(e).Clicked;
		}
		return false;

	}

	public void addElement(GUI_Object_Element element) {
		Elements.add(element);

	}

	public String getElementName(int element) {
		return Elements.get(element).getName();
	}

	public GUI_Object_Element getElementDown() {
		for (int i = 0; i < Elements.size(); i++) {
			if (Elements.get(i).Clicked == true)
				return Elements.get(i);
		}
		return null;
	}

	public int getIndexDown() {
		for (int i = 0; i < Elements.size(); i++) {
			if (Elements.get(i).Clicked == true)
				return i;
		}
		return -1;
	}
	public abstract void OnEndActivation();
}
