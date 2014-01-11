package external;

import game.data.sprite.Colour;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;

import javax.imageio.ImageIO;

public class BufferedImageCreator {
	public static BufferedImage createImage(int[][] r, int[][] g, int[][] b,
			int width, int height, boolean isColor) {

		BufferedImage ret;
		if (isColor)
			ret = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		else
			ret = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
		WritableRaster raster = ret.getRaster();

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (isColor) {
					raster.setSample(x, y, 0, r[y][x]);
					raster.setSample(x, y, 1, g[y][x]);
					raster.setSample(x, y, 2, b[y][x]);
				} else
					raster.setSample(x, y, 0, r[y][x]);
			}
		}

		return ret;
	}

	public static BufferedImage createImage(Colour[][] imageArray, int Scalar,
			boolean isColor) {
		int width = imageArray[0].length * Scalar;
		int height = imageArray.length * Scalar;
		BufferedImage ret;
		if (isColor)
			ret = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		else
			ret = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
		WritableRaster raster = ret.getRaster();

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (isColor) {
					raster.setSample(x, y, 0, (int) (imageArray[(int) Math
							.floor(y / Scalar)][(int) Math.floor(x / Scalar)]
							.getR() * 255));
					raster.setSample(x, y, 1, (int) (imageArray[(int) Math
							.floor(y / Scalar)][(int) Math.floor(x / Scalar)]
							.getG() * 255));
					raster.setSample(x, y, 2, (int) (imageArray[(int) Math
							.floor(y / Scalar)][(int) Math.floor(x / Scalar)]
							.getB() * 255));
				} else
					raster.setSample(x, y, 0, (int) (imageArray[(int) Math
							.floor(y / Scalar)][(int) Math.floor(x / Scalar)]
							.getR() * 255));
			}
		}

		return ret;
	}

	public static void createPNG(Colour[][] imageArray, int Scalar,
			boolean isColor, String path) {
		BufferedImage bI = createImage(imageArray, Scalar, isColor);
		try {
			ImageIO.write(bI, "png", new File(path));
		} catch (Exception e) {

		}
	}

}
