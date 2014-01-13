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
/*
 * A state that handles how the startup menu functions
 */
package game.states;

import external.BufferedImageCreator;
import external.GifSequenceWriter;
import game.data.ProjectData;
import game.data.browser.FiletypeBrowser;
import game.data.browser.ProjectBrowser;
import game.data.sprite.Colour;
import game.data.sprite.SpriteData;
import game.graphics.GUI_Entity;
import game.graphics.GUI_Layer;
import game.graphics.GUI_Layer_Controller;
import game.graphics.GUI_List_DropDown;
import game.graphics.GUI_List_DropDown_ANIMATIONS;
import game.graphics.GUI_List_Scroll_Project;
import game.graphics.GUI_Object_Element;
import game.graphics.GUI_Renderer_ColorArray;
import game.graphics.GUI_Renderer_ColorInterface;
import game.graphics.GUI_Renderer_Palette;
import game.graphics.GUI_Status;
import game.graphics.GUI_Text_Field;
import game.graphics.RelativeDimensions;
import game.graphics.dialog.Dialog_New_Project;
import game.util.ColorPicker;
import game.util.EditorSprite2D;
import game.util.Project;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

public class State_SPRITER extends State {
	private GUI_Layer_Controller GUI_Controller = new GUI_Layer_Controller();
	
