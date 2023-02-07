package Java_Code_Alpha;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;

/**
 * This Song Class allowing us to create a Song object. With the inclusion of
 * song statistics as field variables of this Song object.
 * 
 * @author Ka_Long_Ngai 02/06/2023
 */
public class Song {
    private String trackName;
    private String[] artistsName;
    private int popularity;
    private double bpm, valence, energy, danceability, acousticness;
    private HashSet<String> genres;

    /**
     * Create a Song object with the name of the Track, the artists name that are
     * reponsible of producing this track, the popularity of this song, the bpm of
     * this song, the valence of this song, the energy of this song, the
     * danceability of this song and the acousticness of this song.
     * 
     * @param trackName
     *                     The name of this Song object, String.
     * @param artistsName
     *                     The name of all the artists of this Song object,
     *                     String[].
     * @param popularity
     *                     The popularity of this Song object, int. Range: 0 - 100.
     * @param bpm
     *                     The bpm of the Song object, double. Range: 0 - 1.
     * @param valence
     *                     The valence of this Song object, double. Range: 0 - 1.
     * @param energy
     *                     The energy of this Song object, double. Range: 0 - 1.
     * @param danceability
     *                     The danceability of this Song object, double. Range: 0 -
     *                     1.
     * @param acousticness
     *                     The acousticness of this Song object, double. Range: 0 -
     *                     1.
     * @param genres
     *                     The genres of this Song object, HashSet<String>.
     * 
     * @see <a href=
     *      "https://developer.spotify.com/documentation/web-api/reference/#/operations/get-playlist">Playlist
     *      JSON documentation</a>
     *      <a href=
     *      "https://developer.spotify.com/documentation/web-api/reference/#/operations/get-audio-features">Track
     *      Features JSON documentation.</a>
     *      <a href=
     *      "https://developer.spotify.com/documentation/web-api/reference/#/operations/get-track">Track
     *      JSON documentation</a>
     */
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
     * Read a txt File base on the songID provided, return a newly created Song
     * object if correct.
     * 
     * @param songID
     *               the songID (32 characters) of spotify song.
     * @return
     *         A new Song object with the data written in the text file translated.
     * @exception IllegalArgumentException
     *                                     if songID file is not found or songID is
     *                                     invalid.
     */
    public static Song readFromSongTxt(String songID) throws IllegalArgumentException {
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
        songFile.delete();
        return new Song(trackName, artistsName, popularity, bpm, valence, energy, danceability, acousticness, genres);
    }

    /**
     * @return
     *         The track name of this Song object.
     */
    public String getTrackName() {
        return trackName;
    }

    /**
     * @return
     *         The artists name of this Song object.
     */
    public String[] getArtistsName() {
        return this.artistsName;
    }

    /**
     * @return
     *         The popularity of this Song object.
     * 
     */
    public int getPopularity() {
        return popularity;
    }

    /**
     * @return
     *         The Bpm of this Song object.
     */
    public double getBpm() {
        return bpm;
    }

    /**
     * @return
     *         The valence of this Song object.
     */
    public double getValence() {
        return valence;
    }

    /**
     * @return
     *         The energy of this Song object.
     */
    public double getEnergy() {
        return energy;
    }

    /**
     * @return
     *         the danceability of this Song object.
     */
    public double getDanceability() {
        return danceability;
    }

    /**
     * @return
     *         the acousticness of this Song object.
     */
    public double getAcousticness() {
        return acousticness;
    }

    /**
     * @return
     *         the genres of this Song object.
     */
    public HashSet<String> getGenres() {
        return genres;
    }

    /**
     * @see java.lang.Object#toString()
     * @return
     *         Every stats of this Song object will be added in a list format. Then
     *         being return.
     */
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

    /**
     * Create a deep copy of this current Song object.
     * 
     * @see java.lang.Object#clone()
     * @return
     *         A new cloned version of this Song object.
     */
    @Override
    protected Song clone() {
        return new Song(this.getTrackName(), this.getArtistsName().clone(), this.getPopularity(), this.getBpm(),
                this.getValence(), this.getEnergy(), this.getDanceability(), this.getAcousticness(),
                new HashSet<String>(this.getGenres()));
    }

    /**
     * Check whether the Song object and input share the same value.
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     * @return
     *         A True or false to indiciate equivalence.
     */
    @Override
    public boolean equals(Object arg) {
        if (!(arg instanceof Song)) {
            return false;
        }
        Song arg0 = (Song) arg;
        if (arg0.getArtistsName().length != this.getArtistsName().length) {
            return false;
        }
        for (int i = 0; i < this.getArtistsName().length; i++) {
            if (!this.getArtistsName()[i].equals(arg0.getArtistsName()[i])) {
                return false;
            }
        }
        return this.getTrackName().equals(arg0.getTrackName()) && this.getPopularity() == arg0.getPopularity()
                && this.getBpm() == arg0.getBpm() && this.getValence() == arg0.getValence()
                && this.getEnergy() == arg0.getEnergy() && this.getDanceability() == arg0.getDanceability()
                && this.getAcousticness() == arg0.getAcousticness() && this.getGenres().equals(arg0.getGenres());
    }
}
