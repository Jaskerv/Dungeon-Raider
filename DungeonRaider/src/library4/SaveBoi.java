package library4;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Scanner;

import gameEngine.character.Player;
import gameEngine.engine.Engine;
import gameEngine.item.Weapon;

public class SaveBoi {

	private static final String fname = "resources/save/save09.txt";

	private Engine engine;
	private Player player;
	private Weapon weapon;

	public static final String CLASS_SEPARATOR = "+";

	public SaveBoi(Engine engine) {
		this.engine = engine;
		this.player = engine.getPlayer();
		this.weapon = player.getPrimaryWeapon();
	}

	public void save() {
		FileOutputStream fos = null;
		File saveFile = new File(fname);
		BufferedWriter out = null;
		try {
			// File file = new File("resources/save/save01.txt");
			out = new BufferedWriter(new FileWriter(saveFile));

			out.write(player.save() + CLASS_SEPARATOR);
			out.write(engine.save());

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void load(File file) {

	}

}
