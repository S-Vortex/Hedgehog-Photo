package se.cth.hedgehogphoto.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import se.cth.hedgehogphoto.Constants;
import se.cth.hedgehogphoto.database.Files;
import se.cth.hedgehogphoto.database.PictureObject;
import se.cth.hedgehogphoto.log.Log;
import se.cth.hedgehogphoto.model.MainModel;

/**
 * 
 * @author David Grankvist
 */

public class MainView implements Observer {

	private JFrame frame;
	private JPanel photoViewPanel;
	private	JPanel leftPanelView;
	private JPanel[] panelHolder = new JPanel[3];
	private JPanel topPanel;
	private JButton btnShowhideTags = new JButton("Show/Hide tags");
	private JButton btnShowhideLocation = new JButton("Show/Hide location");
	private JSlider slider = new JSlider(50, 200);
	private JButton showHideComments = new JButton("Show/Hide comments");
	//TODO this list probably needs to be removed, not enough MVC
	private List<PhotoPanel> photoPanels;

	public MainView(StartUpView startView) {
		initialize(startView);
	}

	private void initialize(StartUpView startView) {
		frame = startView;
		//TODO Minimum size?
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		JPanel bottomPanel = new JPanel();
		frame.getContentPane().add(bottomPanel, BorderLayout.SOUTH);

		GroupLayout gl_bottomPanel = new GroupLayout(bottomPanel);
		gl_bottomPanel.setHorizontalGroup(
				gl_bottomPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_bottomPanel.createSequentialGroup()
						.addGap(100)
						.addComponent(btnShowhideTags)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(btnShowhideLocation)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(showHideComments)
						.addGap(202)
						.addComponent(slider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(59, Short.MAX_VALUE))
				);
		gl_bottomPanel.setVerticalGroup(
				gl_bottomPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_bottomPanel.createSequentialGroup()
						.addGap(5)
						.addGroup(gl_bottomPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(slider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_bottomPanel.createParallelGroup(Alignment.BASELINE)
										.addComponent(btnShowhideLocation)
										.addComponent(btnShowhideTags)
										.addComponent(showHideComments)))
										.addContainerGap())
				);
		bottomPanel.setLayout(gl_bottomPanel);

		topPanel = new JPanel();
		topPanel.setLayout(new BorderLayout());
		frame.getContentPane().add(topPanel, BorderLayout.NORTH);

		leftPanelView = new JPanel();
		Dimension d = new Dimension(Constants.PREFERRED_MODULE_WIDTH, Constants.PREFERRED_MODULE_HEIGHT);
		leftPanelView.setPreferredSize(d);
		leftPanelView.setMinimumSize(d);
		leftPanelView.setSize(d);
		frame.getContentPane().add(leftPanelView, BorderLayout.WEST);
		leftPanelView.setLayout(new GridLayout(3, 0, 0, 0));

		for(int i = 0; i < 3; i++) {
			panelHolder[i] = new JPanel();
			leftPanelView.add(panelHolder[i]);
		}

		JScrollPane photoView = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		frame.getContentPane().add(photoView, BorderLayout.CENTER);

		photoViewPanel = new JPanel();
		photoView.setViewportView(photoViewPanel);
		photoViewPanel.setLayout(new WrapLayout(FlowLayout.LEFT));

	}//init
	public void setCommentsButtonListener(ActionListener l){
		this.showHideComments.addActionListener(l);
	}
	public void setTagsButtonListener(ActionListener l){
		this.btnShowhideTags.addActionListener(l);
	}
	public void setLocationButtonListener(ActionListener l){
		this.btnShowhideLocation.addActionListener(l);
	}
	public void setSliderListener(ChangeListener l){
		this.slider.addChangeListener(l);
	}
	public List<PhotoPanel> getPhotoPanels(){
		return this.photoPanels;
	}
	public void addPlugin(JPanel panel, PluginArea placement){
		if(panel != null){
			if(placement == PluginArea.SEARCH){
				topPanel.add(panel, BorderLayout.EAST);
			} else {	
				if(placement == PluginArea.LEFT_TOP){
					panelHolder[0] = panel;
				}

				if(placement == PluginArea.LEFT_MIDDLE){
					panelHolder[1] = panel;
				}

				if(placement == PluginArea.LEFT_BOTTOM){
					panelHolder[2] = panel;
				}

				leftPanelView.removeAll();

				for(int i = 0; i < 3; i++){
					leftPanelView.add(panelHolder[i]);
				}
			}
		} else {
			Log.getLogger().log(Level.SEVERE, "Could not add plugin panel to the view, panel is null");
		}
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		photoPanels = new ArrayList<PhotoPanel>();
		List<? extends PictureObject> images;

		//TODO Maybe refresh in another way than removing the PhotoPanels?
		photoViewPanel.removeAll();

		if(arg1 instanceof MainModel) {
			MainModel model = (MainModel)arg1;
			images = model.getImages();

			for(int i = 0; i<images.size(); i++) {
				PhotoPanel pp = new PhotoPanel(images.get(i).getPath());
				pp.setComment(images.get(i).getComment().getComment());
				pp.setTags(images.get(i).getTags());
				pp.setLocation(images.get(i).getLocation().getLocation());
				photoPanels.add(i, pp);
				photoViewPanel.add(pp);
			}
		}

		if(arg1 instanceof Files){
			images = Files.getInstance().getPictureList();
			for(int i = 0; i<images.size(); i++) {
				PhotoPanel pp = new PhotoPanel(images.get(i).getPath());
				pp.setComment(images.get(i).getComment().getComment());
				pp.setTags(images.get(i).getTags());
				pp.setLocation(images.get(i).getLocation().getLocation());
				photoPanels.add(i, pp);
				photoViewPanel.add(pp);
			}
		}
		frame.revalidate();
		//TODO just a test
		if(!photoPanels.isEmpty()){
			for(int i=0;i<photoPanels.size();i++){
				Image image = photoPanels.get(i).getIcon().getImage();
				float scale = Constants.PREFERRED_PICTURE_HEIGHT/image.getHeight(null);
				BufferedImage bi = ImageUtils.resize(image, Math.round(image.getWidth(null)*scale),
						Math.round(Constants.PREFERRED_PICTURE_HEIGHT));
				ImageIcon icon2 = new ImageIcon(bi);
				photoPanels.get(i).setIcon(icon2);
			}
		}
	}
}