	private UnicodeFont font;
	private int WIDTH,HEIGHT;
	private GUI_Renderer_ColorArray ColorRenderer;
	private GUI_Text_Field FrameCount;
	private GUI_Text_Field ProjectName;
	private GUI_List_Scroll_Project ProjectScrollList;
	private Project CurrentProject;
	private ColorPicker picker;
	public static GUI_Status SpriterStatusLog = new GUI_Status();
	@Override
	protected void Init() {
		GUI_Layer Layer_DropdownBar = new GUI_Layer();
		GUI_Layer Layer_SideBar = new GUI_Layer();
		GUI_Layer Layer_BottomBar = new GUI_Layer();
		GUI_Layer Layer_SpriteEditor = new GUI_Layer();
		GUI_Layer Layer_Floating = new GUI_Layer();
		WIDTH=Game.Width;
		HEIGHT=Game.Height;
		font = LoadFont("ABEAKRG");
		float ButtonStartRX = (5f / 19f );
		float ButtonRWidth =  (14f / 171f);
		int DropDownHeight = (int)(HEIGHT * 1f / 20f);
		int BaseBarHeight = (int)(HEIGHT * 1f / 15f);
		int Dropdowns = 8;
		
		try {
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			Texture playButton = TextureLoader
					.getTexture(
							"PNG",
							ResourceLoader
									.getResourceAsStream("res/Materials/GUI/Buttons/Button_Play.png"),
							false, GL11.GL_NEAREST);
			Texture firstButton = TextureLoader
					.getTexture(
							"PNG",
							ResourceLoader
									.getResourceAsStream("res/Materials/GUI/Buttons/Button_First.png"),
							false, GL11.GL_NEAREST);
			Texture backButton = TextureLoader
					.getTexture(
							"PNG",
							ResourceLoader
									.getResourceAsStream("res/Materials/GUI/Buttons/Button_Back.png"),
							false, GL11.GL_NEAREST);
			Texture forwardButton = TextureLoader
					.getTexture(
							"PNG",
							ResourceLoader
									.getResourceAsStream("res/Materials/GUI/Buttons/Button_Next.png"),
							false, GL11.GL_NEAREST);
			Texture pauseButton = TextureLoader
					.getTexture(
							"PNG",
							ResourceLoader
									.getResourceAsStream("res/Materials/GUI/Buttons/Button_Pause.png"),

							false, GL11.GL_NEAREST);
			Texture SwitchButton = TextureLoader
					.getTexture(
							"PNG",
							ResourceLoader
									.getResourceAsStream("res/Materials/GUI/Buttons/Button_Switch.png"),
							false, GL11.GL_NEAREST);
			Texture PaletteButton = TextureLoader
					.getTexture(
							"PNG",
							ResourceLoader
									.getResourceAsStream("res/Materials/GUI/Buttons/Button_Palette.png"),
							false, GL11.GL_NEAREST);
			Texture button3 = TextureLoader
			.getTexture(
					"PNG",
					ResourceLoader
							.getResourceAsStream("res/Materials/GUI/Buttons/DefaultButton.png"),
					false, GL11.GL_NEAREST);
			Texture button2 = TextureLoader
			.getTexture(
					"PNG",
					ResourceLoader
							.getResourceAsStream("res/Materials/GUI/Buttons/DefaultButton.png"),
					false, GL11.GL_NEAREST);
	
			BackGroundImage = TextureLoader
			.getTexture(
					"PNG",
					ResourceLoader
							.getResourceAsStream("res/Materials/GUI/Backgrounds/DefaultBackground.png"));
			
			
			
			
			FrameCount = new GUI_Text_Field(new RelativeDimensions(ButtonStartRX + ButtonRWidth * 4,
					1-BaseBarHeight/(float)HEIGHT, ButtonRWidth, BaseBarHeight/(float)HEIGHT), "0", font);
			
			GUI_List_DropDown_ANIMATIONS AnimationList = new GUI_List_DropDown_ANIMATIONS(
					(int) (ButtonStartRX* WIDTH) + (int) (ButtonRWidth * WIDTH) * 6, HEIGHT-BaseBarHeight, WIDTH
							- ((int) (ButtonStartRX* WIDTH) + (int) (ButtonRWidth * WIDTH) * 6), BaseBarHeight,
					new GUI_Object_Element(new RelativeDimensions(0, 0, 0, 0), button2, "Animation"),
					new GUI_Object_Element[] {}, font);
			ProjectScrollList = new GUI_List_Scroll_Project(new RelativeDimensions(0,
					 3f / 4f, 5f / 19f,
					1/ 4f), button3, 5, font);
			ColorRenderer = new GUI_Renderer_ColorArray(new RelativeDimensions(
					  5f / 19f,  1f / 10f, 1
							-   5f / 19f,
					  4f / 5f), CreateColorArray(32, 32),
					FrameCount, AnimationList);
			ProjectName = new GUI_Text_Field(new RelativeDimensions(0, 20f / 30f ,
					5f / 19f ,  5f / 60f ),
					"No project", font);
			
			///////////////////////////////SPRITE WINDOW////////////////////////////////////////////
			Layer_SpriteEditor.addObject("SpriteWindow",ColorRenderer);
			
			///////////////////////////////SIDE BAR//////////////////////////////////////////////
			Layer_SideBar.addObject("ToolColours",new GUI_Renderer_ColorInterface(new RelativeDimensions(0,
					DropDownHeight/(float)HEIGHT, 5f / 19f,
					1f / 4f), ColorRenderer, SwitchButton,
					PaletteButton));
			Layer_SideBar.addObject("SpriteScrollList",ProjectScrollList);
			
			Layer_SideBar.addObject("Palette",new GUI_Renderer_Palette(new RelativeDimensions(0,
					5f / 20f+DropDownHeight/(float)HEIGHT, 5f / 19f,
					3f / 10f+DropDownHeight/(float)HEIGHT), ColorRenderer, 126, 9));
			Layer_SideBar.addObject("SpriteLabel",ProjectName);
			
			
			///////////////////////////////BOTTOM BAR////////////////////////////////////////////
			Layer_BottomBar.AddButton("Start",new RelativeDimensions(5f / 19f, 1f-(float)BaseBarHeight/(float)HEIGHT, 14f / 171f, (float)BaseBarHeight/(float)HEIGHT), firstButton);
			Layer_BottomBar.AddButton("Pause",new RelativeDimensions(5f / 19f+14f / 171f, 1f-(float)BaseBarHeight/(float)HEIGHT, 14f / 171f, (float)BaseBarHeight/(float)HEIGHT), pauseButton);	
			Layer_BottomBar.AddButton("Play",new RelativeDimensions(5f / 19f+14f / 171f*2, 1f-(float)BaseBarHeight/(float)HEIGHT, 14f / 171f, (float)BaseBarHeight/(float)HEIGHT), playButton);
			Layer_BottomBar.AddButton("PrevFrame",new RelativeDimensions(5f / 19f+14f / 171f*3, 1f-(float)BaseBarHeight/(float)HEIGHT, 14f / 171f, (float)BaseBarHeight/(float)HEIGHT), backButton);
			Layer_BottomBar.AddButton("NextFrame",new RelativeDimensions(5f / 19f+14f / 171f*5, 1f-(float)BaseBarHeight/(float)HEIGHT, 14f / 171f, (float)BaseBarHeight/(float)HEIGHT), forwardButton);
			Layer_BottomBar.addObject("FrameLabel",FrameCount);
			Layer_BottomBar.addObject("AnimList",AnimationList);
			
			///////////////////////////////DROPDOWN BAR//////////////////////////////////////////
			Layer_DropdownBar.addObject("File",new GUI_List_DropDown(0, 0, (int) (WIDTH * 1f / Dropdowns),
					DropDownHeight, new GUI_Object_Element(new RelativeDimensions(0, 0, 0,
							0), button2, "File"), new GUI_Object_Element[] {
							new GUI_Object_Element(button2,
									"New project.."),
							new GUI_Object_Element( button2,
									"Load project.."),
							new GUI_Object_Element( button2,
									"Save project.."),
							new GUI_Object_Element( button2,
									"Export project.."),
							new GUI_Object_Element( button2,
									"Export animation GIF.."),
							new GUI_Object_Element( button2,
									"Export sprite PNG.."),
							new GUI_Object_Element( button2,
									"Import GIF..")}, font));
			Layer_DropdownBar.addObject("New",new GUI_List_DropDown((int) (WIDTH * 1f / Dropdowns), 0,
					(int) (WIDTH * 1f / Dropdowns), DropDownHeight,
					new GUI_Object_Element( button2, "New"),
					new GUI_Object_Element[] {
							new GUI_Object_Element(button2,
									"Sprite.."),
							new GUI_Object_Element(button2,
									"Animation..") }, font));
			Layer_DropdownBar.addObject("Edit",new GUI_List_DropDown((int) (WIDTH * 2f / Dropdowns), 0,
					(int) (WIDTH * 1f / Dropdowns), DropDownHeight,
					new GUI_Object_Element( button2, "Edit"),
					new GUI_Object_Element[] {
							new GUI_Object_Element( button2,
									"Delete"),
							new GUI_Object_Element( button2,
									"Properties"),
							new GUI_Object_Element( button2,
									"Copy frame"),
							new GUI_Object_Element( button2,
									"Paste frame"),
							new GUI_Object_Element( button2,
									"Paste clipboard image") }, font));
			Layer_DropdownBar.addObject("View",new GUI_List_DropDown((int) (WIDTH * 3f / Dropdowns), 0,
					(int) (WIDTH * 1f / Dropdowns), DropDownHeight,
					new GUI_Object_Element( button2, "View"),
					new GUI_Object_Element[] {
							new GUI_Object_Element( button2,
									"Zoom in"),
							new GUI_Object_Element(button2,
									"Zoom out"),
							new GUI_Object_Element(button2,
									"Center view") }, font));
			Layer_DropdownBar.addObject("Frame",new GUI_List_DropDown((int) (WIDTH * 4f / Dropdowns), 0,
					(int) (WIDTH * 1f / Dropdowns), DropDownHeight,
					new GUI_Object_Element( button2, "Frame"),
					new GUI_Object_Element[] {
							new GUI_Object_Element(button2,
									"Element 0"),
							new GUI_Object_Element(button2,
									"Element 1") }, font));
			Layer_DropdownBar.addObject("Sprite",new GUI_List_DropDown((int) (WIDTH * 5f / Dropdowns), 0,
					(int) (WIDTH * 1f / Dropdowns), DropDownHeight,
					new GUI_Object_Element( button2, "Sprite"),
					new GUI_Object_Element[] {
							new GUI_Object_Element(button2,
									"Element 0"),
							new GUI_Object_Element(button2,
									"Element 1") }, font));
			Layer_DropdownBar.addObject("Effects",new GUI_List_DropDown((int) (WIDTH * 6f / Dropdowns), 0,
					(int) (WIDTH * 1f / Dropdowns), DropDownHeight,
					new GUI_Object_Element( button2, "Effects"),
					new GUI_Object_Element[] {
							new GUI_Object_Element(button2,
									"Element 0"),
							new GUI_Object_Element(button2,
									"Element 1") }, font));
			Layer_DropdownBar.addObject("Project",new GUI_List_DropDown((int) (WIDTH * 7f / Dropdowns), 0,
					(int) (WIDTH * 1f / Dropdowns), DropDownHeight,
					new GUI_Object_Element( button2, "Project"),
					new GUI_Object_Element[] {
							new GUI_Object_Element(button2,
									"Element 0"),
							new GUI_Object_Element(button2,
									"Element 1") }, font));
			
			picker = new ColorPicker(ColorRenderer);
			
			//////Layer ordering//////
			//0 is frontmost element//
			GUI_Controller.addLayer(Layer_Floating, "Floating");
			//Layer_DropdownBar.setAutoFlush(true);
			GUI_Controller.addLayer(Layer_DropdownBar, "DropDownBar");
			GUI_Controller.addLayer(Layer_SideBar, "SideBar");
			GUI_Controller.addLayer(Layer_BottomBar, "BottomBar");
			GUI_Controller.addLayer(Layer_SpriteEditor, "SpriteEditor");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private Colour[][] CreateColorArray(int i, int j) {
		Colour[][] ca = new Colour[j][i];
		for (int y = 0; y < j; y++) {
			for (int x = 0; x < i; x++) {
				ca[y][x] = new Colour(1, 1, 1);
			}
		}
		return ca;
	}

	@Override
	protected void Update() {

	}

	

	@Override
	protected void ProcessInput() {
		//Check if user resized window
		CheckResized();
		//Handle key presses
		RendererControls();
		//Push primary mouse click to GUI objects
		GUI_Controller.ProcessInput(MouseLastX, MouseLastY, Mouse.isButtonDown(0));
		//Control Side bar clicks
		ProcessSideBar();
		//Control Bottom bar clicks
		ProcessBottomBar();
		//Control Top bar clicks
		ProcessDropDownBar();
	}

	private void ProcessViewBar() {
		if (GUI_Controller.isElementDown("DropDownBar","View", 2)) {
			ColorRenderer.Center();

		}
		
	}

	private void ProcessDropDownBar() {
		//Implement clicks on "File" menu
		ProcessFileMenu();
		//Implement clicks on "New" menu
		ProcessNewEntityMenu();
		//Implement clicks on "Edit" menu
		ProcessEditMenu();
		//Implement clicks on "View" menu
		ProcessViewBar();
		
	}

	private void ProcessBottomBar() {
		if (GUI_Controller.isButtonDown("BottomBar","Pause")) {
			ColorRenderer.setPlaying(false);
		}
		if (GUI_Controller.isButtonDown("BottomBar","Play")) {
			ColorRenderer.setPlaying(true);
		}
		if (GUI_Controller.isButtonDown("BottomBar","PrevFrame")) {
			ColorRenderer.addFrame(-1);
			FrameCount.setText(ColorRenderer.getFrameAsString());
		}
		if (GUI_Controller.isButtonDown("BottomBar","NextFrame")) {
			ColorRenderer.addFrame(1);
			FrameCount.setText(ColorRenderer.getFrameAsString());
		}
		if (GUI_Controller.getElementDown("BottomBar","AnimList") != null) {
			ColorRenderer.RecreateTiles();
			FrameCount.setText(ColorRenderer.getFrameAsString());
			GUI_Controller.flushObject("BottomBar","AnimList");
		}
	}

	private void ProcessSideBar() {
		if (CurrentProject != null) {
			if (GUI_Controller.getElementDown("SideBar","SpriteScrollList") != null) {
				int goe2 = GUI_Controller.getIndexDown("SideBar","SpriteScrollList");
				System.out.println("Set Sprite to: "
						+ CurrentProject.getSprite(goe2).getName());
				ColorRenderer.setSprite(CurrentProject.getSprite(goe2));
				GUI_Controller.flushObject("SideBar","SpriteScrollList");
			}
		}
	}

	private void ProcessFileMenu() {
		if (GUI_Controller.isElementDown("DropDownBar","File", 0)) {
			Dialog_New_Project dialog = new Dialog_New_Project(this);
			GUI_Controller.flushObject("DropDownBar","File");
		} else if (GUI_Controller.isElementDown("DropDownBar","File", 1)) {
			// if (CurrentProject != null) {
			LoadProject();
			GUI_Controller.flushObject("DropDownBar","File");
			// }

		} else if (GUI_Controller.isElementDown("DropDownBar","File", 2)) {
			if (CurrentProject != null) {
				SaveProject();
				GUI_Controller.flushObject("DropDownBar","File");
			}

		} else if (GUI_Controller.isElementDown("DropDownBar","File", 4)) {
			if (ColorRenderer.hasSprite()) {
				FiletypeBrowser gb = new FiletypeBrowser("gif");
				String path = gb.GetSavePath();
				gb.DestroyMe();
				Colour[][][] ca = ColorRenderer.getEditorSprite()
						.getCurrentAnimSlides();
				BufferedImage[] bia = new BufferedImage[ca.length];
				for (int i = 0; i < bia.length; i++) {
					bia[i] = BufferedImageCreator.createImage(ca[i], 7, true);
				}
				try {
					GifSequenceWriter.CreateGif(path, bia, 200);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				GUI_Controller.flushObject("DropDownBar","File");
			}
		} else if (GUI_Controller.isElementDown("DropDownBar","File", 5)) {

			if (ColorRenderer.hasSprite()) {
				FiletypeBrowser gb = new FiletypeBrowser("png");
				String path = gb.GetSavePath();
				gb.DestroyMe();
				Colour[][] ca = ColorRenderer.getEditorSprite()
						.getSpriteSlides();

				BufferedImageCreator.createPNG(ca, 1, true, path);
				GUI_Controller.flushObject("DropDownBar","File");
			}
		} else if (GUI_Controller.isElementDown("DropDownBar","File", 6)) {

			if (ColorRenderer.hasSprite()) {
				FiletypeBrowser gb = new FiletypeBrowser("png");
				String path = gb.GetOpenPath();
				gb.DestroyMe();
				if(path!=null)
				try {
					BufferedImage bimg = null;
					System.out.println("Loading:"+path);
					bimg = ImageIO.read(new File(path));
					ColorRenderer.PasteBufferedImageWhole(bimg);
					
				} catch (IOException e) {
					System.err.println("Caught Exception @png import " + e.getMessage());
					e.printStackTrace();
				}
				GUI_Controller.flushObject("DropDownBar","File");
			}
		} else if (GUI_Controller.isElementDown("DropDownBar","File", 3)) {

			if (ColorRenderer.hasSprite()) {
				try {
					ExportProject();
				} catch (IOException e) {
					System.err.println("Caught Exception @Project export: " + e.getMessage());
					e.printStackTrace();
				}
				GUI_Controller.flushObject("DropDownBar","File");
			}
		}
	}

	private void ProcessNewEntityMenu() {
		if (GUI_Controller.isElementDown("DropDownBar","New", 0)) {
			if (CurrentProject == null) {
				Dialog_New_Project dialog = new Dialog_New_Project(this);
			}
			if (CurrentProject != null) {
				ColorRenderer.NewDialog(true);
				CurrentProject.addSprite(ColorRenderer.getEditorSprite());
				ProjectScrollList.Sync(CurrentProject);
			}

			GUI_Controller.flushObject("DropDownBar","New");

		} else if (GUI_Controller.isElementDown("DropDownBar","New", 1)) {
			if (ColorRenderer.hasSprite())
				ColorRenderer.NewDialog(false);
			GUI_Controller.flushObject("DropDownBar","New");
		}

	}

	private void ProcessEditMenu() {
		if (GUI_Controller.isElementDown("DropDownBar","Edit", 2)) {

			if (CurrentProject != null) {
				ColorRenderer.CopyFrame();
			}
			System.out.println("copied frame");
			GUI_Controller.flushObject("DropDownBar","Edit");

		}
		if (GUI_Controller.isElementDown("DropDownBar","Edit", 3)) {

			if (CurrentProject != null) {
				ColorRenderer.PasteFrame();
			}

			GUI_Controller.flushObject("DropDownBar","Edit");

		}
		if (GUI_Controller.isElementDown("DropDownBar","Edit", 4)) {

			if (CurrentProject != null) {
				ColorRenderer.PasteClipboard();
			}

			GUI_Controller.flushObject("DropDownBar","Edit");

		}

	}

	public void NewProject(String path, String name) {
		CurrentProject = new Project(path, name);
		ProjectName.setText(CurrentProject.getName());
	}

	private void RendererControls() {
		if (Keyboard.isKeyDown(Keyboard.KEY_W))
			ColorRenderer.addY(-25);
		if (Keyboard.isKeyDown(Keyboard.KEY_A))
			ColorRenderer.addX(-25);
		if (Keyboard.isKeyDown(Keyboard.KEY_S))
			ColorRenderer.addY(25);
		if (Keyboard.isKeyDown(Keyboard.KEY_D))
			ColorRenderer.addX(25);
		int dZ = Mouse.getDWheel();
		ColorRenderer.addZoom(dZ < 0 ? -0.05f : dZ > 0 ? 0.05f : 0);
		// System.out.println(Mouse.getDWheel());
	}

	

	@Override
	protected void Render2D() {
		GUI_Controller.Render();
		SpriterStatusLog.DrawText( font);
		//font.drawString(5, 5, testString, Color.black);
		font.drawString(
				5,
				20,
				Integer.toString(Mouse.getX()) + ":"
						+ Integer.toString(Mouse.getY()), Color.black);
	}

	@Override
	protected void Render3D() {

	}

	@Override
	public void Unload() {

	}

	public UnicodeFont LoadFont(String name) {
		UnicodeFont font1 = null;
		String fontPath = "res/fonts/" + name + ".ttf";
		try {
			font1 = new UnicodeFont(fontPath, 15, true, false);
		} catch (SlickException e1) {
			e1.printStackTrace();
		}
		font1.addAsciiGlyphs();
		font1.addGlyphs(400, 600);
		font1.getEffects().add(new ColorEffect(java.awt.Color.WHITE));
		try {
			font1.loadGlyphs();
		} catch (SlickException e) {
			e.printStackTrace();
		}
		return font1;
	}

	private void SaveProject() {
		ProjectBrowser fb = new ProjectBrowser();
		try {
			String path = fb.GetSavePath();
			if (path != null) {
				FileOutputStream fileOut = new FileOutputStream(path
						+ ".project");
				ObjectOutputStream out = new ObjectOutputStream(fileOut);
				out.writeObject(CurrentProject.getData());
				out.close();
				fileOut.close();
				fb.DestroyMe();
			}
		} catch (IOException i) {
			i.printStackTrace();
		}
	}

	private void LoadProject() {
		ProjectBrowser fb = new ProjectBrowser();
		try {

			String path = fb.GetOpenPath();
			if (path != null) {
				File f = new File(path);
				FileInputStream fileIn = new FileInputStream(f);

				ObjectInputStream in = new ObjectInputStream(fileIn);
				ProjectData p=null;
				try {
					p = (ProjectData) in.readObject();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				CurrentProject = new Project(p);
				ProjectName.setText(CurrentProject.getName());
				ColorRenderer.SyncWithProjectSprites(CurrentProject
						.getSprites());
				ProjectScrollList.Sync(CurrentProject);
				in.close();
				fileIn.close();
				fb.DestroyMe();

			}
		} catch (IOException  e) {

			System.out
					.println("ERROR: Failed to load ProjectData @ Projectbrowser");
			e.printStackTrace();
		}
	}

	private void ExportProject() throws IOException {
		File file = new File(CurrentProject.getExportPath() + "/" + "src");
		// EXPORT SPRITES
		if (file.exists()) {
			String elementsString = "";
			ArrayList<EditorSprite2D> sprites = CurrentProject.getSprites();
			for (EditorSprite2D sprt : sprites) {
				Object[] data = sprt.getExportData();
				SpriteData sd = new SpriteData(sprt.getSpriteSlides(), data[0],
						data[1]);
				FileOutputStream fileOut = new FileOutputStream(
						CurrentProject.getExportPath() + "/res/"
								+ sprt.getName() + ".sprite");
				elementsString += "<resource type=\"sprite\" id=\""
						+ sprt.getName() + "\">res\\" + sprt.getName()
						+ ".sprite</resource>";
				ObjectOutputStream out = new ObjectOutputStream(fileOut);
				out.writeObject(sd);
				out.close();
				fileOut.close();
			}
			// EXPORT CLASSES
			file = new File(CurrentProject.getExportPath()
					+ "/src/game/data/sprite");
			if (!file.exists()) {
				file.mkdirs();

				InputStream is = this.getClass().getClassLoader()
						.getResourceAsStream("game/data/sprite/SpriteData.zip");
				ZipInputStream zis = new ZipInputStream(is);
				ZipEntry entry;
				byte[] buffer = new byte[2048];
				while ((entry = zis.getNextEntry()) != null) {

					// Once we get the entry from the stream, the stream is
					// positioned read to read the raw data, and we keep
					// reading until read returns 0 or less.
					String outpath = CurrentProject.getExportPath()
							+ "/src/game/data/sprite" + "/" + entry.getName();
					FileOutputStream output = null;
					try {
						output = new FileOutputStream(outpath);
						int len = 0;
						while ((len = zis.read(buffer)) > 0) {
							output.write(buffer, 0, len);
						}
					} finally {
						// we must always close the output file
						if (output != null)
							output.close();
					}
				}
			}
			// ExportXML
			CreateXMLFile(CurrentProject.getExportPath()
					+ "/src/game/data/sprite/SpriteXML.xml",
					"<?xml version=\"1.0\" encoding=\"UTF-8\"?><resources>"
							+ elementsString + "</resources>");

		}
	}

	private void CreateXMLFile(String path, String xmlString) {
		// String xmlString =
		// "<?xml version=\"1.0\" encoding=\"utf-8\"?><a><b></b><c></c></a>";

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
			Document document = builder.parse(new InputSource(new StringReader(
					xmlString)));
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(document);

			StreamResult result = new StreamResult(new File(path));
			transformer.transform(source, result);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	private void CheckResized(){
		if(Display.wasResized()){
			Game.onResize();
			GUI_Entity.UpdateHeight();
			WIDTH=Display.getWidth();
			HEIGHT=Display.getHeight();
			GUI_Controller.onResize();
		
		}
	}
}
