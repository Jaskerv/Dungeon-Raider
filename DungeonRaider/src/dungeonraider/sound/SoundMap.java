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
import javax.sound.sampled.FloatControl;
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
	 * If clip wants to be played at normal db
	 */
	public final static float DEFAULTDB = -1000000000;

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

	/**
	 * Creates a clip with the correct path
	 * 
	 * @param path
	 * @return
	 */
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

	/**
	 * Returns a clip from the library
	 * 
	 * @param clipName
	 * @return
	 */
	public Clip getClip(String clipName) {
		if (soundLibrary.containsKey(clipName)) {
			return createClip(soundLibrary.get(clipName));
		}
		return null;
	}

	/**
	 * Plays clip once with the sound db f
	 * 
	 * @param clip
	 * @param f
	 *            f has to be between -80f and 10f or DEFAULTDB otherwise will be
	 *            played at normal clip db
	 */
	public void playClip(Clip clip, float f) {
		if (f != DEFAULTDB && (f >= -80f && f <= 10f)) {
			FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(f);
		}
		clip.start();
	}

	/**
	 * Plays clip once with the sound db f
	 * 
	 * @param s
	 * @param f
	 *            f has to be between -80f and 10f or DEFAULTDB otherwise will be
	 *            played at normal clip db
	 */
	public void playClip(String s, float f) {
		Clip clip = getClip(s);
		if (f != DEFAULTDB && (f >= -80f && f <= 10f)) {
			FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(f);
		}
		clip.start();
	}

	/**
	 * Plays clip with the sound db f at loop times
	 * 
	 * @param clip
	 * @param loop
	 *            Loop can be looped as many times as needed, if want to repeat
	 *            forever, use Clip.LOOP_CONTINUOUSLY
	 * @param f
	 *            has to be between -80f and 10f or DEFAULTDB otherwise will be
	 *            played at normal clip db
	 */
	public void playClipLoop(Clip clip, int loop, float f) {
		if (f != DEFAULTDB && (f >= -80f && f <= 10f)) {
			FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(f);
		}
		clip.loop(loop);
		clip.start();
	}

	/**
	 * Plays clip with the sound db f at loop times
	 * 
	 * @param s
	 * @param loop
	 *            Loop can be looped as many times as needed, if want to repeat
	 *            forever, use Clip.LOOP_CONTINUOUSLY
	 * @param f
	 *            has to be between -80f and 10f or DEFAULTDB otherwise will be
	 *            played at normal clip db
	 */
	public void playClipLoop(String s, int loop, float f) {
		Clip clip = getClip(s);
		if (f != DEFAULTDB && (f >= -80f && f <= 10f)) {
			FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(f);
		}
		clip.loop(loop);
		clip.start();
	}
}