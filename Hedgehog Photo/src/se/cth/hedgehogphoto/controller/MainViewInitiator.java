package se.cth.hedgehogphoto.controller;

import java.awt.CardLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import se.cth.hedgehogphoto.model.MainModel;
import se.cth.hedgehogphoto.view.ImageUtils;
import se.cth.hedgehogphoto.view.MainView;
import se.cth.hedgehogphoto.view.PhotoPanel;

public class MainViewInitiator {
	private MainView view;
	private MainModel model;
	
	public MainViewInitiator(JFrame frame) {
		this.model = new MainModel();
		this.view = new MainView(frame);
		model.addObserver(this.view);
		new DefaultController(this.view);
		this.view.addPhotoPanelActionListeners(new PhotoPanelActionListener());
		this.view.addPhotoPanelFocusListener(new PhotoPanelFocusListener());
		this.view.addPhotoPanelMouseListener(new PhotoPanelMouseListener());
	}
	
	public MainView getMainView() {
		return this.view;
	}
	
	public MainModel getMainModel() {
		return this.model;
	}
	
	public static class PhotoPanelMouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getSource() instanceof PhotoPanel) {
				PhotoPanel photoPanel = (PhotoPanel) e.getSource();
				Image image = photoPanel.getIcon().getImage();
				BufferedImage bi = ImageUtils.resize(image, image.getWidth(null), image.getHeight(null));
				ImageIcon icon2 = new ImageIcon(bi);
				photoPanel.setIcon(icon2);
				CardLayout cl = (CardLayout)(photoPanel.getCardPanel().getLayout());
				JPanel cardPanel = photoPanel.getCardPanel();
				cl.show(cardPanel, "One");
				photoPanel.getSinglePhotoPanel().add(photoPanel);
			}
		}
	}
}