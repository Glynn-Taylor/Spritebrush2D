package game.graphics;

import game.data.sprite.Colour;
import game.graphics.dialog.Dialog_New_Sprite;
import game.graphics.tools.DrawMode;
import game.graphics.tools.Pencil;
import game.util.EditorSprite2D;
import game.util.ToolBar;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

public class GUI_Renderer_ColorArray extends GUI_Object {
	Colour primary = new Colour(0, 0, 0);
	Colour secondary = new Colour(1, 1, 1);
	Colour[][] Tiles;
	Colour[][] GhostTiles;
	Colour[][] LocalClipboard=null;
	EditorSprite2D CurrentSprite;
	boolean IsPlayingAnim = false;
	int TileWidth;
	int TileHeight;
	int TileStartX, TileStartY;
	float Zoom = 1;
	DrawMode Tool = new Pencil();
	ToolBar t = new ToolBar(this);
	GUI_Text_Field FrameCounter;
	GUI_List_DropDown_ANIMATIONS AnimationList;

	public GUI_Renderer_ColorArray(RelativeDimensions absoluteDimensions, Colour[][] c, GUI_Text_Field Counter,
			GUI_List_DropDown_ANIMATIONS AnimList) {
		this.AbsoluteDimensions= absoluteDimensions;
		Tiles = c;
		GhostTiles = new Colour[Tiles.length][Tiles[0].length];
		
		FrameCounter = Counter;
		AnimationList = AnimList;
		onResize();
		
		// Center();
	}

	public void Center() {
		int CurrentSpriteWidth = CurrentSprite.getCurrentWidth();
		int ZoomedTotalWidth = (int) (TileWidth * Zoom) * CurrentSpriteWidth;
		int ZoomedTotalHeight = (int) (TileHeight * Zoom * Tiles.length);
		TileStartY = EntityHeight / 2 - ZoomedTotalHeight / 2;
		TileStartX = EntityWidth / 2 - ZoomedTotalWidth / 2;
		// TileStartX = 0;
		// TileStartY = 0;
	}

	@Override
	public boolean ProcessInput(int mouseX, int mouseY, boolean mouseDown) {
		if (CurrentSprite != null) {
			if (mouseDown || Mouse.isButtonDown(1)) {
				int ZoomedWidth = (int) (TileWidth * Zoom);
				int ZoomedHeight = (int) (TileHeight * Zoom);
				int x = mouseX - EntityWindowX;
				int y = (SCREEN_HEIGHT - mouseY) - EntityWindowY;
				x = x - TileStartX;
				y = y - TileStartY;
				if (x >= 0 & y >= 0) {
					// ADD DRAW MODE
					int CurrentSpriteFrame = CurrentSprite.getCurrentFrame();
					int CurrentSpriteWidth = CurrentSprite.getCurrentWidth();
					int xRef = (int) ((float) x / (float) ZoomedWidth)
							+ CurrentSpriteFrame * CurrentSpriteWidth;
					int yRef = (int) ((float) y / (float) ZoomedHeight);
					if (yRef < Tiles.length & xRef < Tiles[0].length) {
						// Tiles[yRef][xRef] = (Mouse.isButtonDown(0) ? primary
						// : secondary);
						if (Tool.Draw(Tiles, xRef, yRef,
								(Mouse.isButtonDown(0) ? primary : secondary)))
							return true;
					}

				}
			} else {
				Tool.ClearInput(Tiles);
			}
			if (MouseInsideMe) {
				if (Mouse.isButtonDown(2)) {
					addX(Mouse.getDX());
					addY(-Mouse.getDY());
				}
			}
			if (IsPlayingAnim) {

				CurrentSprite.Tick();
			}
		}

		return false;

	}

