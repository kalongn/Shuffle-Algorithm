package Java_Code_Alpha;

import java.io.FileWriter;
import java.io.File;
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
                "-------------------------------------------------------------\nTrue shuffle, 6 random shuffle. All Songs make it to Top 5: "
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
     * Demonstrating an attempt on building a stat base shuffle algorithm.
     */
    public void statBaseShuffleShowCase() {
        Playlist originalPlaylist = this.activePlaylist.clone();
        HashMap<String, Integer> playlistOccur = new HashMap<>();
        for (int i = 0; i < 6; i++) {
            this.activePlaylist.statBaseShuffle();
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
                "-------------------------------------------------------------\nStat Base shuffle, 6 random shuffle. All Songs make it to Top 5: "
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

    /**
     * Demonstate the runtime of the attempt new shuffle function.
     * 
     * @return
     *         a long variable of the time used in nano-seconds.
     */
    public long timeStatBaseShuffle() {
        long startTime = System.nanoTime();
        this.activePlaylist.statBaseShuffle();
        long endTime = System.nanoTime();
        return endTime - startTime;
    }

    /**
     * Test method to see the distribution of songs appear in the top 5 in 6
     * shuffle.
     * 
     * @param musicController
     *                        the musicController with the Playlist.
     */
    public static void showCaseDistribution(MusicController musicController) {
        musicController.trueShuffleShowCase();
        musicController.spotifyBalanceShuffleShowCase();
        musicController.statBaseShuffleShowCase();
    }

    /**
     * Test method to see what songs are appearing for the entire playlist.
     * 
     * @param playlist
     *                 the playlist you would like to see shuffle.s
     */
    public static void seeSongsFromShuffleAlgorithms(Playlist playlist) {
        Playlist originalPlaylist = playlist.clone();
        playlist.trueShuffle();
        System.out.println("True Random\n" + playlist.toShortHandString());
        playlist = originalPlaylist.clone();
        playlist.spotifyBalanceShuffle();
        System.out.println("Balance Random\n" + playlist.toShortHandString());
        playlist = originalPlaylist.clone();
        playlist.statBaseShuffle();
        System.out.println("Stats-Base Random\n" + playlist.toShortHandString());
    }

    /**
     * See the runTime of each individual shuffle algorithm.
     * 
     * @param musicController
     *                        the musicController with the Playlist.
     * 
     */
    public static void seeRunTimeShuffleAlgorithms(MusicController musicController) {
        System.out.println("True shuffle time: " + musicController.timeTrueShuffle());
        System.out.println("Spotify clone shuffle time: " +
                musicController.timeSpotifyBalanceShuffle());
        System.out.println("Stats-Base shuffle time: " +
                musicController.timeStatBaseShuffle());
    }

    /**
     * This method call the shufflePlaylist function and shuffle the playlist. Then
     * write the result into a new file call "shuffleResultPlaylist.txt".
     * 
     * @param option
     *               An integer between 1-3, 1: true shuffle, 2: spotify
     *               balance shuffle, 3: stat-based shuffle.
     * @see Playlist#shufflePlaylist
     */
    public void shuffleAndOutput(int option) {
        Playlist temp = this.activePlaylist.clone();
        this.activePlaylist.shufflePlaylist(option);
        File file = new File("shuffleResultPlaylist.txt");
        try {
            file.createNewFile();
            FileWriter writer = new FileWriter("shuffleResultPlaylist.txt");
            writer.write(this.activePlaylist.getPlaylistID());
            for (Song i : this.activePlaylist) {
                writer.write("spotify:track:" + i.getTrackID() + ",");
            }
            writer.close();
        } catch (IOException ex) {
            System.out.println("File input output interrupted.");
            ex.printStackTrace();
        }
        this.activePlaylist = temp.clone();
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
            String input = scanner.nextLine().toUpperCase();
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

        spotcloud.shuffleAndOutput(1);

        // showCaseDistribution(spotcloud);
        // seeSongsFromShuffleAlgorithms(spotcloud.activePlaylist);
        // seeRunTimeShuffleAlgorithms(spotcloud);

        // spotcloud.activePlaylist.statBaseShuffle();
    }

}