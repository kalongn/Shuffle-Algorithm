package Java_Code_Alpha;
import java.io.IOException;
import java.util.Scanner;
public class MusicController {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Spotify playlist URL: ");
        String playlistID = scanner.nextLine();
        scanner.close();
        if(playlistID.length() != 76 || !playlistID.startsWith("https://open.spotify.com/playlist/")) {
            System.out.println("Invalid URL.");
            return;
        }
        playlistID = playlistID.substring(34,56);
        try {
            Runtime.getRuntime().exec(new String[] {"bash", "./APICall.sh", playlistID}).waitFor();
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