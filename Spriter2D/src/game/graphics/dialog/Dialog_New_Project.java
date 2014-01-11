package game.graphics.dialog;

import game.data.browser.FolderBrowser;
import game.states.State_SPRITER;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Dialog_New_Project

{
	JButton click;
	JDialog inputbox;
	JLabel lPath;
	JLabel lName;
	JTextField txtPath;
	JTextField txtName;
	FolderBrowser fBrowser = new FolderBrowser();
	State_SPRITER state;

	public Dialog_New_Project(State_SPRITER s) {
		state = s;
		JButton cancel = new JButton("cancel");
		cancel.setBounds(70, 170, 90, 40);
		cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				// add ur code here to manipulate text field values

				inputbox.dispose();
				fBrowser.DestroyMe();
			}

		}

		);
		JButton ok = new JButton("ok");
		ok.setBounds(160, 170, 70, 40);
		ok.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				// add ur code here to manipulate text field values
				state.NewProject(txtPath.getText(), txtName.getText());
				inputbox.dispose();
				fBrowser.DestroyMe();
			}

		}

		);
		JButton browse = new JButton("browse");
		browse.setBounds(70, 70, 160, 40);
		browse.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				// add ur code here to manipulate text field values

				txtPath.setText(fBrowser.GetOpenPath());
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
		inputbox.setTitle("New project..");
		inputbox.getContentPane().setLayout(null);
		inputbox.setBounds(150, 190, 300, 250);

		lPath = new JLabel("Project root: ");
		lName = new JLabel("Name: ");

		lPath.setBounds(10, 20, 120, 40);
		lName.setBounds(10, 120, 120, 40);

		inputbox.getContentPane().add(lPath);
		inputbox.getContentPane().add(lName);

		txtPath = new JTextField("C:/Workspace/MyProject");
		txtName = new JTextField("Project1");

		txtPath.setBounds(80, 20, 200, 40);
		txtName.setBounds(80, 120, 200, 40);

		inputbox.getContentPane().add(txtPath);
		inputbox.getContentPane().add(txtName);

		inputbox.getContentPane().add(ok);
		inputbox.getContentPane().add(cancel);
		inputbox.getContentPane().add(browse);
		inputbox.setAlwaysOnTop(true);
		inputbox.setModal(true);
		inputbox.setVisible(true);

		inputbox.toFront();
		inputbox.repaint();

	}

}
