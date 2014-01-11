package game.data;

import game.util.EditorSprite2D;
import game.util.EditorSprite2D.Animation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class ProjectData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9024206605386115716L;
	private final String exportPath;
	private final String Name;
	private final ArrayList<SpriteData> Sprites;

	public ProjectData(String path, String name,
			ArrayList<EditorSprite2D> spriteList) {
		Sprites = new ArrayList<SpriteData>();
		exportPath = path;
		Name = name;
		for (EditorSprite2D es : spriteList) {
			Sprites.add(new SpriteData(es.getData()));
		}
	}

	private class SpriteData implements Serializable {
		// int CurrentAnimation;
		final Map<String, Integer> AnimationMap;
		final ArrayList<Animation> Anims;
		int CurrentAnim = 0;
		final Animation Playing;
		final String Name;

		public SpriteData(Object[] args) {

			AnimationMap = (Map<String, Integer>) args[0];
			Anims = (ArrayList<Animation>) args[1];
			CurrentAnim = (Integer) args[2];
			Playing = (Animation) args[3];
			Name = (String) args[4];
		}
		/*
		 * private class AnimationData { final Colour[][] AnimationTiles; final
		 * int Duration; final int StartY; final int Frames; final int
		 * CurrentFrame; final int Ticks; final int TileWidth; final int
		 * TileHeight;
		 * 
		 * public AnimationData(Colour[][] aTiles, int[] properties) {
		 * AnimationTiles = aTiles; Duration = properties[0]; StartY =
		 * properties[1]; Frames = properties[2]; CurrentFrame = properties[3];
		 * Ticks = properties[4]; TileWidth = properties[5]; TileHeight =
		 * properties[6]; } }
		 */
	}

	public ArrayList<EditorSprite2D> getSprites() {
		ArrayList<EditorSprite2D> spriteList = new ArrayList<EditorSprite2D>();
		for (SpriteData es : Sprites) {
			spriteList.add(new EditorSprite2D(new Object[] { es.AnimationMap,
					es.Anims, es.CurrentAnim, es.Playing, es.Name }));
		}
		return spriteList;
	}

	public String getPath() {
		// TODO Auto-generated method stub
		return exportPath;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return Name;
	}
}
