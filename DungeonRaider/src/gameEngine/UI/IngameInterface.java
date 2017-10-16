package gameEngine.UI;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import gameEngine.character.Player;
import gameEngine.engine.Engine;
import gameEngine.engine.GameObject;
import gameEngine.engine.Renderer;
import gameEngine.item.Item;
import gameEngine.sprite.Sprite;
import gameEngine.util.Box;

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
	private final int HPMAX = 300;
	private final int hpX = 112;
	private final int hpY = 60;
	private final int hpHeight = 30;

	public IngameInterface(Player player, int width, int height) {
		this.player = player;
		this.width = width;
		this.height = height;
		this.healthBar = new Box(hpX, hpY, HPMAX, hpHeight);
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
		pixels = ui.getPixels().clone();
	}

	@Override
	public void render(Renderer renderer, int xZoom, int yZoom) {
		this.ui.setPixels(pixels.clone());
		List<Item> itemList = player.getInventory().getInventory();
		for (int i = 0; i < itemList.size(); i++) {
			Sprite itemSprite = itemList.get(i).getSprite();
			if (itemList.get(i).getPickedUp()) {
				int itemNumber = 100 + (i*50);
				ui.drawOnSprite(itemSprite, itemNumber, 100, 3, 3);
			}
		}
		int r = (int) (Math.random() * 7000000);
		for (int y = 0; y < height; y++)
			for (int x = 0; x < width; x++) {
				if (healthBar.getWidth() != 0) {
					if (x >= healthBar.getX() && x <= (healthBar.getX() + healthBar.getWidth())) {
						if (y >= healthBar.getY() && y <= (healthBar.getY() + healthBar.getHeight())) {
							int[] tempArray = ui.getPixels();
							tempArray[x + y * width] = r;
							// tempArray[x + y * width] = Color.green.getRGB();
							this.ui.setPixels(tempArray);
						}
					}
				}
			}
		renderer.renderGUI(ui);
	}

	@Override
	public void update(Engine engine) {
		double hpPerc = ((double) player.getHp()) / ((double) player.getHpMax());
		int hpBar = (int) (hpPerc * (double) HPMAX);
		Player player = engine.getPlayer();
		if (hpBar <= HPMAX && hpBar >= 0) {
			this.healthBar = new Box(hpX, hpY, hpBar, hpHeight);
		}
	}

}
