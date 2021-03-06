package se.cth.hedgehogphoto.map.model;

import java.awt.Point;
import java.util.List;

import javax.swing.ImageIcon;

import se.cth.hedgehogphoto.database.PictureObject;

/**
 * Abstract logical representation of a marker
 * which shall be used in a JLayeredPane.
 * @author Florian Minges
 */
public abstract class AbstractMarkerModel extends AbstractComponentModel {
	private String iconPath;
	private int numberOfLocations; 
	
	@Override
	public void initialize() {
		super.initialize();
		setNumberOfLocations(computeNumberOfLocations());
	}
	
	public abstract List<PictureObject> getPictures(List<PictureObject> pictures);
	abstract int computeNumberOfLocations();

	public int getNumberOfLocations() {
		return this.numberOfLocations;
	}
	
	/**
	 * Sets the counter of locations to the given integer.
	 * Non-positive numbers are not allowed, but 0 is ok.
	 * @param numberOfLocations
	 */
	public void setNumberOfLocations(int numberOfLocations) {
		if (numberOfLocations >= 0)
			this.numberOfLocations = numberOfLocations;
	}
	
	public String getIconPath() {
		return this.iconPath;
	}
	
	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
		setProperComponentSize();
		setChanged();
		notifyObservers(Global.ICON_UPDATE);
		/* Setting the proper component size is essential 
		 * for calculations, but with only the string it is hard to check.
		 * One might need to hard-code the size of certain icons. */
	}
	
	public int getProperComponentWidth() {
		ImageIcon icon = new ImageIcon(getIconPath()); //temporary solution
		return icon.getIconWidth();
	}
	
	public int getProperComponentHeight() {
		ImageIcon icon = new ImageIcon(getIconPath()); //temporary solution
		return icon.getIconHeight();
	}
	
	/** Returns the x-difference between the top-left corner and the
	 *  position this marker points at. */
	protected abstract int getXOffset(); 
	
	/** Returns the y-difference between the top-left corner and the
	 *  position this marker points at. */
	protected abstract int getYOffset(); 
	
	protected abstract void handleVisibility();
	protected abstract Point.Double getLonglat();
	
	@Override
	protected void handleZoom() { 
		Point.Double p = this.getLonglat();
		setPointerPosition(MapPanel.computePixelPositionOnMap(p.x, p.y));
	}
}
