import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import se.cth.hedgehogphoto.database.Files;
import se.cth.hedgehogphoto.database.PictureObject;

/**
 * @author Barnabas Sapan
 */

@SuppressWarnings("serial")
public class SearchPreviewView extends JPopupMenu implements Observer{
	private JTextField jtf;
	//Better to add everything to a JPanel first
	//instead of adding directly to a JPopup to prevent some rendering issues.
	private JPanel panel;
	private Files f;
	public SearchPreviewView(Files _f){
		f = _f;
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		add(panel);
		setFocusable(false);
	}

	public void setTextField(JTextField t){
		jtf = t;
	}

	@Override
	public void update(Observable o, Object arg) {
		final SearchModel model = (SearchModel)arg;
		System.out.println("UPDATE @ SEARCH_PREVIEW_VIEW: " + model.getSearchQueryText());
		show(jtf, -50, jtf.getHeight()); //-50 to count for the offset of the textbox
		List<PictureObject> fo = model.getSearchObjects();
		Iterator<PictureObject> itr = fo.iterator();
		panel.removeAll();

		//Adds the search results to the popup, if result resulted in no matches, add
		//a no result label.
		if(fo.isEmpty() == false){
			//TODO Change this value to reflect the max size of the popup.
			//Adds "more result for" text to the popup
			if(fo.size() > 2){
				JPanel p = new JPanel();
				p.setLayout(new FlowLayout());
				p.add(new JLabel("More results for '" + model.getSearchQueryText() + "'"));
				p.addMouseListener(new MouseAdapter() {     
					@Override
					public void mouseClicked(MouseEvent e) {
						f.setPictureList(model.getSearchObjects());
					}
				});
				panel.add(p);
				panel.add(new JSeparator());
			}

			int i = 0;
			while(itr.hasNext() && i < 5){
				PictureObject pic = itr.next();
				SearchComponentView view = new SearchComponentView(pic);
				new SearchComponentController(view, pic, f);
				panel.add(view);
				i++;	
			}

			setPopupSize(250, (i * 70));
		} else {
			JPanel p = new JPanel();
			p.setLayout(new FlowLayout());
			p.setBackground(Color.GRAY);
			p.add(new JLabel("No results for '" + model.getSearchQueryText() + "'. Try again!"));
			panel.add(p);
			setPopupSize(250, 40);
		}

		panel.revalidate();
	}

	public JPanel getPanel(){ return panel; }
}
