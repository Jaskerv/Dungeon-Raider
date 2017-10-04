package dungeonraider.UI;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import dungeonraider.character.Player;
import dungeonraider.engine.Engine;
import dungeonraider.engine.GameObject;
import dungeonraider.engine.Renderer;
import dungeonraider.sprite.Sprite;
import dungeonraider.util.Box;
import dungeonraider.util.Rectangle;

/**
 * The ingame UI for the player
 * 
 * @author Jono Yan
 *
 */
public class IngameInterface implements GameObject {
	private Sprite ui;
	private Player player;
	private int width;
	private int height;
	private int[] pixels;
	public final int XZOOM = 1;
	public final int YZOOM = 1;
	private Box healthBar;

	public IngameInterface(Player player, int width, int height) {
		this.player = player;
		this.width = width;
		this.height = height;
		this.healthBar = new Box(112, 60, 300, 30);
		generateUI();
	}

	private void generateUI() {
		try {
			BufferedImage tempUI = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			BufferedImage UI = Engine.loadImage("resources/Interface/UI - Rectangle 112x60 - 300x30.png");
			tempUI.getGraphics().drawImage(UI, 0, 0, null);
			this.ui = new Sprite(tempUI);
		} catch (Exception e) {
			System.out.println("File not found");
		}
		pixels = ui.getPixels();

	}

	@Override
	public void render(Renderer renderer, int xZoom, int yZoom) {
		for (int y = 0; y < height; y++)
			for (int x = 0; x < width; x++) {
				if (x >= healthBar.getX() && x <= (healthBar.getX() + healthBar.getWidth())) {
					if (y >= healthBar.getY() && y <= (healthBar.getY() + healthBar.getHeight())) {
						pixels[x + y * width] = Color.GREEN.getRGB();
					}
				}
			}
		renderer.renderGUI(ui);
	}

	@Override
	public void update(Engine engine) {

	}

}
