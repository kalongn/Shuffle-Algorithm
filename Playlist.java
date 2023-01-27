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

    private static int randomInt(int min, int max) {
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
     * This method shuffle entire playlist regardless of the statistic of the song.
     * It is the most random in terms of a computer can do.
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
     * duplicate., then shuffle the songCollection, then play every songs from those
     * songCollection even if some songs are not apart of the playlist prior. This
     * is a very niche shuffling algorithm for listeners who want to enjoy the
     * entire songCollection (Album, EP, Singles) as a whole randomly instead of song by song randomly.
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

    /**
     * @param beginIndex
     * @return
     */
    public void similaritySongShuffle(int beginIndex) {
        // base case.
        if (beginIndex + 4 > this.size() || beginIndex > this.size()) {
            return;
        }
        // Select a random song
        int randomInt = randomInt(beginIndex, this.size() - 1);
        if (randomInt == beginIndex) {
            beginIndex++;
        }
        Song selected = this.get(randomInt);
        this.remove(this.get(randomInt));
        // calculate the similarity value from the randomly selected song to rest of the
        // unselected playlist.
        ArrayList<Double> cvValue = new ArrayList<>();
        for (int i = beginIndex; i < this.size(); i++) {
            cvValue.add(Song.compareSong(selected, this.get(i)));
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
            this.swapSong(beginIndex, index);
            cvValue.set(index, -0.1);
        }
        // add back the selected song.
        this.add(beginIndex, selected);
        // shuffle the 4 songs.
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        for (int i = beginIndex + 3; i > beginIndex; i--) {
            int rndIndex = rnd.nextInt(i + 1);
            swapSong(rndIndex, i);
        }
        // recursively calling this and increment where the next method start.
        similaritySongShuffle(beginIndex + 4);
    }

    public String toString() {
        String returnStr = "This Playlist\n------------------------------------------------\n";
        for (Song song : this) {
            returnStr += song.toString() + "\n";
        }
        return returnStr += "------------------------------------------------";
    }
}
