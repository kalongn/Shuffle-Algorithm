import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.ThreadLocalRandom;

public class Playlist extends ArrayList<Song> {

    public void enqueue(Song song) {
        super.add(song);
    }

    public Song dequeue() {
        return super.remove(0);
    }

    private int randomInt(int min, int max) {
        return min + (int) (Math.random() * ((max - min) + 1));
    }

    public void addAllFromSongCollection(SongCollection collection) {
        for (Song song : collection.getSongs()) {
            enqueue(song);
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

    /**
     * As the name suggested, shuffle everysong randomly.
     */
    public void absoluteShuffle() {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        for (int i = this.size() - 1; i > 0; i--) {
            int rndIndex = rnd.nextInt(i + 1);
            swapSong(rndIndex, i);
        }
    }

    /**
     * This method will collect all the songCollection from each song and ignore
     * duplicate., then shuffle the songCollection, then play everysong from those
     * songCollection even if some songs are not apart of the playlist prior.
     */
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

    public Playlist similaritySongShuffle(int beginIndex, Playlist playlist) {

        // base case.
        if (beginIndex + 4 > playlist.size() || beginIndex > playlist.size()) {
            return playlist;
        }

        // Select a random song
        int randomInt = randomInt(beginIndex, playlist.size() - 1);
        if (randomInt == beginIndex) {
            beginIndex++;
        }
        Song selected = playlist.get(randomInt);
        playlist.remove(playlist.get(randomInt));

        // calculate the similarity value from the randomly selected song to rest of the
        // unselected playlist.
        ArrayList<Double> cvValue = new ArrayList<>();
        for (int i = beginIndex; i < playlist.size(); i++) {
            cvValue.add(Song.compareSong(selected, playlist.get(i)));
        }

        // finding the top 3 song similar to the selected song and swapping them to the
        // beginning of the queue;
        for (int i = 0; i < 3; i++) {
            double max = cvValue.get(0);
            int index = 0;
            for (int j = 1; j < cvValue.size(); j++) {
                if (max < cvValue.get(j)) {
                    max = cvValue.get(j);
                    index = j;
                }
            }
            playlist.swapSong(beginIndex, index);
            cvValue.set(index, -0.1);
        }

        // add back the selected song.
        playlist.add(beginIndex, selected);

        // shuffle the 4 songs.
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        for (int i = beginIndex + 3; i > beginIndex; i--) {
            int rndIndex = rnd.nextInt(i + 1);
            swapSong(rndIndex, i);
        }

        // recursively calling this and increment where the next method start.
        return similaritySongShuffle(beginIndex + 4, playlist);
    }

    public String toString() {
        String returnStr = "This Playlist\n------------------------------------------------\n";
        for (Song song : this) {
            returnStr += song.toString() + "\n";
        }
        return returnStr += "------------------------------------------------";
    }
}
