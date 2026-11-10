package gui;
/**
 * One row of the TableView in AdvancedControlsDemo.
 */
public class Song {
    private String title;
    private String artist;
    private String released;
    private String movie;

    public Song(String title, String artist, String released, String movie) {
        this.title = title;
        this.artist = artist;
        this.released = released;
        this.movie = movie;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getReleased() {
        return released;
    }

    public String getMovie() {
        return movie;
    }
}
