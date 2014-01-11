package game.graphics.dialog;

import game.graphics.GUI_Renderer_ColorArray;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Dialog_New_Sprite

{
	JButton click;
	JDialog inputbox;
	JLabel lSpriteName;
	JLabel lWidth;
	JLabel lHeight;
	JLabel lName;
	JLabel lDuration;
	JLabel lFrames;
	JTextField txtSpriteName;
	JTextField txtWidth;
	JTextField txtHeight;
	JTextField txtName;
	JTextField txtDuration;
	JTextField txtFrames;
	GUI_Renderer_ColorArray Renderer;
	boolean NewSprite = false;

	public Dialog_New_Sprite(GUI_Renderer_ColorArray renderer, boolean newSprite) {
		Renderer = renderer;
		NewSprite = newSprite;
		JButton cancel = new JButton("cancel");
		cancel.setBounds(70, 290, 90, 40);
		cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				// add ur code here to manipulate text field values

				inputbox.dispose();

			}

		}

		);
		JButton ok = new JButton("ok");
		ok.setBounds(160, 290, 70, 40);
		ok.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				// add ur code here to manipulate text field values
				if (NewSprite) {
					Renderer.CreateSprite(Integer.parseInt(txtWidth.getText()),
							Integer.parseInt(txtHeight.getText()),
							txtName.getText(),
							Integer.parseInt(txtFrames.getText()),
							Integer.parseInt(txtDuration.getText()),
							txtSpriteName.getText());
				} else {
					Renderer.CreateAnimation(
							Integer.parseInt(txtWidth.getText()),
							Integer.parseInt(txtHeight.getText()),
							txtName.getText(),
							Integer.parseInt(txtFrames.getText()),
							Integer.parseInt(txtDuration.getText()));
				}
				inputbox.dispose();
			}

		}

		);

		// inputbox = new JDialog(this, true);
		inputbox = new JDialog();
		inputbox.setAlwaysOnTop(true);
		inputbox.addWindowListener(new WindowListener() {

			@Override
			public void windowActivated(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowClosed(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowClosing(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeactivated(WindowEvent arg0) {
				inputbox.toFront();

			}

			@Override
			public void windowDeiconified(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowIconified(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowOpened(WindowEvent arg0) {
				// TODO Auto-generated method stub
				inputbox.toFront();
				inputbox.setAlwaysOnTop(true);
			}

		}

		);
		inputbox.setTitle("First animation");
		inputbox.getContentPane().setLayout(null);
		inputbox.setBounds(150, 150, 340, 370);

		lSpriteName = new JLabel("Sprite name:");
		lWidth = new JLabel("Frame width:");
		lHeight = new JLabel("Frame height:");
		lName = new JLabel("Animation name:");
		lDuration = new JLabel("Duration:");
		lFrames = new JLabel("Total frames:");

		lSpriteName.setBounds(20, 20, 120, 40);
		lWidth.setBounds(20, 60, 120, 40);
		lHeight.setBounds(20, 110, 120, 40);
		lName.setBounds(20, 150, 120, 40);
		lDuration.setBounds(20, 190, 120, 40);
		lFrames.setBounds(20, 230, 120, 40);

		if (newSprite)
			inputbox.getContentPane().add(lSpriteName);
		inputbox.getContentPane().add(lWidth);
		inputbox.getContentPane().add(lHeight);
		inputbox.getContentPane().add(lName);
		inputbox.getContentPane().add(lDuration);
		inputbox.getContentPane().add(lFrames);

		txtSpriteName = new JTextField("Sprite Name");
		txtWidth = new JTextField("16");
		txtHeight = new JTextField("16");
		txtName = new JTextField("anim1");
		txtDuration = new JTextField("60");
		txtFrames = new JTextField("5");

		txtSpriteName.setBounds(160, 20, 120, 40);
		txtWidth.setBounds(160, 60, 120, 40);
		txtHeight.setBounds(160, 110, 120, 40);
		txtName.setBounds(160, 150, 120, 40);
		txtDuration.setBounds(160, 190, 120, 40);
		txtFrames.setBounds(160, 230, 120, 40);

		if (newSprite)
			inputbox.getContentPane().add(txtSpriteName);
		inputbox.getContentPane().add(txtWidth);
		inputbox.getContentPane().add(txtHeight);
		inputbox.getContentPane().add(txtName);
		inputbox.getContentPane().add(txtDuration);
		inputbox.getContentPane().add(txtFrames);
		inputbox.getContentPane().add(ok);
		inputbox.getContentPane().add(cancel);
		// inputbox.s

		inputbox.setAlwaysOnTop(true);
		inputbox.setModal(true);
		inputbox.setVisible(true);

		inputbox.toFront();
		inputbox.repaint();
		// inputbox.requestFocus();
		// inputbox.requestFocus();
		// this.toFront();
		// this.requestFocus();
		// inputbox.toFront();
		// inputbox.requestFocus();
	}

}
