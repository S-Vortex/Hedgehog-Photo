package se.cth.hedgehogphoto.controller;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

import se.cth.hedgehogphoto.database.DaoFactory;
import se.cth.hedgehogphoto.database.JpaPictureDao;
import se.cth.hedgehogphoto.model.PhotoPanelConstantsI;
import se.cth.hedgehogphoto.view.PhotoPanel;

public class PhotoPanelFocusListener implements FocusListener {
	private static DaoFactory daoFactory = DaoFactory.getInstance();
	private JpaPictureDao pictureDao = daoFactory.getJpaPictureDao();

	public PhotoPanelFocusListener(){

	}

	@Override
	public void focusGained(FocusEvent arg0) {		
	}

	@Override
	public void focusLost(FocusEvent e) {
		if (e.getSource() instanceof JTextField) {
			JTextField cell = (JTextField) e.getSource();

			if(cell.getName().equals(PhotoPanelConstantsI.COMMENT)){
				if (cell.getParent() instanceof PhotoPanel) {
					String path = ((PhotoPanel)cell.getParent()).getPath();			
					pictureDao.addComment(cell.getText(), path);
					System.out.println("JTF" +cell.getText());
					System.out.println(pictureDao.findById(path));
				}

			} else if(cell.getName().equals(PhotoPanelConstantsI.LOCATION)){
				if (cell.getParent() instanceof PhotoPanel) {
					String path = ((PhotoPanel)cell.getParent()).getPath();	
					pictureDao.addLocation(cell.getText(), path);
					System.out.println("JTF" +cell.getText());
					System.out.println(pictureDao.findById(path));
				}
			} else if(cell.getName().equals(PhotoPanelConstantsI.NAME)){
				if (cell.getParent() instanceof PhotoPanel) {
					String path = ((PhotoPanel)cell.getParent()).getPath();	
					pictureDao.setName(cell.getText(), path);
					System.out.println("JTF" +cell.getText());
					System.out.println(pictureDao.findById(path));
				}
			} else if(cell.getName().equals(PhotoPanelConstantsI.TAGS)){
				if (cell.getParent() instanceof PhotoPanel) {
					String path = ((PhotoPanel)cell.getParent()).getPath();	
					pictureDao.deleteTags(path);
					JTextField jtf = (JTextField)e.getSource();
					String[] tags = jtf.getText().split(";");
					for(int i = 0; i < tags.length; i++){
						pictureDao.addTag(tags[i], path);
						System.out.println("JTF" +jtf.getText());
					}
					System.out.print("ALL"+DaoFactory.getInstance().getJpaTagDao().getAll());
				}
			}
		}
	}
}
