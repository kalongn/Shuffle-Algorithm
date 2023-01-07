public class Time {
    private int hours, minutes, seconds;

    public Time(int seconds) {
        setTime(0, 0, seconds);
        updateTimeMaxHours();
    }

    public Time(int minutes, int seconds) {
        setTime(0, minutes, seconds);
        updateTimeMaxHours();
    }

    public Time(int hours, int minutes, int seconds) {
        setTime(hours, minutes, seconds);
        updateTimeMaxHours();
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        if (isUnitOfTimeNegative(hours)) {
            throw new IllegalArgumentException("Hours cannot be negative");
        }
        this.hours = hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        if (isUnitOfTimeNegative(minutes)) {
            throw new IllegalArgumentException("Minutes cannot be negative");
        }
        this.minutes = minutes;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        if (isUnitOfTimeNegative(seconds)) {
            throw new IllegalArgumentException("Seconds cannot be negative");
        }
        this.seconds = seconds;
    }

    private void setTime(int hours, int minutes, int seconds) {
        setSeconds(seconds);
        setMinutes(minutes);
        setHours(hours);
    }

    public boolean isUnitOfTimeNegative(int unitOfTimes) {
        return unitOfTimes < 0;
    }

    public static int[] timeConvert(int smallerUnitOfTime) {
        int biggerUnitOfTime = smallerUnitOfTime / 60;
        smallerUnitOfTime = smallerUnitOfTime % 60;
        return new int[] { smallerUnitOfTime, biggerUnitOfTime };
    }

    private void updateTimeMaxHours() {
        int resultTimeConvertArray[] = timeConvert(seconds);
        seconds = resultTimeConvertArray[0];
        minutes += resultTimeConvertArray[1];
        resultTimeConvertArray = timeConvert(minutes);
        minutes = resultTimeConvertArray[0];
        hours += resultTimeConvertArray[1];
    }

    public String toString() {
        return "" + hours + " hours " + minutes + " minutes " + seconds + " seconds.";
    }

    public String toShortHandString() {
        return "" + hours + ":" + minutes + ":" + seconds;
    }

    // TestCode
    public static void main(String[] args) {
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
    }
}
