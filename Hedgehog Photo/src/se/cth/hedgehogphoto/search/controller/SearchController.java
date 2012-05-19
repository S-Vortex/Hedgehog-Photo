package se.cth.hedgehogphoto.search.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import se.cth.hedgehogphoto.database.Files;
import se.cth.hedgehogphoto.database.PictureObject;
import se.cth.hedgehogphoto.search.model.SearchModel;
import se.cth.hedgehogphoto.search.model.SearchThread;
import se.cth.hedgehogphoto.search.view.JPopupItemI;
import se.cth.hedgehogphoto.search.view.JPopupPreview;
import se.cth.hedgehogphoto.search.view.JSearchBox;

/**
 * @author Barnabas Sapan
 */

public class SearchController {
	private SearchModel model;
	private JSearchBox view;
	
	public SearchController(SearchModel model, JSearchBox view){
		this.model = model;
		this.view = view;
		
		this.view.setSearchBoxActionListener(new SearchBoxActionListener());
		this.view.setSearchBoxFocusListener(new SearchBoxFocusListener());
		this.view.setSearchBoxDocumentListener(new SearchBoxDocumentListener());
		this.view.setSearchButtonListener(new SearchButtonListener());
	}
	
	public SearchController(SearchModel model, JSearchBox view, JPopupPreview preview){
		this(model, view);
		this.view.setSearchPreview(preview);
	}
	
	//-----------------------------LISTENERS-----------------------------------

	/** Listens to mouse-events of the JPopupItemI. */
	public class PreviewComponentMouseListener extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getSource() instanceof JPopupItemI) {
				JPopupItemI item = (JPopupItemI) e.getSource();
				Files.getInstance().setPictureList(item.getPictures());
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			if (e.getSource() instanceof JPopupItemI) {
				JPopupItemI view = (JPopupItemI) e.getSource();
				view.setBackground(JPopupItemI.HOVER_COLOR);
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			if (e.getSource() instanceof JPopupItemI) {
				JPopupItemI view = (JPopupItemI) e.getSource();
				view.setBackground(JPopupItemI.DEFAULT_COLOR);
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}
	}
	
	//------------------------------SEARCHBOX-LISTENERS------------------------------
	
	//TODO: Use instanceof checks, and typecast before doing that stuff!
	
	/** Enter is pressed from the textfield, do and display search! */
	public class SearchBoxActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(!view.getSearchBoxText().equals(view.getPlaceholderText())){
				List<PictureObject> fo = model.getSearchObjects();
				Files.getInstance().setPictureList(fo);
			}	
		}
	}
	
	/** Changes between the standby text (no focus) and allowing the user to enter text (focus). */
	public class SearchBoxFocusListener implements FocusListener {
        @Override
        public void focusGained(FocusEvent e) {
        	if (view.getSearchBoxText().equals(view.getPlaceholderText())) {   
            	view.setSearchBoxText("");
            }
        }
        
        @Override
        public void focusLost(FocusEvent e) {
        	if(view.getSearchBoxText().isEmpty()){
        		view.setSearchBoxText(view.getPlaceholderText());
        	}
        }

	}
	
	/** Calls update() on each keystroke by the user. */
	public class SearchBoxDocumentListener implements DocumentListener {
		private Thread t = new SearchThread(model, 500);
		
        @Override
        public void changedUpdate(DocumentEvent e) {
            update();
        }
        
        @Override
        public void removeUpdate(DocumentEvent e) {
            update();
        }
        
        @Override
        public void insertUpdate(DocumentEvent e) {
            update();
        }
        
        private void update(){
        	if(!view.getSearchBoxText().isEmpty() && !view.getSearchBoxText().equals(view.getPlaceholderText())){
            	//Updates our model to always contain the latest and most recent search query.
        		model.setSearchQueryText(view.getSearchBoxText());
        		if(t.getState() == Thread.State.NEW){
            		t.start();
            	} else {
            		t.interrupt();
            		t = new SearchThread(model, 500);
            		t.start();
            	}
        	}
        }
	}
	
	public class SearchButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(!view.getSearchBoxText().equals(view.getPlaceholderText())){
				new SearchThread(model, 0).start();
			}
		}
	}
}
