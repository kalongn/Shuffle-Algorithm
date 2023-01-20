public class Test {
    public static void main(String[] args) {
        System.out.println("Time Class Testing.");
        Time secondsTestOne = new Time(30);
        Time secondsTestTwo = new Time(119);
        Time secondsTestThree = new Time(3661);
        Time overAllTestOne = new Time(1000, 93);
        Time overAllTestTwo = new Time(199, 129102, 23);
        Time overAllTestThree = new Time(1, 120, 33);
        System.out.println(secondsTestOne + "\n" + secondsTestTwo + "\n" + secondsTestThree);
        System.out.println(overAllTestOne + " \n" + overAllTestTwo + "\n" + overAllTestThree);
        System.out.println(secondsTestOne.toShortHandString() + "\n" + secondsTestTwo.toShortHandString() + "\n"
                + secondsTestThree.toShortHandString());
        System.out.println(overAllTestOne.toShortHandString() + " \n" + overAllTestTwo.toShortHandString() + "\n"
                + overAllTestThree.toShortHandString());
        try {
            Time secondsExceptionTest = new Time(-1);
            System.out.println(secondsExceptionTest);
        } catch (IllegalArgumentException ex) {
            System.out.println("Seconds Exception Passed");
        }
        try {
            Time minutesExceptionTest = new Time(-1, 0);
            System.out.println(minutesExceptionTest);
        } catch (IllegalArgumentException ex) {
            System.out.println("minutes Exception Passed");
        }
        try {
            Time hoursExceptionTest = new Time(-1, 0);
            System.out.println(hoursExceptionTest);
        } catch (IllegalArgumentException ex) {
            System.out.println("hours Exception Passed");
        }
        System.out.println(Time.timeAddition(overAllTestOne, overAllTestTwo));
        System.out.println("\nSong, Artist and Song Collection Testing.");

        Artist artist1 = new Artist("fusq");
        Artist artist2 = new Artist("MYLK");
        Song song1 = new Song("When I", 130, new Artist[] { artist1 }, 421964, new Time(1, 50), false,
                new String[] { "J-pop", "Melo" });
        Song song2 = new Song("Inner", 132, new Artist[] { artist1, artist2 }, 1212751, new Time(5, 26), false,
                new String[] { "J-pop", "Melo" });
        Song song3 = new Song("Interlude", 158, new Artist[] { artist1 }, 176744, new Time(53), false,
                new String[] { "J-pop", "Melo" });
        Song song4 = new Song("Gateway", 123, new Artist[] { artist1 }, 1481497, new Time(3, 5), false,
                new String[] { "J-pop", "Melo" });
        Song song5 = new Song("Blush", 127, new Artist[] { artist1, artist2 }, 879622, new Time(2, 43), false,
                new String[] { "J-pop", "Melo" });
        Song song6 = new Song("Then", 141, new Artist[] { artist1 }, 425813, new Time(1, 1), false,
                new String[] { "J-pop", "Melo" });
        Song[] songs1_6 = new Song[] { song1, song2, song3, song4, song5, song6 };
        SongCollection testEP = new SongCollection("Polarity", songs1_6, artist1, 2017, 1);
        for (int i = 0; i < songs1_6.length; i++) {
            System.out.println(songs1_6[i]);
        }
        System.out.println("\n" + testEP);
        System.out.println("\n" + artist1);
    }
}