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
     * Demonstrating why a true random for song shuffling.
     */
    public void trueShuffleShowCase() {
        Playlist originalPlaylist = this.activePlaylist.clone();
        HashMap<String, Integer> playlistOccur = new HashMap<>();
        for (int i = 0; i < 6; i++) {
            this.activePlaylist.trueShuffle();
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

    /**
     * Demonstate the runtime of a true shuffle function.
     * 
     * @return
     *         a long variable of the time used in nano-seconds.
     */
    public long timeTrueShuffle() {
        long startTime = System.nanoTime();
        this.activePlaylist.trueShuffle();
        long endTime = System.nanoTime();
        return endTime - startTime;
    }

    /**
     * Demonstrating an imitation of spotify 2014 shuffling algorithm.
     */
    public void spotifyBalanceShuffleShowCase() {
        Playlist originalPlaylist = this.activePlaylist.clone();
        HashMap<String, Integer> playlistOccur = new HashMap<>();
        for (int i = 0; i < 6; i++) {
            this.activePlaylist.spotifyBalanceShuffle();
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
                "-------------------------------------------------------------\nSpotify 2014 shuffle, 6 random shuffle. All Songs make it to Top 5: "
                        + playlistOccur.size() + "\n");
    }

    /**
     * Demonstate the runtime of the spotify 2014 shuffle function.
     * 
     * @return
     *         a long variable of the time used in nano-seconds.
     */
    public long timeSpotifyBalanceShuffle() {
        long startTime = System.nanoTime();
        this.activePlaylist.spotifyBalanceShuffle();
        long endTime = System.nanoTime();
        return endTime - startTime;
    }

    public static void main(String[] args) throws IOException {

        MusicController spotcloud = new MusicController();
        boolean newPlaylist = false;
        Scanner scanner = new Scanner(System.in);
        try {
            FileInputStream file = new FileInputStream("playlist.obj");
            ObjectInputStream inStream = new ObjectInputStream(file);
            spotcloud.activePlaylist = (Playlist) inStream.readObject();
            inStream.close();
            System.out.println("Detected previous Playlist.\n");
            System.out.print("Do you want to use this as your data sample? (Y/N): ");
            String input = scanner.nextLine();
            if (input.equals("N")) {
                throw new Exception("expected");
            }
        } catch (Exception ex) {
            if (ex.getMessage().equals("expected")) {
                System.out.println("Please create a new playlist.");
            } else {
                System.out.println("no previous playlist detected.\nPlease create a new playlist.");
            }
            newPlaylist = true;
            System.out.print("Enter Spotify playlist URL: ");
            String playlistID = scanner.nextLine();
            if (playlistID.length() != 76 || !playlistID.startsWith("https://open.spotify.com/playlist/")) {
                System.out.println("Invalid URL.");
                scanner.close();
                return;
            }
            playlistID = playlistID.substring(34, 56);
            try {
                Runtime.getRuntime().exec(new String[] { "bash", "./APICall.sh", playlistID }).waitFor();
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted.");
                scanner.close();
                return;
            } catch (IOException e) {
                System.out.println("Invalid URL.");
                e.printStackTrace();
            }
            spotcloud.activePlaylist = Playlist.readFromPlaylistTxt(playlistID);
        }
        scanner.close();

        if (newPlaylist) {
            System.out.print(spotcloud.activePlaylist);
            FileOutputStream file = new FileOutputStream("playlist.obj");
            ObjectOutputStream outStream = new ObjectOutputStream(file);
            outStream.writeObject(spotcloud.activePlaylist);
            outStream.close();
            return;
        }

        // spotcloud.trueShuffleShowCase();
        // spotcloud.spotifyBalanceShuffleShowCase();
        Playlist originalPlaylist = spotcloud.activePlaylist.clone();
        spotcloud.activePlaylist.trueShuffle();
        System.out.println("True Random\n" + spotcloud.activePlaylist.toShortHandString());
        spotcloud.activePlaylist = originalPlaylist;
        spotcloud.activePlaylist.spotifyBalanceShuffle();
        System.out.println("Balance Random\n" + spotcloud.activePlaylist.toShortHandString());
        // System.out.println(spotcloud.timeTrueShuffle());
        // System.out.println(spotcloud.timeSpotifyBalanceShuffle());
    }

}