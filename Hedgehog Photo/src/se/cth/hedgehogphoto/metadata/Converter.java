package se.cth.hedgehogphoto.metadata;

import java.awt.Point;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import se.cth.hedgehogphoto.geocoding.model.URLCreator;
import se.cth.hedgehogphoto.geocoding.model.XMLParser;
import se.cth.hedgehogphoto.log.Log;
import se.cth.hedgehogphoto.objects.LocationObjectOther;

public class Converter {
	private static Converter converter;

	private Converter(){

	}

	public static Converter getInstance(){
		if(converter == null)
			converter = new Converter();

		return converter;
	}

	public LocationObjectOther findLocationPlace(LocationObjectOther locationObj){
		Point.Double point = new Point.Double();
		point.setLocation(locationObj.getLongitude(),locationObj.getLatitude());
		URL url = URLCreator.getInstance().queryReverseGeocodingURL(point);
		LocationObjectOther newlocationObject = XMLParser.getInstance().processReverseGeocodingSearch(url);

		try{
			locationObj.setLocation(newlocationObject.getLocation());
		}catch(Exception e){
		}

		return locationObj;
	}

	public String convertComment(String comment) {
		try{

			if(!(comment.equals(""))){
				try {
					comment = convertDecimalNumbersToString(comment);
				}
				catch (NumberFormatException e) {
					Log.getLogger().log(Level.SEVERE, "Failed to convert \"" + comment +
																	"\" to a comment.", e);
				}
			}
		}
		catch (Exception e) {
			Log.getLogger().log(Level.SEVERE, "Failed to convert \"" + comment + 
															"\" to a comment.", e);
		}
		
		return comment;
	}

	public List<String> convertTags(String tag) {
		List<String> tags= new ArrayList<String>();
		try{
			if(!(tag.equals(""))){
				try {
					String string = convertDecimalNumbersToString(tag);
					tag = string;

					String[] args = string.split(";");

					try{
						for(int k = 0; k < args.length; k++){
							tags.add(args[k]);
						}
					}catch(NullPointerException e){
					}

				}
				catch (NumberFormatException e) {
					Log.getLogger().log(Level.SEVERE, "Failed to convert \"" + tag + "\" to a tag.", e);
				}
			}
		}catch (Exception e) {
			Log.getLogger().log(Level.SEVERE, "Failed to convert \"" +tag + "\" to a tag.", e);
		}
		
		return tags;
	}

	/**
	 * Converts a string that is on decimal format into a
	 * "normal" string. The string may look something like 
	 * "67, 101, 33, 33"
	 * @param decimalString
	 * @return
	 * @throws NumberFormatException
	 */
	public String convertDecimalNumbersToString(String decimalString) throws NumberFormatException {
		StringBuilder builder = new StringBuilder("");
		List<Integer> list = new ArrayList<Integer>();

		String string = decimalString.substring(1);
		for (String s : string.split(", ")) {
			list.add(Integer.parseInt(s));
		}

		for (Integer i : list) {
			String aChar = Character.valueOf((char)(int)i).toString();
			aChar = aChar.trim();
			builder.append(aChar);
		}

		return builder.toString();
	}

}
