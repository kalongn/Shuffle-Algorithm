/**
 * This Single Class allowing us to create a Single object. Contain mutiple
 * field that would be important to the shuffling algorithm that will be
 * implemented using this class. Such as name of the single, songs
 * that is apart of this single, total length of the single, the artist
 * of the single and the releaseYear of this album.
 * 
 * @author Ka_Long_Ngai 01/15/2023
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

    public Song[] getSongs() {
        return songs;
    }

    public void setSongs(Song[] songs) {
        this.songs = songs;
    }

    public Time getLengthOfSongs() {
        return lengthOfSongs;
    }

    public void setLengthOfSongs(Time lengthOfSongs) {
        this.lengthOfSongs = lengthOfSongs;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public Time calculateTotalLengthSingle(Song[] songs) throws NullPointerException{
        if(songs.length == 0) {
            throw new NullPointerException("There is no songs within this Singles.");
        }
        Time returnSum = new Time(0);
        for(Song song : songs) {
            returnSum = Time.timeAddition(returnSum, song.getSongLength());
        }
        return returnSum;
    }

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
