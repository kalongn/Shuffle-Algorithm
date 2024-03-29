package Java_Code_Alpha;

import java.util.ArrayList;
import java.util.Collections;
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

    private String playlistTitle, playlistID;

    /**
     * Create a Playlist object, with additional variables, playlistID,
     * playlistTitle field variable.
     * 
     * @param playlistID
     *                      The playlist ID of the playlist (spotify ID for the
     *                      playlist.)
     * 
     * @param playlistTitle
     *                      The name of this playlist, String.
     */
    public Playlist(String playlistID, String playlistTitle) {
        this.playlistID = playlistID;
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
        Playlist returnPlaylist = new Playlist(playListID, playlistTitle);
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
     * @return
     *         the playlist ID of this playlist object.
     */
    public String getPlaylistID() {
        return this.playlistID;
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
     * This method true shuffle part of the playlist base on the given index.
     * 
     * @param beginIndex
     * @param endIndex
     */
    public void trueShuffle(int beginIndex, int endIndex) {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        for (int i = endIndex - 1; i > beginIndex; i--) {
            int rndIndex = rnd.nextInt(i + 1);
            swapSong(rndIndex, i);
        }
    }

    /**
     * Seperate each Artist(The first artist in the artists String array[]) with
     * their respective songs inside the LinkedList.
     * This is assuming all artists name are unique, if they are not. Probably
     * should update to use artistID. but for the sake of simplicity, we implied
     * that all artists name are unique. This method will make the current playlist
     * aka this reference filled with all null references.
     * 
     * @return
     *         A hashmap of artists and their respective songs all in a linkedList.
     */
    private HashMap<String, LinkedList<Song>> seperateSongByArtists() {
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
        return songSortByArtist;
    }

    /**
     * This method intended to imitate the shuffling algorithm idea from the Spotify
     * engineering R&D post in 2014, It essentially sort songs by artists. Spread
     * those songs out evenly and put it in the playlist. This method has 2 version,
     * verison 1 uses linear probing (select forward or backward as its director for
     * linear probing randomly) which is commented out. Version 2 added song base on
     * the 25% range of the length of the playlist of the randomly generate offset
     * index. Randomly select a number with those range and append that song into
     * that position. Finally remove all dummy null references.
     */
    public void spotifyBalanceShuffle() {
        int playListSize = this.size();
        HashMap<String, LinkedList<Song>> songSortByArtist = seperateSongByArtists();

        /*
         * This is the part where the balance shuffling happened, we attempted to put
         * songs into the respective place with an offset.
         */

        ArrayList<String> artistsNames = new ArrayList<>(songSortByArtist.keySet());
        for (int i = 0; i < artistsNames.size(); i++) {
            LinkedList<Song> allSongsFromCurrArtists = songSortByArtist.get(artistsNames.get(i));
            // trying to divide artists song with equal spacing.
            int seperateSameArtists = playListSize / allSongsFromCurrArtists.size();
            for (int j = 0; j < allSongsFromCurrArtists.size(); j++) {
                int randomOffset = randomInt((j * seperateSameArtists) % playListSize, playListSize - 1);
                // Version 1 code (linear probing in a way.)
                /*
                 * int[] probSelection = new int[] { -1, 1 };
                 * int probeAmount = probSelection[randomInt(0 , 1)];
                 * while (this.get(randomOffset) != null) {
                 * randomOffset = (randomOffset + probeAmount) % playListSize;
                 * if (randomOffset < 0) {
                 * randomOffset = playListSize - 1;
                 * }
                 * }
                 * this.set(randomOffset, allSongsFromCurrArtists.get(j));
                 */

                // Version 2 code (randomly adding the song into a 25% range of a randomOffset)
                if (this.get(randomOffset) != null) {
                    int probAmount = randomInt(-1 * playListSize * 25 / 100, playListSize * 25 / 100);
                    int newIndex = Math.max(randomOffset + probAmount, 0) % playListSize;
                    if (this.get(Math.max(randomOffset + probAmount, 0) % playListSize) == null) {
                        this.set(newIndex, allSongsFromCurrArtists.get(j));
                    } else {
                        this.add(newIndex, allSongsFromCurrArtists.get(j));
                    }
                } else {
                    this.set(randomOffset, allSongsFromCurrArtists.get(j));
                }
            }
        }
        // part of Version 2 (remove all the dummy null reference.)
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i) == null) {
                this.remove(i);
                i--;
            }
        }
    }

    /**
     * This method shuffle base on statistics of a song. It shuffle the playlist by
     * true shuffle first. Then "pop" the head of the list off. Then calculate all
     * the similarity values from this song and the entire playlist. Sort the
     * playlist. truely shuffle the lower bound (number lower then the "median") and
     * the upper bound (number higher then the "median"). Then add the upper bound
     * song interchange into the lower bound so it form a lower -> higher -> lower
     * -> higher pattern idealy. Finally append the randomly select head song in at
     * position 0.
     */
    public void statBaseShuffle() {
        this.trueShuffle();
        Song randomSelect = this.remove(0);
        for (Song i : this) {
            randomSelect.weightedSimilarities(i);
        }
        Collections.sort(this);
        this.trueShuffle(0, (this.size() / 2 + 1));
        this.trueShuffle(this.size() / 2 + 1, this.size());
        for (int i = 0; i < this.size(); i += 2) {
            this.add(i, this.remove(this.size() - 1));
        }
        this.add(0, randomSelect);
    }

    /**
     * Shuffle the current playlist using one of 3 option.
     * 
     * @param shuffleOption
     *                      An integer between 1-3, 1: true shuffle, 2: spotify
     *                      balance shuffle, 3: stat-based shuffle.
     * @throws IllegalArgumentException
     *                                  Thrown if the input is not within the 1-3
     *                                  range.
     */
    public void shufflePlaylist(int shuffleOption) throws IllegalArgumentException {
        switch (shuffleOption) {
            case 1:
                this.trueShuffle();
                break;
            case 2:
                this.spotifyBalanceShuffle();
                break;
            case 3:
                this.statBaseShuffle();
                break;
            default:
                throw new IllegalArgumentException(
                        "The input is not a shuffle option. Please input an integer between 1-3.");
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
        Playlist clone = new Playlist(this.getPlaylistID(), this.getPlaylistTitle());
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
        if(this == arg0) {
            return true;
        }
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
