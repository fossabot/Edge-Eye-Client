package camera;
import javax.swing.JPanel;

public class Camera {

	private int id;

	private String camName;
	private String subStreamURL;
	private String mainStreamURL;

	private boolean forceTCP;

	private StreamThread subStreamThread;
	private StreamThread mainStreamThread;

	private JPanel liveView = new JPanel();

	/**
	 * Creates a Camera Java object.
	 * 
	 * @param String - ID number
	 * @param String - Name
	 * @param String - IP
	 * @param String - Protocall (HTTP/RTSP)
	 * @param String - Stream URL
	 * @param String - Username
	 * @param String - Password
	 * @param String - ForceTCP (true/false)
	 */
	public Camera(String id, String camName, String ip, String protocall, String mainStreamURL, String subStreamURL,
			String username, String password, String forceTCP) {
		this.id = Integer.parseInt(id);
		this.camName = camName;
		this.forceTCP = Boolean.parseBoolean(forceTCP);

		if (username == null || username.equalsIgnoreCase("null")) {
			this.mainStreamURL = protocall + "://" + ip + mainStreamURL;
			this.subStreamURL = protocall + "://" + ip + subStreamURL;
		}

		else {
			this.mainStreamURL = protocall + "://" + username + ":" + password + "@" + ip + mainStreamURL;
			this.subStreamURL = protocall + "://" + username + ":" + password + "@" + ip + subStreamURL;
		}
	}

	public int getCameraID() {
		return this.id;
	}

	public String getCamName() {
		return this.camName;
	}

	public JPanel getLiveView() {
		return liveView;
	}

	// TODO When another camera main stream is started, stop this one.
	public void startMainStream() {
		this.mainStreamThread = new StreamThread(this.mainStreamURL, forceTCP, liveView);
		this.mainStreamThread.start();
	}

	public void startSubStream() {
		this.subStreamThread = new StreamThread(this.subStreamURL, forceTCP, liveView);
		this.subStreamThread.start();
	}

	public boolean getMainThreadState() {
		try {
			return (this.mainStreamThread.getState() != Thread.State.TERMINATED); // Returns true if running.
		} catch (NullPointerException e) { // Throws NullPointerException if we didn't create a StreamThread.
			return false;
		}
	}

	public boolean getSubThreadState() {
		try {
			return (this.subStreamThread.getState() != Thread.State.TERMINATED); // Returns true if running.
		} catch (NullPointerException n) { // Throws NullPointerException if we didn't create a StreamThread.
			return false;
		}

	}

	public void terminateMainStream() {
		this.mainStreamThread.interrupt();
	}

	public void terminateSubStream() {
		this.subStreamThread.interrupt();
	}
}
