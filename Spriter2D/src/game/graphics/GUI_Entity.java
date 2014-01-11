package game.graphics;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.opengl.Texture;

import game.states.Game;

public abstract class GUI_Entity {
	protected static int SCREEN_HEIGHT = Game.Height;
	protected int EntityWindowX, EntityWindowY, EntityWidth, EntityHeight;
	protected Texture texture;
	protected boolean Enabled = true;
	protected boolean MouseInsideMe;
	
	protected RelativeDimensions AbsoluteDimensions;
	public static void UpdateHeight(){
		SCREEN_HEIGHT=Game.Height;
	}
	public void onResize(){
		EntityWindowX = AbsoluteDimensions.GetX(Game.Width);
		EntityWindowY = AbsoluteDimensions.GetY(Game.Height);
		EntityWidth = AbsoluteDimensions.GetWidth(Game.Width);
		EntityHeight = AbsoluteDimensions.GetHeight(Game.Height);

		//System.out.println("Display x:y "+Integer.toString(Display.getWidth())+":"+Integer.toString(Display.getHeight()));
		OnResizeEntity();
	}
	protected abstract void OnResizeEntity();
	
}
