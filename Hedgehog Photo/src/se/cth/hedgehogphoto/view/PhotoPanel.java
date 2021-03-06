package se.cth.hedgehogphoto.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import se.cth.hedgehogphoto.database.DatabaseHandler;
import se.cth.hedgehogphoto.database.TagObject;
import se.cth.hedgehogphoto.global.Constants;
import se.cth.hedgehogphoto.model.PhotoPanelConstantsI;
import se.cth.hedgehogphoto.objects.FileObject;

@SuppressWarnings("serial")
public class PhotoPanel extends JPanel {

	private JTextField nameTextField = new JTextField("Name");
	private javax.swing.JTextArea commentArea = new javax.swing.JTextArea();
	private JTextField tagsTextField = new JTextField();
	private JTextField locationTextField = new JTextField();
	private JLabel commentLabel = new JLabel("Comments");
	private JLabel tagsLabel = new JLabel("Tags:");
	private JLabel locationLabel = new JLabel("Location:");
	private JLabel photoNameLabel = new JLabel("Name");
	private JLabel iconLabel = new JLabel("");
	private ImageIcon icon;
	private String path;
	private Dimension scaleDimension;

	public PhotoPanel(FileObject f) {
		this(f.getFilePath());
	}

	public PhotoPanel(String path) {
		this.path = path;
		this.nameTextField.setBackground(Color.LIGHT_GRAY);
		this.setName(DatabaseHandler.getInstance().findPictureById(path).getName());
		this.commentArea.setName(PhotoPanelConstantsI.COMMENT);
		this.locationTextField.setName(PhotoPanelConstantsI.LOCATION);
		this.nameTextField.setName(PhotoPanelConstantsI.NAME);
		this.tagsTextField.setName(PhotoPanelConstantsI.TAGS);
		this.nameTextField.setActionCommand("name");
		this.tagsTextField.setActionCommand("tags");
		this.locationTextField.setActionCommand("location");
		this.commentArea.setPreferredSize(new Dimension(100,100));
		this.commentLabel.setPreferredSize(new Dimension(100,10));
		this.locationTextField.setPreferredSize(new Dimension(200,20));
		this.locationLabel.setPreferredSize(new Dimension(100,10));
		this.tagsTextField.setPreferredSize(new Dimension(200,20));
		this.tagsLabel.setPreferredSize(new Dimension(100,10));
		this.nameTextField.setPreferredSize(new Dimension(100,20));
		this.photoNameLabel.setPreferredSize(new Dimension(50,10));
		this.icon = new ImageIcon(path);
		this.iconLabel.setIcon(icon);
		this.setVisible(true);

		GridBagLayout gBig = new GridBagLayout();
		setLayout(gBig);
		GridBagConstraints nameBag = new GridBagConstraints();
		nameBag.gridx = 1;
		nameBag.gridy = 1;
		nameBag.gridheight = 1;
		nameBag.gridwidth = 1;
		gBig.setConstraints(this.photoNameLabel , nameBag);
		add(this.photoNameLabel);
		
		GridBagConstraints photoNameBag = new GridBagConstraints();
		photoNameBag.gridx = 2;
		photoNameBag.gridy = 1;
		photoNameBag.gridheight = 1;
		photoNameBag.gridwidth = 1;
		gBig.setConstraints(this.nameTextField , photoNameBag);
		add(this.nameTextField);
		this.nameTextField.setVisible(true);

		GridBagConstraints con = new GridBagConstraints();
		con.gridx = 1;
		con.gridy = 2;
		con.gridheight = 3;
		con.gridwidth = 2;

		gBig.setConstraints(this.iconLabel, con);
		add(this.iconLabel);

		this.locationTextField.setBackground(Color.LIGHT_GRAY);
		GridBagConstraints loc = new GridBagConstraints();
		loc.gridx = 2;
		loc.gridy = 6;
		loc.gridheight = 1;
		loc.gridwidth = 1;
		gBig.setConstraints(this.locationTextField, loc);
		add(this.locationTextField);
		GridBagConstraints locLabel = new GridBagConstraints();
		locLabel.gridx = 1;
		locLabel.gridy = 6;
		locLabel.gridheight = 1;
		locLabel.gridwidth = 1;
		gBig.setConstraints(this.locationLabel, locLabel);
		add(this.locationLabel);

		GridBagConstraints comLabel = new GridBagConstraints();
		comLabel.gridx = 3;
		comLabel.gridy = 3;
		gBig.setConstraints(this.commentLabel, comLabel);
		add(this.commentLabel);
		this.commentArea.setBackground(Color.LIGHT_GRAY);
		GridBagConstraints com = new GridBagConstraints();
		com.gridx = 3;
		com.gridy = 4;

		gBig.setConstraints(this.commentArea, com);
		add(this.commentArea);

		GridBagConstraints tagLabel = new GridBagConstraints();
		tagLabel.gridx = 1;
		tagLabel.gridy = 5;
		tagLabel.gridheight = 1;
		tagLabel.gridwidth = 1;
		gBig.setConstraints(this.tagsLabel,  tagLabel);
		add(this.tagsLabel);

		int x= 0;
		this.tagsTextField.setBackground(Color.LIGHT_GRAY);
		GridBagConstraints tag = new GridBagConstraints();
		tag.gridx = x + 2;
		tag.gridy = 5;
		tag.gridheight = 1;
		tag.gridwidth = 1;

		gBig.setConstraints(this.tagsTextField,tag);
		add(this.tagsTextField);
		this.setBackground(Constants.GUI_BACKGROUND);
	}
	
