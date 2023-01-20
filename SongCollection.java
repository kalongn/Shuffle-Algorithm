/**
 * This SongCollection Class allowing us to create a SongCollection object.
 * Contain mutiple field that would be important to the shuffling algorithm that
 * will be implemented using this class. Such as name of the SongCollection,
 * songs that is apart of this SongCollection, total length of the
 * SongCollection, the artist of the SongCollection and the releaseYear of this
 * album.
 * 
 * @author Ka_Long_Ngai 01/19/2023
 */

public class SongCollection {
    private String name;
    private Song[] songs;
    private Time lengthOfSongs;
    private Artist artist;
    private int releaseYear;
    private int typeOfSongCollection;

    /**
     * Create a SongCollection object with various field, name of the
     * SongCollection, All the songs
     * within this SongCollection, The total length of the Songs within this
     * SongCollection, The
     * Artist of this SongCollection and the Release Year of this SongCollection.
     * 
     * @param name
     *                             Represent the name of the SongCollection object.
     * @param songs
     *                             Represent the songs of the SongCollection object.
     * @param artist
     *                             Represent the artist of the SongCollection
     *                             object.
     * @param releaseYear
     *                             Represent the release year of the SongCollection
     *                             object.
     * @param typeOfSongCollection
     *                             Representing the type of Song collection this
     *                             SongCollection object is.
     */
    public SongCollection(String name, Song[] songs, Artist artist, int releaseYear, int typeOfSongCollection) {
        this.name = name;
        this.songs = songs;
        this.lengthOfSongs = calculateTotalLengthsongCollection(songs);
        this.artist = artist;
        this.releaseYear = releaseYear;
        this.typeOfSongCollection = typeOfSongCollection;

        for (Song song : songs) {
            song.setSongCollection(this);
        }
        this.artist.addToSongReleases(this);
    }

    /**
     * @return the songs within this SongCollection object.
     */
    public Song[] getSongs() {
        return songs;
    }

    /**
     * @param songs songs to set for the SongCollection object.
     */
    public void setSongs(Song[] songs) {
        this.songs = songs;
    }

    /**
     * @return the length of all the song within songs for the SongCollection
     *         object.
     */
    public Time getLengthOfSongs() {
        return lengthOfSongs;
    }

    /**
     * @param lengthOfSongs length of songs within this SongCollection object to set
     *                      for the SongCollection object.
     */
    public void setLengthOfSongs(Time lengthOfSongs) {
        this.lengthOfSongs = lengthOfSongs;
    }

    /**
     * @return the artist of the SongCollection object.
     */
    public Artist getArtist() {
        return artist;
    }

    /**
     * @param artist artist to set for the SongCollection object.
     */
    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    /**
     * @return the name of the SongCollection object.
     */
    public String getName() {
        return name;
    }

    /**
     * @param name name to set for the SongCollection object.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the release year of the SongCollection object.
     */
    public int getReleaseYear() {
        return releaseYear;
    }

    /**
     * @param releaseYear release Year to set for the SongCollection object.
     */
    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    /**
     * @return the typeOfSongCollection of the SongCollection object.
     *         <ul>
     *         <li>0: Single</li>
     *         <li>1: EP</li>
     *         <li>2: Album</li>
     *         <li>3: Collection</li>
     *         </ul>
     */
    public int getTypeOfSongCollection() {
        return typeOfSongCollection;
    }

    /**
     * @param typeOfSongCollection type of Song collection to set for the
     *                             SongCollection object.
     *                             <ul>
     *                             <li>0: Single</li>
     *                             <li>1: EP</li>
     *                             <li>2: Album</li>
     *                             <li>3: Collection</li>
     *                             </ul>
     * @exception IllegalArgumentException Thrown when input is not one of the
     *                                     option above (in the range of 0-3 and an
     *                                     int).
     */
    public void setTypeOfSongCollection(int typeOfSongCollection) throws IllegalArgumentException {
        if (typeOfSongCollection < 0 || typeOfSongCollection > 3) {
            throw new IllegalArgumentException(
                    "Not a Valid type of song Collection. Please enter a number in the range of 0 - 3. With 0 representing a Single, 1 representing an EP, 2 representing an Album and 3 representing a Collection.");
        }
        this.typeOfSongCollection = typeOfSongCollection;
    }

    /**
     * This method convert the typeOfSongCollection int variable into a String that
     * represent the respective index.
     * 
     * @return the proper readable name of what each number from 0 - 3 represent.
     *         <ul>
     *         <li>0: Single</li>
     *         <li>1: EP</li>
     *         <li>2: Album</li>
     *         <li>3: Collection</li>
     *         </ul>
     */
    public String typeOfSongCollectionToString() {

        switch (this.getTypeOfSongCollection()) {
            case 0:
                return "Single";
            case 1:
                return "EP";
            case 2:
                return "Album";
            case 3:
                return "Collection";
            default:
                return "Undefined";
        }
    }

    /**
     * This method is a for each loop that calculate the total length of the entire
     * SongCollection object, by looping over all the songs within the songs param
     * array.
     * 
     * @param songs
     *              The songs array that contain all the songs in order to calculate
     *              for an output.
     * @return
     *         the total Time of the length of the entire SongCollection.
     * @throws NullPointerException
     *                              If the param songs is an empty array.
     */
    public Time calculateTotalLengthsongCollection(Song[] songs) throws NullPointerException {
        if (songs.length == 0) {
            throw new NullPointerException("There is no songs within this songCollections.");
        }
        Time returnSum = new Time(0);
        for (Song song : songs) {
            returnSum = Time.timeAddition(returnSum, song.getSongLength());
        }
        return returnSum;
    }

    /**
     * @return All the songs within this SongCollection object in a List format.
     */
    @Override
    public String toString() {
        String returnString = this.typeOfSongCollectionToString() + ", " + this.getName() + ", "
                + this.getArtist().getArtistName()
                + ", " + this.getReleaseYear()
                + ", " + this.getSongs().length + ", " + getLengthOfSongs()
                + "\n------------------------------------------------\n";
        for (Song song : songs) {
            returnString += song.toString() + "\n";
        }
        returnString += "------------------------------------------------";
        return returnString;
    }

}
