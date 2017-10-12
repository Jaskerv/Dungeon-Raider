package dungeonraider.sound;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.Mixer.Info;

/**
 * This will make a sound library where it makes waves
 * 
 * @author Jono Yan
 *
 */
public class SoundMap {
	private Map<String, String> soundLibrary;
	private Mixer mixer;

	/**
	 * Needs the path of the file that contains the sound
	 * 
	 * 
	 * Format: clipName\\tpathname
	 * 
	 * @param path
	 */
	public SoundMap(String path) {
		this.soundLibrary = new HashMap<>();
		try {
			File file = new File(path);
			parse(file);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void parse(File file) {
		try {
			String content = new Scanner(file).useDelimiter("\\Z").next();
			Map<String, List<String>> lib = object.parse.Parser.parse(content);
			for (Map.Entry<String, List<String>> e : lib.entrySet()) {
				soundLibrary.put(e.getKey(), e.getValue().get(0));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public Clip createClip(String path) {
		Clip clip = null;
		Mixer.Info[] mixInfos = AudioSystem.getMixerInfo();
		for (Info info : mixInfos) {
			if (info.getName().contains("Primary") || info.getName().contains("Default")) {
				this.mixer = AudioSystem.getMixer(info);
			}
		}
		DataLine.Info dataInfo = new DataLine.Info(Clip.class, null);
		try {
			File f = new File(path);
			AudioInputStream stream = AudioSystem.getAudioInputStream(f);
			clip = (Clip) AudioSystem.getLine(dataInfo);
			clip.open(stream);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return clip;
	}

	public Clip getClip(String clipName) {
		if (soundLibrary.containsKey(clipName)) {
			return createClip(soundLibrary.get(clipName));
		}
		return null;
	}

	public void playClip(Clip clip) {
		clip.start();
	}

	public void playClip(String s) {
		Clip clip = getClip(s);
		clip.start();
	}

	public void playClipLoop(Clip clip, int loop) {
		clip.loop(loop);
		clip.start();
	}

	public void playClipLoop(String s, int loop) {
		Clip clip = getClip(s);
		clip.loop(loop);
		clip.start();
	}
}