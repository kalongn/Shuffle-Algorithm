package Java_Code_Alpha;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Scanner;

/**
 * This Song Class allowing us to create a Song object. With the inclusion of
 * song statistics as field variables of this Song object.
 * 
 * @author Ka_Long_Ngai 02/06/2023
 */
public class Song implements Serializable, Comparable<Song> {
    private String trackName;
    private String[] artistsName;
    private int popularity;
    private double bpm, valence, energy, danceability, acousticness, tempSimValue;
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
        this.tempSimValue = -1.0;
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
     * @return
     *         the tempSimValue of this Song object.
     */
    public double getTempSimValue() {
        return this.tempSimValue;
    }

    /**
     * Round double to the thousand place.
     * 
     * @param number
     *               Input double variable.
     * @return
     *         the input double round to thousand place.
     */
    public static double roundtoThousand(double number) {
        return (double) Math.round(number * 1000) / 1000;
    }

    /**
     * This method calculate the similarity value between 2 given double param, will
     * return a decimal between 0 and 1 where value closer or approaching 0 is less
     * similar. Value closer to 1 or approaching 1 is very similar if not the same.
     * 
     * @param one
     *            the first double value.
     * @param two
     *            the second double value.
     * @return
     *         A double value range from [0, 1] represent a percentage of
     *         similarity.
     */
    public static double compareTwoDouble(double one, double two) {
        return roundtoThousand(1 - ((double) Math.abs(one - two) / ((double) (one + two / 2))));
    }

    /**
     * This method calculate the similarity between the popularity of the 2 songs
     * param, will return a decimal between 0 and 1 where 0 is not similar at all
     * and 1 is that both songs share the same popularity.
     * 
     * @param otherSong
     *                  The Song object you want to compare with this reference Song
     *                  object.
     * @return
     *         A double value range from [0,1] represent a percentage of similarity.
     */
    public double comparePopularity(Song otherSong) {
        return compareTwoDouble((double) this.getPopularity(), (double) otherSong.getPopularity());
    }

    /**
     * This method calculate the similarity between the bpm of the 2 songs
     * param, will return a decimal between 0 and 1 where 0 is not similar at all
     * and 1 is that both songs share the same bpm.
     * 
     * @param otherSong
     *                  the Song object you want to compare with this reference Song
     *                  object.
     * @return
     *         A double value range from [0, 1] represent a percentage of
     *         similarity.
     */
    public double compareBPM(Song otherSong) {
        return compareTwoDouble(this.getBpm(), otherSong.getBpm());
    }

    /**
     * This method calculate the similarity between the Valence of the 2 songs
     * param, will return a decimal between 0 and 1 where 0 is not similar at all
     * and 1 is that both songs share the same Valence.
     * 
     * @param otherSong
     *                  the Song object you want to compare with this reference Song
     *                  object.
     * @return
     *         A double value range from [0, 1] represent a percentage of
     *         similarity.
     */
    public double compareValence(Song otherSong) {
        return compareTwoDouble(this.getValence(), otherSong.getValence());
    }

    /**
     * This method calculate the similarity between the Energy of the 2 songs
     * param, will return a decimal between 0 and 1 where 0 is not similar at all
     * and 1 is that both songs share the same Energy.
     * 
     * @param otherSong
     *                  the Song object you want to compare with this reference Song
     *                  object.
     * @return
     *         A double value range from [0, 1] represent a percentage of
     *         similarity.
     */
    public double compareEnergy(Song otherSong) {
        return compareTwoDouble(this.getEnergy(), otherSong.getEnergy());
    }

    /**
     * This method calculate the similarity between the Danceability of the 2 songs
     * param, will return a decimal between 0 and 1 where 0 is not similar at all
     * and 1 is that both songs share the same Danceability.
     * 
     * @param otherSong
     *                  the Song object you want to compare with this reference Song
     *                  object.
     * @return
     *         A double value range from [0, 1] represent a percentage of
     *         similarity.
     */
    public double compareDanceability(Song otherSong) {
        return compareTwoDouble(this.getDanceability(), otherSong.getDanceability());
    }

