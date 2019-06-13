package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import camera.Camera;

@SuppressWarnings("serial")
public class GUI extends JFrame {
	private static GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	private static int screenWidth = gd.getDisplayMode().getWidth();
	private static int screenHeight = gd.getDisplayMode().getHeight();

	private static int programWidth = screenWidth;
	private static int programHeight = screenHeight;

	private static MenuBar mainProgramMenu = new MenuBar();

	private static ArrayList<Camera> cameras;

	public GUI(ArrayList<Camera> cams) {
		super("Remote View | Edge Eye");
		cameras = cams;

		this.setJMenuBar(mainProgramMenu.setupMainMenuBar());

		this.getContentPane().setBackground(Color.BLACK);

		this.setLayout(new GridLayout(0, 2)); // https://docs.oracle.com/javase/7/docs/api/java/awt/GridLayout.html#GridLayout()

		// adds window event listener
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent event) {
				int reply = JOptionPane.showConfirmDialog(GUI.this, "Are you sure you want to quit?", "Exit",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (reply == JOptionPane.YES_OPTION) {
					dispose();

					for (int i = 0; i < cameras.size(); i++) {
						// Shutdown main stream
						if (cameras.get(i).getMainThreadState() == true) {
							cameras.get(i).terminateMainStream();

							do {
								try {
									Thread.sleep(500);
								} catch (Exception e) {
									e.printStackTrace();
								}
							} while (cameras.get(i).getMainThreadState() == true);
						}

						// Shutdown sub stream
						if (cameras.get(i).getSubThreadState() == true) {
							cameras.get(i).terminateSubStream();

							do {
								try {
									Thread.sleep(500);
								} catch (Exception e) {
									e.printStackTrace();
								}
							} while (cameras.get(i).getSubThreadState() == true);
						}
					}

					System.out.println("[INFO] Program Exiting");
					System.exit(0);
				}
			}
		});

		// Set Program Icon
		try {
			setIconImage(new ImageIcon(ImageIO.read(new File("program-icon.png"))).getImage());
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (int i = 0; i < cameras.size(); i++) {
			add(cameras.get(i).getLiveView());
		}

		// centers on screen
		this.setPreferredSize(new Dimension(programWidth, programHeight));

		// Centers program on screen.
		// Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		// Point middle = new Point(screenSize.width / 2, screenSize.height / 2);
		// Point newLocation = new Point(middle.x - (getWidth() / 2), middle.y -
		// (getHeight() / 2));
		this.setLocationRelativeTo(null);

		this.pack();
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setVisible(true);
	}

}