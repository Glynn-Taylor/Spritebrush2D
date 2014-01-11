package game.graphics;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.opengl.Texture;

public class GUI_Text_Field extends GUI_Object {
	String txt;
	UnicodeFont font;
	int StringX, StringY;

	public GUI_Text_Field(RelativeDimensions absoluteDimensions, String text,
			UnicodeFont f) {
		txt = text;
		this.AbsoluteDimensions = absoluteDimensions;
		font = f;
		onResize();
		

	}

	@Override
	public boolean ProcessInput(int mouseX, int mouseY, boolean mouseDown) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void RenderElements() {
		Box(EntityWindowX, EntityWindowY, EntityWidth, EntityHeight,
				Color.black);
		Box(EntityWindowX + 5, EntityWindowY + 5, EntityWidth - 10,
				EntityHeight - 10, Color.white);
		font.drawString(StringX, StringY, txt, Color.black);
		Color.white.bind();
	}

	void Box(int x, int y, int w, int h, Color c) {
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		c.bind();
		GL11.glBegin(GL11.GL_QUADS);

		GL11.glVertex3f(x, y, 0);

		GL11.glVertex3f(x, y + h, 0);

		GL11.glVertex3f(x + w, y + h, 0);

		GL11.glVertex3f(x + w, y, 0);

		GL11.glEnd();
		Color.white.bind();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}

	public void setText(String str) {
		txt = str;
		StringX = EntityWindowX + (EntityWidth - font.getWidth(str)) / 2;
		StringY = EntityWindowY + (EntityHeight - font.getHeight(str)) / 2;
	}

	@Override
	public void OnEndActivation() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void OnResizeEntity() {
		StringX = EntityWindowX + (EntityWidth - font.getWidth(txt)) / 2;
		StringY = EntityWindowY + (EntityHeight - font.getHeight(txt)) / 2;

	}
}