    /**
     * This method calculate the similarity between the Acousticness of the 2 songs
     * param, will return a decimal between 0 and 1 where 0 is not similar at all
     * and 1 is that both songs share the same Acousticness.
     * 
     * @param otherSong
     *                  the Song object you want to compare with this reference Song
     *                  object.
     * @return
     *         A double value range from [0, 1] represent a percentage of
     *         similarity.
     */
    public double compareAcousticness(Song otherSong) {
        return compareTwoDouble(this.getAcousticness(), otherSong.getAcousticness());
    }

    /**
     * This method calculate the similarity between the genres of the 2 songs
     * param, will return a decimal between 0 and 1 where 0 is not similar at all
     * and 1 is that both songs share all the same genres. If this method return
     * -1.0, this method is indicating the genres cannot be used for any valid
     * comparison.
     * 
     * @param otherSong
     *                  The Song object you want to compare with this reference Song
     *                  object.
     * @return
     *         A double value range from [0,1] represent a percentage of similarity.
     *         If the value return is -1, it indiciate a failure to calculate valid
     *         result since either this or otherSong does not have a valid Genres.
     */
    public double compareGenres(Song otherSong) {
        if (this.getGenres().size() == 0 || otherSong.getGenres().size() == 0) {
            return -1.0;
        }
        HashSet<String> combinedGenres = new HashSet<>();
        combinedGenres.addAll(this.getGenres());
        combinedGenres.addAll(otherSong.getGenres());
        int totalGenresSize = this.getGenres().size() + otherSong.getGenres().size();
        return roundtoThousand((double) (totalGenresSize - combinedGenres.size()) / combinedGenres.size());
    }

    /**
     * This method sums up all the similarity value and takes the avg of all the
     * similarity values. The value will be stored in the otherSong param's
     * tempSimValue attribute.
     * 
     * @param otherSong
     *                  The Song object you want to compare with this reference Song
     *                  object.
     */
    public void avgSimilarities(Song otherSong) {
        double genresVal = this.compareGenres(otherSong);
        if (genresVal == -1.0) {
            otherSong.tempSimValue = roundtoThousand((this.compareAcousticness(otherSong) + this.compareBPM(otherSong)
                    + this.compareDanceability(otherSong) + this.compareEnergy(otherSong)
                    + this.comparePopularity(otherSong) + this.compareValence(otherSong)) / 6);
        }
        otherSong.tempSimValue = roundtoThousand((this.compareAcousticness(otherSong) + this.compareBPM(otherSong)
                + this.compareDanceability(otherSong) + this.compareEnergy(otherSong) + genresVal
                + this.comparePopularity(otherSong) + this.compareValence(otherSong)) / 7);
    }

    /**
     * This method return a weight proportion of similarity across all songs,
     * althought all value are set by a developer. This can be modified later or
     * even implement later script create more flexible/random experience on the
     * weight of each category. The value will be stored in the otherSong param's
     * tempSimValue attribute.
     * 
     * @param otherSong
     *                  The Song object you want to compare with this reference Song
     *                  object.
     */
    public void weightedSimilarities(Song otherSong) {
        double genresVal = this.compareGenres(otherSong);
        double otherStatsWeighted = roundtoThousand(
                this.compareAcousticness(otherSong) * 0.15 + this.compareBPM(otherSong) * .25
                        + this.compareDanceability(otherSong) * 0.05 + this.compareEnergy(otherSong) * 0.15
                        + this.comparePopularity(otherSong) * .10 + this.compareValence(otherSong) * .3);
        if (genresVal == -1.0) {
            otherSong.tempSimValue = otherStatsWeighted;
        }
        otherSong.tempSimValue = roundtoThousand(genresVal * .4 + otherStatsWeighted * .6);
    }

    /**
     * @return
     *         The song with only the object tracks name. Without all the stats.
     */
    public String toShortHandString() {
        return "\n" + this.getTrackName();
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

    /**
     * Compare 2 songs object, this is be base on their tempSimValue as we required
     * this for sorting.
     * 
     * @return
     *         0 indicated the tempSimValue is the same. 1 indicated the
     *         tempSimValue is greater on songOne, -1 indicated the tempSimValue is
     *         greater on songTwo.
     */
    @Override
    public int compareTo(Song otherSong) {
        if (this.tempSimValue == otherSong.getTempSimValue()) {
            return 0;
        } else if (this.tempSimValue > otherSong.getTempSimValue()) {
            return 1;
        }
        return -1;
    }
}
