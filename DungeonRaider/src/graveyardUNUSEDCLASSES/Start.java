package graveyardUNUSEDCLASSES;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import dungeonraider.engine.Engine;
import dungeonraider.util.FontImporter;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

/**
 * Mockup main menu. Contains: Firey background image Dungeon Raider logo
 * created on the internet DRM-Free main menu music
 * 
 * @see https://www.escapemotions.com/experiments/flame/ - Background image
 * @see http://flamingtext.com/ "Blackbird" - Title
 * @see https://www.reddit.com/r/gamedev/comments/6y699a
 *      /i_have_released_my_1363_songs_free_under_creative/ "rollwithit.mp3" -
 *      Music
 * 
 * @author harry
 *
 */
public class Start extends JFrame implements KeyListener, MouseListener {

	private static final int WIDTH = 1280;
	private static final int HEIGHT = 720;
	private Image backgroundImage;
	private Image title;
	/** Audio clip */
	private Clip clip;
	/** This boolean indicates if the user has pressed a button */
	private boolean active = false;
	/** First key press */
	private boolean firstKeyPress = true;
	/** Selection from 'Play' to 'Quit' */
	private int[] menuSelection = new int[3];
	private int index = 0;

	private static final long serialVersionUID = 1L;

	private Font font8Bit;

	/**
	 * Initialises the main menu frame, reads the resources and adds the key
	 * listener
	 * 
	 * @param _title
	 */
	public Start(String _title) {
		super(_title);
		font8Bit = FontImporter.fontImport("resources/fonts/Perfect DOS VGA 437.ttf");
		menuSelection[0] = 200;
		menuSelection[1] = 350;
		menuSelection[2] = 500;
		try {
			backgroundImage = ImageIO.read(new FileInputStream("resources/images/TitleScreen.png"));
			// title = ImageIO.read(
			// new FileInputStream("resources/images/DungeonRaider.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		addKeyListener(this);
		setFocusable(true);
	}

	/**
	 * This method plays the main menu music and draws to the JPanel that is created
	 * within the Frame
	 */
	public void execute() {
		playMusic();
		JPanel drawingPanel = new JPanel() {
			private static final long serialVersionUID = 1L;

			@Override
			public void paintComponent(Graphics _g) {
				super.paintComponent(_g);
				Graphics2D g = (Graphics2D) _g;
				// font smoothening
				RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_ON);

				rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
				g.setRenderingHints(rh);
				g.setFont(new Font("Purisa", Font.PLAIN, 35));
				g.setColor(Color.gray.brighter());
				g.drawImage(backgroundImage, 0, 0, 1280, 720, null);
				// g.drawImage(title, 300, 30, title.getWidth(null), title.getHeight(null),
				// null);

				if (active) {
					g.setFont(font8Bit.deriveFont(Font.PLAIN, 24));
					g.drawString("Play", 600, 250);
					g.drawString("Info", 597, 400);
					g.drawString("Quit", 600, 550);
					g.fillRect(578, menuSelection[index] + 37, 10, 10);
				} else {
					g.setFont(font8Bit.deriveFont(Font.PLAIN, 24));
					g.drawString("PRESS ANY KEY TO CONTINUE", 480, 370);
				}
			}
		};
		this.setContentPane(drawingPanel);
		this.validate();
		setSize(WIDTH, HEIGHT);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setVisible(true);
	}

	/**
	 * This method plays the music when the user is in the main menu
	 */
	public void playMusic() {
		try {
			File file = new File("resources/sountracks/Born To Do This - RuneScape Soundtrack.wav");
			AudioInputStream stream;
			AudioFormat format;
			DataLine.Info info;
			stream = AudioSystem.getAudioInputStream(file);
			format = stream.getFormat();
			info = new DataLine.Info(Clip.class, format);
			clip = (Clip) AudioSystem.getLine(info);
			clip.addLineListener(e -> {
				if (e.getType() == LineEvent.Type.STOP) {
					clip.close();
				}
			});
			clip.open(stream);
			clip.start();
		} catch (Exception e) {

		}
	}

	/**
	 * User interaction with the main menu
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		// Gets rid of the 'press any key to continue' string, and displays
		// the main menu buttons
		if (firstKeyPress) {
			active = true;
			firstKeyPress = false;
			this.repaint();
			return;
		}
		active = true;
		int keyCode = e.getKeyCode();
		// navigating the main menu
		if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W) {
			if (index > 0) {
				index--;
				this.repaint();
			}
		} else if (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S) {
			if (index < 2) {
				index++;
				this.repaint();
			}
		} else if (keyCode == KeyEvent.VK_ENTER) {
			// starts the game (new Engine instance)
			if (index == 0) {
				// prevents freezing when stopping the music
				Thread t = new Thread(new Runnable() {
					public void run() {
						clip.stop();
					}
				});
				t.start();
				this.dispose();
				// Creates the instance of the game
				Engine game = new Engine();
				Thread thread = new Thread(game);
				thread.start();
			}
			// 'Info' button - unimplemented
			else if (index == 1) {
				JOptionPane.showMessageDialog(this, "--Unimplemented--");
			}
			// 'Quit' button
			else if (index == 2) {
				System.exit(0);
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	/**
	 * s
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new Start("Dungeon Raider").execute());
	}

}
