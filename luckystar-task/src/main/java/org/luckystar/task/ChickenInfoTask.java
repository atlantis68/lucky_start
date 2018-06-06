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

import org.apache.commons.lang.StringUtils;
import org.luckystar.model.CacheInfo;
import org.luckystar.model.ChickenInfo;
import org.luckystar.service.DataBaseService;
import org.luckystar.service.HttpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

@Component
@Scope("prototype") 
public class ChickenInfoTask implements Runnable {

	private static final Logger logger = LoggerFactory.getLogger(ChickenInfoTask.class);
	
	private String myName;
	
	private int seq;
	
	private int num;
	
	private int interval;
	
	private SimpleDateFormat monthFormat;
	
	private SimpleDateFormat dateFormat;

	private SimpleDateFormat timeFormat;
	
	private Calendar calendar;
	
	private int diff;
	
	@Autowired
	private DataBaseService dataBaseService;

	public void init(int seq, int num, int interval, int diff) {
		this.seq = seq;
		this.num = num;
		this.interval = interval;
		this.diff = diff;
		myName = "chickeninfo_task_" + seq;
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
						if(CacheInfo.totalNumber == 1 || 
								(CacheInfo.totalNumber == 2 && chickenInfo.getId() % CacheInfo.totalNumber == CacheInfo.modNumber)) {
							if(entry.getKey() % num == seq) {
								try {
									if(StringUtils.isNotEmpty(chickenInfo.getCookie())) {
										Request request = new Request.Builder()
										    .url("http://fanxing.kugou.com/UServices/UserService/UserService/getMyUserDataInfo?args=[]&_=" + lastTime)
										    .addHeader("Accept", "application/json, text/javascript, */*; q=0.01")
										    .addHeader("Accept-Language", "zh-CN,zh;q=0.8")
										    .addHeader("Cache-Control", "no-cache")
										    .addHeader("Connection", "keep-alive")
										    .addHeader("Host", "fanxing.kugou.com")
										    .addHeader("Pragma", "no-cache")
										    .addHeader("Referer", "http://fanxing.kugou.com/")
										    .addHeader("X-Requested-With", "XMLHttpRequest")
						    			    .addHeader("Cookie", chickenInfo.getCookie())
										    .build();
										Thread.sleep(new Random().nextInt(interval * diff));
										Response response = HttpService.sendHttp(request);
										if(response != null && response.isSuccessful()) {
											JSONObject result = JSON.parseObject(response.body().string());
											String errorno = result.get("errorno").toString();
											if(errorno.equals("0")) {
												JSONObject fxUserInfo = JSON.parseObject(result.get("data").toString());
												JSONObject userInfo = JSON.parseObject(fxUserInfo.get("fxUserInfo").toString());
												Date now = new Date(System.currentTimeMillis());
												String curDay = dateFormat.format(now);
												List<Map<String, Object>> count = dataBaseService.checkWorkInfo(entry.getKey(), curDay);
//												logger.info("request chicken info succeeded, response code is {}, body : {}, current time : {}", 
//														response.code(), userInfo, timeFormat.format(now));
												if(count != null && count.size() > 0) {
													Map<String, Object> workInfo = count.get(0);
													workInfo.put("star_level", userInfo.get("starLevel") != null ? userInfo.get("starLevel") : 0);
													workInfo.put("rich_level", userInfo.get("richLevel") != null ? userInfo.get("richLevel") : 0);
													workInfo.put("bean_total", userInfo.get("beanTotal") != null ? userInfo.get("beanTotal") : 0);
													workInfo.put("coin", userInfo.get("coin") != null ? userInfo.get("coin") : 0);
													workInfo.put("coin_total", userInfo.get("coinTotal") != null ? userInfo.get("coinTotal") : 0);
													workInfo.put("fans_count", userInfo.get("fansCount") != null ? userInfo.get("fansCount") : 0);
													workInfo.put("follow_count", userInfo.get("followCount") != null ? userInfo.get("followCount") : 0);
													workInfo.put("experience", userInfo.get("experience") != null ? userInfo.get("experience") : 0);
													if(workInfo.get("fisrt_bean") == null) {
														calendar.setTime(now);
														calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 1);
														String yesDay = dateFormat.format(calendar.getTime());
														count = dataBaseService.checkWorkInfo(entry.getKey(), yesDay);
														if(count != null && count.size() > 0) {
															Map<String, Object> yesWorkInfo = count.get(0);
															workInfo.put("fisrt_bean", yesWorkInfo.get("bean_total") != null ? yesWorkInfo.get("bean_total") : 
																(userInfo.get("beanTotal") != null ? userInfo.get("beanTotal") : 0));
															logger.info("update {}|{} bean using yesterday data : last = {}, now = {}", chickenInfo.getId(), 
																	chickenInfo.getStarId(), yesWorkInfo.get("bean_total"), userInfo.get("beanTotal"));
														} else {
															workInfo.put("fisrt_bean", userInfo.get("beanTotal") != null ? userInfo.get("beanTotal") : 0);	
															logger.info("update {}|{} bean using current data", chickenInfo.getId(), chickenInfo.getStarId());
														}
													}
													dataBaseService.updateWorkInfo2(workInfo);
												} else {
													List<Map<String, Object>> taskInfo = dataBaseService.doGetTaskInfo(chickenInfo.getId(), chickenInfo.getlId(), monthFormat.format(now));
													if(taskInfo != null && taskInfo.size() > 0) {
														Map<String, Object> workInfo = new HashMap<String, Object>();
														workInfo.put("star_id", entry.getKey());
														workInfo.put("l_id", chickenInfo.getlId());
														workInfo.put("star_level", userInfo.get("starLevel") != null ? userInfo.get("starLevel") : 0);
														workInfo.put("rich_level", userInfo.get("richLevel") != null ? userInfo.get("richLevel") : 0);
														calendar.setTime(now);
														calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 1);
														String yesDay = dateFormat.format(calendar.getTime());
														count = dataBaseService.checkWorkInfo(entry.getKey(), yesDay);
														if(count != null && count.size() > 0) {
															Map<String, Object> yesWorkInfo = count.get(0);
															workInfo.put("fisrt_bean", yesWorkInfo.get("bean_total") != null ? yesWorkInfo.get("bean_total") : 
																(userInfo.get("beanTotal") != null ? userInfo.get("beanTotal") : 0));
															logger.info("insert {}|{} bean using yesterday data : last = {}, now = {}", chickenInfo.getId(), 
																	chickenInfo.getStarId(), yesWorkInfo.get("bean_total"), userInfo.get("beanTotal"));
														} else {
															workInfo.put("fisrt_bean", userInfo.get("beanTotal") != null ? userInfo.get("beanTotal") : 0);	
															logger.info("insert {}|{} bean using current data", chickenInfo.getId(), chickenInfo.getStarId());
														}
														workInfo.put("bean_total", userInfo.get("beanTotal") != null ? userInfo.get("beanTotal") : 0);
														workInfo.put("coin", userInfo.get("coin") != null ? userInfo.get("coin") : 0);
														workInfo.put("coin_total", userInfo.get("coinTotal") != null ? userInfo.get("coinTotal") : 0);
														workInfo.put("fans_count", userInfo.get("fansCount") != null ? userInfo.get("fansCount") : 0);
														workInfo.put("follow_count", userInfo.get("followCount") != null ? userInfo.get("followCount") : 0);
														workInfo.put("experience", userInfo.get("experience") != null ? userInfo.get("experience") : 0);
														workInfo.put("cur_month", monthFormat.format(now));
														workInfo.put("cur_day", curDay);
														workInfo.put("last_time", timeFormat.format(now));
														workInfo.put("task_info_id", taskInfo.get(0).get("id"));
														dataBaseService.insertWorkInfo2(workInfo);
													}											
												}
												Object nickName = userInfo.get("nickName");
												if(nickName != null && (StringUtils.isEmpty(chickenInfo.getNickName()) || 
														!chickenInfo.getNickName().equals(nickName.toString()))) {
													logger.info("{}|{} nickname is null or has changed, new name is {}", chickenInfo.getId(), chickenInfo.getStarId(), nickName);
													dataBaseService.updateNickName(chickenInfo.getId(), nickName.toString());
													chickenInfo.setNickName(nickName.toString());
												}
												Object roomId = userInfo.get("roomId");
												if(roomId != null && (StringUtils.isEmpty(chickenInfo.getRoomId()) || 
														!chickenInfo.getRoomId().equals(roomId.toString()))) {
													logger.info("{}|{} roomid is null or has changed, new roomid is {}", chickenInfo.getId(), chickenInfo.getStarId(), roomId);
													dataBaseService.updateRoomId(chickenInfo.getId(), roomId.toString());
													chickenInfo.setRoomId(roomId.toString());
												}
											} else if(errorno.equals("100035031")) {
												CacheInfo.putContent(chickenInfo.getStarId(), "Cookie不正确或者已过期，请重新填写<br>\r\n");												
												logger.info("user {}|{} errorno is not equal to zero : {}", chickenInfo.getId(), chickenInfo.getStarId(), result);
											}
										} else {
											logger.info("request user {}|{} info failed, response code is {}", chickenInfo.getId(), chickenInfo.getStarId(), response.code());
										}
									} else {
										CacheInfo.putContent(chickenInfo.getStarId(), "Cookie为空，请填写<br>\r\n");
									}
								} catch(Exception e) {
									logger.error("{} : ", entry.getKey(), e);
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
