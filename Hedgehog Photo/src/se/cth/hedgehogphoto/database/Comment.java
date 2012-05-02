package se.cth.hedgehogphoto.database;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
/**
 * 
 * @author Julia
 *
 */
@Entity
public class Comment {

@Id
private String comment;
	
@OneToMany
private List<Picture> picture;

@OneToMany
private List<Album> album;

	
	public List<Picture> getPicture() {
	return picture;
}

public void setPicture(List<Picture> picture) {
	this.picture = picture;
}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getComment() {
		return comment;
	}


	@Override
	public String toString() {
		return " [Comment= " + comment+ "] ";
	}

	public List<Album> getAlbum() {
		return album;
	}

	public void setAlbum(List<Album> album) {
		this.album = album;
	}
	public String getCommentAsString(){
		return comment;
	}
	
}
