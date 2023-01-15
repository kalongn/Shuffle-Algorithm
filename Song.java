/**
 * This Song Class allowing us to create a Song object.
 * 
 * @author Ka_Long_Ngai 01/15/2023
 */
public class Song {
    private String name;
    private int bpm;
    private long plays;
    private Time songLength;
    private boolean isExplicit;
    private Album songAlbum;
    private ExtendedPlay songExtendedPlay;
    private Single songSingle;
    private String[] genres;

    public Song(String name, int bpm, long plays, Time songLength, boolean isExplicit, Album songAlbum,
            ExtendedPlay songExtendedPlay, Single songSingle, String[] genres) {
        this.name = name;
        this.bpm = bpm;
        this.plays = plays;
        this.songLength = songLength;
        this.isExplicit = isExplicit;
        this.songAlbum = songAlbum;
        this.songExtendedPlay = songExtendedPlay;
        this.songSingle = songSingle;
        this.genres = genres;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBpm() {
        return bpm;
    }

    public void setBpm(int bpm) throws IllegalArgumentException {
        if (bpm <= 0) {
            throw new IllegalArgumentException("BPM of the song cannot less then or equal to 0.");
        }
        this.bpm = bpm;
    }

    public long getPlays() {
        return plays;
    }

    public void setPlays(long plays) {
        if (plays < 0) {
            throw new IllegalArgumentException("Plays of the song cannot less then 0.");
        }
        this.plays = plays;
    }

    public Time getSongLength() {
        return songLength;
    }

    public void setSongLength(Time songLength) {
        this.songLength = songLength;
    }

    public boolean isExplicit() {
        return isExplicit;
    }

    public void setExplicit(boolean isExplicit) {
        this.isExplicit = isExplicit;
    }

    public Album getSongAlbum() {
        return songAlbum;
    }

    public void setSongAlbum(Album songAlbum) {
        this.songAlbum = songAlbum;
    }

    public ExtendedPlay getSongExtendedPlay() {
        return songExtendedPlay;
    }

    public void setSongExtendedPlay(ExtendedPlay songExtendedPlay) {
        this.songExtendedPlay = songExtendedPlay;
    }

    public Single getSongSingle() {
        return songSingle;
    }

    public void setSongSingle(Single songSingle) {
        this.songSingle = songSingle;
    }

    public String[] getGenres() {
        return genres;
    }

    public void setGenres(String[] genres) {
        this.genres = genres;
    }
}