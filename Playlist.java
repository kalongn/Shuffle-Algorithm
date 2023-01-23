import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;

public class Playlist extends ArrayList<Song> {

    public void enqueue(Song song) {
        super.add(song);
    }

    public Song dequeue() {
        return super.remove(0);
    }

    public void addAllFromSongCollection(SongCollection collection) {
        for (Song song : collection.getSongs()) {
            super.add(song);
        }
    }

    public void swapSong(int index1, int index2) throws IndexOutOfBoundsException {
        if (index1 > this.size() - 1 || index2 > this.size() - 1) {
            throw new IndexOutOfBoundsException();
        }
        Song temp = this.get(index1);
        this.set(index1, this.get(index2));
        this.set(index2, temp);
    }

    private int randomInt(int min, int max) {
        return min + (int) (Math.random() * ((max - min) + 1));
    }

    public void absoluteShuffle() {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        for (int i = this.size() - 1; i > 0; i--) {
            int rndIndex = rnd.nextInt(i + 1);
            swapSong(rndIndex, i);
        }
    }

    public void songCollectionShuffle() {
        HashSet<SongCollection> allSongCollectionsHS = new HashSet<SongCollection>();
        for (int i = 0; i < this.size(); i++) {
            allSongCollectionsHS.add(this.get(i).getSongCollection());
        }
        ArrayList<SongCollection> allSongCollectionsAL = new ArrayList<>(allSongCollectionsHS);
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        for (int i = allSongCollectionsAL.size() - 1; i > 0; i--) {
            int rndIndex = rnd.nextInt(i + 1);
            SongCollection temp = allSongCollectionsAL.get(rndIndex);
            allSongCollectionsAL.set(rndIndex, allSongCollectionsAL.get(i));
            allSongCollectionsAL.set(i, temp);
        }
        removeAll(this);
        for (SongCollection indSongCollections : allSongCollectionsAL) {
            addAllFromSongCollection(indSongCollections);
        }
    }

    public void similaritySongShuffle() {

    }
}
