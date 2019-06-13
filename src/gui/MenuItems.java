package gui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;

public class MenuItems {

	public void addCamera() {
		JFrame addCam = new JFrame();
		addCam.setTitle("Add Camera | Edge Eye");
		addCam.getContentPane().setBackground(Color.WHITE);
		addCam.setPreferredSize(new Dimension(800, 600));

		addCam.pack();
		addCam.setVisible(true);
	}

	public void manageCameras() {
		JFrame manageCams = new JFrame();
		manageCams.setTitle("Manage Cameras | Edge Eye");
		manageCams.getContentPane().setBackground(Color.WHITE);
		manageCams.setPreferredSize(new Dimension(800, 600));

		manageCams.pack();
		manageCams.setVisible(true);
	}

	public void importCamConfig() {
		JFrame manageCams = new JFrame();
		manageCams.setTitle("Import Configuration | Edge Eye");
		manageCams.getContentPane().setBackground(Color.WHITE);
		manageCams.setPreferredSize(new Dimension(600, 300));

		manageCams.pack();
		manageCams.setVisible(true);
	}
}
