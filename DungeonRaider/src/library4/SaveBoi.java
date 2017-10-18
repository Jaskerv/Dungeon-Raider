package library4;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import gameEngine.character.Player;
import gameEngine.engine.Engine;
import gameEngine.item.Weapon;

public class SaveBoi implements Saveable {

	private static final String fname = "resources/save/save09.txt";

	private Engine engine;
	private Player player;
	private Weapon weapon;

	public SaveBoi(Engine engine) {
		this.engine = engine;
		this.player = engine.getPlayer();
		this.weapon = player.getPrimaryWeapon();
	}

	@Override
	public String save() {
		FileOutputStream fos = null;
		File saveFile = new File(fname);
		BufferedWriter out = null;
		try {
			// File file = new File("resources/save/save01.txt");
			out = new BufferedWriter(new FileWriter(saveFile));

			out.write(player.save());
			out.write(weapon.save());

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public void load() {

	}

}
