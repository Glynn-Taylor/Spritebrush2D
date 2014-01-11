package game.graphics.tools;

import game.data.sprite.Colour;

public interface DrawMode {
	public abstract boolean Draw(Colour[][] ca, int x, int y, Colour c);

	public abstract void ClearInput(Colour[][] ca);
}
