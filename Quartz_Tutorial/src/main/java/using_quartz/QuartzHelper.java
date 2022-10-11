package using_quartz;

// Imports Zum Instanziieren der notwendigen Quartz Objekte.

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.JobBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;
import static org.quartz.CronScheduleBuilder.*;
import static org.quartz.CalendarIntervalScheduleBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.DateBuilder.*;

public class QuartzHelper {
    private static QuartzHelper helper = null;

    // Should be only a singleton
    private QuartzHelper() {
    }

    /**
     * Creates returns a singleton instance for {@link QuartzHelper}
     *
     * @return Helper class for Quartz to control jobs
     */
    public static QuartzHelper getQuartzHelper() {
        if (helper == null) {
            helper = new QuartzHelper();
        }
        return helper;
    }

    /**
     * Used to build a trigger with a cron expression, identity and group
     *
     * @param cronExpression cron details for the trigger
     * @param identity name for the trigger
     * @param group group for the trigger
     * @return the created trigger
     */

    public Trigger buildTrigger(String cronExpression, String identity, String group) {
        return newTrigger()
                .withIdentity(identity, group)
                .withSchedule(cronSchedule(cronExpression))
                .build();
    }

    /**
     * Creates the job detail for the given job class
     *
     * @param jobClass the job class
     * @param identity the identity for the job detail
     * @param group the group for the job detail
     * @return the created job detail
     */
    public JobDetail buildJobDetail(Class<? extends org.quartz.Job> jobClass, String identity, String group) {
        return newJob(jobClass)
                .withIdentity(identity, group)
                .build();
    }

    /**
     * Creates the Standard Scheduler
     * @param job the job details
     * @param trigger the necessary job trigger
     * @return the created scheduler
     */
    public Scheduler startScheduler(JobDetail job, Trigger trigger) {
        Scheduler scheduler = null;
        try {
            scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.scheduleJob(job, trigger);
            scheduler.start();
        } catch(SchedulerException se){
            se.printStackTrace();
        }
        return scheduler;
    }

    /**
     * Stops the given scheduler
     * @param scheduler the given scheduler
     */
    public void stopScheduler(Scheduler scheduler) {
        try {
            scheduler.shutdown();
        } catch (SchedulerException se) {
            se.printStackTrace();
        }
    }


    /*
    1. Seconds
    2. Minutes
    3. Hours
    4. Day-of-Month
    5. Month
    6. Day-of-week
    7. Year (optional)

    Every Wednesday 12 pm
    "0 0 12 ? * WED"

    Every 5 Minutes
    "0 0/5 * * * ?"

    Every Wednesday and Friday at 10:30, 11:30, 12:30 and 13:30
    “0 30 10-13 ? * WED,FRI”

    Every half hour between 7 am and 10 am on 6th and 22th of every month
    Won´t fiter at 10 am but on 9:30 am
    “0 0/30 7-9 6,22 * ?”
     */

}
