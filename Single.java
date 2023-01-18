/**
 * This Single Class allowing us to create a Single object. Contain mutiple
 * field that would be important to the shuffling algorithm that will be
 * implemented using this class. Such as name of the single, songs
 * that is apart of this single, total length of the single, the artist
 * of the single and the releaseYear of this album.
 * 
 * @author Ka_Long_Ngai 01/18/2023
 */

public class Single {

    private String name;
    private Song[] songs;
    private Time lengthOfSongs;
    private Artist artist;
    private int releaseYear;

    /**
     * Create a Single object with various field, name of the Single, All the songs
     * within this Single, The total length of the Songs within this Single, The
     * Artist of this Single and the Release Year of this Single.
     * 
     * @param name
     * @param songs
     * @param artist
     * @param releaseYear
     */
    public Single(String name, Song[] songs, Artist artist, int releaseYear) {
        this.name = name;
        this.songs = songs;
        this.lengthOfSongs = calculateTotalLengthSingle(songs);
        this.artist = artist;
        this.releaseYear = releaseYear;
    }

    /**
     * @return the songs within this Single object.
     */
    public Song[] getSongs() {
        return songs;
    }

    /**
     * @param songs songs to set for the Single object.
     */
    public void setSongs(Song[] songs) {
        this.songs = songs;
    }

    /**
     * @return the length of all the song within songs for the Single object.
     */
    public Time getLengthOfSongs() {
        return lengthOfSongs;
    }

    /**
     * @param lengthOfSongs length of songs within this Single object to set for the
     *                      Single object.
     */
    public void setLengthOfSongs(Time lengthOfSongs) {
        this.lengthOfSongs = lengthOfSongs;
    }

    /**
     * @return the artist of the Single object.
     */
    public Artist getArtist() {
        return artist;
    }

    /**
     * @param artist artist to set for the Single object.
     */
    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    /**
     * @return the name of the Single object.
     */
    public String getName() {
        return name;
    }

    /**
     * @param name name to set for the Single object.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the release year of the Single object.
     */
    public int getReleaseYear() {
        return releaseYear;
    }

    /**
     * @param releaseYear release Year to set for the Single object.
     */
    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    /**
     * This method is a for each loop that calculate the total length of the entire
     * Single object, by looping over all the songs within the songs param array.
     * 
     * @param songs
     *              The songs array that contain all the songs in order to calculate
     *              for an output.
     * @return
     *         the total Time of the length of the entire Single.
     * @throws NullPointerException
     *                              If the param songs is an empty array.
     */
    public Time calculateTotalLengthSingle(Song[] songs) throws NullPointerException {
        if (songs.length == 0) {
            throw new NullPointerException("There is no songs within this Singles.");
        }
        Time returnSum = new Time(0);
        for (Song song : songs) {
            returnSum = Time.timeAddition(returnSum, song.getSongLength());
        }
        return returnSum;
    }

    /**
     * @return All the songs within this Single object in a List format.
     */
    @Override
    public String toString() {
        String returnString = this.getName() + ", " + this.getReleaseYear()
                + "\n------------------------------------------------\n";
        for (Song song : songs) {
            returnString += song.getName() + "\n";
        }
        returnString += "------------------------------------------------\n" + getLengthOfSongs();
        return returnString;
    }
}
