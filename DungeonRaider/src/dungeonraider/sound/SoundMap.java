package dungeonraider.sound;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;

/**
 * This will make a sound library where it makes waves
 * 
 * @author Jono Yan
 *
 */
public class SoundMap {
	private Map<String, Clip> soundlibrary;

	/**
	 * Needs the path of the file that contains the sound
	 * 
	 * 
	 * Format: clipName\\tpathname
	 * 
	 * @param path
	 */
	public SoundMap(String path) {
		this.soundlibrary = new HashMap<>();
		try {

			File file = new File(path);
			parse(file);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void parse(File file) {
		try {
			Scanner sc = new Scanner(file);
			while (sc.hasNext()) {
				String line = sc.nextLine();
				String[] split = line.split("\\t");
				String clipName = split[0];
				try {
					File f = new File(split[1]);
					AudioInputStream stream;
					AudioFormat format;
					DataLine.Info info;
					stream = AudioSystem.getAudioInputStream(f);
					format = stream.getFormat();
					info = new DataLine.Info(Clip.class, format);
					Clip clip = (Clip) AudioSystem.getLine(info);
					clip.addLineListener(e -> {
						if (e.getType() == LineEvent.Type.STOP) {
							clip.close();
						}
					});
					soundlibrary.put(clipName, clip);
				} catch (Exception e) {

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return the soundlibrary
	 */
	public Map<String, Clip> getSoundlibrary() {
		return soundlibrary;
	}

}