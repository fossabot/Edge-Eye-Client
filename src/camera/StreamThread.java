package camera;
import java.awt.Dimension;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;

public class StreamThread extends Thread {

	private FFmpegFrameGrabber frameGrabber;
	private final Java2DFrameConverter converter = new Java2DFrameConverter();
	private JPanel liveView;

	public StreamThread(String streamURL, boolean forceTCP, JPanel liveView) {
		this.frameGrabber = new FFmpegFrameGrabber(streamURL);
		this.frameGrabber.setVideoCodec(8);

		if (streamURL.contains("rtsp"))
			this.frameGrabber.setFormat("rtsp");

		if (forceTCP == true)
			this.frameGrabber.setOption("rtsp_transport", "tcp");

		this.liveView = liveView;
	}

	@Override
	public synchronized void run() {
		try {
			this.frameGrabber.start();

			Dimension size = this.liveView.getSize();
			int refreshSize = 0;

			JLabel a;
			JLabel b = null; // Skips remove on first run.
			/*
			 * JLabel background = null;
			 * 
			 * try { background = new JLabel(new ImageIcon(this.scale((ImageIO.read(new
			 * File("loading.png"))), (int) size.getWidth(), (int) size.getHeight()))); }
			 * catch (Exception e) { e.printStackTrace(); }
			 * 
			 * liveView.add(background);
			 */

			while (!Thread.interrupted()) {
				refreshSize += 2;
				if (refreshSize >= 10) {
					size = this.liveView.getSize();
					refreshSize = 0;
				}

				// a = new JLabel(new ImageIcon(converter.convert(this.frameGrabber.grab())));
				a = new JLabel(new ImageIcon(this.scale(converter.convert(this.frameGrabber.grab()),
						(int) size.getWidth(), (int) size.getHeight())));
				// a = new JLabel(new ImageIcon(new
				// ImageIcon(converter.convert(this.frameGrabber.grab().clone()))
				// .getImage().getScaledInstance(800, 600, 1)));
				liveView.add(a);
				liveView.revalidate();
				liveView.repaint();

				if (b != null)
					liveView.remove(b);

				// b = new JLabel(new ImageIcon(converter.convert(this.frameGrabber.grab())));
				b = new JLabel(new ImageIcon(this.scale(converter.convert(this.frameGrabber.grab()),
						(int) size.getWidth(), (int) size.getHeight())));
				// b = new JLabel(new ImageIcon(new
				// ImageIcon(converter.convert(this.frameGrabber.grab().clone()))
				// .getImage().getScaledInstance(800, 600, 1)));
				liveView.add(b);
				liveView.remove(a);
				liveView.revalidate();
				liveView.repaint();
			}

			System.out.println("[HALT] Shutting down stream...");
			frameGrabber.stop();
			frameGrabber.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private BufferedImage scale(BufferedImage src, int w, int h) {
		BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		int x, y;
		int ww = src.getWidth();
		int hh = src.getHeight();
		for (x = 0; x < w; x++) {
			for (y = 0; y < h; y++) {
				int col = src.getRGB(x * ww / w, y * hh / h);
				img.setRGB(x, y, col);
			}
		}
		return img;
	}
}
