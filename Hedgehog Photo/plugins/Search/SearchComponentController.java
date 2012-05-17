import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import se.cth.hedgehogphoto.database.Files;
import se.cth.hedgehogphoto.database.PictureObject;

/**
 * @author Barnabas Sapan
 */
public class SearchComponentController{
	private SearchComponentView view;
	private final PictureObject fo;
	private Files f;
	private Color oldColor;

	public SearchComponentController(SearchComponentView _view, final PictureObject _fo, Files _f){
		view = _view;
		fo = _fo;
		f = _f;

		view.addMouseListener(new MouseListener(){
			//TODO This needs to be done.
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("Clicked on: " + fo.getPath());
				List<PictureObject> pic = new ArrayList<PictureObject>();
				pic.add(fo);
				f.setPictureList(pic);
			}

			@Override
			public void mouseEntered(MouseEvent e) {	
				oldColor = view.getBackground();
				view.setBackground(new Color(210, 210, 210));
			}

			@Override
			public void mouseExited(MouseEvent e) {	
				view.setBackground(oldColor);
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}
		});
	}
}