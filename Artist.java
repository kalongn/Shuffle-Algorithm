import java.io.Serializable;
import java.util.ArrayList;

/**
 * This Artist Class allowing us to create a Artist object. This Artist class
 * contain crucial information of an artist. The Name, songs and collection of
 * songs, the totalAmountOfPlays and the 5 most popular song for this artist
 * object.
 * 
 * @author Ka_Long_Ngai 01/19/2023
 */
public class Artist implements Serializable {

    private String artistName;
    private ArrayList<Song> songs;
    private ArrayList<SongCollection> songsReleases;
    private long totalPlays;
    private Song[] popularSongs;
    private final int POPULARSONGSARRLENGTH = 5;

    /**
     * Creater an Artist object with the name of the artist only, other variable
     * will be set to empty or null state and will be adjusted when songs are added
     * to the artist profile.
     * 
     * @param artistName
     *                   the name of the artist to be set to.
     */
    public Artist(String artistName) {
        this.artistName = artistName;
        this.songs = new ArrayList<Song>();
        songsReleases = new ArrayList<SongCollection>();
        totalPlays = -1;
        popularSongs = new Song[] { null, null, null, null, null };
    }

    /**
     * @return the artist's name of the Artist object.
     */
    public String getArtistName() {
        return artistName;
    }

    /**
     * @param artistName the artist name to set for this Artist object.
     */
    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    /**
     * @return all the songs under this Artist object.
     */
    public ArrayList<Song> getSongs() {
        return songs;
    }

    /**
     * @param song add a single Song to this Artist, will update popular songs and
     *             totalPlays of this artist accordingly.
     */
    public void addSong(Song song) {
        this.songs.add(song);
        this.addSongToPopularSongs(song);
        this.addtoTotalPlays(song.getPlays());
    }

    /**
     * @return an ArrayList of all songCollection that is under this Artist object.
     */
    public ArrayList<SongCollection> getSongsReleases() {
        return songsReleases;
    }

    /**
     * @param collectionToBeAdded add a new songCollection into this Artist Object's
     *                            songReleases ArrayList.
     */
    public void addToSongReleases(SongCollection collectionToBeAdded) {
        this.songsReleases.add(collectionToBeAdded);
    }

    /**
     * @return the totalPlays of this Artist object.
     */
    public long getTotalPlays() {
        return totalPlays;
    }

    /**
     * @param playsToBeAdded the long value of a newly added song into the
     *                       totalPlays.
     */
    public void addtoTotalPlays(long playsToBeAdded) {
        this.totalPlays += playsToBeAdded;
    }

    /**
     * @return an array with a length of 5 of the most popular songs.
     */
    public Song[] getPopularSongs() {
        return popularSongs;
    }

    /**
     * This method sort the popular songs with the song having the most plays under
     * this Artist object be on the top. Forming a decending order array.
     */
    private void sortPopularSongs() {
        boolean done = false;
        for (int i = 0; i < POPULARSONGSARRLENGTH; i++) {
            done = true;
            for (int j = 0; j < POPULARSONGSARRLENGTH - i - 1; j++) {
                if (popularSongs[j] == null || popularSongs[j + 1] == null) {
                    break;
                }
                if (popularSongs[j].getPlays() < popularSongs[j + 1].getPlays()) {
                    Song temp = popularSongs[j];
                    popularSongs[j] = popularSongs[j + 1];
                    popularSongs[j + 1] = temp;
                    done = false;
                }
            }
            if (done) {
                return;
            }
        }
    }

    /*
     * private int findMinInPopularSongs() {
     * int indexOfMin = 0;
     * if (popularSongs[indexOfMin] == null) {
     * return -1;
     * }
     * for (int i = 0; i < POPULARSONGSARRLENGTH; i++) {
     * if (popularSongs[i] == null) {
     * return indexOfMin;
     * }
     * if (popularSongs[indexOfMin].getPlays() > popularSongs[i].getPlays()) {
     * indexOfMin = i;
     * }
     * }
     * return indexOfMin;
     * }
     */

    /**
     * This method is way to determine whether a newly added song is also a
     * popularSongs for the artist. If it is, it will put into the most popularSongs
     * array in the right order.
     * 
     * @param songToBeAdd Song that will be added to the Artist PopularSongs array
     *                    if it has a higher Plays count then the minimum plays song
     *                    on the popularSongs array.
     */
    public void addSongToPopularSongs(Song songToBeAdd) {
        int indexOfMin = 0;
        for (int i = 0; i < POPULARSONGSARRLENGTH; i++) {
            if (popularSongs[i] == null) {
                popularSongs[i] = songToBeAdd;
                this.sortPopularSongs();
                return;
            }
            if (popularSongs[indexOfMin].getPlays() > popularSongs[i].getPlays()) {
                indexOfMin = i;
            }
        }
        if (songToBeAdd.getPlays() < popularSongs[indexOfMin].getPlays()) {
            return;
        }
        popularSongs[indexOfMin] = songToBeAdd;
        this.sortPopularSongs();
    }

    /**
     * Precondition: targetArtist must exist within artists array.
     * 
     * @param targetArtist
     * @param artists
     * @return
     */
    public static double artistContributionValue(Artist targetArtist, Artist[] artists) {
        long totalPlays = 0L;
        for (Artist artist : artists) {
            totalPlays += artist.getTotalPlays();
        }
        return Song.roundtoHundreth((double) targetArtist.getTotalPlays() / totalPlays);
    }

    /**
     * @return the Artist object into a List of items that indicate by headers.
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        String returnStr = this.getArtistName() + ", " + this.getTotalPlays()
                + "\n\nPopular Songs\n------------------------------------------------\n";
        for (int i = 0; i < POPULARSONGSARRLENGTH; i++) {
            if (popularSongs[i] != null) {
                returnStr += popularSongs[i].toString() + "\n";
            }
        }
        returnStr += "------------------------------------------------\n\nDiscography\n------------------------------------------------\n";
        for (int i = 0; i < this.getSongsReleases().size(); i++) {
            returnStr += "\n" + this.getSongsReleases().get(i).toString() + "\n\n";
        }
        returnStr += "------------------------------------------------\n\nAppeared On\n------------------------------------------------";
        for (int i = 0; i < this.getSongs().size(); i++) {
            if (!this.getSongs().get(i).getArtists()[0].getArtistName().equals(this.getArtistName())) {
                returnStr += "\n" + this.getSongs().get(i).toString();
            }
        }
        return returnStr + "\n------------------------------------------------";
    }

    /**
     * @return True: Artist object is the same, false: Artist object is not the same
     *         or the input is not an Artist object.
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Artist)) {
            return false;
        }
        Artist artist = (Artist) obj;
        return this.getArtistName().equals(artist.getArtistName()) && this.getTotalPlays() == artist.getTotalPlays();
    }
}
