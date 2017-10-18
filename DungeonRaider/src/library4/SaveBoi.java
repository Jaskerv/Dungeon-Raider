package library4;
import gameEngine.engine.*;
import gameEngine.map.*;
import gameEngine.sound.*;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

import gameEngine.character.*;
import gameEngine.character.*;
import gameEngine.item.*;


public class SaveBoi implements Saveable{


	private static final String fname = "resources/save/save09.txt";


private Engine engine;
private Player player;
private Weapon weapon;

public SaveBoi(Engine engine) {this.engine = engine;}

	@Override
	public String save() {
		FileOutputStream fos = null;
		try {
			//	File file = new File("resources/save/save01.txt");
			PrintWriter out = new PrintWriter(fname);
			out.println(player.save());
			out.println(weapon.save());


		}
		catch (Exception e) {
			e.printStackTrace();
		}		return null;
	}


	
	@Override
	public void load() {
		// TODO Auto-generated method stub

	}



}
