package Java_Code_Alpha;

import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * This Playlist Class allowing us to create a Playlist object. This class
 * extended ArrayList while having an additional field variable, aka the
 * playlist name.
 * 
 * @author Ka_Long_Ngai 02/06/2023
 */
public class Playlist extends ArrayList<Song> {

    private String playlistTitle;

    /**
     * Create a Playlist object, with an additional playlistTitle field variable.
     * 
     * @param playlistTitle
     *                      The name of this playlist, String.
     */
    public Playlist(String playlistTitle) {
        this.playlistTitle = playlistTitle;
    }

    /**
     * read from a PlaylistID file and convert every songID from the playlist into a
     * Song object and added this new Playlist Object and return it.
     * 
     * @param playListID
     *                   the playlist ID of a spotify URL (32 characters).
     * @return
     *         A playlist Object with every valid Song ID which created Song objects
     *         added into this Playlist.
     * @exception IllegalArgumentException
     *                                     thrown when the playList ID is not valid
     *                                     or file is not found.
     */
    public static Playlist readFromPlaylistTxt(String playListID) throws IllegalArgumentException {
        File playListFile = new File("./Condensed_Datas/PlaylistDatas/" + playListID + ".txt");
        Scanner scanner;
        try {
            scanner = new Scanner(playListFile);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Invalid playlist ID.");
        }
        String playlistTitle = scanner.nextLine();
        Playlist returnPlaylist = new Playlist(playlistTitle);
        while (scanner.hasNextLine()) {
            Song song = Song.readFromSongTxt(scanner.nextLine());
            returnPlaylist.add(song);
        }
        scanner.close();
        playListFile.delete();
        return returnPlaylist;
    }

    /**
     * @return
     *         the title/name of this playlist.
     */
    public String getPlaylistTitle() {
        return this.playlistTitle;
    }

    /**
     * return the entire playlist in a list format String.
     * 
     * @see java.util.AbstractCollection#toString()
     */
    @Override
    public String toString() {
        String returnString = "-------------------------------------------------------------\n"
                + this.getPlaylistTitle();
        for (Song i : this) {
            returnString += "\n" + i;
        }
        return returnString;
    }

    /**
     * Create a deep copy of this Playlist Object.
     * 
     * @see java.util.ArrayList#clone()
     * @return
     *         A cloned this sPlaylist object.
     */
    @Override
    public Playlist clone() {
        Playlist clone = new Playlist(this.getPlaylistTitle());
        for (Song i : this) {
            clone.add(i.clone());
        }
        return clone;
    }

    /**
     * Determine whether the playlist have the same Song object in the same order
     * and each song same the same data.
     * 
     * @see java.util.AbstractList#equals(java.lang.Object)
     * @return
     *         True or false to indicate equivalence.
     */
    @Override
    public boolean equals(Object arg0) {
        if (!(arg0 instanceof Playlist)) {
            return false;
        }
        Playlist other = (Playlist) arg0;
        for (int i = 0; i < this.size(); i++) {
            if (!this.get(i).equals(other.get(i))) {
                return false;
            }
        }
        return true;
    }
}
