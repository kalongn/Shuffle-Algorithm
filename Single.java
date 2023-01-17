public class Single {

    private String name;
    private Song[] songs;
    private Time lengthOfSongs;
    private Artist artist;

    public Single(String name, Song[] songs, Time lengthOfSongs, Artist artist) {
        this.name = name;
        this.songs = songs;
        this.lengthOfSongs = lengthOfSongs;
        this.artist = artist;
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

    @Override
    public String toString() {
        String returnString = this.getName() + "\n------------------------------------------------\n";
        for (Song song : songs) {
            returnString += song.getName() + "\n";
        }
        returnString += "------------------------------------------------\n" + getLengthOfSongs();
        return returnString;
    }
}
