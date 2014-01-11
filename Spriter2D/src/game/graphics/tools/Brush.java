package game.graphics.tools;

import game.data.sprite.Colour;

public class Brush implements DrawMode {
	final float dl = (float) 3.125e-3;

	void line(Colour[][] ca, int x1, int y1, int x2, int y2, Colour c) {

		float l;
		int dx = x2 - x1;
		int dy = y2 - y1;
		// Can reflect in y=x by swapping
		for (l = (float) 0.0; l < 1.0; l += dl) {
			ca[y1 + (int) (Math.floor(dy * l + 0.5))][x1
					+ (int) (Math.floor(dx * l + 0.5))] = c;
		}
	}

	public Brush() {
		// TODO Auto-generated constructor stub
	}

	int xRef = -1, yRef = -1;

	@Override
	public boolean Draw(Colour[][] ca, int x, int y, Colour c) {
		ca[y][x] = c;
		if (xRef < 0 | yRef < 0) {
			xRef = x;
			yRef = y;
		} else {
			line(ca, xRef, yRef, x, y, c);
			xRef = x;
			yRef = y;
		}
		return false;
	}

	@Override
	public void ClearInput(Colour[][] ca) {
		xRef = -1;
		yRef = -1;
	}

}
