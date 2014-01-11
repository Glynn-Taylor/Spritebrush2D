package game.util;

import game.data.sprite.Colour;
import game.graphics.GUI_Renderer_ColorArray;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFrame;

//http://www.java2s.com/Code/Java/Swing-JFC/ListeningforOKandCancelEventsinaJColorChooserDialog.htm
public class ColorPicker extends JFrame {
	GUI_Renderer_ColorArray ColorRenderer;
	JColorChooser Chooser = new JColorChooser();
	ActionListener okListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent evt) {
			java.awt.Color newColor = Chooser.getColor();
			if (ColorRenderer != null)
				ColorRenderer.setPrimary(new Colour(newColor.getRed() / 255f,
						newColor.getGreen() / 255f, newColor.getBlue() / 255f));
		}
	};
	ActionListener cancelListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent evt) {
			java.awt.Color newColor = Chooser.getColor();
		}
	};

	public void pickColor() {
		if (!Chooser.isShowing()) {
			JDialog jd = JColorChooser.createDialog(this, "Color picker",
					false, Chooser, okListener, cancelListener);
			jd.setVisible(true);
			jd.setAlwaysOnTop(true);
		}

	}

	public ColorPicker(GUI_Renderer_ColorArray rend) throws HeadlessException {
		ColorRenderer = rend;
	}

}
