package se.cth.hedgehogphoto.note;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

public class NotePreview extends JPanel{
	
	public NotePreview(int side){
		this.setBackground(Color.white);
		this.setPreferredSize(new Dimension(side, side));
	}
	
	public void paintPreview(int diam, Color c){
        PaintUtils.erasePainting(this);
		PaintUtils.paintCircle(this.getGraphics(), this.getWidth()/2, this.getHeight()/2, diam, c);
	}
}