package lib;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/*
 * This Library is in charge of saving and loading the game. This will be done
 * using serialization to convert objects into streams of bytes and storing
 * it into a txt file. This allows us to read that txt file and convert it
 * into objects this is a proccess otherwise known as deserialization.
 *
 * @author Ali Almozani
 *
 */

public class SaveGame {

	public static final String fname = "resources/save/save01.txt";

	public static void saveGame(Serializable object) {

		FileOutputStream fos = null;
		try {
			//	File file = new File("resources/save/save01.txt");
			fos = new FileOutputStream(fname.toString());
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(object);
			oos.flush();
			oos.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static String loadGame() {
		if(fileExist()) {
			//true: create a file input stream
			FileInputStream fis = null;

			try {
				fis = new FileInputStream(fname);
				ObjectInputStream ois = new ObjectInputStream(fis);
				SaveGameData loadedObject = (SaveGameData) ois.readObject();
				System.out.println(loadedObject.toString());
				ois.close();
				return loadedObject.toString();
			} catch(ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	//Returns true if file exists within the given directory
	public static boolean fileExist() {
		return new File(fname).isFile();
	}

	public static void main(String[] args) {
		SaveGameData dataSave = new SaveGameData();
		System.out.println(dataSave.toString());
		saveGame(dataSave);
		loadGame();

	}

}