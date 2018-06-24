package org.luckystar.task;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.luckystar.model.CacheInfo;
import org.luckystar.model.ChickenInfo;
import org.luckystar.service.DataBaseService;
import org.luckystar.service.HttpService;
import org.luckystar.util.ToolUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

@Component
@Scope("prototype") 
public class WorkTimeTask implements Runnable {

	private static final Logger logger = LoggerFactory.getLogger(WorkTimeTask.class);
	
	private String myName;
	
	private int seq;
	
	private int num;
	
	private int interval;
	
	private String key1;
	
	private String key2;
	
	private String key3;
	
	private SimpleDateFormat monthFormat;
	
	private SimpleDateFormat dateFormat;

	private SimpleDateFormat timeFormat;
	
	private Calendar calendar;
	
	private float rate;
	
	private int diff;
	
	private int threshold;
	
	@Autowired
	private DataBaseService dataBaseService;

	public void init(int seq, int num, int interval, int diff, float rate, int threshold) {
		this.seq = seq;
		this.num = num;
		this.interval = interval;
		this.diff = diff;
		this.rate = rate;
		this.threshold = threshold;
		myName = "worktime_task_" + seq;
		key1 = "明星级别:";
		key2 = "财富级别:";
		key3 = "'";
		monthFormat = new SimpleDateFormat("yyyyMM");
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		calendar = Calendar.getInstance();  
		new Thread(this).start();
	}
	
