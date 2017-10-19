package gameEngine.UI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
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
import gameEngine.util.FontImporter;

/**
 * The ingame UI for the player
 *
 * @author Jono Yan
 *
 */
public class IngameInterface implements GameObject {
	private BufferedImage img;
	private Sprite ui;
	private Sprite coinSprite;
	private Engine engine;
	private int width;
	private int height;
	private int[] pixels;
	public final int XZOOM = 1;
	public final int YZOOM = 1;
	private Box healthBar;
	// pixel location
	private final int HPMAX = 300;
	private final int HPX = 112;
	private final int HPY = 60;
	private final int HPHEIGHT = 30;
	private Font font;
	private BufferedImage potion;

	public IngameInterface(Engine engine, int width, int height,
			Sprite coinSprite) {
		this.engine = engine;
		this.width = width;
		this.height = height;
		this.healthBar = new Box(HPX, HPY, HPMAX, HPHEIGHT);
		this.coinSprite = coinSprite;
		this.font = FontImporter
				.fontImport("resources/fonts/Perfect DOS VGA 437.ttf");
		this.potion = Engine.loadImage("resources/images/potion.png");
		generateUI();
	}

	private void generateUI() {
		img = Engine.loadImage(
				"resources/Interface/UI - Rectangle 112x60 - 300x30.png");
	}

	@Override
	public void render(Renderer renderer, int xZoom, int yZoom) {
		BufferedImage clone = new BufferedImage(img.getWidth(), img.getHeight(),
				BufferedImage.TYPE_INT_RGB);
		Graphics g = clone.getGraphics();
		/**
		 * Draw anything with graphics after this
		 */
		g.drawImage(img, 0, 0, null);
		g.setColor(Color.GREEN);
		g.fillRect(HPX, HPY, healthBar.getWidth(), HPHEIGHT);
		g.setColor(Color.WHITE);
		g.setFont(font.deriveFont(24f));
		g.drawString(getPlayerBalance(), 140, 118);
		g.setColor(Color.YELLOW);
		g.drawString("Lvl: "
				+ engine.getPlayer().getPrimaryWeapon().getNumberOfUpgrades(),
				115, 150);
		g.setColor(Color.RED);
		g.setFont(font.deriveFont(17f));
		g.drawString(
				"Dmg: " + engine.getPlayer().getPrimaryWeapon().getDamage(),
				115, 170);
		g.drawString("Crit: "
				+ engine.getPlayer().getPrimaryWeapon().getCritChance() + "%",
				115, 190);
		ui = new Sprite(clone);

		/**
		 * Draw sprites after this
		 */
		List<Item> itemList = engine.getPlayer().getInventory().getInventory();
		for (int i = 0; i < itemList.size(); i++) {
			Sprite itemSprite = itemList.get(i).getSprite();
			int itemNumber = 110 + (i * 50);
			ui.drawOnSprite(itemSprite, itemNumber, 200, 3, 3);

		}
		// coin sprite
		ui.drawOnSprite(coinSprite, 98, 75, 3, 3);

		renderer.renderGUI(ui);
	}

	@Override
	public void update(Engine engine) {
		double hpPerc = ((double) engine.getPlayer().getHp())
				/ ((double) engine.getPlayer().getHpMax());
		int hpBar = (int) (hpPerc * (double) HPMAX);
		Player player = engine.getPlayer();
		if (hpBar <= HPMAX && hpBar >= 0) {
			this.healthBar = new Box(HPX, HPY, hpBar, HPHEIGHT);
		}
	}

	public String getPlayerBalance() {
		StringBuilder balance = new StringBuilder();
		int balanceLength = 8;
		String playerBalance = Integer
				.toString(engine.getPlayer().getGoldTotal());
		// for (int i = 0; i < balanceLength; i++) {
		// if (i < (balanceLength - playerBalance.length())){
		// balance.append("0");
		// }
		// else {
		// balance.append(playerBalance.charAt(balanceLength -
		// playerBalance.length()));
		// }
		// }
		return playerBalance;

	}

}
