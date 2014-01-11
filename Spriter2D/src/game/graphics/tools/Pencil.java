package game.graphics.tools;

import game.data.sprite.Colour;

public class Pencil implements DrawMode {

	public Pencil() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean Draw(Colour[][] ca, int x, int y, Colour c) {
		ca[y][x] = c;
		return false;

	}

	@Override
	public void ClearInput(Colour[][] ca) {
		// TODO Auto-generated method stub

	}

}
