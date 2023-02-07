package Java_Code_Alpha;

import java.io.IOException;
import java.util.Scanner;

/**
 * This MusicController Class allowing us to control a Playlist by directly
 * giving a Spotify playlist URL. This will call the APICall.sh which get the
 * required data from the spotify API and then construct all the required data
 * in java and return a new Playlist object.
 * 
 * @author Ka_Long_Ngai 02/07/2023
 */

public class MusicController {
    public static void main(String[] args) {
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
        Playlist playlist = Playlist.readFromPlaylistTxt(playlistID);
        System.out.print(playlist);
    }

}