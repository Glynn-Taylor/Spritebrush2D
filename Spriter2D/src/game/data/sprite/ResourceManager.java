package game.data.sprite;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.loading.LoadingList;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.util.BufferedImageUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ResourceManager {

	private static ResourceManager _instance = new ResourceManager();
	private final Map<String, Sprite2D> spriteMap;

	private ResourceManager() {
		spriteMap = new HashMap<String, Sprite2D>();
		Load("game/data/sprite/SpriteXML.xml");
	}

	public final static ResourceManager getInstance() {
		return _instance;
	}

	public void loadResources(InputStream is) throws SlickException {
		loadResources(is, false);
	}

	public void loadResources(InputStream is, boolean deferred)
			throws SlickException {
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder docBuilder = null;
		try {
			docBuilder = docBuilderFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new SlickException("Could not load resources", e);
		}
		Document doc = null;
		try {
			doc = docBuilder.parse(is);
		} catch (SAXException e) {
			throw new SlickException("Could not load resources", e);
		} catch (IOException e) {
			throw new SlickException("Could not load resources", e);
		}
		// normalize text representation
		doc.getDocumentElement().normalize();
		NodeList listResources = doc.getElementsByTagName("resource");
		int totalResources = listResources.getLength();
		if (deferred) {
			LoadingList.setDeferredLoading(true);
		}
		for (int resourceIdx = 0; resourceIdx < totalResources; resourceIdx++) {
			Node resourceNode = listResources.item(resourceIdx);
			if (resourceNode.getNodeType() == Node.ELEMENT_NODE) {
				Element resourceElement = (Element) resourceNode;
				String type = resourceElement.getAttribute("type");
				if (type.equals("sprite")) {
					addElementAsImage(resourceElement);
				}
			}
		}
	}

	private final void addElementAsImage(Element resourceElement)
			throws SlickException {
		loadSprite(resourceElement.getAttribute("id"),
				resourceElement.getTextContent());
	}

	public Sprite2D loadSprite(String id, String path) throws SlickException {
		if (path == null || path.length() == 0)
			throw new SlickException("Sprite resource [" + id
					+ "] has invalid path");
		Sprite2D sprite = null;
		try {
			// image = new Image(path);
			File f = new File(path);
			FileInputStream fileIn = new FileInputStream(f);

			ObjectInputStream in = new ObjectInputStream(fileIn);
			SpriteData sd = (SpriteData) in.readObject();
			sprite = new Sprite2D(CreateImage(sd.getSpriteSheet()), sd);

		} catch (FileNotFoundException e) {
			throw new SlickException("Could not load image", e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.spriteMap.put(id, sprite);
		return sprite;
	}

	private Image CreateImage(Colour[][] spriteSheet) {
		Image slickImage = null;
		Texture texture = null;
		try {
			texture = BufferedImageUtil.getTexture("",
					CreateBufferedImage(spriteSheet), GL11.GL_NEAREST);
			slickImage = new Image(texture.getImageWidth(),
					texture.getImageHeight());

		} catch (IOException e) {
			System.out.println("File IO Error @ ResourceManager");
			e.printStackTrace();
		} catch (SlickException e) {
			System.out.println("Slick Error @ ResourceManager");
			e.printStackTrace();
		}

		slickImage.setTexture(texture);

		return slickImage;
	}

	private BufferedImage CreateBufferedImage(Colour[][] imageArray) {
		int width = imageArray[0].length;
		int height = imageArray.length;
		BufferedImage ret;

		ret = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		WritableRaster raster = ret.getRaster();

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {

				raster.setSample(x, y, 0, (int) (imageArray[y][x].getR() * 255));
				raster.setSample(x, y, 1, (int) (imageArray[y][x].getG() * 255));
				raster.setSample(x, y, 2, (int) (imageArray[y][x].getB() * 255));

			}
		}

		return ret;
	}

	public final Sprite2D getSprite(String ID) {
		return spriteMap.get(ID);
	}

	public void Load(String path) {
		InputStream istream = this.getClass().getClassLoader()
				.getResourceAsStream(path);
		try {
			loadResources(istream);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
