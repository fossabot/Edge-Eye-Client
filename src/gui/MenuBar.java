package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MenuBar {

	private static MenuItems items = new MenuItems();

	public JMenuBar setupMainMenuBar() {
		JMenuBar menuBar = new JMenuBar();

		// File Menu
		JMenu menuFile = new JMenu("File");

		JMenuItem menuAdd = new JMenuItem("Add New Camera");
		menuAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				items.addCamera();
			}
		});

		JMenuItem menuManage = new JMenuItem("Manage Cameras");
		menuManage.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				items.manageCameras();
			}
		});

		JMenuItem menuImport = new JMenuItem("Import Cameras & Configuration");
		menuImport.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				items.importCamConfig();
			}
		});

		menuFile.add(menuAdd);
		menuFile.add(menuManage);
		menuFile.add(menuImport);

		// View Menu
		JMenu menuView = new JMenu("View");

		JMenuItem menuZoom = new JMenuItem("Configure Zoom");

		menuView.add(menuZoom);

		// Settings Menu
		JMenu menuSettings = new JMenu("Settings");

		JMenuItem menuLayout1 = new JMenuItem("Set Layout 1");

		menuSettings.add(menuLayout1);

		// Server Menu
		JMenu menuServer = new JMenu("Server");

		JMenuItem menuConnectServer = new JMenuItem("Connect to Server");
		JMenuItem menuServerPreferences = new JMenuItem("Preferences");

		menuServer.add(menuConnectServer);
		menuServer.add(menuServerPreferences);

		// Help Menu
		JMenu menuHelp = new JMenu("Help");

		JMenuItem menuAbout = new JMenuItem("About");
		JMenuItem menuUpdates = new JMenuItem("Check for Updates");
		JMenuItem menuLicensing = new JMenuItem("Licensing");

		menuHelp.add(menuAbout);
		menuHelp.add(menuLicensing);
		menuHelp.add(menuUpdates);

		// Add to top menu bar
		menuBar.add(menuFile);
		menuBar.add(menuView);
		menuBar.add(menuSettings);
		menuBar.add(menuServer);
		menuBar.add(menuHelp);

		return menuBar;
	}

}
