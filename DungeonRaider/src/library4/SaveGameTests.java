package library4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.Test;

public class SaveGameTests {

	private static final String PATH = "resources/save/save01.txt";

	/**
	 * This test makes sure that the load() method actually
	 * returns something.
	 */
	@Test
	public void testSaveGame_01() {
		String loadSaveState = SaveGame.loadGame();
		assert(loadSaveState != null);
	}

	/**
	 * This test ensures the save data is not only the
	 * toString() results for each object
	 */
	@Test
	public void testSaveGame_02() {
		SaveGameData save = new SaveGameData();
		String currentData = save.toString();
		//saves the game
		SaveGame.saveGame(save);
		File saveData = new File(PATH);
		String currentSaveAfterwards = "";
		Scanner scan = null;
		try {
			scan = new Scanner(saveData);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			fail();
		}
		while (scan.hasNext()) {
			currentSaveAfterwards += scan.next();
		}
		if (currentData.equals(currentSaveAfterwards)) {
			fail("The saved game data should not equal to the non-saved"
					+ " game data.");
		}
	}

	/**
	 * This test ensures that loading a game has the same value
	 * as the save state.
	 */
	@Test
	public void testSaveGame_03() {
		SaveGameData save = new SaveGameData();
		String currentSaveState = save.toString();
		String loadSaveState = SaveGame.loadGame();
		assertEquals(currentSaveState, loadSaveState);
	}
}
