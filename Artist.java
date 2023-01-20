import java.util.ArrayList;

public class Artist {

    private String artistName;
    private ArrayList<Song> songs;
    private ArrayList<SongCollection> songsReleases;
    private long totalPlays;
    private Song[] popularSongs;
    private final int POPULARSONGSARRLENGTH = 5;

    public Artist(String artistName) {
        this.artistName = artistName;
        this.songs = new ArrayList<Song>();
        songsReleases = new ArrayList<SongCollection>();
        totalPlays = -1;
        popularSongs = new Song[] { null, null, null, null, null };
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
        this.addSongToPopularSongs(song);
        this.addtoTotalPlays(song.getPlays());
    }

    public ArrayList<SongCollection> getSongsReleases() {
        return songsReleases;
    }

    public void setSongsReleases(ArrayList<SongCollection> songsReleases) {
        this.songsReleases = songsReleases;
    }

    public void addToSongReleases(SongCollection collectionToBeAdded) {
        this.songsReleases.add(collectionToBeAdded);
    }

    public long getTotalPlays() {
        return totalPlays;
    }

    public void setTotalPlays(long totalPlays) {
        this.totalPlays = totalPlays;
    }

    public void addtoTotalPlays(long playsToBeAdded) {
        this.totalPlays += playsToBeAdded;
    }

    public Song[] getPopularSongs() {
        return popularSongs;
    }

    public void setPopularSongs(Song[] popularSongs) {
        this.popularSongs = popularSongs;
    }

    public void sortPopularSongs() {
        boolean done = false;
        for (int i = 0; i < POPULARSONGSARRLENGTH; i++) {
            done = true;
            for (int j = 0; j < POPULARSONGSARRLENGTH - i - 1; j++) {
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

    public void addSongToPopularSongs(Song songToBeAdd) {
        int indexOfMin = 0;
        for (int i = 0; i < POPULARSONGSARRLENGTH; i++) {
            if (popularSongs[i] == null) {
                popularSongs[i] = songToBeAdd;
                return;
            }
            if (popularSongs[indexOfMin].getPlays() > popularSongs[i].getPlays()) {
                indexOfMin = i;
            }
        }
        if (songToBeAdd.getPlays() > popularSongs[indexOfMin].getPlays()) {
            popularSongs[indexOfMin] = songToBeAdd;
        }
        this.sortPopularSongs();
    }

    @Override
    public String toString() {
        String returnStr = this.getArtistName() + ", " + this.getTotalPlays()
                + "\nPopular Songs\n------------------------------------------------\n";
        for (int i = 0; i < POPULARSONGSARRLENGTH; i++) {
            if (popularSongs[i] != null) {
                returnStr += popularSongs[i].toString() + "\n";
            }
        }
        returnStr += "------------------------------------------------\n\nDiscography\n------------------------------------------------\n";
        for (int i = 0; i < this.getSongsReleases().size(); i++) {
            returnStr += "\n" + this.getSongsReleases().get(i).toString() + "\n";
        }
        return returnStr + "\n------------------------------------------------";
    }
}
