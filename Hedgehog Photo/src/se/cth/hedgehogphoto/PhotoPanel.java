package hedgehogView;

import javax.swing.JPanel;
import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Label;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;

public class PhotoPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	JLabel commentsLabel = new JLabel("Comments");
	JLabel tagsLabel = new JLabel("Tags:");
	JLabel locationLabel = new JLabel("Location:");
	
	public PhotoPanel(String path) {
		setLayout(new BorderLayout(0, 0));
		
		JPanel tagsLocationsPanel = new JPanel();
		add(tagsLocationsPanel, BorderLayout.SOUTH);
	
		GroupLayout gl_tagsLocationsPanel = new GroupLayout(tagsLocationsPanel);
		gl_tagsLocationsPanel.setHorizontalGroup(
			gl_tagsLocationsPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_tagsLocationsPanel.createSequentialGroup()
					.addGroup(gl_tagsLocationsPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(tagsLabel)
						.addComponent(locationLabel))
					.addContainerGap(405, Short.MAX_VALUE))
		);
		gl_tagsLocationsPanel.setVerticalGroup(
			gl_tagsLocationsPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_tagsLocationsPanel.createSequentialGroup()
					.addComponent(tagsLabel)
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(locationLabel))
		);
		tagsLocationsPanel.setLayout(gl_tagsLocationsPanel);
		
		JPanel commentsPanel = new JPanel();
		add(commentsPanel, BorderLayout.CENTER);
		commentsPanel.setLayout(new BorderLayout(0, 0));
		
		JLabel iconLabel = new JLabel("");
		iconLabel.setIcon(new ImageIcon(path));
		commentsPanel.add(iconLabel, BorderLayout.WEST);
		
		commentsPanel.add(commentsLabel, BorderLayout.CENTER);

	}
	public void displayComments(Boolean b){
		this.commentsLabel.setVisible(b);
	}
	public boolean isVisibleComments(){
		return this.commentsLabel.isVisible();
	}
	public void displayTags(Boolean b){
		this.tagsLabel.setVisible(b);
	}
	public boolean isVisibleTags(){
		return this.tagsLabel.isVisible();
	}
	public void displayLocation(Boolean b){
		this.locationLabel.setVisible(b);
	}
	public boolean isVisibleLocation(){
		return this.locationLabel.isVisible();
	}
}