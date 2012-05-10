package se.cth.hedgehogphoto.map.geocoding;

import java.awt.Color;
import java.awt.GridLayout;
import java.text.DecimalFormat;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import se.cth.hedgehogphoto.objects.LocationObject;

public class LocationPanel extends JPanel {
	private JLabel nameLabel;
	private JLabel longitudeLabel, latitudeLabel;
	private LocationObject location;
	private static Color color;
	private static int colorScale;
	
	public LocationPanel(LocationObject location) {
		//set instance variables
		this.location = location;
		this.nameLabel = new JLabel(location.getLocation());
		
		//round doubles (gps-coords) /just the visible part/
		DecimalFormat df = new DecimalFormat("#.#######");
		String lon = df.format(location.getLongitude());
		String lat = df.format(location.getLatitude());

		this.longitudeLabel = new JLabel("Longitude: " + lon);
		this.latitudeLabel = new JLabel("Latitude: " + lat);
		
		//align left
		this.nameLabel.setHorizontalTextPosition(SwingConstants.LEFT);
		this.longitudeLabel.setHorizontalTextPosition(SwingConstants.LEFT);
		this.latitudeLabel.setHorizontalTextPosition(SwingConstants.LEFT);
		this.longitudeLabel.setHorizontalAlignment(SwingConstants.LEFT);
		this.latitudeLabel.setHorizontalAlignment(SwingConstants.LEFT);
		
		//add margins
		final int margin = 10;
		this.nameLabel.setBorder(BorderFactory.createEmptyBorder(margin, margin, margin, margin));
		this.longitudeLabel.setBorder(BorderFactory.createEmptyBorder(margin, margin, margin, margin));
		this.latitudeLabel.setBorder(BorderFactory.createEmptyBorder(margin, margin, margin, margin));
		
		//set layout
		this.setLayout(new GridLayout(2,1));
		JPanel helpPanel = new JPanel();
		helpPanel.setLayout(new GridLayout(1,2));
		
		//add border
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
		
		//add components
		helpPanel.add(this.longitudeLabel);
		helpPanel.add(this.latitudeLabel);
		this.add(this.nameLabel);
		this.add(helpPanel);
		
		this.setPreferredSize(this.getPreferredSize());
		
		//set background color
		this.setBackground(color);
		incrementColorScale();
	}
	
	public static void resetColorScale() {
		color = new Color(255,255,255); //white
		colorScale = 1;
	}
	
	private void incrementColorScale() {
		colorScale++;
		int red = 255 - colorScale*2;
		int green = 255 - colorScale*4;
		int blue = 255 - colorScale*10;
		color = new Color(red,green,blue);
	}

}