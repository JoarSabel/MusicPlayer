package Controller;

import Model.*;
import View.MusicOrganizerWindow;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MusicOrganizerController {

	private MusicOrganizerWindow view;
	private SoundClipBlockingQueue queue;
	private Folder root;
	
	public MusicOrganizerController() {
		
		// TODO: Create the root album for all sound clips
		root = new Folder("All Sound Clips");
		
		// Create the View in Model-View-Controller
		view = new MusicOrganizerWindow(this);
		
		// Create the blocking queue
		queue = new SoundClipBlockingQueue();
		
		// Create a separate thread for the sound clip player and start it
		(new Thread(new SoundClipPlayer(queue))).start();
	}

	/**
	 * Load the sound clips found in all subfolders of a path on disk. If path is not
	 * an actual folder on disk, has no effect.
	 */
	public Set<SoundClip> loadSoundClips(String path) {
		Set<SoundClip> clips = SoundClipLoader.loadSoundClips(path);
		List<SoundClip> sclips = new ArrayList<>();
		sclips.addAll(clips);
		root.addSong(sclips);
		System.out.println(root.getAllSounds().toString() + "loadSoundclips");
		// TODO: Add the loaded sound clips to the root album

		return clips;
	}
	
	/**
	 * Returns the root album
	 */
	public Folder getRootAlbum(){
		return root;
	}
	
	/**
	 * Adds an album to the Music Organizer
	 */
	public void addNewAlbum(Folder folder, String name){ //TODO Update parameters if needed - e.g. you might want to give the currently selected album as parameter
		// TODO: Add your code here
		view.onAlbumAdded(folder.createSubfolder(name));

	}
	
	/**
	 * Removes an album from the Music Organizer
	 */
	public void deleteAlbum(Folder folder){ //TODO Update parameters if needed
		// TODO: Add your code here
		view.onAlbumRemoved(folder);
		folder.getParentAlbum().removeAlbum(folder);
	}
	
	/**
	 * Adds sound clips to an album
	 */
	public void addSoundClips(Folder folder, List<SoundClip> sounds){ //TODO Update parameters if needed
		// TODO: Add your code here
		folder.addSong(sounds);
		view.onClipsUpdated();
	}
	
	/**
	 * Removes sound clips from an album
	 */
	public void removeSoundClips(Folder f, List<SoundClip> soundClip){ //TODO Update parameters if needed
		// TODO: Add your code here
		f.removeSong(soundClip);
		view.onClipsUpdated();

	}
	
	/**
	 * Puts the selected sound clips on the queue and lets
	 * the sound clip player thread play them. Essentially, when
	 * this method is called, the selected sound clips in the 
	 * View.SoundClipTable are played.
	 */
	public void playSoundClips(){
		List<SoundClip> l = view.getSelectedSoundClips();
		for(int i=0;i<l.size();i++)
			queue.enqueue(l.get(i));
	}
}
