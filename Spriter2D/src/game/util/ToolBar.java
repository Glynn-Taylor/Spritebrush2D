package game.util;

import game.graphics.GUI_Renderer_ColorArray;
import game.graphics.tools.Brush;
import game.graphics.tools.DrawMode;
import game.graphics.tools.Fill;
import game.graphics.tools.Line;
import game.graphics.tools.Pencil;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;

public class ToolBar {
	JFrame frame;
	DrawMode[] modes;
	GUI_Renderer_ColorArray renderer;

	JButton MakeButton(String path, final int indx) {
		Image i = null;

		try {
			i = ImageIO.read(getClass().getResourceAsStream(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ImageIcon icon = new ImageIcon(i);
		JButton jb = new JButton(icon);
		jb.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent me) {
				renderer.setDrawMode(modes[indx]);
				// Mouse.setNativeCursor(new Cursor);
			}
		});
		return jb;
	}

	public ToolBar(GUI_Renderer_ColorArray r) {
		// Tutorial on JToolBar at
		// http://zetcode.com/tutorials/javaswingtutorial/menusandtoolbars/
		renderer = r;
		modes = new DrawMode[] { new Pencil(), new Fill(), new Brush(),
				new Line(r) };
		JButton label = MakeButton("/Materials/Icons/Icon_Pencil.png", 0);
		JButton label2 = MakeButton("/Materials/Icons/Icon_Brush.png", 2);
		JButton label3 = MakeButton("/Materials/Icons/Icon_Fill.png", 1);
		JButton label4 = MakeButton("/Materials/Icons/Icon_Line.png", 3);

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		// JToolBar toolbar1 = new JToolBar(JToolBar.VERTICAL);
		JToolBar toolbar1 = new JToolBar(JToolBar.HORIZONTAL);
		JToolBar toolbar2 = new JToolBar(JToolBar.HORIZONTAL);
		toolbar1.add(label);
		toolbar1.add(label2);
		toolbar1.add(label3);
		toolbar1.setFloatable(false);
		toolbar1.setAlignmentX(0);
		toolbar1.add(label4);
		toolbar2.setFloatable(false);
		// toolbar2.setAlignmentY(1);
		panel.add(toolbar1);
		panel.add(toolbar2, BorderLayout.SOUTH);
		// frame.setUndecorated(true);
		// Add it to a frame.
		frame = new JFrame();
		// frame.setUndecorated(true);
		// frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.getContentPane().add(panel, BorderLayout.WEST);
		// frame.getContentPane().add(label);
		frame.pack();
		frame.setVisible(true);
	}

}
