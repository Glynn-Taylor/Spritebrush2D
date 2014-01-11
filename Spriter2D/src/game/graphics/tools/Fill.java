package game.graphics.tools;

import game.data.sprite.Colour;

public class Fill implements DrawMode {

	public Fill() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean Draw(Colour[][] ca, int x, int y, Colour c) {
		if (!Matches(c, ca[y][x]))
			FloodFill(ca, x, y, c, ca[y][x]);
		return false;
	}

	private void FloodFill(Colour[][] ca, int x, int y, Colour c,
			Colour comparisonC) {
		if (Matches(comparisonC, ca[y][x])) {
			ca[y][x] = c;
			if (y > 0)
				FloodFill(ca, x, y - 1, c, comparisonC);
			if (y < ca.length - 1)
				FloodFill(ca, x, y + 1, c, comparisonC);
			if (x < ca[0].length - 1)
				FloodFill(ca, x + 1, y, c, comparisonC);
			if (x > 0)
				FloodFill(ca, x - 1, y, c, comparisonC);

		}

	}

	boolean Matches(Colour c1, Colour c2) {
		if (c1.getR() == c2.getR() & c1.getG() == c2.getG()
				& c1.getB() == c2.getB())
			return true;

		return false;
	}

	@Override
	public void ClearInput(Colour[][] ca) {
		// TODO Auto-generated method stub

	}

}
