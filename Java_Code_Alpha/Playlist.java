package Java_Code_Alpha;

import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Playlist extends ArrayList<Song> {

    private String playlistTitle;

    public Playlist(String playlistTitle) {
        this.playlistTitle = playlistTitle;
    }

    public static Playlist readFromPlaylistTxt(String playListID) {
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

    public String getPlaylistTitle() {
        return this.playlistTitle;
    }

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
