package Model;

import java.util.ArrayList;
import java.util.List;

/**
 *  Main class for structuring the program, including add and delete functions
 */
public class Folder {

    protected List<Folder> subFolders;
    protected List<SoundClip> sounds;
    private Folder parent;
    private String name;


    /**
     * @param name The name of the folder
     */
    public Folder (String name){
        assert !name.isEmpty() : "Invalid name";
        assert name != null;
        this.name = name;
        sounds = new ArrayList<>();
        subFolders = new ArrayList<>();

    }


    public Folder getParentAlbum(){
        return this.parent;
    }

    /**
     * @return returns the name of an folder
     */
    public String getName (){
        return this.name;
    }

    public String toString(){
        return this.name;
    }

    /**
     * @return returns a different boolean, depending on if the folder is a root folder or not
     */
    public boolean isRoot(){return this.parent == null;}

    /**
     * Gets the content of the folder and it's subfolders
     */
//    public String getContent(){
//
//        StringBuilder bob = new StringBuilder();
//
//        if (this.subFolders.isEmpty() && this.sounds.isEmpty()){
//            bob.append("Empty");
//        }
//
//        if (this.subFolders.isEmpty()) {
//            bob.append("No Subfolders");
//            bob.append("\n");
//        }
//        else {
//            bob.append("Subfolders: \n");
//            for (Folder fol : this.subFolders){
//                bob.append("- " + fol.getName() + "\n");
//            }
//        }
//        if (this.sounds.isEmpty()) {
//            bob.append("No soundclips\n");
//        }
//        else {
//            bob.append("Soundclips: \n");
//            for (SoundClip sound : this.sounds){
//                bob.append("- " + sound.toString()+ "\n");
//            }
//        }
//        return bob.toString();
//    }

    public boolean contains(Folder f) {
        return this.subFolders.contains(f);
    }

    public boolean contains(SoundClip s) {
        return this.sounds.contains(s);
    }

    /**
     * @param s Takes the soundclip as input
     * @return returns ture/false depending on whether it finds the file or not
     */
    public boolean findSoundClip(SoundClip s){

        assert s != null : "Invalid name";
        boolean fact = false;
        for (SoundClip sound : this.sounds){

            if (sound == s){
                fact = true;
                break;
            }
            else{fact = false;}
        }
        return fact;
    }

    /**
     * @param name Takes an input string and sets it as the name for the subfolder
     */
    public Folder createSubfolder(String name){

        assert !name.isEmpty() : "Invalid name";
        assert name != null;
        Folder nuFold = new Folder(name);
        this.subFolders.add(nuFold);
        nuFold.parent = this;
        return nuFold;
    }

    public void removeAlbum(Folder name){
        Folder temp = findFolder(name);
        assert temp != null : "No such folder exists";
        this.subFolders.remove(temp);


    }

    /**
     * @param s The soundfile itself
     */
    public void addSong (List<SoundClip> s){

        assert s != null : "Invalid soundcip";
        assert this != null : "Invalid folder";

        for (SoundClip sounds : s){
            if (!this.sounds.contains(sounds)) {
                this.sounds.add(sounds);
            }

        }
        if (!this.isRoot()){
            this.parent.addSong(s);
        }
//        this.sounds.add(sound);
//        if (!this.isRoot()){
//            this.parent.addSong(sound);
//        }
    }

    /**
     * @param sound The soundfile itself
     */
    public void removeSong(List<SoundClip> sound){
        this.sounds.removeAll(sound);
        if (!this.subFolders.isEmpty()){
            for (Folder f : this.subFolders){
                f.removeSong(sound);
            }
        }
    }

    public List<SoundClip> getAllSounds(){
        return this.sounds;
    }

    /**
     * @param name Takes a name of the folder
     * @return returns the folder if found
     */
    public Folder findFolder(Folder name){
        for (Folder f : this.subFolders){
            if (f == name){
                return f;
            }
        }
        return null;
    }

}
