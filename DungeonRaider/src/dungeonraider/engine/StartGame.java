package dungeonraider.engine;

import java.awt.BasicStroke;
import java.awt.Canvas;
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
import java.awt.image.BufferedImage;
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
import dungeonraider.sprite.Sprite;
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
 * @author Jono Yan, Harry Nguon
 *
 */
public class StartGame extends Canvas implements KeyListener, MouseListener, GameObject {

	private BufferedImage backgroundImage;
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
	private Engine engine;
	private final int xZoom = 1;
	private final int yZoom = 1;
	private BufferedImage img;
	private Graphics g;
	private Sprite sprite;
	private boolean up = false;
	private boolean down = false;
	private boolean enter = false;

	/**
	 * Initialises the main menu frame, reads the resources and adds the key
	 * listener
	 * 
	 * @param _title
	 */
	public StartGame(Engine engine) {
		super();
		img = new BufferedImage(Engine.WIDTH, Engine.HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = img.getGraphics();
		this.engine = engine;
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
		playMusic();
	}

	/**
	 * This method plays the main menu music and draws to the JPanel that is created
	 * within the Frame
	 */
	public void execute() {

		this.validate();
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
			clip.loop(100);
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
			return;
		}
		int keyCode = e.getKeyCode();
		// navigating the main menu
		if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W) {
			if (index > 0) {
				index--;
				Clip clip = engine.getSoundLibrary().getSoundlibrary().get("cursorMove");
				clip.start();
			}
		} else if (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S) {
			if (index < 2) {
				index++;
			}
		} else if (keyCode == KeyEvent.VK_ENTER) {
			// starts the game (new Engine instance)
			if (index == 0) {
				engine.switchCanvas();
			}
			// 'Info' button - unimplemented
			else if (index == 1) {
				System.out.println("un");
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

	@Override
	public void render(Renderer renderer, int xZoom, int yZoom) {
		g.drawImage(backgroundImage, 0, 0, backgroundImage.getWidth(), backgroundImage.getHeight(), null);
		if (active) {
			g.setColor(Color.white);
			g.setFont(font8Bit.deriveFont(Font.PLAIN, 24));
			g.drawString("Play", 600, 250);
			g.drawString("Info", 597, 400);
			g.drawString("Quit", 600, 550);
			g.fillRect(578, menuSelection[index] + 37, 10, 10);
		} else {
			g.setColor(Color.white);
			g.setFont(font8Bit.deriveFont(Font.PLAIN, 24));
			g.drawString("PRESS ANY KEY TO CONTINUE", 480, 370);
		}
		sprite = new Sprite(img);
		renderer.renderGUI(sprite);
	}

	@Override
	public void update(Engine engine) {

	}

	/**
	 * @return the xZoom
	 */
	public int getxZoom() {
		return xZoom;
	}

	/**
	 * @return the yZoom
	 */
	public int getyZoom() {
		return yZoom;
	}

	public void stopMusic() {
		clip.stop();
	}
}
