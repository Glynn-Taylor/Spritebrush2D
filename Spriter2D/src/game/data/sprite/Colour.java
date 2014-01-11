/*******************************************************************************
 * Copyright (c) 2013 Glynn Taylor.
 * All rights reserved. This program and the accompanying materials, 
 * (excluding imported libraries, such as LWJGL and Slick2D)
 * are made available under the terms of the GNU Public License
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *     Glynn Taylor - initial API and implementation
 ******************************************************************************/
package game.data.sprite;

import java.io.Serializable;

import org.newdawn.slick.Color;

public class Colour implements Serializable {
	private final float R, G, B, A;

	public Colour(float r, float g, float b) {
		R = r;
		G = g;
		B = b;
		A=1;
	}
	public Colour(float r, float g, float b, float a) {
		R = r;
		G = g;
		B = b;
		A=a;
	}

	/**
	 * @return the r
	 */
	public float getR() {
		return R;
	}

	/**
	 * @return the g
	 */
	public float getG() {
		return G;
	}

	/**
	 * @return the b
	 */
	public float getB() {
		return B;
	}
	/**
	 * @return the a
	 */
	public float getA() {
		return A;
	}

	public Color getColor() {
		return new Color(R, G, B);
	}
	public Color getColorRGBA() {
		return new Color(R, G, B,A);
	}
}
