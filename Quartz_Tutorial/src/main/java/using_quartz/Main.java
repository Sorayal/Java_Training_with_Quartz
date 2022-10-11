package using_quartz;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

// Imports Zum Instanziieren der notwendigen Quartz Objekte.
import static org.quartz.JobBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;
import static org.quartz.CronScheduleBuilder.*;
import static org.quartz.CalendarIntervalScheduleBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.DateBuilder.*;

/**
 * Stichwörter: Fluent Interface, Domain Specific Language DSL
 */

public class Main {
    public static void main(String[] args) {
        try{
            // Nach der Instanziierung kann der Scheduler / Job Planer gestartet, in Standby gehalten und gestoppt werden.
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

            scheduler.start();

            // legt die einzelnen Job Details fest
            JobDetail job = newJob(HelloJob.class)
                    .withIdentity("myJob", "group_test")
                    .build();

            // Creating Trigger
            Trigger trigger = newTrigger()
                    .withIdentity("myTrigger","group_test")
                            .startNow()
                                    .withSchedule(simpleSchedule()
                                            .withIntervalInSeconds(30)
                                            .repeatForever())
                    .build();
            scheduler.scheduleJob(job, trigger);
            //scheduler.shutdown();
        }catch(SchedulerException se){
            se.printStackTrace();
        }
    }
}
