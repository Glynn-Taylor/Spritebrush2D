package game.graphics;

import org.lwjgl.opengl.Display;

public class RelativeDimensions {
	private final float WidthRelative,HeightRelative;
	private float XRelative,YRelative;
	public RelativeDimensions(float XRelative,float YRelative,float WidthRelative,float HeightRelative){
		this.XRelative=XRelative;
		this.YRelative=YRelative;
		this.WidthRelative=WidthRelative;
		this.HeightRelative=HeightRelative;
		
	}
	public int GetWidth(int paneWidth){
		return (int) (paneWidth*WidthRelative);
	}
	public int GetHeight(int paneHeight){
		return (int) (paneHeight*HeightRelative);
	}
	public int GetX(int paneWidth){
		return (int) (paneWidth*XRelative);
	}
	public int GetY(int paneHeight){
		return (int) ((paneHeight)*YRelative);
	}
	public float GetRelativeW(){
		return (WidthRelative);
	}
	public float GetRelativeH(){
		return (HeightRelative);
	}
	public float GetRelativeX(){
		return (XRelative);
	}
	public float GetRelativeY(){
		return (YRelative);
	}
	public void AdditiveRX(float percentX) {
		XRelative+=percentX;
		
	}
	public void AdditiveRY(float percentY) {
		YRelative+=percentY;
		
	} 
	
}
