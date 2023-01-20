/**
 * This Song Class allowing us to create a Song object. Contain mutiple field
 * that would be important to the shuffling algorithm that will be implemented
 * using this class. Such as bpm, genre, apart of what album, single or EP.
 * 
 * @author Ka_Long_Ngai 01/15/2023
 */
public class Song {

    private String name;
    private int bpm;
    private Artist[] artists;
    private long plays;
    private Time songLength;
    private boolean isExplicit;
    private SongCollection songCollection;
    private String[] genres;

    /**
     * Create a Song object with various field, such as the name of the song, the
     * beat per minute of the song, the amount of plays this song have, the length
     * of the song, is the song explicit, which album, EP or single it is apart of
     * and the genres of the song.
     * 
     * @param name
     *                   Represent the name of the song.
     * @param bpm
     *                   Represnet the beat per minute of the song.
     * @param artists
     *                   Represent the artists of the song. The First Artist (index
     *                   0) of this variable will be the primary Artist.
     * @param plays
     *                   Represent the amount of plays this song have (or as a
     *                   way to measure popularity).
     * @param songLength
     *                   Represent the length fo the song using the Time
     *                   Class.
     * @param isExplicit
     *                   Determine whether the song contain explicit lyrics or
     *                   message.
     * @param genres
     *                   Represent all the genres this song fall under.
     */
    public Song(String name, int bpm, Artist[] artists, long plays, Time songLength, boolean isExplicit,
            String[] genres) {
        this.name = name;
        this.setBpm(bpm);
        this.artists = artists;
        this.setPlays(plays);
        this.songLength = songLength;
        this.isExplicit = isExplicit;
        this.songCollection = null;
        this.genres = genres;

        for (Artist artist : artists) {
            artist.addSong(this);
        }
    }

    /**
     * @return the name of the Song object.
     */
    public String getName() {
        return name;
    }

    /**
     * @param name name to set for the Song object.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the bpm of the Song object.
     */
    public int getBpm() {
        return bpm;
    }

    /**
     * @param bpm bpm to set for the Song object
     * @throws IllegalArgumentException Thrown if bpm is set to a negative value.
     */
    public void setBpm(int bpm) throws IllegalArgumentException {
        if (bpm <= 0) {
            throw new IllegalArgumentException("BPM of the song cannot less then or equal to 0.");
        }
        this.bpm = bpm;
    }

    /**
     * @return the artist of the Song object.
     */
    public Artist[] getArtists() {
        return artists;
    }

    /**
     * @param artist artist to set for the Song object.
     */
    public void setArtists(Artist[] artists) {
        this.artists = artists;
    }

    /**
     * @return the plays of the Song object.
     */
    public long getPlays() {
        return plays;
    }

    /**
     * @param plays plays to set for the Song object.
     * @throws IllegalArgumentException Thrown if plays is set to a negative value.
     */
    public void setPlays(long plays) throws IllegalArgumentException {
        if (plays < 0) {
            throw new IllegalArgumentException("Plays of the song cannot less then 0.");
        }
        this.plays = plays;
    }

    /**
     * @return the length of the Song object.
     */
    public Time getSongLength() {
        return songLength;
    }

    /**
     * @param songLength length of the song to set for the Song object.
     */
    public void setSongLength(Time songLength) {
        this.songLength = songLength;
    }

    /**
     * @return true: song is explicit, false: song is not explicit.
     */
    public boolean isExplicit() {
        return isExplicit;
    }

    /**
     * @param isExplicit status of explicity of the song to set for the Somg object.
     */
    public void setExplicit(boolean isExplicit) {
        this.isExplicit = isExplicit;
    }

    /**
     * @return the songCollection of this Song object.
     */
    public SongCollection getSongCollection() {
        return songCollection;
    }

    /**
     * @param songCollection the songCollection to set for the Song object.
     */
    public void setSongCollection(SongCollection songCollection) {
        this.songCollection = songCollection;
    }

    /**
     * @return the genres of the Song object.
     */
    public String[] getGenres() {
        return genres;
    }

    /**
     * @param genres array of genres to set for the Song object.
     */
    public void setGenres(String[] genres) {
        this.genres = genres;
    }

    /**
     * @return the Song object in a single line displaying the song name, which
     *         album/EP/Single the song is apart of, the amount of plays the song
     *         have and its length.
     * 
     */
    @Override
    public String toString() {
        String returnStr = getName();
        if (isExplicit) {
            returnStr += "| |E| |";
        } else {
            returnStr += "| || |";
        }
        for (Artist artist : getArtists()) {
            returnStr += artist.getArtistName() + ", ";
        }
        returnStr = "|" + returnStr.substring(0, returnStr.length() - 2);
        if (this.getSongCollection() != null) {
            returnStr += "|" + getSongCollection().getName();
        }
        returnStr += "|" + getPlays() + "|" + getSongLength();
        return returnStr + "|";
    }

}