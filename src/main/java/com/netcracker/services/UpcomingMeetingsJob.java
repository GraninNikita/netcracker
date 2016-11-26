package com.netcracker.services;

import com.netcracker.controllers.MeetingsController;
import com.netcracker.entities.MeetingsEntity;
import org.joda.time.DateTime;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nick on 22.11.2016.
 */
public class UpcomingMeetingsJob implements Job {


    private final int accuracy = 30;
    private static List<MeetingsEntity> meetingsList;
    private static List<MeetingsEntity> upcomingMeetingsList;  // предстоящие события
    private DateTime nowTime;
    public final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.0");


    public static List<MeetingsEntity> getUpcomingMeetingsList() {
        return upcomingMeetingsList;
    }

    public static void setUpcomingMeetingsList(List<MeetingsEntity> upcomingMeetingsList) {
        UpcomingMeetingsJob.upcomingMeetingsList = upcomingMeetingsList;
    }

    /*
    * @param accuracy it's accuracy in minutes
    * */
    public boolean isTimeToNotificate(int accuracy) {
        setUpcomingMeetingsList(new ArrayList<MeetingsEntity>());

        nowTime = new DateTime(); // current time
        MeetingsController meetingsController = new MeetingsController();
        meetingsList = meetingsController.getAll();
        for (MeetingsEntity meeting : meetingsList) {
            DateTime meetingDateStart = new DateTime(meeting.getDateStart());
            if (meetingDateStart.getYear() == nowTime.getYear()
                    && meetingDateStart.getMonthOfYear() == nowTime.getMonthOfYear()
                    && meetingDateStart.getDayOfMonth() == nowTime.getDayOfMonth()
                    && meetingDateStart.getHourOfDay() == nowTime.getHourOfDay()
                    && (meetingDateStart.getMinuteOfHour() - nowTime.getMinuteOfHour() < this.accuracy)) {

                getUpcomingMeetingsList().add(meeting);
                System.out.println("It's time to notificate");
                System.out.println(meetingDateStart.toString("dd/MM/YYYY HH:mm:ss"));
            }

        }
        return false;
    }

    public void execute(JobExecutionContext context) throws JobExecutionException {
        if (isTimeToNotificate(30)) {
            System.out.println("It's time to NOTOFICATE");
        }

    }
}
