package game.util;

import game.data.sprite.Colour;
import game.graphics.GUI_List_DropDown_ANIMATIONS;
import game.graphics.GUI_Text_Field;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EditorSprite2D implements Serializable {
	// Colour[][] SpriteSheet;
	Map<String, Integer> AnimationMap;
	ArrayList<Animation> Anims;
	int CurrentAnim = 0;
	Animation Playing;
	transient GUI_Text_Field FrameCounter;
	transient GUI_List_DropDown_ANIMATIONS AnimationList;
	final String Name;

	public EditorSprite2D(int firstTileWidth, int firstTileHeight, String name,
			int frames, int duration, GUI_Text_Field Counter,
			GUI_List_DropDown_ANIMATIONS AnimList, String n) {
		Name = n;
		Anims = new ArrayList<Animation>();
		AnimationMap = new HashMap<String, Integer>();
		// Anims.add(new Animation(firstTileWidth, firstTileHeight, duration,
		// frames, 0));
		// SpriteSheet = spriteS;

		// AnimationMap.put(name, 0);
		FrameCounter = Counter;
		AnimationList = AnimList;
		addAnimation(firstTileHeight, firstTileWidth, name, frames, duration);
		Playing = Anims.get(0);
		AnimList.setSprite(this);
	}

	public EditorSprite2D(Object[] data) {
		AnimationMap = (Map<String, Integer>) data[0];
		Anims = (ArrayList<Animation>) data[1];
		CurrentAnim = (Integer) data[2];
		Playing = (Animation) data[3];
		Name = (String) data[4];
	}

	public void addAnimation(int firstTileHeight, int firstTileWidth,
			String name, int frames, int duration) {

		Anims.add(new Animation(firstTileWidth, firstTileHeight, duration,
				frames, getNextStartY()));
		AnimationMap.put(name, Anims.size() - 1);
		// AnimationList.addElement(new GUI_Object_Element(0, 0, 0, 0, null,
		// name));
		AnimationList.Sync((HashMap<String, Integer>) AnimationMap);
		setAnimation(name);

	}

	public void SyncAnimList() {
		AnimationList.setSprite(this);
		AnimationList.Sync((HashMap<String, Integer>) AnimationMap);
	}

	private int getNextStartY() {
		int y = 0;
		for (int i = 0; i < Anims.size(); i++) {
			y += Anims.get(i).TileHeight;
		}
		return y;
	}

	public Colour[][] getCurrentAnim() {
		return Playing.AnimationTiles;

	}

	public void setFrame(int i) {

	}

	public void setAnimation(String animName) {
		try {
			CurrentAnim = AnimationMap.get(animName);
			Playing = Anims.get(CurrentAnim);
			AnimationList.setHeaderName(animName);
		} catch (Exception e) {
			System.out.println("ERROR: Not a valid animation name!: "
					+ animName);
			for (String s : AnimationMap.keySet()) {
				System.out.println("Possible match: " + s);
			}
		}
	}

	public void Tick() {
		Playing.Tick();
	}

	public class Animation implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 210398157561008120L;
		Colour[][] AnimationTiles;
		final int Duration;
		final int StartY;
		final int Frames;
		int CurrentFrame = 0;
		int Ticks = 0;
		int TileWidth;
		int TileHeight;

		public Colour[][] getTiles() {
			return AnimationTiles;
		}

		public Animation(int tw, int th, int duration, int frames, int startY) {
			this.TileWidth = tw;
			this.TileHeight = th;
			this.Duration = duration;
			this.Frames = frames;
			this.StartY = startY;
			AnimationTiles = new Colour[TileHeight][TileWidth * Frames];
			for (int y = 0; y < AnimationTiles.length; y++) {
				for (int x = 0; x < AnimationTiles[0].length; x++) {
					AnimationTiles[y][x] = new Colour(1, 1, 1);
				}
			}
		}

		public void Tick() {

			Ticks = (Ticks + 1) % Duration;
			CurrentFrame = (int) ((double) Ticks / (double) Duration * Frames);
			if (FrameCounter != null)
				FrameCounter.setText(Integer.toString(CurrentFrame));
		}

		public int getLX() {
			return TileWidth * CurrentFrame;

		}

		public int getTY() {
			return StartY;

		}

		public int getRX() {
			return TileWidth + TileWidth * CurrentFrame;

		}

		public int getBY() {
			return StartY + TileWidth;

		}

		public void ChangeFrame(int i) {
			CurrentFrame = (CurrentFrame + i) % Frames;
			CurrentFrame = (CurrentFrame < 0 ? 0 : CurrentFrame);
		}
	}

	public int getCurrentFrame() {

		return Playing.CurrentFrame;
	}

	public int getCurrentWidth() {

		return Playing.TileWidth;
	}
	public int getCurrentHeight() {

		return Playing.TileHeight;
	}

	public void ChangeFrame(int i) {
		Playing.ChangeFrame(i);

	}

	public int getTotalFrames() {
		return Playing.Frames;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return Name;
	}

	public String getCurrentAnimName() {
		// TODO Auto-generated method stub
		// return AnimationMap.
		for (Map.Entry<String, Integer> e : AnimationMap.entrySet()) {

			Integer value = e.getValue();
			if (value == CurrentAnim) {
				return e.getKey();
			}
		}
		return "No animation";
	}

	public Colour[][][] getCurrentAnimSlides() {
		Colour[][][] rca = new Colour[Playing.Frames][Playing.TileHeight][Playing.TileWidth];
		for (int f = 0; f < Playing.Frames; f++) {
			for (int y = 0; y < Playing.TileHeight; y++) {
				for (int x = 0; x < Playing.TileWidth; x++) {
					rca[f][y][x] = Playing.AnimationTiles[y][x
							+ Playing.TileWidth * f];
				}
			}

		}
		return rca;
	}

	public Object[] getData() {
		// TODO Auto-generated method stub
		return new Object[] { AnimationMap, Anims, CurrentAnim, Playing, Name };
	}

	public void setUIElements(GUI_Text_Field tf, GUI_List_DropDown_ANIMATIONS da) {
		FrameCounter = tf;
		AnimationList = da;

	}

	public Colour[][] getSpriteSlides() {
		int height = 0;
		int maxWidth = 0;
		for (Animation a : Anims) {
			height += a.TileHeight;
			maxWidth = maxWidth < a.TileWidth * a.Frames ? a.TileWidth
					* a.Frames : maxWidth;
			System.out.println("anim added");
		}
		Colour[][] rca = new Colour[height][maxWidth];
		int currentY = 0;
		for (Animation a : Anims) {
			Colour[][] ca = a.getTiles();
			for (int y = 0; y < ca.length; y++) {
				for (int x = 0; x < ca[0].length; x++) {
					rca[y + currentY][x] = ca[y][x];
				}
			}
			currentY += a.TileHeight;
		}
		for (int y = 0; y < rca.length; y++) {
			for (int x = 0; x < rca[0].length; x++) {
				if (rca[y][x] == null)
					rca[y][x] = new Colour(1, 1, 1);
			}
		}
		return rca;
	}

	public Object[] getExportData() {
		Object[] rdata = new Object[2];
		rdata[0] = AnimationMap;
		ArrayList<game.data.sprite.Animation> convertedAnims = new ArrayList<game.data.sprite.Animation>();
		for (Animation a : Anims) {
			convertedAnims.add(new game.data.sprite.Animation(a.TileWidth,
					a.TileHeight, a.Duration, a.Frames, a.StartY));
		}
		rdata[1] = convertedAnims;
		return rdata;
	}

}