	@Override
	public void run() {
		while(true) {
			try {
				long lastTime = System.currentTimeMillis();
				ConcurrentHashMap<Long, ChickenInfo> chickens = CacheInfo.chickenInfoCache;
				if(chickens != null) {
					for(Entry<Long, ChickenInfo> entry : chickens.entrySet()) {
						ChickenInfo chickenInfo = entry.getValue();
						if(ToolUtils.isHitLocal(chickenInfo.getId())) {
							if(entry.getKey() % num == seq) {
								long startTime = 0;
								try {
									String type = CacheInfo.laborUnionCache.get(chickenInfo.getlId()).getType();
									switch(type) {
										case "FANXING":
											Request request = new Request.Builder()
										    .url("http://fanxing.kugou.com/index.php?action=user&id=" + entry.getKey())
										    .addHeader("Accept", "application/json, text/javascript, */*; q=0.01")
										    .addHeader("Accept-Language", "zh-CN,zh;q=0.8")
										    .addHeader("Cache-Control", "no-cache")
										    .addHeader("Connection", "keep-alive")
										    .addHeader("Host", "fanxing.kugou.com")
										    .addHeader("Pragma", "no-cache")
										    .addHeader("Referer", "http://fanxing.kugou.com/")
										    .addHeader("X-Requested-With", "XMLHttpRequest")
										    .build();
										Thread.sleep(new Random().nextInt(interval * diff));
										startTime = System.currentTimeMillis();
										Response response = HttpService.sendHttp(request);
										if(response != null && response.isSuccessful()) {
											String result = response.body().string();
											if(result.indexOf("参数错误") == -1) {
												boolean isOnline = false;
												String starName = "";
												String richName = "";
												if(result.indexOf("正在直播") > -1) {
													isOnline = true;
												} 
												int start = result.indexOf(key1);
												if(start > -1) {
													int end = result.indexOf(key3, start);
													if(end > start) {
														starName = result.substring(start + key1.length(), end);
													}
												}
												start = result.indexOf(key2);
												if(start > -1) {
													int end = result.indexOf(key3, start);
													if(end > start) {
														richName = result.substring(start + key2.length(), end);
													}
												}
												Date now = new Date(System.currentTimeMillis());
												String curDay = dateFormat.format(now);
												List<Map<String, Object>> count = dataBaseService.checkWorkInfo(entry.getKey(), curDay);
//												logger.info("request work time succeeded, response code is {}, isOnline : {}, current time : {}", 
//														response.code(), isOnline, timeFormat.format(now));
												if(count != null && count.size() > 0) {
													Map<String, Object> workInfo = count.get(0);
													if(isOnline) {
														long last = timeFormat.parse(workInfo.get("last_time").toString()).getTime();
														int curWorkTime = workInfo.get("work_time") != null ? Integer.parseInt(workInfo.get("work_time").toString()) : 0;
														long diff = (long)((now.getTime() - last) * rate / 1000);
														if(diff >= interval * threshold || diff < 0) {
															logger.warn("labor union = {}, id = {}, starId = {}, diff = {} will be reset to 0", chickenInfo.getlId(), chickenInfo.getId(), 
																	entry.getKey(), diff);
															diff = 0;
														}
														workInfo.put("work_time", curWorkTime + diff);
														workInfo.put("online_status", 1);
														logger.info("labor union = {}, id = {}, starId = {}, now = {}, last = {}, cost = {}, diff = {}", chickenInfo.getlId(), 
																chickenInfo.getId(), entry.getKey(), timeFormat.format(new Date(now.getTime())), timeFormat.format(new Date(last)), 
																System.currentTimeMillis() - startTime, diff);
													} else {
														workInfo.put("online_status", 0);
													}
													workInfo.put("star_name", starName);
													workInfo.put("rich_name", richName);
													workInfo.put("last_time", timeFormat.format(now));
													dataBaseService.updateWorkInfo1(workInfo);
												} else {
													List<Map<String, Object>> taskInfo = dataBaseService.doGetTaskInfo(chickenInfo.getId(), chickenInfo.getlId(), monthFormat.format(now));
													if(taskInfo != null && taskInfo.size() > 0) {
														Map<String, Object> workInfo = new HashMap<String, Object>();
														Date weeHours = timeFormat.parse(curDay + " 00:00:00");
														workInfo.put("star_id", entry.getKey());
														workInfo.put("l_id", chickenInfo.getlId());
														if(isOnline) {
															long diff = (long)((now.getTime() - weeHours.getTime()) * rate / 1000);
															if(diff >= interval * threshold || diff < 0) {
																logger.warn("today labor union = {}, id = {}, starId = {}, diff = {} will be reset to {}", chickenInfo.getlId(), chickenInfo.getId(), 
																		entry.getKey(), diff, interval);
																diff = interval;
															}
															workInfo.put("work_time", diff);
															workInfo.put("online_status", 1);
														} else {
															workInfo.put("work_time", 0);
															workInfo.put("online_status", 0);
														}
														workInfo.put("cur_month", monthFormat.format(now));
														workInfo.put("cur_day", curDay);
														workInfo.put("last_time", timeFormat.format(now));
														workInfo.put("task_info_id", taskInfo.get(0).get("id"));
														dataBaseService.insertWorkInfo1(workInfo);
														calendar.setTime(now);
														calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 1);
														curDay = dateFormat.format(calendar.getTime());
														count = dataBaseService.checkWorkInfo(entry.getKey(), curDay);
														if(count != null && count.size() > 0) {
															workInfo = count.get(0);
															if(isOnline) {
																long last = timeFormat.parse(workInfo.get("last_time").toString()).getTime();
																int curWorkTime = workInfo.get("work_time") != null ? Integer.parseInt(workInfo.get("work_time").toString()) : 0;
																long diff = (long)((weeHours.getTime() - last) * rate / 1000);
																if(diff >= interval * threshold || diff < 0) {
																	logger.warn("yesterday labor union = {}, id = {}, starId = {}, diff = {} will be reset to 0", chickenInfo.getlId(), chickenInfo.getId(), 
																			entry.getKey(), diff);
																	diff = 0;
																}
																workInfo.put("work_time", curWorkTime + diff);	
																workInfo.put("online_status", 1);
															} else {
																workInfo.put("online_status", 0);
															}
															workInfo.put("last_time", timeFormat.format(weeHours));
															dataBaseService.updateWorkInfo1(workInfo);
														}
													}
												}
											} else {
//												logger.info("star_id {} is error : {}", entry.getKey(), result);
											}
										} else {
											logger.info("request work time failed, response code is {}", response.code());
										}
										break;
									default:
										break;
									}
								} catch(Exception e) {
									logger.error("{}, cost = {} : ", entry.getKey(), System.currentTimeMillis() - startTime, e);
								}
							}
						}
					}
				}
				long diffTime = lastTime + interval * 1000 - System.currentTimeMillis();
				if(diffTime > 0) {
					Thread.sleep(diffTime);
				}
			} catch(Exception e) {
				logger.error("{} : ", myName, e);
			} finally {
//				logger.info("{}获取考勤信息完成", myName);
			}
		}
	}
}
