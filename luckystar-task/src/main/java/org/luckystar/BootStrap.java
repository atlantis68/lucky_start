package org.luckystar;

import org.luckystar.task.CacheTask;
import org.luckystar.task.ChickenInfoTask;
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
    	ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"spring-service.xml"});
    	CacheTask cacheTask = (CacheTask)context.getBean("cacheTask");
    	cacheTask.init();
    	int threadCount = 20;
    	WorkTimeTask[] workTimeTasks = new WorkTimeTask[threadCount];
    	for(int i = 0; i < threadCount; i++) {
    		workTimeTasks[i] = (WorkTimeTask)context.getBean("workTimeTask");
    		workTimeTasks[i].init(i, threadCount, 10);
    	}
    	ChickenInfoTask[] chickenInfoTasks = new ChickenInfoTask[threadCount];
    	for(int i = 0; i < threadCount; i++) {
    		chickenInfoTasks[i] = (ChickenInfoTask)context.getBean("chickenInfoTask");
    		chickenInfoTasks[i].init(i, threadCount, 136);
    	}
    }

}
