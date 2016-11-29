package com.netcracker.services;

import com.netcracker.controllers.MeetingsController;
import com.netcracker.entities.MeetingsEntity;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import javax.mail.MessagingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by Nick on 22.11.2016.
 */
public class UpcomingMeetingsJob implements Job {


    final static Logger logger = Logger.getLogger(UpcomingMeetingsJob.class);

    private final int accuracy = 30;
    private static List<MeetingsEntity> meetingsList;
    private static Stack<MeetingsEntity> upcomingMeetingsList = new Stack<>();  // предстоящие события
    public final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.0");

    public static Stack<MeetingsEntity> getUpcomingMeetingsList() {
        return upcomingMeetingsList;
    }

    public static void setUpcomingMeetingsList(Stack<MeetingsEntity> upcomingMeetingsList) {
        UpcomingMeetingsJob.upcomingMeetingsList = upcomingMeetingsList;
    }

    /*
    * @param accuracy it's accuracy in minutes
    * */
    public boolean isTimeToNotificate(int accuracy) {
        boolean isHaveUpcomingMeetings = false;
        upcomingMeetingsList = new Stack<>();
        meetingsList = MeetingsController.getAll();

        for (MeetingsEntity meeting : meetingsList) {
            DateTime nowTime = new DateTime(); // current time
            DateTime meetingDateStart = new DateTime(meeting.getDateStart());

            if (meeting.getState() && meetingDateStart.getYear() == nowTime.getYear()
                    && meetingDateStart.getMonthOfYear() == nowTime.getMonthOfYear()
                    && meetingDateStart.getDayOfMonth() == nowTime.getDayOfMonth()
                    && meetingDateStart.getHourOfDay() == nowTime.getHourOfDay()
                    && (meetingDateStart.getMinuteOfHour() - nowTime.getMinuteOfHour() < this.accuracy)) {

                getUpcomingMeetingsList().push(meeting);
                logger.error("Pushed meeting to stack: " + meeting.getMeetingId() + " " + meeting.getName());
                isHaveUpcomingMeetings = true;
            }

        }
        return isHaveUpcomingMeetings;
    }

    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.warn("EXECUTE");
        if (isTimeToNotificate(30)) {
            logger.info("System has upcoming meetings: " + upcomingMeetingsList.size());
        }

    }
}

