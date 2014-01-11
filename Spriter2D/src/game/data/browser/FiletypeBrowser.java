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
 * Creates a file browser that looks for gamesaves in the 
 default game save folder
 */
package game.data.browser;

import java.io.File;
import java.io.IOException;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileFilter;

public class FiletypeBrowser extends JFrame {

	private static final long serialVersionUID = 4382570231691080960L;
	final String FileType;
	/*
	 * public String GetOpenPath() { JFileChooser chooser = new JFileChooser();
	 * File file = new File(chooser.getFileSystemView().getDefaultDirectory()
	 * .toString());
	 * 
	 * chooser.setCurrentDirectory(file);
	 * chooser.setAcceptAllFileFilterUsed(true); // chooser.setFileFilter(new
	 * ImageFileFilter());
	 * chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); int retVal =
	 * chooser.showOpenDialog(this); if (retVal == JFileChooser.APPROVE_OPTION)
	 * { File myFile = chooser.getSelectedFile(); return
	 * myFile.getAbsolutePath(); } return null;
	 * 
	 * }
	 */
	JDialog inputbox;

	public FiletypeBrowser(String fileType) {
		this.FileType = fileType;
	}

	public String GetOpenPath() {
		return GetPath(true);
	}
	public String GetSavePath() {
		return GetPath(false);

	}
	private String GetPath(boolean Open) {
		inputbox = new JDialog();
		inputbox.setAlwaysOnTop(true);

		inputbox.setAlwaysOnTop(true);
		inputbox.setModal(false);
		inputbox.setVisible(true);

		inputbox.toFront();
		inputbox.repaint();
		JFileChooser chooser = new JFileChooser();
		File file = new File(chooser.getFileSystemView().getDefaultDirectory()
				.toString());

		chooser.setCurrentDirectory(file);
		chooser.setAcceptAllFileFilterUsed(true);
		chooser.setFileFilter(new ImageFileFilter());

		int retVal = Open?chooser.showOpenDialog(inputbox):chooser.showSaveDialog(inputbox);
		chooser.requestFocus();
		chooser.requestFocusInWindow();
		chooser.repaint();
		// inputbox.setVisible(false);
		if (retVal == JFileChooser.APPROVE_OPTION) {
			File myFile = chooser.getSelectedFile();
			 inputbox.dispose();
			try {
				return myFile.getCanonicalPath() + (myFile.getCanonicalPath().endsWith(".png")?"": ("." + FileType));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		inputbox.dispose();
		return null;

	}
	public void DestroyMe() {
		// inputbox.dispose();
		if(inputbox!=null)
			inputbox.dispose();
		this.dispose();

	}

	class ImageFileFilter extends FileFilter {

		@Override
		public boolean accept(File pathname) {
			if (pathname.isDirectory())
				return true;
			if (pathname.getName().endsWith("." + FileType))
				return true;

			return false;
		}

		@Override
		public String getDescription() {
			return "." + FileType;

		}
	}
}
