package game.data.sprite;

import java.util.ArrayList;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Image;

public class Sprite2D {
	final Image SpriteSheet;
	protected Map<String, Integer> AnimationMap;
	protected ArrayList<Animation> Anims;
	protected int CurrentAnim = 0;
	protected Animation Playing;

	public Sprite2D(Image spriteS, SpriteData sd) {
		Anims = sd.getAnims();
		AnimationMap = sd.getAnimMap();
		SpriteSheet = spriteS;

		Playing = Anims.get(0);
	}

	public void Render(int x, int y, int w, int h) {
		Playing.Tick();
		SpriteSheet.bind();
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(
				((float) Playing.getLX() / (float) SpriteSheet.getWidth() * SpriteSheet
						.getTextureWidth()),
				((float) Playing.getTY() / (float) SpriteSheet.getHeight())
						* SpriteSheet.getTextureHeight());
		GL11.glVertex3f(x, y, 0);

		GL11.glTexCoord2f(
				((float) Playing.getLX() / (float) SpriteSheet.getWidth() * SpriteSheet
						.getTextureWidth()),
				((float) Playing.getBY() / (float) SpriteSheet.getHeight())
						* SpriteSheet.getTextureHeight());
		GL11.glVertex3f(x, y + h, 0);

		GL11.glTexCoord2f(
				((float) Playing.getRX() / (float) SpriteSheet.getWidth() * SpriteSheet
						.getTextureWidth()),
				((float) Playing.getBY() / (float) SpriteSheet.getHeight())
						* SpriteSheet.getTextureHeight());
		GL11.glVertex3f(x + w, y + h, 0);

		GL11.glTexCoord2f(
				((float) Playing.getRX() / (float) SpriteSheet.getWidth() * SpriteSheet
						.getTextureWidth()),
				((float) Playing.getTY() / (float) SpriteSheet.getHeight())
						* SpriteSheet.getTextureHeight());
		GL11.glVertex3f(x + w, y, 0);
		GL11.glEnd();
	}

	public void setAnimation(String animName) {
		try {
			CurrentAnim = AnimationMap.get(animName);
			Playing = Anims.get(CurrentAnim);
		} catch (Exception e) {
			System.out.println("ERROR: Not a valid animation name!");
		}
	}

}
