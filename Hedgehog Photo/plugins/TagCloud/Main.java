import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import se.cth.hedgehogphoto.database.DatabaseAccess;
import se.cth.hedgehogphoto.database.Files;
import se.cth.hedgehogphoto.database.PictureObject;
import se.cth.hedgehogphoto.database.TagObject;
import se.cth.hedgehogphoto.plugin.GetDatabase;
import se.cth.hedgehogphoto.plugin.GetVisibleFiles;
import se.cth.hedgehogphoto.plugin.InitializePlugin;
import se.cth.hedgehogphoto.plugin.Panel;
import se.cth.hedgehogphoto.plugin.Plugin;
import se.cth.hedgehogphoto.view.PluginArea;


/**
 * @author Barnabas Sapan
 */

@Plugin(name="TagCloud", version="1.0", 
author="Barnabas Sapan", description="N/A")
public class Main {
	private TagCloudView tgv;
	private DatabaseAccess db;
	private Files files;
 
	@InitializePlugin
	public void start() {
		TagCloudModel tgm = new TagCloudModel(db);
		tgv = new TagCloudView();
		tgm.addObserver(tgv);
		
		List<String> l = new ArrayList<String>();
		for(PictureObject po : db.getAllPictures()){
			for(TagObject to : po.getTags()){
				l.add(to.getTag());
			}
		}
		
		tgm.setTags(l);
		files.addObserver(tgm);
	}

	@Panel(placement=PluginArea.LEFT_BOTTOM)
	public JPanel get(){
		return tgv;	
	}

	@GetDatabase
	public void setDB(DatabaseAccess db){
		this.db = db;
	}
	
	@GetVisibleFiles
	public void setFiles(Files files){
		this.files = files;
	}

}