	public void setScaleDimension(Dimension dimension){
		this.scaleDimension = dimension;
	}
	
	public Dimension getScaleDimension(){
		return this.scaleDimension;
	}
	
	public String getPath() {
		return this.path;
	}
	
	public void setTextFieldActionListeners(ActionListener al) {
		this.tagsTextField.addActionListener(al);
		this.locationTextField.addActionListener(al);
		this.nameTextField.addActionListener(al);
	}
	
	public void setTextFieldFocusListeners(FocusListener listener) {
		this.commentArea.addFocusListener(listener);
		this.tagsTextField.addFocusListener(listener);
		this.locationTextField.addFocusListener(listener);
		this.nameTextField.addFocusListener(listener);
	}
	
	//TODO this method is not working
	public void setMouseListener(MouseListener l){
		this.iconLabel.addMouseListener(l);
	}
	
	public void setName(String name){
		this.nameTextField.setText(name);
	}
	
	public void setTags(List<? extends TagObject> tags){
		StringBuilder builder = new StringBuilder("");
		for(TagObject t : tags) {
			builder.append(t.getTag() + ";");
		}
		this.tagsTextField.setText(builder.toString());
	}

	public void setLocation(String location){
		this.locationTextField.setText(location);
	}

	public void setComment(String comment){
		this.commentArea.setText(comment);
	}

	public ImageIcon getIcon(){
		return this.icon;
	}

	public void setIcon(ImageIcon icon){
		this.iconLabel.setIcon(icon);
	}

	public void displayComments(boolean b){
		this.commentArea.setVisible(b);
		this.commentLabel.setVisible(b);
	}

	public boolean isVisibleComments(){
		return this.commentArea.isVisible();
	}

	public void displayTags(boolean b){
		this.tagsTextField.setVisible(b);
		this.tagsLabel.setVisible(b);
	}

	public boolean isVisibleTags(){
		return this.tagsTextField.isVisible();
	}

	public void displayLocation(boolean b){
		this.locationTextField.setVisible(b);
		this.locationLabel.setVisible(b);
	}

	public boolean isVisibleLocation(){
		return this.locationTextField.isVisible();
	}

	public void displayPhotoName(boolean b){
	
		this.nameTextField.setVisible(b);
		this.photoNameLabel.setVisible(b);
	}

	public boolean isVisiblePhotoName(){
		return this.nameTextField.isVisible();
	}
}
