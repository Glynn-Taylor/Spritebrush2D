package game.graphics;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;
import org.newdawn.slick.UnicodeFont;

public class GUI_List_DropDown extends GUI_Object {
	GUI_Object_Element VisibleElement;
	int ElementCount;
	boolean OptionsVisible = false;
	UnicodeFont font;

	// GUI_Object_Element[] VisibleElement;

	public GUI_List_DropDown(int startX, int startY, int SingleButtonwidth,
			int SingleButtonheight, GUI_Object_Element visibleElement,
			GUI_Object_Element[] choices, UnicodeFont fnt) {
		
		EntityWindowX = startX;
		EntityWindowY = startY;
		EntityWidth = SingleButtonwidth;
		ElementCount = choices.length + 1;
		EntityHeight = SingleButtonheight * ElementCount;
		AbsoluteDimensions=new RelativeDimensions((float)EntityWindowX/(float)Display.getWidth(), (float)EntityWindowY/(float)Display.getHeight(), (float)EntityWidth/(float)Display.getWidth(), (float)EntityHeight/(float)Display.getHeight());
		for (int i = 0; i < choices.length; i++) {
			Elements.add(choices[i]);
		}
		font = fnt;
		VisibleElement = visibleElement;
	}

	@Override
	public boolean ProcessInput(int mouseX, int mouseY, boolean mouseDown) {
		if (mouseDown) {
			// int x = mouseX - ScreenX;
			int y = (SCREEN_HEIGHT - mouseY) - EntityWindowY;
			if (y <= EntityHeight / ElementCount) {
				OptionsVisible = !OptionsVisible;
				return true;
			} else if (OptionsVisible) {
				Elements.get((int) (((float) (y - EntityHeight
						/ ElementCount) / (float) (EntityHeight - EntityHeight
						/ ElementCount)) * (ElementCount - 1))).Clicked = true;
				return true;
			}
		}
		return false;
	}

	@Override
	protected void RenderElements() {
		VisibleElement.Render(EntityWindowX, EntityWindowY, EntityWidth,
				EntityHeight / ElementCount);
		RenderText(0, VisibleElement.getName());
		if (OptionsVisible) {
			for (int i = 0; i < Elements.size(); i++) {
				Elements.get(i)
						.Render(EntityWindowX,
								EntityWindowY
										+ ((i + 1) * EntityHeight / ElementCount),
								EntityWidth,
								EntityHeight / ElementCount);
				RenderText(i + 1, Elements.get(i).getName());
			}
		}
		if (!MouseInsideMe)
			OptionsVisible = false;
	}

	@Override
	public void addElement(GUI_Object_Element element) {
		Elements.add(element);
		EntityHeight = EntityHeight + EntityHeight
				/ ElementCount;
		ElementCount = ElementCount + 1;
		ReleaseClicks();
	}

	void RenderText(int element, String str) {
		int StringX = EntityWindowX + (EntityWidth - font.getWidth(str)) / 2;
		int StringY = EntityWindowY + (EntityHeight / ElementCount) * element
				+ ((EntityHeight / ElementCount) - font.getHeight(str))
				/ 2;
		font.drawString(StringX, StringY, str, Color.black);
		Color.white.bind();
	}

	@Override
	public void OnEndActivation() {
		OptionsVisible=false;
		
	}

	@Override
	protected void OnResizeEntity() {
		// TODO Auto-generated method stub
		
	}
}
