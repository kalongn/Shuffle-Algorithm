package Java_Code_Alpha;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

    private Playlist activePlaylist;

    public static void main(String[] args) throws IOException{

        MusicController spotcloud = new MusicController();

        try {
            FileInputStream file = new FileInputStream("playlist.obj");
            ObjectInputStream inStream = new ObjectInputStream(file);
            spotcloud.activePlaylist = (Playlist) inStream.readObject();
            inStream.close();
            System.out.println("Detected previous Playlist.");
        } catch (Exception ex) {
            System.out.println("no previous playlist detected.\nPlease create a new playlist.");
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
        System.out.print(spotcloud.activePlaylist);
        FileOutputStream file = new FileOutputStream("playlist.obj");
        ObjectOutputStream outStream = new ObjectOutputStream(file);
        outStream.writeObject(spotcloud.activePlaylist);
        outStream.close();
    }

}