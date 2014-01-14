package game.graphics;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

public abstract class GUI_Window extends GUI_Object_Graphical {
	
	protected void MovementControl(int element, int mouseX, int mouseY){
		if (Elements.get(element).InsideObject(mouseX, mouseY)) {
				
				int diffX = Mouse.getDX();
				int diffY = Mouse.getDY();
				float percentX = (float)diffX/(float)Display.getWidth();
				float percentY = (float)diffY/(float)Display.getHeight();
				AbsoluteDimensions.AdditiveRX(percentX);
				AbsoluteDimensions.AdditiveRY(-percentY);
				System.out.println("Moving window: "+Float.toString(percentX)+" "+Float.toString(percentY));
				for(int i=0;i<Elements.size();i++){
					Elements.get(i).MoveByPercent(percentX, -percentY);
				}
				onResize();
			
		}
	}
	@Override
	public boolean InsideObject(int mouseX, int mouseY) {
		if (mouseX > EntityWindowX && mouseX < EntityWindowX + EntityWidth) {
			if (SCREEN_HEIGHT - mouseY > EntityWindowY
					&& SCREEN_HEIGHT - mouseY < EntityWindowY + EntityHeight) {
				MouseInsideMe = true;

				return true;
				// EDIT ME
			}

		}
		MouseInsideMe = false;
		ProcessInput(mouseX, mouseY,false);
		
		return false;
		
	}
}
