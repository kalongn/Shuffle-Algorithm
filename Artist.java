import java.util.ArrayList;
public class Artist {

    private String artistName;
    private ArrayList<Song> songs;
    private SongCollection songsReleases;
    private long monthlyListeners;
    private Song[] popularSongs;

    public Artist(String artistName) {
        this.artistName = artistName;
        this.songs = new ArrayList<Song>();
        songsReleases = null;
        monthlyListeners = -1;
        popularSongs = new Song[] {null, null, null, null, null};
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public ArrayList<Song> getSongs() {
        return songs;
    }

    public void setSongs(ArrayList<Song> songs) {
        this.songs = songs;
    }

    public void addSong(Song song) {
        this.songs.add(song);
    }

    public SongCollection getSongsReleases() {
        return songsReleases;
    }

    public void setSongsReleases(SongCollection songsReleases) {
        this.songsReleases = songsReleases;
    }

    public long getMonthlyListeners() {
        return monthlyListeners;
    }

    public void setMonthlyListeners(long monthlyListeners) {
        this.monthlyListeners = monthlyListeners;
    }

    public Song[] getPopularSongs() {
        return popularSongs;
    }

    public void setPopularSongs(Song[] popularSongs) {
        this.popularSongs = popularSongs;
    }
}
