package quartztest;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

public class Main {
    public static void main(String[] args) {
        try{
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

            scheduler.start();
            scheduler.shutdown();
        }catch(SchedulerException se){
            se.printStackTrace();
        }
    }
}
