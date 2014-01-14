package game.graphics;

import game.states.Game;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.opengl.Texture;

public class GUI_Object_Element extends GUI_Object_Graphical {

	
	public boolean Clicked = false;
	private String Name = "";
	public GUI_Object_Element(Texture t, String name){
		AbsoluteDimensions = new RelativeDimensions(0,0,0,0);
		texture = t;
		Name = name;
	}
	public GUI_Object_Element(RelativeDimensions absoluteDimensions,
			Texture t, String name) {

		this.AbsoluteDimensions= absoluteDimensions; 
		onResize();
		texture = t;
		Name = name;
	}

	public void Render() {
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

	public void Render(int x, int y, int width, int height) {
		if (texture != null) {
			texture.bind();
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
		} else {
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			Box(x, y, width, height, Color.black);
			Box(x + 5, y + 5, width - 10, height - 10, Color.white);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
		}

	}

	public void InsideElement(int mouseX, int mouseY) {
		if (mouseX > EntityWindowX && mouseX < EntityWindowX + EntityWidth) {
			if (SCREEN_HEIGHT - mouseY > EntityWindowY
					&& SCREEN_HEIGHT - mouseY < EntityWindowY + EntityHeight) {
				// MouseInsideMe = true;
				Clicked = true;
			}

		}
		// MouseInsideMe = false;
		// return false;
	}
	public void MoveByPercent(float x, float y){
		AbsoluteDimensions.AdditiveRX(x);
		AbsoluteDimensions.AdditiveRY(y);
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return Name;
	}

	public void setName(String n) {
		Name = n;
	}

	public void RenderName(Font font, Color c) {
		RenderText(Name, EntityWindowX, EntityWindowY, EntityWidth, EntityHeight, font, c);
	}
	@Override
	public boolean ProcessInput(int mouseX, int mouseY, boolean mouseDown) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void RenderElements() {
		// TODO Auto-generated method stub
		
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
