package Java_Code_Alpha;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.HashMap;

/**
 * This MusicController Class allowing us to control a Playlist by directly
 * giving a Spotify playlist URL. This will call the APICall.sh which get the
 * required data from the spotify API and then construct all the required data
 * in java and return a new Playlist object.
 * 
 * @author Ka_Long_Ngai 02/07/2023
 */

public class MusicController {

    private Playlist activePlaylist;

    /**
     * Demonstrating why a normal absolute random doesn't work a song shuffling.
     * Since the distrubution only stable in a long term which user won't be
     * shuffling this playlist again and again during a listening section. But
     * rather just one time shuffle.
     */
    public void absoluteShuffleShowCase() {
        Playlist originalPlaylist = this.activePlaylist.clone();
        HashMap<String, Integer> playlistOccur = new HashMap<>();
        for (int i = 0; i < 6; i++) {
            this.activePlaylist.absoluteShuffle();
            for (int j = 0; j < 5; j++) {
                playlistOccur.put(this.activePlaylist.get(j).getTrackName(),
                        playlistOccur.getOrDefault(this.activePlaylist.get(j).getTrackName(), 0) + 1);
            }
            this.activePlaylist = originalPlaylist.clone();
        }

        // printing the hashmap
        for (String name : playlistOccur.keySet()) {
            int occur = playlistOccur.get(name);
            System.out.println(name + ", Occurance: " + occur);
        }
        System.out.println(
                "-------------------------------------------------------------\nAbsolute shuffle, 6 random shuffle. All Songs make it to Top 5: "
                        + playlistOccur.size() + "\n");
    }

    public static void main(String[] args) throws IOException {

        MusicController spotcloud = new MusicController();
        boolean newPlaylist = false;
        try {
            FileInputStream file = new FileInputStream("playlist.obj");
            ObjectInputStream inStream = new ObjectInputStream(file);
            spotcloud.activePlaylist = (Playlist) inStream.readObject();
            inStream.close();
            System.out.println("Detected previous Playlist.\n");
        } catch (Exception ex) {
            System.out.println("no previous playlist detected.\nPlease create a new playlist.");
            newPlaylist = true;
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter Spotify playlist URL: ");
            String playlistID = scanner.nextLine();
            scanner.close();
            if (playlistID.length() != 76 || !playlistID.startsWith("https://open.spotify.com/playlist/")) {
                System.out.println("Invalid URL.");
                return;
            }
            playlistID = playlistID.substring(34, 56);
            try {
                Runtime.getRuntime().exec(new String[] { "bash", "./APICall.sh", playlistID }).waitFor();
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted.");
                return;
            } catch (IOException e) {
                System.out.println("Invalid URL.");
                e.printStackTrace();
            }
            spotcloud.activePlaylist = Playlist.readFromPlaylistTxt(playlistID);
        }

        if (newPlaylist) {
            System.out.print(spotcloud.activePlaylist);
            FileOutputStream file = new FileOutputStream("playlist.obj");
            ObjectOutputStream outStream = new ObjectOutputStream(file);
            outStream.writeObject(spotcloud.activePlaylist);
            outStream.close();
            return;
        }

        // spotcloud.absoluteShuffleShowCase();

        spotcloud.activePlaylist.spotifyBalanceShuffle();
        System.out.println(spotcloud.activePlaylist.toShortHandString());
    }

}