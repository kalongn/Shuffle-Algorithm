package Java_Code_Alpha;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;

public class Song {
    private String trackName;
    private String[] artistsName;
    private int popularity;
    private double bpm, valence, energy, danceability, acousticness;
    private HashSet<String> genres;

    public Song(String trackName, String[] artistsName, int popularity, double bpm, double valence, double energy,
            double danceability,
            double acousticness, HashSet<String> genres) {
        this.trackName = trackName;
        this.artistsName = artistsName;
        this.popularity = popularity;
        this.bpm = bpm;
        this.valence = valence;
        this.energy = energy;
        this.danceability = danceability;
        this.acousticness = acousticness;
        this.genres = genres;
    }

    /**
     * 
     * @param songID
     * @return
     */
    public static Song readFromSongTxt(String songID) {
        File songFile = new File("./Condensed_Datas/SongDatas/" + songID + ".txt");
        Scanner scanner;
        try {
            scanner = new Scanner(songFile);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Invalid song ID.");
        }

        String trackName = scanner.nextLine();
        String artists = scanner.nextLine();
        String[] artistsName;
        if (artists.contains(",")) {
            artistsName = artists.split(",");
        } else {
            artistsName = new String[] { artists };
        }
        int popularity = scanner.nextInt();
        double bpm = scanner.nextDouble();
        double valence = scanner.nextDouble();
        double energy = scanner.nextDouble();
        double danceability = scanner.nextDouble();
        double acousticness = scanner.nextDouble();
        scanner.nextLine();
        HashSet<String> genres = new HashSet<>();
        while (scanner.hasNextLine()) {
            genres.add(scanner.nextLine());
        }
        scanner.close();

        return new Song(trackName, artistsName, popularity, bpm, valence, energy, danceability, acousticness, genres);
    }

    public String getTrackName() {
        return trackName;
    }

    public String[] getArtistsName() {
        return this.artistsName;
    }

    public int getPopularity() {
        return popularity;
    }

    public double getBpm() {
        return bpm;
    }

    public double getValence() {
        return valence;
    }

    public double getEnergy() {
        return energy;
    }

    public double getDanceability() {
        return danceability;
    }

    public double getAcousticness() {
        return acousticness;
    }

    public HashSet<String> getGenres() {
        return genres;
    }

    @Override
    public String toString() {
        String returnStr = "-------------------------------------------------------------\n" + this.getTrackName();
        returnStr += "\nThe artist(s) of this song is/are: ";
        for (String i : this.getArtistsName()) {
            returnStr += "\n- " + i;
        }
        returnStr += "\nThe popularity of this song is " + this.getPopularity() + " out of 100.";
        returnStr += "\nThe BPM of this song is " + this.getBpm() + ".";
        returnStr += "\nThe valence of this song is " + this.getValence() + " out of 1.";
        returnStr += "\nThe energy of this song is " + this.getEnergy() + " out of 1.";
        returnStr += "\nThe danceability of this song is " + this.getDanceability() + " out of 1.";
        returnStr += "\nThe acousticness of this song is " + this.getAcousticness() + " out of 1.";
        returnStr += "\nThis song may include the following genres: ";
        for (String i : this.getGenres()) {
            returnStr += "\n- " + i;
        }
        returnStr += "\n-------------------------------------------------------------";
        return returnStr;
    }

    public static void main(String[] args) {
        Song test = readFromSongTxt("0O6u0VJ46W86TxN9wgyqDj");
        System.out.println(test);
    }
}