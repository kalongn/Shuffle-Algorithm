package Java_Code_Alpha;

import java.util.Comparator;

/**
 * This SongComparator Class allowing us to compare Song objects.
 * 
 * @author Ka_Long_Ngai 02/26/2023
 */
public class SongComparator implements Comparator<Song> {

    /**
     * Compare 2 songs object, this is be base on their tempSimValue as we required
     * this for sorting.
     * 
     * @return
     *         0 indicated the tempSimValue is the same. 1 indicated the
     *         tempSimValue is greater on songOne, -1 indicated the tempSimValue is
     *         greater on songTwo.
     */
    @Override
    public int compare(Song songOne, Song songTwo) {
        if (songOne.getTempSimValue() == songTwo.getTempSimValue()) {
            return 0;
        } else if (songOne.getTempSimValue() > songTwo.getTempSimValue()) {
            return 1;
        }
        return -1;
    }
}