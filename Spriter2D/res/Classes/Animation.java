package game.data.sprite;

import java.io.Serializable;

public class Animation implements Serializable {
	final int Duration;
	final int StartY;
	final int Frames;
	int CurrentFrame = 0;
	int Ticks = 0;
	int TileWidth;
	int TileHeight;

	public Animation(int tw, int th, int duration, int frames, int startY) {
		this.TileWidth = tw;
		this.TileHeight = th;
		this.Duration = duration;
		this.Frames = frames;
		this.StartY = startY;
	}

	public void Tick() {

		Ticks = (Ticks + 1) % Duration;
		CurrentFrame = (int) ((double) Ticks / (double) Duration * Frames);
	}

	public int getLX() {
		return TileWidth * CurrentFrame;

	}

	public int getTY() {
		return StartY;

	}

	public int getRX() {
		return TileWidth + TileWidth * CurrentFrame;

	}

	public int getBY() {
		return StartY + TileHeight;

	}
}
