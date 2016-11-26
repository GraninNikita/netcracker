package com.netcracker.sheduler;

import com.netcracker.services.*;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.annotation.Bean;

import java.util.Date;

import static org.quartz.DateBuilder.evenMinuteDate;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;
import static org.quartz.JobBuilder.*;

/**
 * Created by Nick on 22.11.2016.
 */
@Bean
public class MainSheduler {
    public static void main(String[] args) {

        try {

            SchedulerFactory sf = new StdSchedulerFactory();
            Scheduler sched = sf.getScheduler();

            sched.start();

            // define the job and tie it to our HelloJob class
            JobDetail job = newJob(UpcomingMeetingsJob.class)
                    .withIdentity("job1", "group1")
                    .build();
            // compute a time that is on the next round minute
            Date runTime = evenMinuteDate(new Date());

            // Trigger the job to run on the next round minute
            Trigger trigger = newTrigger()
                    .withIdentity("trigger1", "group1")
                    .startNow()
                    .withSchedule(simpleSchedule()
                            .withIntervalInMinutes(1)
                            .repeatForever())
                    .build();
            // Tell quartz to schedule the job using our trigger
            sched.scheduleJob(job, trigger);

//            Thread.sleep(60000);
//            sched.shutdown(false);


        } catch (SchedulerException se) {
            se.printStackTrace();
        }
//        catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }
}

