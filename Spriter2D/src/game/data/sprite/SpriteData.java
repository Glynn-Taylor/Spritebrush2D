package game.data.sprite;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class SpriteData implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -564854538796822478L;
	private final Colour[][] SpriteSheet;
	protected Map<String, Integer> AnimationMap;
	private final ArrayList<Animation> Anims;
	protected int CurrentAnim = 0;
	protected Animation Playing;

	public SpriteData(Colour[][] spriteSheet, Object data, Object data2) {
		SpriteSheet = spriteSheet;
		AnimationMap = (Map<String, Integer>) data;
		Anims = (ArrayList<Animation>) data2;
		Playing = getAnims().get(0);
	}

	public Map<String, Integer> getAnimMap() {
		return AnimationMap;

	}

	/**
	 * @return the anims
	 */
	public ArrayList<Animation> getAnims() {
		return Anims;
	}

	/**
	 * @return the spriteSheet
	 */
	public Colour[][] getSpriteSheet() {
		return SpriteSheet;
	}

}
