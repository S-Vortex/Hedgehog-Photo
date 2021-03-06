package se.cth.hedgehogphoto.metadata;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.logging.Level;

import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.Sanselan;
import org.apache.sanselan.common.IImageMetadata;

import se.cth.hedgehogphoto.log.Log;
import se.cth.hedgehogphoto.objects.ImageObject;


/**
 * A wrapper-class for the metadata extractor
 * @author Florian
 */
public class Metadata {

	private static final String[] metadataTypes = 
		{"Modify Date", "Artist", "XPComment", "XPAuthor", 
		"XPKeywords", "Date Time Original", "Interop Index", 
		"Interop Version", "Unknown Tag (0x3)", "Unknown Tag (0x4)"};

	public static ImageObject getImageObject(File file) {
		IImageMetadata metadata = extractMetadata(file);

		return getImageObject(metadata); 
	}

	public static IImageMetadata extractMetadata(File file) {
		IImageMetadata metadata = null;

		try {
			metadata = Sanselan.getMetadata(file);
		} catch (ImageReadException e) {
			Log.getLogger().log(Level.WARNING, "Failed to read metadata from file " + file, e);
		} 
		catch (IOException e) {
			Log.getLogger().log(Level.WARNING, "Failed to read metadata from file " + file, e);
		}

		return metadata;
	}

	public static ImageObject getImageObject(IImageMetadata imageMetadata) {
		ImageObject imageObject = new ImageObject();
		if (imageMetadata == null) {
			return imageObject;
		}
			
		String metadata = imageMetadata.toString(); 

		/* New Feature in java 1.7 - closes stream automatically */
		try (BufferedReader br = new BufferedReader( new StringReader(metadata) )) {
			String line;
			while((line = br.readLine()) != null) {		
				if (containsTargetMetadata(line)) {
					setPropertyFromString(imageObject, line);
				}
			}
		} catch (IOException e) {
			Log.getLogger().log(Level.SEVERE, "IOException", e);
		} 

		return imageObject;
	}

	private static boolean containsTargetMetadata(String line) {
		int nbrOfTypes = metadataTypes.length;
		for(int i = 0; i < nbrOfTypes; i++) {
			if(line.contains(metadataTypes[i])) {
				return true;
			}
		}
		return false;
	}

	private static void setPropertyFromString(ImageObject imageObject, String line) {
		line = line.trim();
		String property = getProperty(line);
		String value = getPropertyValue(line);
		imageObject.setProperty(property, value);
	}

	private static String getProperty(String line) {
		int indexOfPropertyEnds = line.indexOf(":");
		return line.substring(0,indexOfPropertyEnds);
	}

	private static String getPropertyValue(String line) {
		int indexOfValueStarts = line.indexOf(":") + 1;
		return line.substring(indexOfValueStarts);
	}
}
