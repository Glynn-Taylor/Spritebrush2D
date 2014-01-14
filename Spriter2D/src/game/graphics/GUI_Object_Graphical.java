package game.graphics;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;

public abstract class GUI_Object_Graphical extends GUI_Object {

	protected void RenderText(String text,int x,int y, int Width, int Height, Font font, Color c) {
		int StringX = x + (Width - font.getWidth(text)) / 2;
		int StringY = y + (Height-font.getHeight(text))/ 2;
		font.drawString(StringX, StringY, text, c);
		Color.white.bind();
	}
	protected void Box(int x, int y, int w, int h, Color c) {

		c.bind();
		GL11.glBegin(GL11.GL_QUADS);

		GL11.glVertex3f(x, y, 0);

		GL11.glVertex3f(x, y + h, 0);

		GL11.glVertex3f(x + w, y + h, 0);

		GL11.glVertex3f(x + w, y, 0);

		GL11.glEnd();

	}
}
