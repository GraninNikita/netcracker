package com.netcracker.sheduler;

import com.netcracker.services.EmailService;
import com.netcracker.services.UpcomingMeetingsJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Created by Nick on 22.11.2016.
 */

public class MainSheduler  {
    public static void main(String[] args) {

        try {

            SchedulerFactory sf = new StdSchedulerFactory();
            Scheduler sched = sf.getScheduler();
            sched.start();

            // define job to add upcoming meetings
            JobDetail job1 = newJob(UpcomingMeetingsJob.class)
                    .withIdentity("upcoming", "group1")
                    .build();

            // job for notificate
            JobDetail job2 = newJob(EmailService.class)
                    .withIdentity("notificate", "group2")
                    .build();

            Trigger trigger1 = newTrigger()
                    .withIdentity("trigger1", "group1")
                    .startNow()
                    .withSchedule(simpleSchedule()
                            .withIntervalInMinutes(1)
                            .repeatForever())
                    .build();

            Trigger trigger2 = newTrigger()
                    .withIdentity("trigger2", "group2")
                    .startNow()
                    .withSchedule(simpleSchedule()
                            .withIntervalInSeconds(10)
                            .repeatForever())
                    .build();


            // Tell quartz to schedule the job using our trigger
            sched.scheduleJob(job1, trigger1);
            sched.scheduleJob(job2, trigger2);


        } catch (SchedulerException se) {
            se.printStackTrace();
        }
    }


}