	@Override
	protected void RenderElements() {
		
		if (CurrentSprite != null) {
			int CurrentSpriteFrame = CurrentSprite.getCurrentFrame();
			int CurrentSpriteWidth = CurrentSprite.getCurrentWidth();
			int ZoomedWidth = (int) (TileWidth * Zoom);
			int ZoomedHeight = (int) (TileHeight * Zoom);
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			for (int y = 0; y < Tiles.length; y++) {
				for (int x = CurrentSpriteWidth * CurrentSpriteFrame; x < CurrentSpriteWidth
						+ CurrentSpriteWidth * CurrentSpriteFrame; x++) {
					Box(EntityWindowX
							+ TileStartX
							+ ((x - CurrentSpriteWidth * CurrentSpriteFrame) * ZoomedWidth),
							EntityWindowY + TileStartY + (y * ZoomedHeight),
							ZoomedWidth, ZoomedHeight, Tiles[y][x].getColor());
					if (GhostTiles[y][x] != null) {
						Box(EntityWindowX
								+ TileStartX
								+ ((x - CurrentSpriteWidth * CurrentSpriteFrame) * ZoomedWidth),
								EntityWindowY + TileStartY + (y * ZoomedHeight),
								ZoomedWidth, ZoomedHeight,
								GhostTiles[y][x].getColor());
					}
				}
			}
			Color.white.bind();
			GL11.glEnable(GL11.GL_TEXTURE_2D);
		}
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

	public void addX(int x) {
		TileStartX += x;
	}

	public void addY(int y) {
		TileStartY += y;
	}

	public void addZoom(float z) {

		Zoom += z;

	}

	public void addFrame(int i) {
		if (CurrentSprite != null)
			CurrentSprite.ChangeFrame(i);

	}

	public void setPrimary(Colour c) {
		primary = c;
	}

	public Color getPrimary() {
		return primary.getColor();
	}

	public Colour getPrimaryC() {
		return primary;
	}

	public void setPlaying(boolean playing) {
		IsPlayingAnim = playing;
	}

	public Colour getSecondaryC() {
		return secondary;
	}

	public void setSecondary(Colour c) {
		secondary = c;
	}

	public Color getSecondary() {
		return secondary.getColor();
	}

	public void setDrawMode(DrawMode d) {
		Tool = d;
	}

	public Colour[][] getGhostTiles() {
		return GhostTiles;
	}

	public void ClearGhosting() {
		for (int y = 0; y < Tiles.length; y++) {
			for (int x = 0; x < Tiles[0].length; x++) {
				GhostTiles[y][x] = null;
			}
		}
	}

	public void NewDialog(boolean newSprite) {

		Dialog_New_Sprite dialog = new Dialog_New_Sprite(this, newSprite);
	}

	public void CreateSprite(int width, int height, String name, int frames,
			int duration, String SpriteName) {
		
		CurrentSprite = new EditorSprite2D(width, height, name, frames,
				duration, FrameCounter, AnimationList, SpriteName);
		RecreateTiles();

	}

	public void RecreateTiles() {
		// System.out.println("Recreating tiles..");
		Tiles = CurrentSprite.getCurrentAnim();
		GhostTiles = new Colour[Tiles.length][Tiles[0].length];
		int CurrentSpriteWidth = CurrentSprite.getCurrentWidth();
		// int Frames = CurrentSprite.getTotalFrames();
		TileWidth = EntityWidth / CurrentSpriteWidth;
		TileHeight = EntityHeight / Tiles.length;
		TileHeight = (TileWidth < TileHeight ? TileWidth : TileHeight);
		TileWidth = (TileWidth > TileHeight ? TileHeight : TileWidth);
		Center();
	}

	private boolean IsNumber(String string) {
		try {
			Integer.parseInt(string);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public String getFrameAsString() {
		return Integer.toString(CurrentSprite.getCurrentFrame());
	}

	public void CreateAnimation(int w, int h, String name, int frames,
			int duration) {
		CurrentSprite.addAnimation(h, w, name, frames, duration);
		RecreateTiles();
	}

	public boolean hasSprite() {
		// TODO Auto-generated method stub
		return CurrentSprite != null;
	}

	public EditorSprite2D getEditorSprite() {
		return CurrentSprite;
	}

	public void setSprite(EditorSprite2D sprite) {
		// System.out.println("Setting sprite..");
		CurrentSprite = sprite;
		CurrentSprite.SyncAnimList();

		RecreateTiles();
	}

	public void SyncWithProjectSprites(ArrayList<EditorSprite2D> sprites) {
		for (EditorSprite2D es : sprites) {
			if (FrameCounter == null)
				System.out.println("Null counter");
			es.setUIElements(FrameCounter, AnimationList);
		}
		setSprite(sprites.get(0));
	}
	public void CopyFrame(){
		if (CurrentSprite != null){
			LocalClipboard=new Colour[Tiles.length][CurrentSprite.getCurrentWidth()];
			int CurrentSpriteFrame = CurrentSprite.getCurrentFrame();
			int CurrentSpriteWidth = CurrentSprite.getCurrentWidth();
			for (int y = 0; y < Tiles.length; y++) {
				for (int x = CurrentSpriteWidth * CurrentSpriteFrame; x < CurrentSpriteWidth
						+ CurrentSpriteWidth * CurrentSpriteFrame; x++) {
					LocalClipboard[y][x-CurrentSpriteWidth * CurrentSpriteFrame] = new Colour(Tiles[y][x].getR(),Tiles[y][x].getG(),Tiles[y][x].getB());
				}
			}
		}
	
	}
	public void PasteFrame(){
		if (CurrentSprite != null&&LocalClipboard!=null){
			int CurrentSpriteFrame = CurrentSprite.getCurrentFrame();
			int CurrentSpriteWidth = CurrentSprite.getCurrentWidth();
			for (int y = 0; y < Tiles.length; y++) {
				for (int x = CurrentSpriteWidth * CurrentSpriteFrame; x < CurrentSpriteWidth
						+ CurrentSpriteWidth * CurrentSpriteFrame; x++) {
					Tiles[y][x]= LocalClipboard[y][x-CurrentSpriteWidth * CurrentSpriteFrame];
				}
			}
		}
	
	}
	public void PasteBufferedImageWhole(BufferedImage img){
		try{
			PasteToSpriteSheet(0, img.getWidth(), 0, img.getHeight(),img);
		}catch(Exception e){
			
		}
	}
	public void PasteClipboard() {
		int CurrentSpriteFrame = CurrentSprite.getCurrentFrame();
		int CurrentSpriteWidth = CurrentSprite.getCurrentWidth();
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		try{
			PasteToSpriteSheet(CurrentSpriteWidth * CurrentSpriteFrame, CurrentSpriteWidth, 0, Tiles.length, (BufferedImage) clipboard
					.getData(DataFlavor.imageFlavor));
		}catch(Exception e){
			
		}
	}
	
	private void PasteToSpriteSheet(int pixXstart, int pixXlength, int pixYstart, int pixYLength, BufferedImage image){
		
		if (CurrentSprite != null)
			try {
				int rgb;

				for (int y = pixYstart; y < pixYLength; y++) {
					for (int x = pixXstart; x < pixXstart+pixXlength; x++) {
						if (y < image.getHeight()
								& (x) < image
										.getWidth()) {
							rgb = image.getRGB((x), y);
							if(x<Tiles[0].length&&y<Tiles.length)
							Tiles[y][x] = new Colour(
									((rgb >> 16) & 0x000000FF) / 255f,
									((rgb >> 8) & 0x000000FF) / 255f,
									(rgb & 0x000000FF) / 255f);
						} 
					}
				}
			} catch (Exception e) {

			}
	}
	@Override
	public void OnEndActivation() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void OnResizeEntity() {
		TileWidth = EntityWidth / Tiles[0].length;
		TileHeight = EntityHeight / Tiles.length;
		TileHeight = (TileWidth < TileHeight ? TileWidth : TileHeight);
		TileWidth = (TileWidth > TileHeight ? TileHeight : TileWidth);
		
	}
}
