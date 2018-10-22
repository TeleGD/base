package app;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Font;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.NullAudio;
import org.newdawn.slick.openal.SoundStore;
import org.newdawn.slick.opengl.EmptyImageData;

public class AppLoader {

	private static Map <String, Map <Integer, Map <Integer, Font>>> fonts;
	private static Map <String, Image> images;
	private static Map <String, Audio> audios;

	static {
		AppLoader.fonts = new HashMap <String, Map <Integer, Map <Integer, Font>>> ();
		AppLoader.images = new HashMap <String, Image> ();
		AppLoader.audios = new HashMap <String, Audio> ();
	}

	private static InputStream openStream (String filename) {
		InputStream stream = null;
		if (filename != null && filename.startsWith ("/")) {
			try {
				stream = new FileInputStream (System.class.getResource (filename).getPath ());
			} catch (IOException | NullPointerException error) {}
		}
		return stream;
	}

	private static void closeStream (InputStream stream) {
		try {
			stream.close ();
		} catch (IOException | NullPointerException error) {}
	}

	public static Font loadFont (String filename, int type, int size) {
		Map <Integer, Map <Integer, Font>> types = null;
		if ((types = AppLoader.fonts.get (filename)) == null) {
			types = new HashMap <Integer, Map <Integer, Font>> ();
			AppLoader.fonts.put (filename, types);
		}
		Map <Integer, Font> sizes = null;
		if ((sizes = types.get (type)) == null) {
			sizes = new HashMap <Integer, Font> ();
			types.put (type, sizes);
		}
		Font resource = sizes.get (size);
		if (resource == null) {
			InputStream stream = AppLoader.openStream (filename);
			if (stream != null) {
				try {
					resource = new TrueTypeFont (java.awt.Font.createFont (java.awt.Font.TRUETYPE_FONT, stream).deriveFont (type, size), true);
				} catch (java.awt.FontFormatException | IOException error) {}
				AppLoader.closeStream (stream);
			}
			if (resource == null) {
				resource = new TrueTypeFont (new java.awt.Font (null, java.awt.Font.PLAIN, 16), true);
			}
			sizes.put (size, resource);
		}
		return resource;
	}

	public static Image loadImage (String filename) {
		Image resource = AppLoader.images.get (filename);
		if (resource == null) {
			InputStream stream = AppLoader.openStream (filename);
			if (stream != null) {
				try {
					resource = new Image (stream, filename, false);
				} catch (SlickException error) {}
				AppLoader.closeStream (stream);
			}
			if (resource == null) {
				resource = new Image (new EmptyImageData (0, 0));
			}
			AppLoader.images.put (filename, resource);
		}
		return resource;
	}

	public static Audio loadAudio (String filename) {
		Audio resource = AppLoader.audios.get (filename);
		if (resource == null) {
			InputStream stream = AppLoader.openStream (filename);
			if (stream != null) {
				SoundStore.get ().init ();
				try {
					resource = SoundStore.get ().getOgg (stream);
				} catch (IOException error) {}
				if (resource == null) {
					try {
						resource = SoundStore.get ().getWAV (stream);
					} catch (IOException error) {}
				}
				if (resource == null) {
					try {
						resource = SoundStore.get ().getAIF (stream);
					} catch (IOException error) {}
				}
				if (resource == null) {
					try {
						resource = SoundStore.get ().getMOD (stream);
					} catch (IOException error) {}
				}
				AppLoader.closeStream (stream);
			}
			if (resource == null) {
				resource = new NullAudio ();
			}
			AppLoader.audios.put (filename, resource);
		}
		return resource;
	}

}
