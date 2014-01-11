package game.graphics;

import game.data.sprite.Colour;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

public class GUI_Renderer_ColorInterface_HSV extends GUI_Object {
	GUI_Renderer_ColorArray ColorRenderer;

	/*
	 * static Texture playButton = TextureLoader .getTexture( "PNG",
	 * ResourceLoader
	 * .getResourceAsStream("res/Materials/GUI/Buttons/Button_Play.png"), false,
	 * GL11.GL_NEAREST);
	 */

	public GUI_Renderer_ColorInterface_HSV(int startX, int startY, int width,
			int height, GUI_Renderer_ColorArray colArray) {
		EntityWindowX = startX;
		EntityWindowY = startY;
		EntityWidth = width;
		EntityHeight = height;
		ColorRenderer = colArray;

	}

	@Override
	public boolean ProcessInput(int mouseX, int mouseY, boolean mouseDown) {
		if (mouseDown)
			for (int i = 0; i < Elements.size(); i++) {
				Elements.get(i).InsideElement(mouseX, mouseY);
			}
		if (isElementDown(0)) {
			Colour c = ColorRenderer.getPrimaryC();
			ColorRenderer.setPrimary(ColorRenderer.getSecondaryC());
			ColorRenderer.setSecondary(c);
			ReleaseClicks();
			return true;
		}
		if (isElementDown(1)) {

			ReleaseClicks();
			return true;
		}
		return false;
	}

	@Override
	protected void RenderElements() {

		GL11.glDisable(GL11.GL_TEXTURE_2D);
		// Box(ScreenX, ScreenY, ScreenButtonWidth, ScreenButtonHeight,
		// Color.white);
		Box(EntityWindowX, EntityWindowY, EntityWidth, EntityHeight,
				Color.black);
		Box(EntityWindowX + 5, EntityWindowY + 5, EntityWidth - 10,
				EntityHeight - 10, Color.white);
		Box((int) (EntityWindowX + EntityWidth * 7f / 16f) - 5,
				(int) (EntityWindowY + EntityHeight * 7f / 16f) - 5,
				(int) (EntityWidth * 1f / 4f) + 10,
				(int) (EntityHeight * 1f / 4f) + 10, Color.black);
		Box((int) (EntityWindowX + EntityWidth * 5f / 16f) - 5,
				(int) (EntityWindowY + EntityHeight * 5f / 16f) - 5,
				(int) (EntityWidth * 1f / 4f) + 10,
				(int) (EntityHeight * 1f / 4f) + 10, Color.black);
		Box((int) (EntityWindowX + EntityWidth * 7f / 16f),
				(int) (EntityWindowY + EntityHeight * 7f / 16f),
				(int) (EntityWidth * 1f / 4f),
				(int) (EntityHeight * 1f / 4f),
				ColorRenderer.getSecondary());
		Box((int) (EntityWindowX + EntityWidth * 5f / 16f),
				(int) (EntityWindowY + EntityHeight * 5f / 16f),
				(int) (EntityWidth * 1f / 4f),
				(int) (EntityHeight * 1f / 4f),
				ColorRenderer.getPrimary());
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		Color.white.bind();
		RenderElementsTruePosition();
	}

	void Box(int x, int y, int w, int h, Color c) {

		c.bind();
		GL11.glBegin(GL11.GL_QUADS);

		GL11.glVertex3f(x, y, 0);

		GL11.glVertex3f(x, y + h, 0);

		GL11.glVertex3f(x + w, y + h, 0);

		GL11.glVertex3f(x + w, y, 0);

		GL11.glEnd();

	}

	@Override
	public void OnEndActivation() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void OnResizeEntity() {
		// TODO Auto-generated method stub
		
	}

}
