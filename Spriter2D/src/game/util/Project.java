package game.util;

import game.data.ProjectData;

import java.util.ArrayList;

public class Project {
	private final ArrayList<EditorSprite2D> Sprites;
	private final String exportPath;
	private final String Name;

	public Project(String path, String name) {
		Sprites = new ArrayList<EditorSprite2D>();
		exportPath = path;
		Name = name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return Name;
	}

	public void addSprite(EditorSprite2D sprite) {
		if (sprite != null)
			Sprites.add(sprite);
	}

	public EditorSprite2D getSprite(int i) {
		return Sprites.get(i);
	}

	public ArrayList<EditorSprite2D> getSprites() {
		return Sprites;
	}

	public String[] getElements() {
		String[] rsa = new String[Sprites.size()];
		for (int i = 0; i < rsa.length; i++) {
			rsa[i] = Sprites.get(i).getName();
		}
		return rsa;
	}

	public ProjectData getData() {
		// TODO Auto-generated method stub
		return new ProjectData(getExportPath(), Name, Sprites);
	}

	public Project(ProjectData pd) {
		Sprites = pd.getSprites();
		exportPath = pd.getPath();
		Name = pd.getName();
	}

	/**
	 * @return the exportPath
	 */
	public String getExportPath() {
		return exportPath;
	}

}
