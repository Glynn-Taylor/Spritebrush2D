package game.graphics;

import game.states.Game;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;

public class GUI_Status {
	private RelativeDimensions Dimensions = new RelativeDimensions(0.5F-0.15F, 0.5F-0.05F, 0.15F, 0.05F);
	private String Text="";
	long InitialDraw;
	long TimeSince;
	boolean Drawing=false;
	private final float DisplayTime=500F;
	
	public void PushText(String s){
		if(Text!=s){
			Text=s;
		}
		InitialDraw=System.currentTimeMillis();
		Drawing =true;
	}
	public void DrawText(Font font){
		if(Drawing){
			TimeSince=System.currentTimeMillis()-InitialDraw;
			if(TimeSince>DisplayTime){
				Drawing=false;
			}else{
				int x = Dimensions.GetX(Game.Width);
				int y = Dimensions.GetY(Game.Height);
				int w = Dimensions.GetWidth(Game.Width);
				int h = Dimensions.GetHeight(Game.Height);
				Color cw=new Color(1, 1, 1, 1-(TimeSince/DisplayTime));
				Color cb=new Color(0, 0, 0, 1-(TimeSince/DisplayTime));
				Box(x, y, w, h,
						cb);
				Box(x + 5, y + 5, w - 10,
						h - 10, cw);
				RenderText(x,y,w,h, font,cb);
				//font.drawString(x, y, Text, new Color(0, 0, 0, 1-(TimeSince/5000)));
				Color.white.bind();
				System.out.println("drawing");
			}
		}
	}
	void RenderText(int x,int y, int Width, int Height, Font font, Color c) {
		int StringX = x + (Width - font.getWidth(Text)) / 2;
		int StringY = y + (Height-font.getHeight(Text))/ 2;
		font.drawString(StringX, StringY, Text, c);
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
}
