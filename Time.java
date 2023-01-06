public class Time {
    private int hours, minutes, seconds;

    public Time(int seconds) {
        setTime(0,0,seconds);
        updateTimeMaxHours();
    }

    public Time(int minutes, int seconds) {
        setTime(0,minutes,seconds);
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
        if(isUnitOfTimeNegative(hours)) {
            throw new IllegalArgumentException("Hours cannot be negative");
        }
        this.hours = hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        if(isUnitOfTimeNegative(minutes)) {
            throw new IllegalArgumentException("Minutes cannot be negative");
        }
        this.minutes = minutes;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        if(isUnitOfTimeNegative(seconds)) {
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
        return new int[] {smallerUnitOfTime, biggerUnitOfTime};
    }

    private void updateTimeMaxHours() {
        if(seconds < 60) {
            return;
        }
        int minutesSecondsArr[] = timeConvert(seconds);
        seconds = minutesSecondsArr[0];
        if(minutesSecondsArr[1] < 60) {
            minutes+=minutesSecondsArr[1];
            return; 
        }
        int hoursMinutesArr[] = timeConvert(minutesSecondsArr[1]);
        minutes = hoursMinutesArr[0];
        hours+=hoursMinutesArr[1];
    }

    public String toString() {
        return "" + hours + " hours " + minutes + " minutes " + seconds + " seconds.";
    }
    

    //TestCode
    public static void main(String[] args) {
        Time secondsTestOne = new Time(30);
        Time secondsTestTwo = new Time(119);
        Time secondsTestThree = new Time(3661);
        System.out.println(secondsTestOne + "\n" + secondsTestTwo + "\n" + secondsTestThree);
        try {
            Time secondsTestFour = new Time(-1);
            System.out.println(secondsTestFour);
        } catch (IllegalArgumentException ex) {
            System.out.println("Seconds Exception Passed");
        }
    }
}  
