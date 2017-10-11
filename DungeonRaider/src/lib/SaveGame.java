package lib;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class SaveGame {

	public static void saveGame(Serializable object) {

		FileOutputStream fos = null;
		try {
			File file = new File("resources/save/save01.txt");
			fos = new FileOutputStream(file.toString());
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(object);
			oos.flush();
			oos.close();
		}
		catch (Exception e) {

		}

	}

	public static void loadGame() {

	}

	public static void main(String[] args) {
		DataSave dataSave = new DataSave();
		System.out.println(dataSave.toString());
		saveGame(dataSave);

	}

}