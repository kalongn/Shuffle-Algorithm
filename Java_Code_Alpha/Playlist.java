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
}
