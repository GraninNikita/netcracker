package com.netcracker.services;

import com.netcracker.controllers.MeetingsController;
import com.netcracker.entities.MeetingsEntity;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Minutes;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by Nick on 22.11.2016.
 */
@Component()
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
            if (meeting.getState() == true && (Minutes.minutesBetween(nowTime.plusHours(3), meetingDateStart).getMinutes()
                    <= (meeting.getNotificationTime() + 2)) && (Minutes.minutesBetween(nowTime.plusHours(3), meetingDateStart).getMinutes()) >= 0) {
                getUpcomingMeetingsList().push(meeting);
                logger.error("Pushed meeting to stack: " + meeting.getMeetingId() + " " + meeting.getName());
                logger.error("Разница " + Minutes.minutesBetween(nowTime.plusHours(3), meetingDateStart).getMinutes());
                isHaveUpcomingMeetings = true;
            }
        }
        return isHaveUpcomingMeetings;
    }

    public void execute(JobExecutionContext context) throws JobExecutionException {
        if (isTimeToNotificate(30)) {
            logger.info("System has upcoming meetings: " + upcomingMeetingsList.size());
        }

    }
}

