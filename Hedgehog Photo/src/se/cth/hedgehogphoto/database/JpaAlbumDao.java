package se.cth.hedgehogphoto.database;

import java.util.ArrayList;
import java.util.List;





public class JpaAlbumDao  extends JpaDao<Album, String> implements AlbumDao{
	private static Files files = Files.getInstance();
	private static List<Album> albumList = files.getAlbumList();
	
	public JpaAlbumDao(){
		super();
	}
	public void updateAllAlbums(){
		albumList = getAllAlbums();
		files.setAlbumList(albumList);
	}
	public List<Album> getAllAlbums(){
		return getAll();

	}
	public void updateSearchfromDates(String search){
		albumList = searchfromDates(search);
		files.setAlbumList(albumList);
	}
	public void updateSearchPicturesfromDates(String search){
		albumList  = searchfromDates(search);
		files.setAlbumList(albumList );
	}
	public  List<Album>  searchfromDates(String search){
		if(search.equals("")){
		 return findByLike("date",search.toLowerCase());
		}else{
			return null;
		}
	}
	public void updateSearchfromNames(String search){
		albumList  = searchfromNames(search);
		files.setAlbumList(albumList );
	}
	public List<Album>  searchfromNames(String search){
		if(search.equals("")){
			return findByLike("albumName", search.toLowerCase());
		}else{
			return null;
		}

	}
	public void updateSearchfromComments(String search){
		albumList = searchfromComments(search);
		files.setAlbumList(albumList);
	}
	public List<Album> searchfromComments(String search){
		search = search.toLowerCase();
		if(!(search.equals(""))){
			JpaCommentDao jcd = new JpaCommentDao();
			List<Comment> comments = jcd.findByLike("comment", search);
			if(!(comments.isEmpty())){
				List<Album> albums = new ArrayList<Album>();
				for(Comment comment:comments)
				albums.addAll(findByEntity(comment,"dao.database.Comment"));
			}
	}
	return null;
	}
	public void updateSearchfromTags(String search){
		albumList = searchfromTags(search);
		files.setAlbumList(albumList);
	}
	public  List<Album> searchfromTags(String search){
		search = search.toLowerCase();
		if(!(search.equals(""))){
			JpaTagDao jtd = new JpaTagDao();
			List<Tag> tags = jtd.findByLike("Tag", search);
			List<Album> albums = new ArrayList<Album>();
 			for(Tag t: tags){
				albums.addAll(findByEntity(t,"dao.database.Tag"));
			}
			return albums;
		}
		return null;
	}
	public void updateSearchfromLocations(String search){
		albumList  =  searchfromLocations(search);
		files.setAlbumList(albumList);
	}
	public List<Album> searchfromLocations(String search){
		search = search.toLowerCase();
		if(!(search.equals(""))){
			JpaLocationDao jld = new JpaLocationDao();
			List<Location> locations = jld.findByLike("Location", search);
			List<Album> albums = new ArrayList<Album>();
			for(Location l:locations){
				albums.addAll(findByEntity(l,"dao.database.Location"));
			}
			return albums;
		}
		return null;
	}
	public  void updateAddTag(String tag, String albumName){
		for(Album f:albumList){
			if(f.getAlbumName().equals(albumName))
				albumList.remove(f);
		}
		addTag(tag, albumName);
		files.setAlbumList(albumList);
	}
public void addTag(String tag, String albumName){
	Album album = findById(albumName);
	albumName = albumName.toLowerCase();
	tag = tag.toLowerCase();
	if(album != null) {
	
		JpaTagDao jtd = new JpaTagDao();
		Tag tagg = jtd.findById(tag);
		if(tagg!= null){
			List<Album> albums = tagg.getAlbums();
			if(!(albums.contains(album))){
				beginTransaction();
				albums.add(album);
				tagg.setAlbums(albums);
				 entityManager.persist(tagg);
				commitTransaction();
			}
		}else{
			beginTransaction();
			Tag newTag = new Tag();
			newTag.setTag(tag);
			List<Album> albums = new ArrayList<Album>();
			albums.add(album);
			newTag.setAlbums(albums);
			List<Tag> tags =album.getTags();
			tags.add(newTag);
			album.setTags(tags);
			persist(album);
			 entityManager.persist(newTag);
			commitTransaction();
		}

	}

}
public  void updateAddComment(String comment, String albumName){
	for(Album f:albumList){
		if(f.getAlbumName().equals(albumName))
			albumList.remove(f);
	}
	addComment(comment, albumName);
	files.setAlbumList(albumList);
}

public void addComment(String comment, String albumName){
	albumName = albumName.toLowerCase();
	comment = comment.toLowerCase();
	Album album = findById(albumName);
	if(album != null) {
		JpaCommentDao jcd = new JpaCommentDao();
		Comment comm = jcd.findById(comment);
		if(comm != null){
			beginTransaction();
			List<Album> albums = comm.getAlbums();
			if((!albums.contains(album))){
				albums.add(album);
				comm.setAlbums(albums);
				persist(album);
				 entityManager.persist(comm);;
				commitTransaction();
			}


		}else{
			beginTransaction();
			Comment com = new Comment();
			com.setComment(comment);
			List<Album> albums = new ArrayList<Album>();
			albums.add(album);
			com.setAlbums(albums);
			album.setComment(com);
			 entityManager.persist(com);
			persist(album);
			commitTransaction();
		}
	}
}
public  void updateAddLocation(String location, String albumName){
	for(Album f:albumList){
		if(f.getAlbumName().equals(albumName))
			albumList.remove(f);
	}
	addLocation(location, albumName);
	files.setAlbumList(albumList);
}

public void addLocation(String location, String albumName){
	albumName = albumName.toLowerCase();
	location = location.toLowerCase();
	Album album = findById(albumName);
	if(album != null) {
	JpaLocationDao jld = new JpaLocationDao();
		Location loc = jld.findById(location);
		if(loc != null){
			List<Album> albums =loc.getAlbums();

			if((!albums.contains(album))){

				beginTransaction();
				albums.add(album);
				loc.setAlbums(albums);
				album .setLocation(loc);
				persist(album);
				 entityManager.persist(loc);
				commitTransaction();
			}
		}else{
			beginTransaction();
			 loc= new Location();
			loc.setLocation(location);
			List<Album> albums = new ArrayList<Album>();
			albums.add(album);
			loc.setAlbums(albums);
			album .setLocation(loc);
			 entityManager.persist(loc);
			persist(album);
			commitTransaction();
		}
	}

}
public  void updateDeleteComment(String albumName){
	deleteComment(albumName);
	for(Album f:albumList){
		if(f.getAlbumName().equals(albumName))
			albumList.remove(f);
	}
	files.setAlbumList(albumList);
}
public void deleteComment(String albumName){
	albumName = albumName.toLowerCase();
	Album album = findById(albumName);
	if(album != null){
		Comment com = album.getComment();
		beginTransaction();
		List<Album> albums = com.getAlbums();
		albums.remove(album);
		com.setAlbums(albums);
		 entityManager.persist(com);
		commitTransaction();
	}
}
public  void updateDeleteLocation(String albumName){
	deleteLocation(albumName);
	for(Album f:albumList){
		if(f.getAlbumName().equals(albumName))
			albumList.remove(f);
	}
	files.setAlbumList(albumList);
}
public void deleteLocation(String albumName){
	Album album = findById(albumName);
	if(album!= null){
		Location loc = album.getLocation();
		beginTransaction();
		List<Album> albums = loc.getAlbums();
		albums.remove(album);
		loc.setAlbums(albums);
		 entityManager.persist(loc);
		commitTransaction();
	}
}

public void updateDeleteTags(String albumName){
	deleteTags(albumName);
	for(Album f:albumList){
		if(f.getAlbumName().equals(albumName))
			albumList.remove(f);
	}
	files.setAlbumList(albumList);
}
public void deleteTags(String albumName){
	albumName = albumName.toLowerCase();
	Album album = findById(albumName);
	if(album!= null){
		List<Tag> tags = album.getTags();
		for(Tag tag: tags){

			beginTransaction();
			List<Album> albums =tag.getAlbums();
			albums .remove(album);
			tag.setAlbums(albums);
			 entityManager.persist(tag);
			commitTransaction();
		}
	}
}

public void deletePicture(String filePath){
	JpaPictureDao jpd = new JpaPictureDao();
	Picture picture = jpd.findById(filePath);

	if(picture != null){
		beginTransaction();
		Album album = picture.getAlbum();
		List<Picture> pics = album.getPictures();
		pics.remove(picture);
		album.setPictures(pics);
		persist(album);
		beginTransaction();	
	}
}
}
