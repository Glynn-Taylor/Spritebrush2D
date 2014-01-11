package game.graphics;

import game.util.Project;

import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class GUI_List_Scroll_Project extends GUI_Object {

	private final int Visible;
	private int CurrentPosition = 0;
	private Texture SideBarTex;
	private Texture ScrollBarTex;
	UnicodeFont font;
	private Project ProjectReference;

	// private final GUI_Renderer_ColorArray Renderer;

	public GUI_List_Scroll_Project(RelativeDimensions absoluteDimensions, Texture t, int visible, UnicodeFont fnt) {
		// Renderer = rend;
		try {
			SideBarTex = TextureLoader
					.getTexture(
							"PNG",
							ResourceLoader
									.getResourceAsStream("res/Materials/GUI/Scroll/SliderRail.png"),
							false, GL11.GL_NEAREST);
			ScrollBarTex = TextureLoader
					.getTexture(
							"PNG",
							ResourceLoader
									.getResourceAsStream("res/Materials/GUI/Scroll/SliderButton.png"),
							false, GL11.GL_NEAREST);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.AbsoluteDimensions= absoluteDimensions; 
		onResize();
		this.Visible = visible;
		texture = t;
		font = fnt;
		
	}

	@Override
	public boolean ProcessInput(int mouseX, int mouseY, boolean mouseDown) {
		// return mouseDown;

		if (mouseDown & !Elements.isEmpty()) {
			int x = mouseX - EntityWindowX;
			int y = (SCREEN_HEIGHT - mouseY) - EntityWindowY;
			if (x > EntityWidth * 6 / 7) {
				if (Elements.size() > Visible)
					CurrentPosition = (int) (((float) y / (float) EntityHeight) * (Elements
							.size() - (Visible - 1)));

			} else {
				Elements.get((int) (((float) y / (float) EntityHeight) * Visible)
						+ CurrentPosition).Clicked = true;
				return true;
			}
		}
		return false;

	}

	@Override
	protected void RenderElements() {
		for (int i = 0; i < Visible; i++) {
			if (i + CurrentPosition < Elements.size()) {
				Elements.get(i + CurrentPosition).Render(EntityWindowX,
						EntityWindowY + (EntityHeight) / Visible * i,
						EntityWidth * 6 / 7,
						(EntityHeight) / Visible);
				RenderText(i, Elements.get(i + CurrentPosition).getName());
			}
		}
		RenderTexture(EntityWindowX + EntityWidth * 6 / 7, EntityWindowY,
				EntityWidth * 1 / 7, (EntityHeight), SideBarTex);
		RenderTexture(
				EntityWindowX + EntityWidth * 6 / 7,
				(Elements.size() >= Visible ? ((int) (1 / (float) Elements
						.size() * EntityHeight) * CurrentPosition + EntityWindowY)
						: EntityWindowY), EntityWidth * 1 / 7,
				(int) ((Elements.size() >= Visible ? (float) Visible
						/ (float) Elements.size() : 1) * (EntityHeight)),
				ScrollBarTex);
	}

	public void RenderTexture(int x, int y, int width, int height, Texture tex) {
		tex.bind();
		GL11.glBegin(GL11.GL_QUADS);

		GL11.glTexCoord2f(0, 0);
		GL11.glVertex3f(x, y, 0);

		GL11.glTexCoord2f(0, 1);
		GL11.glVertex3f(x, y + height, 0);

		GL11.glTexCoord2f(1, 1);
		GL11.glVertex3f(x + width, y + height, 0);

		GL11.glTexCoord2f(1, 0);
		GL11.glVertex3f(x + width, y, 0);

		GL11.glEnd();

	}

	void RenderText(int element, String str) {
		int StringX = EntityWindowX + (EntityWidth - font.getWidth(str)) / 2;
		int StringY = EntityWindowY + (EntityHeight / Visible) * element
				+ ((EntityHeight / Visible) - font.getHeight(str)) / 2;
		font.drawString(StringX, StringY, str, Color.black);
		Color.white.bind();
	}

	public void Sync(Project p) {
		Elements.clear();
		String[] sa = p.getElements();
		for (int i = 0; i < sa.length; i++) {
			Elements.add(new GUI_Object_Element(new RelativeDimensions(0, 0, 0, 0), null, sa[i]));
		}
		ProjectReference = p;
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
