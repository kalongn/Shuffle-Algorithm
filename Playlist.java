import java.util.ArrayList;
public class Playlist extends ArrayList<Song> {
    
    public void enqueue(Song song) {
        super.add(song);
    }

    public Song dequeue() {
        return super.remove(0);
    }

    public void song(int index1, int index2) throws IndexOutOfBoundsException{
        if(index1 > this.size()-1 || index2 > this.size() -1 ) {
            throw new IndexOutOfBoundsException();
        }
        Song temp = this.get(index1);
        this.set(index1, this.get(index2));
        this.set(index2, temp);
    }

    public void absoluteShuffle() {

    }

    public void songCollectionShuffle() {

    }

    public void similaritySongShuffle() {

    }
}
