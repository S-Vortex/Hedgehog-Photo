
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import se.cth.hedgehogphoto.database.Files;
import se.cth.hedgehogphoto.database.PictureObject;

/**
 * MapController - handles mouseevents on the markers and
 * calls the appropriate methods.
 * @author Florian Minges
 */
public class MapController implements PropertyChangeListener {
	private MapView mapView;
	private static Files files;
	
	public MapController(MapView mapView, Files files) {
		this.mapView = mapView;
		MapController.files = files;
		mapView.setMouseAdapter(new MarkerListener());
		mapView.addListeners();
		mapView.addPropertyChangeListener(this);
	}
	

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		if (event.getPropertyName().equals(Global.MARKERS_UPDATE)) {
			mapView.addListener(new MarkerListener());
		}
		
	}
	
	/** 
	 * MouseListener-class for the markers.
	 * @author Florian
	 */
	public static class MarkerListener extends MouseAdapter {
		@Override
		public void mouseReleased(MouseEvent arg0) {}
		@Override
		public void mouseClicked(MouseEvent arg0) {
			if (arg0.getSource() instanceof AbstractJOverlayMarker) {
				AbstractJOverlayMarker marker = (AbstractJOverlayMarker) arg0.getSource();
				List<PictureObject> pictures = new ArrayList<PictureObject>();
				marker.getModel().getPictures(pictures);
				MapController.files.setPictureList(pictures);
			}
		}
		@Override
		public void mousePressed(MouseEvent arg0) {
			
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			arg0.getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			if (arg0.getSource() instanceof AbstractJOverlayPanel) {
				AbstractJOverlayPanel marker = (AbstractJOverlayPanel) arg0.getSource();
				//TODO: Show an overlay info-label in the map (not yet created)
			}
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			arg0.getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
    }
}
