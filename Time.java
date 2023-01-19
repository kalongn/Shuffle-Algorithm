/**
 * This Time Class allowing us to create a Time object. With the maximum time
 * unit being hours and minimum time unit being seconds.
 * 
 * @author Ka_Long_Ngai 01/14/2023
 */
public class Time {

    private int hours, minutes, seconds;

    /**
     * Create a Time object with only seconds as input, will update time unit
     * accordingly
     * if seconds exceed 60s or minutes exceed 60 minutes.
     * 
     * 
     * @param seconds
     *                The input seconds for this new Time object, seconds could not
     *                be negative. If negative, an illegalArgumentException will be
     *                thrown.
     */
    public Time(int seconds) {
        setTime(0, 0, seconds);
        updateTimeMaxHours();
    }

    /**
     * Create a Time object with minutes and seconds as input, will update time unit
     * accordingly if seconds or minutes exceed 60 minutes.
     * 
     * @param minutes
     *                The input minutes for this new Time object, seconds could not
     *                be negative. If negative, an illegalArgumentException will be
     *                thrown.
     * @param seconds
     *                The input seconds for this new Time object, seconds could not
     *                be negative. If negative, an illegalArgumentException will be
     *                thrown.
     */
    public Time(int minutes, int seconds) {
        setTime(0, minutes, seconds);
        updateTimeMaxHours();
    }

    /**
     * Create a Time object with hours, minutes and seconds as input, will update
     * time unit accordingly if seconds or minutes exceed 60 minutes.
     * 
     * @param hours
     *                The input hours for this new Time object, seconds could not
     *                be negative. If negative, an illegalArgumentException will be
     *                thrown.
     * @param minutes
     *                The input minutes for this new Time object, seconds could not
     *                be negative. If negative, an illegalArgumentException will be
     *                thrown.
     * @param seconds
     *                The input seconds for this new Time object, seconds could not
     *                be negative. If negative, an illegalArgumentException will be
     *                thrown.
     */
    public Time(int hours, int minutes, int seconds) {
        setTime(hours, minutes, seconds);
        updateTimeMaxHours();
    }

    /**
     * @return the hours of the Time object.
     */
    public int getHours() {
        return hours;
    }

    /**
     * @param hours hours to set for the Time object.
     * @exception IllegalArgumentException Thrown if hours is set to a negative
     *                                     value.
     */
    public void setHours(int hours) throws IllegalArgumentException {
        if (isUnitOfTimeNegative(hours)) {
            throw new IllegalArgumentException("Hours cannot be negative");
        }
        this.hours = hours;
    }

    /**
     * @return the minutes of the Time object.
     */
    public int getMinutes() {
        return minutes;
    }

    /**
     * @param minutes minutes to set for the Time object.
     * @exception IllegalArgumentException Thrown if minutes is set
     *                                     to a negative
     *                                     value.
     */
    public void setMinutes(int minutes) throws IllegalArgumentException {
        if (isUnitOfTimeNegative(minutes)) {
            throw new IllegalArgumentException("Minutes cannot be negative");
        }
        this.minutes = minutes;
    }

    /**
     * @return the seconds of the Time object.
     */
    public int getSeconds() {
        return seconds;
    }

    /**
     * @param seconds seconds to set for the Time object.
     * @exception IllegalArgumentException Thrown if seconds is set to a negative
     *                                     value.
     */
    public void setSeconds(int seconds) {
        if (isUnitOfTimeNegative(seconds)) {
            throw new IllegalArgumentException("Seconds cannot be negative");
        }
        this.seconds = seconds;
    }

    /**
     * Set all 3 param of hours, minutes and seconds all at once instead of 3
     * seperate method call.
     * 
     * @param hours
     *                The input hours for this new Time object, seconds could not
     *                be negative. If negative, an illegalArgumentException will be
     *                thrown.
     * @param minutes
     *                The input minutes for this new Time object, seconds could not
     *                be negative. If negative, an illegalArgumentException will be
     *                thrown.
     * @param seconds
     *                The input seconds for this new Time object, seconds could not
     *                be negative. If negative, an illegalArgumentException will be
     *                thrown.
     */
    private void setTime(int hours, int minutes, int seconds) {
        setSeconds(seconds);
        setMinutes(minutes);
        setHours(hours);
    }

    /**
     * Determine if the input is negative or not.
     * 
     * @param unitOfTimes
     *                    An input of an int (could be any number)
     * @return True: the input number is negative, False: the input number is
     *         positive.
     */
    public boolean isUnitOfTimeNegative(int unitOfTimes) {
        return unitOfTimes < 0;
    }

    /**
     * 
     * @param smallerUnitOfTime
     *                          The parameter should only be minutes or seconds so
     *                          that the proper convertion can happen.
     * @return an int array composes of the larger unit of time and the smaller unit
     *         of time. Ex: {hours, minutes} or {minutes, seconds}.
     * 
     */
    public static int[] timeConvert(int smallerUnitOfTime) {
        int biggerUnitOfTime = smallerUnitOfTime / 60;
        smallerUnitOfTime = smallerUnitOfTime % 60;
        return new int[] { smallerUnitOfTime, biggerUnitOfTime };
    }

    /**
     * This method update the time for the entire Time object starting from seconds,
     * then minutes. Adjusting param to the right unit and amount without exceed
     * seconds of 60 seconds and minutes of 60 minutes.
     */
    private void updateTimeMaxHours() {
        int resultTimeConvertArray[] = timeConvert(seconds);
        seconds = resultTimeConvertArray[0];
        minutes += resultTimeConvertArray[1];
        resultTimeConvertArray = timeConvert(minutes);
        minutes = resultTimeConvertArray[0];
        hours += resultTimeConvertArray[1];
    }

    /**
     * This method return a new Time by adding the two param Time objects.
     * 
     * @param timeOne
     *                Time object one to be added.
     * @param timeTwo
     *                Time object two to be added.
     * @return the sum of the 2 Time object, has been adjusted to the correct unit
     *         of Time.
     */
    public static Time timeAddition(Time timeOne, Time timeTwo) {
        Time sum = new Time(timeOne.getHours() + timeTwo.getHours(), timeOne.getMinutes() + timeTwo.getMinutes(),
                timeOne.getSeconds() + timeTwo.getSeconds());
        sum.updateTimeMaxHours();
        return sum;
    }

    /**
     * @return the Time object in a single line displaying hours, minutes and
     *         seconds. If any time unit is 0, that specific time unit will not be
     *         displayed.
     */
    @Override
    public String toString() {
        String returnString = "";
        if (seconds != 0) {
            returnString += seconds + " sec";
        }
        if (minutes != 0) {
            returnString = minutes + " min " + returnString;
        }
        if (hours != 0) {
            returnString = hours + " hr " + returnString;
        }
        return returnString;
    }

    /**
     * @return the time object ina single line short hand displayng hours, minutes
     *         and seconds. If hours is 0, it will not be displayed.
     */
    public String toShortHandString() {
        String returnString = minutes + ":" + seconds;
        if (hours != 0) {
            returnString = hours + ":" + returnString;
        }
        return returnString;
    }

}
