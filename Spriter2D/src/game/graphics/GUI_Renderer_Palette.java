package game.graphics;

import game.data.sprite.Colour;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

public class GUI_Renderer_Palette extends GUI_Object {
	int PWidth, PHeight;
	int TileWidth, TileHeight;
	Color[] Palette;
	GUI_Renderer_ColorArray Renderer;

	public GUI_Renderer_Palette(RelativeDimensions absoluteDimensions,
			GUI_Renderer_ColorArray c, int Colours, int Rows) {
		this.AbsoluteDimensions= absoluteDimensions; 
		
		Renderer = c;
		Palette = new Color[Colours];
		PHeight = Rows;
		PWidth = Colours / Rows;
		
		onResize();
		for(int gs = 0; gs < 10; gs++)
		{
			float gsc = (float)gs/9;
			Palette[gs] = new Color(gsc,gsc,gsc);
		}
		for(int i = 10; i < Palette.length-10; i++)
		{
			java.awt.Color jc = java.awt.Color.getHSBColor((float) (i-10) / (float) (Palette.length-20), 0.85f, 1.0f);
			Palette[i] = new Color(jc.getRGB());
		}
		setPalette(Palette.length-11,10,new Color(199,178,153),new Color(96,56,18));
		//Palette[0] = Color.black;
		//Palette[1] = Color.white;
		//Palette[2] = Color.blue;
		//Palette[3] = Color.green;
		//Palette[4] = Color.cyan;
		//Palette[5] = Color.magenta;
		//Palette[6] = Color.orange;
		//Palette[7] = Color.yellow;
	}
	public void setPalette(int start, int length, Color gradientStartColor,Color gradientEndColor){
         for (int i = 0; i < length; i++) {
                 float fraction = ((float) i) / (length - 1);
           
               
                         float r1 = gradientStartColor.getRed() / 255.0F;
                         float r2 = gradientEndColor.getRed() / 255.0F;
                         float r = Math.max(0,
                                         Math.min(1, r2 * fraction + r1 * (1 - fraction)));
                         float g1 = gradientStartColor.getGreen() / 255.0F;
                         float g2 = gradientEndColor.getGreen() / 255.0F;
                         float g = Math.max(0,
                                         Math.min(1, g2 * fraction + g1 * (1 - fraction)));
                         float b1 = gradientStartColor.getBlue() / 255.0F;
                         float b2 = gradientEndColor.getBlue() / 255.0F;
                         float b = Math.max(0,
                                         Math.min(1, b2 * fraction + b1 * (1 - fraction)));
                 
                 Palette[start+i] =  new Color(r, g, b);
         }
	}
	@Override
	public boolean ProcessInput(int mouseX, int mouseY, boolean mouseDown) {
		if (mouseDown || Mouse.isButtonDown(1)) {

			int x = mouseX - EntityWindowX;
			int y = (SCREEN_HEIGHT - mouseY) - EntityWindowY;

			if (x >= 0 & y >= 0) {
				// ADD DRAW MODE
				int xRef = (int) ((float) x / (float) TileWidth);
				int yRef = (int) ((float) y / (float) TileHeight);
				if (yRef < PHeight & xRef < PWidth)
					if (Palette[yRef * PWidth + xRef] != null) {
						Color c1 = Palette[yRef * PWidth + xRef];
						Colour c = new Colour(c1.r, c1.g, c1.b);
						if (mouseDown) {
							Renderer.setPrimary(c);
						} else {
							Renderer.setSecondary(c);
						}
						return true;
					} else {
						Palette[yRef * PWidth + xRef] = Renderer.getPrimary();
					}

			}

		}
		return false;

	}

	Color BackGroundC = new Color(201, 211, 226);

	@Override
	protected void RenderElements() {
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		Box(EntityWindowX - 5, EntityWindowY - 5, EntityWidth + 10,
				EntityHeight + 10, Color.black);
		Box(EntityWindowX, EntityWindowY, EntityWidth, EntityHeight,
				BackGroundC);
		for (int y = 0; y < PHeight; y++) {
			for (int x = 0; x < PWidth; x++) {
				if (Palette[y * PWidth + x] != null) {
					Box(EntityWindowX + TileWidth * x, EntityWindowY + TileHeight * y,
							TileWidth, TileHeight, Palette[y * PWidth + x]);
				}
			}
		}
		Color.white.bind();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
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
		EntityWindowX  += 5;
		EntityWindowY += 5;
		EntityWidth -= 10;
		EntityHeight -= 10;
		TileWidth = EntityWidth / PWidth;
		TileHeight = EntityHeight / PHeight;
	}

}
