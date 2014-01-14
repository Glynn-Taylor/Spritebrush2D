package game.graphics;

import game.states.State_SPRITER;
import game.util.Project;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.opengl.Texture;

public class GUI_Window_Project_Properties extends GUI_Window {
	
	private Font font;
	private boolean ProjectTab = true;
	public GUI_Window_Project_Properties(RelativeDimensions dimensions,
			Texture tab, Texture grabBar, Font f) {
		font=f;
		AbsoluteDimensions = dimensions;
		float DragBarHeight = 1f/15f;
		//Add drag bar
		
		Elements.add(new GUI_Object_Element(new RelativeDimensions(
				AbsoluteDimensions.GetRelativeX(), AbsoluteDimensions
						.GetRelativeY(),
				(AbsoluteDimensions.GetRelativeW()),
				(AbsoluteDimensions.GetRelativeH() * DragBarHeight)), grabBar, ""));
		// Add tabs
		Elements.add(new GUI_Object_Element(new RelativeDimensions(
				AbsoluteDimensions.GetRelativeX(), AbsoluteDimensions
						.GetRelativeY()+(AbsoluteDimensions.GetRelativeH() * DragBarHeight),
				(AbsoluteDimensions.GetRelativeW() * 1f / 2f),
				(AbsoluteDimensions.GetRelativeH() * 1f / 15f)), tab, "Project"));
		
		Elements.add(new GUI_Object_Element(new RelativeDimensions(
				AbsoluteDimensions.GetRelativeX()
						+ (AbsoluteDimensions.GetRelativeW() * 1f / 2f),
				AbsoluteDimensions.GetRelativeY()+(AbsoluteDimensions.GetRelativeH() * DragBarHeight), (AbsoluteDimensions
						.GetRelativeW() * 1f / 2f), (AbsoluteDimensions
						.GetRelativeH() * 1f / 15f)), tab, "Sprites"));
		
		onResize();
	}

	@Override
	public boolean ProcessInput(int mouseX, int mouseY, boolean mouseDown) {
		
		if (mouseDown)
			for (int i = 0; i < Elements.size(); i++) {
				Elements.get(i).InsideElement(mouseX, mouseY);
			}
		if(mouseDown){
			MovementControl(0,mouseX,mouseY);
			System.out.println("MovementControl C:");
		}
		
		if (isElementDown(1)) {
			ProjectTab=true;
			return true;
		}
		if (isElementDown(2)) {
			ProjectTab=false;
			return true;
		}
		return false;
	}

	@Override
	protected void RenderElements() {
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		
		Box(EntityWindowX, (int)(EntityWindowY+EntityHeight*1f / 15f), EntityWidth, (int)(EntityHeight*14f / 15f),
				Color.black);
		Box(EntityWindowX + 5, (int)(EntityWindowY+EntityHeight*1 / 15f), EntityWidth - 10,
				(int)(EntityHeight*14f / 15f)- 10, Color.white);
		
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		Color.white.bind();
		RenderElementsTruePosition();
		Elements.get(1).RenderName(font, Color.black);
		Elements.get(2).RenderName(font, Color.black);
		Color.white.bind();
		if(ProjectTab){
			RenderProjectTab();
		}else{
			RenderSpriteTab();
			
		}
		
	}
	
	private void RenderSpriteTab() {
		// TODO Auto-generated method stub
		
	}

	private void RenderProjectTab() {
		Project p = State_SPRITER.CurrentProject;
		
		if(p!=null){
			int startY=(int)(EntityWindowY+EntityHeight*1f / 15f);
			int lineHeight= (int)(EntityHeight*14f / 15f*1/5f);
			RenderText(p.getExportPath(), EntityWindowX, startY, EntityWidth, lineHeight,font,Color.black);
		}else{
			RenderText("No project", EntityWindowX, (int)(EntityWindowY+EntityHeight*1f / 15f), EntityWidth, (int)(EntityHeight*14f / 15f),
					font,Color.black);
		}
		
	}

	@Override
	public void OnEndActivation() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void OnResizeEntity() {
		for(int i=0;i<Elements.size();i++){
			Elements.get(i).onResize();
		}

	}

}
