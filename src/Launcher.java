import java.util.ArrayList;

import javax.swing.SwingUtilities;

import camera.Camera;
import gui.GUI;

public class Launcher {

	public static void main(String[] args) {
		ArrayList<Camera> cameras = new ArrayList<>();

		for (int i = 0; i < cameras.size(); i++) {
			cameras.get(i).startSubStream();
		}

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new GUI(cameras);
			}
		});
	}
}
