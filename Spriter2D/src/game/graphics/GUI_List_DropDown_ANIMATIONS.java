package game.graphics;

import game.util.EditorSprite2D;

import java.util.HashMap;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;
import org.newdawn.slick.UnicodeFont;

public class GUI_List_DropDown_ANIMATIONS extends GUI_Object {
	GUI_Object_Element VisibleElement;
	int ElementCount;
	boolean OptionsVisible = false;
	UnicodeFont font;
	EditorSprite2D EditorSprite;

	// GUI_Object_Element[] VisibleElement;

	public GUI_List_DropDown_ANIMATIONS(int startX, int startY,
			int SingleButtonwidth, int SingleButtonheight,
			GUI_Object_Element visibleElement, GUI_Object_Element[] choices,
			UnicodeFont fnt) {
		AbsoluteDimensions=new RelativeDimensions(0, 0, 0, 0);
		EntityWindowX = startX;

		// System.out.println(Integer.toString(ScreenY));
		EntityWidth = SingleButtonwidth;
		ElementCount = choices.length + 1;
		EntityWindowY = startY - SingleButtonheight * ElementCount
				+ SingleButtonheight;
		EntityHeight = SingleButtonheight * ElementCount;
		AbsoluteDimensions=new RelativeDimensions((float)EntityWindowX/(float)Display.getWidth(), (float)EntityWindowY/(float)Display.getHeight(), (float)EntityWidth/(float)Display.getWidth(), (float)EntityHeight/(float)Display.getHeight());
		for (int i = 0; i < choices.length; i++) {
			Elements.add(choices[i]);
		}
		font = fnt;
		VisibleElement = visibleElement;
	}

	public void setHeaderName(String s) {
		VisibleElement.setName(s);
	}

	@Override
	public boolean ProcessInput(int mouseX, int mouseY, boolean mouseDown) {
		if (mouseDown) {
			// OptionsVisible = !OptionsVisible;
			// int x = mouseX - ScreenX;
			int y = (SCREEN_HEIGHT - mouseY) - EntityWindowY;
			y = EntityHeight - y;
			if (y <= EntityHeight / ElementCount) {
				OptionsVisible = !OptionsVisible;
				return true;
			} else if (OptionsVisible) {

				Elements.get((int) (((float) (y - EntityHeight
						/ ElementCount) / (float) (EntityHeight - EntityHeight
						/ ElementCount)) * (ElementCount - 1))).Clicked = true;

				if (EditorSprite != null)
					EditorSprite
							.setAnimation(Elements
									.get((int) (((float) (y - EntityHeight
											/ ElementCount) / (float) (EntityHeight - EntityHeight
											/ ElementCount)) * (ElementCount - 1)))
									.getName());
				return true;
			}
		}
		return false;
	}

	@Override
	protected void RenderElements() {
		VisibleElement.Render(EntityWindowX, EntityWindowY + EntityHeight
				* (ElementCount - 1) / ElementCount, EntityWidth,
				EntityHeight / ElementCount);
		RenderText(0, VisibleElement.getName());
		if (OptionsVisible) {
			for (int i = 0; i < Elements.size(); i++) {
				Elements.get(i)
						.Render(EntityWindowX,
								(EntityWindowY + EntityHeight)
										- ((i + 2) * EntityHeight / ElementCount),
								EntityWidth,
								EntityHeight / ElementCount);
				RenderText(i + 1, Elements.get(i).getName());
			}
		}
		if (!MouseInsideMe)
			OptionsVisible = false;
	}

	public void setSprite(EditorSprite2D sprite) {
		EditorSprite = sprite;
		setHeaderName(sprite.getCurrentAnimName());
		// setHeaderName(sprite.getName())
	}

	@Override
	public void addElement(GUI_Object_Element element) {
		Elements.add(element);
		EntityWindowY -= EntityHeight / ElementCount;
		EntityHeight = EntityHeight + EntityHeight
				/ ElementCount;
		ElementCount = ElementCount + 1;

		ReleaseClicks();
	}

	void RenderText(int element, String str) {
		int StringX = EntityWindowX + (EntityWidth - font.getWidth(str)) / 2;
		int StringY = EntityWindowY + EntityHeight
				- (EntityHeight / ElementCount) * (element)
				- ((EntityHeight / ElementCount) + font.getHeight(str))
				/ 2;
		font.drawString(StringX, StringY, str, Color.black);
		Color.white.bind();
	}

	public void Sync(HashMap<String, Integer> animlist) {
		Elements.clear();
		for (String s : animlist.keySet()) {
			addElement(new GUI_Object_Element(new RelativeDimensions(0, 0, 0, 0), null, s));
		}
	}

	@Override
	public void OnEndActivation() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void OnResizeEntity() {
		// TODO Auto-generated method stub
		
	}
}
