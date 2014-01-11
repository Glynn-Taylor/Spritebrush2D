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
/*Acts as an on screen button (generic), can be set-up with
 different textures, registers mouse clicks if updated.
 */
package game.graphics;

import game.states.Game;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;

public class GUI_Button extends GUI_Entity {
	
	private final float MouseOverMultiplier = 1.1f;
	
	
	public void Render() {
		texture.bind();
		GL11.glBegin(GL11.GL_QUADS);

		GL11.glTexCoord2f(0, 0);
		GL11.glVertex3f(EntityWindowX, EntityWindowY, 0);

		GL11.glTexCoord2f(0, 1);
		GL11.glVertex3f(EntityWindowX, EntityWindowY + EntityHeight
				* (MouseInsideMe ? MouseOverMultiplier : 1), 0);

		GL11.glTexCoord2f(1, 1);
		GL11.glVertex3f(EntityWindowX + EntityWidth
				* (MouseInsideMe ? MouseOverMultiplier : 1), EntityWindowY
				+ EntityHeight
				* (MouseInsideMe ? MouseOverMultiplier : 1), 0);

		GL11.glTexCoord2f(1, 0);
		GL11.glVertex3f(EntityWindowX + EntityWidth
				* (MouseInsideMe ? MouseOverMultiplier : 1), EntityWindowY, 0);

		GL11.glEnd();

	}
	
	public GUI_Button(RelativeDimensions absoluteDimensions, Texture t) {
		this.AbsoluteDimensions= absoluteDimensions; 
		onResize();
		texture = t;
	}

	public boolean InsideButton(int mouseX, int mouseY) {
		if (mouseX > EntityWindowX && mouseX < EntityWindowX + EntityWidth) {
			if (SCREEN_HEIGHT - mouseY > EntityWindowY
					&& SCREEN_HEIGHT - mouseY < EntityWindowY + EntityHeight) {
				MouseInsideMe = true;
				return true;
			}

		}
		MouseInsideMe = false;
		return false;
	}
	@Override
	protected void OnResizeEntity() {
		// TODO Auto-generated method stub
		
	}
}
