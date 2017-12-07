package org.luckystar;

import java.util.Properties;

import org.luckystar.task.AutoExchangeTask;
import org.luckystar.task.CacheTask;
import org.luckystar.task.ChickenInfoTask;
import org.luckystar.task.TimeTask;
import org.luckystar.task.WorkTimeTask;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 
 * @date 2017年8月9日
 * @author duheng
 *
 */
public class BootStrap {

	
    public static void main( String[] args ) {
    	try {
    		Properties properties = new Properties();
			properties.load(BootStrap.class.getResourceAsStream("/sys.properties"));
	    	ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"spring-service.xml"});
	    	CacheTask cacheTask = (CacheTask)context.getBean("cacheTask");
	    	cacheTask.init();
	    	Thread.sleep(5 * 1000);
	    	int workTimeCount = Integer.parseInt(properties.get("ls.workTime.threadCount").toString().trim());
	    	int workTimeInterval = Integer.parseInt(properties.get("ls.workTime.interval").toString().trim());
	    	int workTimeDiff = Integer.parseInt(properties.get("ls.workTime.diff").toString().trim());
	    	Float workTimeRate = Float.parseFloat(properties.get("ls.workTime.rate").toString().trim());
	    	WorkTimeTask[] workTimeTasks = new WorkTimeTask[workTimeCount];
	    	for(int i = 0; i < workTimeCount; i++) {
	    		workTimeTasks[i] = (WorkTimeTask)context.getBean("workTimeTask");
	    		workTimeTasks[i].init(i, workTimeCount, workTimeInterval, workTimeDiff, workTimeRate);
	    	}
	    	int userInfoCount = Integer.parseInt(properties.get("ls.userInfo.threadCount").toString().trim());
	    	int userInfoInterval = Integer.parseInt(properties.get("ls.userInfo.interval").toString().trim());
	    	int userInfoDiff = Integer.parseInt(properties.get("ls.userInfo.diff").toString().trim());
	    	ChickenInfoTask[] chickenInfoTasks = new ChickenInfoTask[userInfoCount];
	    	for(int i = 0; i < userInfoCount; i++) {
	    		chickenInfoTasks[i] = (ChickenInfoTask)context.getBean("chickenInfoTask");
	    		chickenInfoTasks[i].init(i, userInfoCount, userInfoInterval, userInfoDiff);
	    	}
	    	TimeTask timeTask = new TimeTask();
	    	timeTask.init();
	    	AutoExchangeTask atutoExchangeTask = new AutoExchangeTask();
	    	atutoExchangeTask.init();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(-1);
		}
    }

}
