package game.graphics.tools;

import game.data.sprite.Colour;
import game.graphics.GUI_Renderer_ColorArray;

import org.lwjgl.input.Keyboard;

public class Line implements DrawMode {

	final float dl = (float) 3.125e-3;
	GUI_Renderer_ColorArray renderer;

	void DrawLine(Colour[][] ca, int x1, int y1, int x2, int y2, Colour c) {

		float l;
		int dx = x2 - x1;
		int dy = y2 - y1;
		// Can reflect in y=x by swapping
		for (l = (float) 0.0; l < 1.0; l += dl) {
			ca[y1 + (int) (Math.floor(dy * l + 0.5))][x1
					+ (int) (Math.floor(dx * l + 0.5))] = c;
		}
	}

	public Line(GUI_Renderer_ColorArray rend) {
		renderer = rend;
	}

	int xRef = -1, yRef = -1;

	@Override
	public boolean Draw(Colour[][] ca, int x, int y, Colour c) {
		// mDown = true;
		// ca[y][x] = c;
		if (xRef < 0 | yRef < 0) {
			xRef = x;
			yRef = y;
			drawCol = c;
		} else {
			EndXRef = x;
			EndYRef = y;
			renderer.ClearGhosting();

			if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
				xRef = -1;
				yRef = -1;
				EndXRef = -1;
				EndYRef = -1;

				return true;
			} else {
				DrawLine(renderer.getGhostTiles(), xRef, yRef, EndXRef,
						EndYRef, c);
			}

			// if()
			// DrawLine(ca, xRef, yRef, x, y, c);
			// xRef = -1;
			// yRef = -1;
		}
		return false;
	}

	Colour drawCol;
	int EndXRef = -1, EndYRef = -1;

	// boolean mDown = false;

	@Override
	public void ClearInput(Colour[][] ca) {
		if (xRef >= 0) {
			DrawLine(ca, xRef, yRef, EndXRef, EndYRef, drawCol);
			xRef = -1;
			yRef = -1;
			EndXRef = -1;
			EndYRef = -1;
			renderer.ClearGhosting();
		}
		// mDown = false;
		// xRef = -1;
		// yRef = -1;
	}

}
