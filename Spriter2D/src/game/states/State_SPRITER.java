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
import game.graphics.GUI_List_DropDown;
import game.graphics.GUI_List_DropDown_ANIMATIONS;
import game.graphics.GUI_List_Scroll_Project;
import game.graphics.GUI_Object_Element;
import game.graphics.GUI_Renderer_ColorArray;
import game.graphics.GUI_Renderer_ColorInterface;
import game.graphics.GUI_Renderer_Palette;
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

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
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
	private final GUI_Layer GUI = new GUI_Layer();
	UnicodeFont font;
	int WIDTH,HEIGHT;
	private GUI_Renderer_ColorArray ColorRenderer;
	private GUI_Text_Field FrameCount;
	private GUI_Text_Field ProjectName;
	private GUI_List_Scroll_Project ProjectScrollList;
	private Project CurrentProject;

	@Override
	protected void Init() {
		WIDTH=Game.Width;
		HEIGHT=Game.Height;
		font = LoadFont("ABEAKRG");
		float ButtonStartRX = (5f / 19f );
		//float ButtonStartRY =  (9f / 10f );
		float ButtonRWidth =  (14f / 171f);
		//float ButtonRHeight =  (1f / 10f);
		int DropDownHeight = (int)(HEIGHT * 1f / 15f);
		int BaseBarHeight = (int)(HEIGHT * 1f / 15f);
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
			GUI.AddButton(new RelativeDimensions(5f / 19f, 1f-(float)BaseBarHeight/(float)HEIGHT, 14f / 171f, (float)BaseBarHeight/(float)HEIGHT), firstButton);
			GUI.AddButton(new RelativeDimensions(5f / 19f+14f / 171f, 1f-(float)BaseBarHeight/(float)HEIGHT, 14f / 171f, (float)BaseBarHeight/(float)HEIGHT), pauseButton);
			
			GUI.AddButton(new RelativeDimensions(5f / 19f+14f / 171f*2, 1f-(float)BaseBarHeight/(float)HEIGHT, 14f / 171f, (float)BaseBarHeight/(float)HEIGHT), playButton);
			
			GUI.AddButton(new RelativeDimensions(5f / 19f+14f / 171f*3, 1f-(float)BaseBarHeight/(float)HEIGHT, 14f / 171f, (float)BaseBarHeight/(float)HEIGHT), backButton);
			GUI.AddButton(new RelativeDimensions(5f / 19f+14f / 171f*5, 1f-(float)BaseBarHeight/(float)HEIGHT, 14f / 171f, (float)BaseBarHeight/(float)HEIGHT), forwardButton);
			Texture button2 = TextureLoader
					.getTexture(
							"PNG",
							ResourceLoader
									.getResourceAsStream("res/Materials/GUI/Buttons/DefaultButton.png"),
							false, GL11.GL_NEAREST);
			// GUI.AddButton(ButtonStartX, ButtonStartY + Spacing * 1,
			// button2.getImageWidth(), button2.getImageHeight(), button2);
			Texture button3 = TextureLoader
					.getTexture(
							"PNG",
							ResourceLoader
									.getResourceAsStream("res/Materials/GUI/Buttons/DefaultButton.png"),
							false, GL11.GL_NEAREST);
			// GUI.AddButton(ButtonStartX, ButtonStartY + Spacing * 2,
			// button3.getImageWidth(), button3.getImageHeight(), button3);
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
			// Drawing
			// 0
			ColorRenderer = new GUI_Renderer_ColorArray(new RelativeDimensions(
					  5f / 19f,  1f / 10f, 1
							-   5f / 19f,
					  4f / 5f), CreateColorArray(32, 32),
					FrameCount, AnimationList);
			GUI.AddObject(ColorRenderer);
			// SCROLL
			// 1
			GUI.AddObject(ProjectScrollList);
			/*
			 * for (int i = 0; i < 25; i++) { GUI.addElement(1, new
			 * GUI_Object_Element(55, 55, 90, 10, button3, "Element" +
			 * Integer.toString(i))); }
			 */
			ProjectName = new GUI_Text_Field(new RelativeDimensions(0, 13f / 20f ,
					5f / 19f ,  1f / 10f ),
					"No project", font);
			// FIELDS
			// 2

			GUI.AddObject(FrameCount);
			// 3
			GUI.AddObject(ProjectName);
			// 4
			GUI.AddObject(AnimationList);
			// GUI.AddObject(new GUI_Text_Field(ButtonStartX + ButtonWidth * 6,
			// ButtonStartY, WIDTH - (ButtonStartX + ButtonWidth * 6),
			// ButtonHeight, "ANIMATION", font));
			// PALETTE
			// 5
			GUI.AddObject(new GUI_Renderer_Palette(new RelativeDimensions(0,
					7f / 20f-DropDownHeight/(float)HEIGHT, 5f / 19f,
					3f / 10f+DropDownHeight/(float)HEIGHT), ColorRenderer, 126, 9));
			// PICKER
			// 6
			GUI.AddObject(new GUI_Renderer_ColorInterface(new RelativeDimensions(0,
					1f / 10f-DropDownHeight/(float)HEIGHT, 5f / 19f,
					1f / 4f), ColorRenderer, SwitchButton,
					PaletteButton));

			// DROP DOWNS
			// 7
			
			GUI.AddObject(new GUI_List_DropDown(0, 0, (int) (WIDTH * 1f / 5f),
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
			// 8
			GUI.AddObject(new GUI_List_DropDown((int) (WIDTH * 1f / 5f), 0,
					(int) (WIDTH * 1f / 5f), DropDownHeight,
					new GUI_Object_Element( button2, "New"),
					new GUI_Object_Element[] {
							new GUI_Object_Element(button2,
									"Sprite.."),
							new GUI_Object_Element(button2,
									"Animation..") }, font));
			// 9
			GUI.AddObject(new GUI_List_DropDown((int) (WIDTH * 2f / 5f), 0,
					(int) (WIDTH * 1f / 5f), DropDownHeight,
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
			// 10
			GUI.AddObject(new GUI_List_DropDown((int) (WIDTH * 3f / 5f), 0,
					(int) (WIDTH * 1f / 5f), DropDownHeight,
					new GUI_Object_Element( button2, "View"),
					new GUI_Object_Element[] {
							new GUI_Object_Element( button2,
									"Zoom in"),
							new GUI_Object_Element(button2,
									"Zoom out"),
							new GUI_Object_Element(button2,
									"Center view") }, font));
			// MISC
			// 11
			GUI.AddObject(new GUI_Text_Field(new RelativeDimensions( 4f / 5f, 0,
					1f / 5f, DropDownHeight/(float)HEIGHT), "",
					font));
			picker = new ColorPicker(ColorRenderer);
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

	ColorPicker picker;

	@Override
	protected void ProcessInput() {
		CheckResized();
		// Not funtioning (zoom/center) for large grids?
		RendererControls();

		GUI.ProcessInput(MouseLastX, MouseLastY, Mouse.isButtonDown(0));
		ProcessFileMenu();
		ProcessNewEntityMenu();
		ProcessEditMenu();
		// Animation
		if (GUI.getElementDown(4) != null) {
			ColorRenderer.RecreateTiles();
			FrameCount.setText(ColorRenderer.getFrameAsString());
			GUI.flushObject(4);
		}
		if (GUI.isElementDown(10, 2)) {
			ColorRenderer.Center();

		}
		if (GUI.isButtonDown(3)) {
			ColorRenderer.addFrame(-1);
			FrameCount.setText(ColorRenderer.getFrameAsString());
		}
		if (GUI.isButtonDown(1)) {
			ColorRenderer.setPlaying(false);
			testString = "no";
		}
		if (GUI.isButtonDown(2)) {
			ColorRenderer.setPlaying(true);
			testString = "yes";
		}
		if (GUI.isButtonDown(4)) {
			ColorRenderer.addFrame(1);
			FrameCount.setText(ColorRenderer.getFrameAsString());
		}
		GUI_Object_Element goe = GUI.getElementDown(0);
		if (goe != null) {
			testString = goe.getName();
		}
		if (CurrentProject != null) {
			if (GUI.getElementDown(1) != null) {
				int goe2 = GUI.getIndexDown(1);
				System.out.println("Set_Sprite: "
						+ CurrentProject.getSprite(goe2).getName());
				ColorRenderer.setSprite(CurrentProject.getSprite(goe2));
				GUI.flushObject(1);
				// System.out.println("Sprite change complete");
			}
		}
		
	}

	private void ProcessFileMenu() {
		if (GUI.isElementDown(7, 0)) {
			Dialog_New_Project dialog = new Dialog_New_Project(this);
			GUI.flushObject(7);
		} else if (GUI.isElementDown(7, 1)) {
			// if (CurrentProject != null) {
			LoadProject();
			GUI.flushObject(7);
			// }

		} else if (GUI.isElementDown(7, 2)) {
			if (CurrentProject != null) {
				SaveProject();
				GUI.flushObject(7);
			}

		} else if (GUI.isElementDown(7, 4)) {
			// WRITE GIFS, FUCK YEAH BABY!
			// GIFS GIFS GIFS!
			// SUCK IT BITCHES :P
			// #ThugLife
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
				GUI.flushObject(7);
			}
		} else if (GUI.isElementDown(7, 5)) {

			if (ColorRenderer.hasSprite()) {
				FiletypeBrowser gb = new FiletypeBrowser("png");
				String path = gb.GetSavePath();
				gb.DestroyMe();
				Colour[][] ca = ColorRenderer.getEditorSprite()
						.getSpriteSlides();

				BufferedImageCreator.createPNG(ca, 1, true, path);
				GUI.flushObject(7);
			}
		} else if (GUI.isElementDown(7, 6)) {

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
				GUI.flushObject(7);
			}
		} else if (GUI.isElementDown(7, 3)) {

			if (ColorRenderer.hasSprite()) {
				try {
					ExportProject();
				} catch (IOException e) {
					System.err.println("Caught Exception @Project export: " + e.getMessage());
					e.printStackTrace();
				}
				GUI.flushObject(7);
			}
		}
	}

	private void ProcessNewEntityMenu() {
		if (GUI.isElementDown(8, 0)) {
			if (CurrentProject == null) {
				Dialog_New_Project dialog = new Dialog_New_Project(this);
			}
			if (CurrentProject != null) {
				ColorRenderer.NewDialog(true);
				CurrentProject.addSprite(ColorRenderer.getEditorSprite());
				ProjectScrollList.Sync(CurrentProject);
			}

			GUI.flushObject(8);

		} else if (GUI.isElementDown(8, 1)) {
			if (ColorRenderer.hasSprite())
				ColorRenderer.NewDialog(false);
			GUI.flushObject(8);
		}

	}

	private void ProcessEditMenu() {
		if (GUI.isElementDown(9, 2)) {

			if (CurrentProject != null) {
				ColorRenderer.CopyFrame();
			}
			System.out.println("copied frame");
			GUI.flushObject(9);

		}
		if (GUI.isElementDown(9, 3)) {

			if (CurrentProject != null) {
				ColorRenderer.PasteFrame();
			}

			GUI.flushObject(9);

		}
		if (GUI.isElementDown(9, 4)) {

			if (CurrentProject != null) {
				ColorRenderer.PasteClipboard();
			}

			GUI.flushObject(9);

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

	private String testString = "I'm a ttf font!";

	@Override
	protected void Render2D() {

		GUI.Render();
		font.drawString(5, 5, testString, Color.black);
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
			GUI.onResize();
		
		}
	}
}
