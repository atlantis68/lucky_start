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
	    	int cacheTaskDelay = Integer.parseInt(properties.get("ls.cacheTask.delay").toString().trim());
	    	int cacheTaskPeriod = Integer.parseInt(properties.get("ls.cacheTask.period").toString().trim());
	    	int cacheTaskBoundaryValue = Integer.parseInt(properties.get("ls.cacheTask.boundaryValue").toString().trim());
	    	CacheTask cacheTask = (CacheTask)context.getBean("cacheTask");
	    	cacheTask.init(cacheTaskDelay, cacheTaskPeriod, cacheTaskBoundaryValue);
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
	    	int mailRandom = Integer.parseInt(properties.get("ls.mail.random").toString().trim());
	    	int mailFixed = Integer.parseInt(properties.get("ls.mail.fixed").toString().trim());
	    	int timeTaskDelay = Integer.parseInt(properties.get("ls.timeTask.delay").toString().trim());
	    	int timeTaskPeriod = Integer.parseInt(properties.get("ls.timeTask.period").toString().trim());
	    	TimeTask timeTask = (TimeTask)context.getBean("timeTask");
	    	timeTask.init(timeTaskDelay, timeTaskPeriod, mailRandom, mailFixed);
	    	int autoExchangeDelay = Integer.parseInt(properties.get("ls.autoExchange.delay").toString().trim());
	    	int autoExchangePeriod = Integer.parseInt(properties.get("ls.autoExchange.period").toString().trim());
	    	int autoExchangeMinNum = Integer.parseInt(properties.get("ls.autoExchange.minNum").toString().trim());
	    	int autoExchangePrice = Integer.parseInt(properties.get("ls.autoExchange.price").toString().trim());
	    	int autoExchangeCny = Integer.parseInt(properties.get("ls.autoExchange.cny").toString().trim());
	    	AutoExchangeTask atutoExchangeTask = (AutoExchangeTask)context.getBean("atutoExchangeTask");
	    	atutoExchangeTask.init(autoExchangeDelay, autoExchangePeriod, autoExchangeMinNum, autoExchangePrice,
	    			autoExchangeCny, mailRandom, mailFixed);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(-1);
		}
    }

}
