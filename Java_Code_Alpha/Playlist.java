package Java_Code_Alpha;

import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * This Playlist Class allowing us to create a Playlist object. This class
 * extended ArrayList while having an additional field variable, aka the
 * playlist name.
 * 
 * @author Ka_Long_Ngai 02/06/2023
 */
public class Playlist extends ArrayList<Song> {

    private String playlistTitle;

    /**
     * Create a Playlist object, with an additional playlistTitle field variable.
     * 
     * @param playlistTitle
     *                      The name of this playlist, String.
     */
    public Playlist(String playlistTitle) {
        this.playlistTitle = playlistTitle;
    }

    /**
     * Generate a int value between this 2 param inclusively.
     * 
     * @param min
     *            The minimum value you want this function to have a chance
     *            generate.
     * @param max
     *            The maximum value you want this function to have a chance
     *            generate.
     * @return
     *         the random int value.s
     */
    private static int randomInt(int min, int max) {
        return min + (int) (Math.random() * ((max - min) + 1));
    }

    /**
     * read from a PlaylistID file and convert every songID from the playlist into a
     * Song object and added this new Playlist Object and return it.
     * 
     * @param playListID
     *                   the playlist ID of a spotify URL (32 characters).
     * @return
     *         A playlist Object with every valid Song ID which created Song objects
     *         added into this Playlist.
     * @exception IllegalArgumentException
     *                                     thrown when the playList ID is not valid
     *                                     or file is not found.
     */
    public static Playlist readFromPlaylistTxt(String playListID) throws IllegalArgumentException {
        File playListFile = new File("./Condensed_Datas/PlaylistDatas/" + playListID + ".txt");
        Scanner scanner;
        try {
            scanner = new Scanner(playListFile);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Invalid playlist ID.");
        }
        String playlistTitle = scanner.nextLine();
        Playlist returnPlaylist = new Playlist(playlistTitle);
        while (scanner.hasNextLine()) {
            Song song = Song.readFromSongTxt(scanner.nextLine());
            returnPlaylist.add(song);
        }
        scanner.close();
        playListFile.delete();
        return returnPlaylist;
    }

    /**
     * @return
     *         the title/name of this playlist.
     */
    public String getPlaylistTitle() {
        return this.playlistTitle;
    }

    /**
     * Swap 2 Song object within this Playlist given the indexes of the 2 respective
     * Song object.
     * 
     * @param index1
     *               Index one for one of the Song object.
     * @param index2
     *               Index two for the other Song object.
     * @throws IndexOutOfBoundsException
     *                                   If either index is not within the size of
     *                                   the Playlist object or negative value.s
     */
    public void swapSong(int index1, int index2) throws IndexOutOfBoundsException {
        if (index1 > this.size() - 1 || index2 > this.size() - 1 || index1 < 0 || index2 < 0) {
            throw new IndexOutOfBoundsException();
        }
        Song temp = this.get(index1);
        this.set(index1, this.get(index2));
        this.set(index2, temp);
    }

    /**
     * This method shuffle entire playlist regardless of the statistic of the song.
     * It is the most random in terms of a computer can do. This distribution over a
     * large of number of trials should return an even distribution of all songs.
     */
    public void trueShuffle() {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        for (int i = this.size() - 1; i > 0; i--) {
            int rndIndex = rnd.nextInt(i + 1);
            swapSong(rndIndex, i);
        }
    }

    /**
     * This method intended to imitate the shuffling algorithm idea from the Spotify
     * engineering R&D post in 2014, It essentially sort songs by artists. Spread
     * those songs out evenly and put it in the playlist. Probing is required when
     * the direct number given was not nesscarily an open spot.
     */
    public void spotifyBalanceShuffle() {
        int playListSize = this.size();
        /*
         * Seperate each Artist(The first artist in the artists String array[]) with
         * their respective songs inside the LinkedList.
         * This is assuming all artists name are unique, if they are not. Probably
         * should update to use artistID. but for the sake of simplicity, we implied
         * that all artists name are unique.
         */
        HashMap<String, LinkedList<Song>> songSortByArtist = new HashMap<>();
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i) == null) {
                continue;
            }
            String currName = this.get(i).getArtistsName()[0];
            LinkedList<Song> currArtistSongs = new LinkedList<>();
            currArtistSongs.add(this.get(i));
            for (int j = i + 1; j < this.size(); j++) {
                if (this.get(j) == null || (!this.get(j).getArtistsName()[0].equals(currName))) {
                    continue;
                }
                currArtistSongs.add(this.get(j));
                this.set(j, null);
            }
            songSortByArtist.put(currName, currArtistSongs);
            this.set(i, null);
        }

        /*
         * This is the part where the balance shuffling happened, we attempted to put
         * songs into the respective place with an offset. If the current position is
         * already accquried by a song, then a linear probing will occur to find a null
         * spot within the playlist. Either backward or going forward of the playlist.
         */

        ArrayList<String> artistsNames = new ArrayList<>(songSortByArtist.keySet());
        for (int i = 0; i < artistsNames.size(); i++) {
            LinkedList<Song> allSongsFromCurrArtists = songSortByArtist.get(artistsNames.get(i));
            int seperateSameArtists = playListSize / allSongsFromCurrArtists.size();
            for (int j = 0; j < allSongsFromCurrArtists.size(); j++) {
                int randomOffset = randomInt((0 + j * seperateSameArtists) % playListSize, playListSize - 1);
                int[] probSelection = new int[] { -1, 1 };
                int probeAmount = probSelection[randomInt(0, 1)];
                while (this.get(randomOffset) != null) {
                    randomOffset = (randomOffset + probeAmount) % 30;
                    if (randomOffset < 0) {
                        randomOffset = 29;
                    }
                }
                this.set(randomOffset, allSongsFromCurrArtists.get(j));
            }

        }
    }

    /**
     * @return
     *         the entire playlist in a list format String (does not include stats).
     */
    public String toShortHandString() {
        String returnString = "Playlist: " + this.getPlaylistTitle()
                + "\n-------------------------------------------------------------";
        for (Song i : this) {
            returnString += i.toShortHandString();
        }
        return returnString + "\n-------------------------------------------------------------\n";
    }

    /**
     * return the entire playlist in a list format String.
     * 
     * @see java.util.AbstractCollection#toString()
     * @return
     *         the entire playlist in a list format String (include stats).
     */
    @Override
    public String toString() {
        String returnString = "Playlist: " + this.getPlaylistTitle()
                + "\n-------------------------------------------------------------";
        for (Song i : this) {
            returnString += "\n" + i;
        }
        return returnString + "\n-------------------------------------------------------------\n";
    }

    /**
     * Create a deep copy of this Playlist Object.
     * 
     * @see java.util.ArrayList#clone()
     * @return
     *         A cloned this sPlaylist object.
     */
    @Override
    public Playlist clone() {
        Playlist clone = new Playlist(this.getPlaylistTitle());
        for (Song i : this) {
            clone.add(i.clone());
        }
        return clone;
    }

    /**
     * Determine whether the playlist have the same Song object in the same order
     * and each song same the same data.
     * 
     * @see java.util.AbstractList#equals(java.lang.Object)
     * @return
     *         True or false to indicate equivalence.
     */
    @Override
    public boolean equals(Object arg0) {
        if (!(arg0 instanceof Playlist)) {
            return false;
        }
        Playlist other = (Playlist) arg0;
        for (int i = 0; i < this.size(); i++) {
            if (!this.get(i).equals(other.get(i))) {
                return false;
            }
        }
        return true;
    }
}
