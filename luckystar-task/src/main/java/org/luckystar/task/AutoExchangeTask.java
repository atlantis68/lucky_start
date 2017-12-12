package org.luckystar.task;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.lang.StringUtils;
import org.luckystar.model.CacheInfo;
import org.luckystar.model.ChickenInfo;
import org.luckystar.model.LaborUnion;
import org.luckystar.service.HttpService;
import org.luckystar.util.MailUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

@Component
public class AutoExchangeTask extends TimerTask {

	private static final Logger logger = LoggerFactory.getLogger(AutoExchangeTask.class);

	private String myName;
	
	private String startKey;

	private String endKey;
	
	private int minNum;
	
	private int price;
	
	private int cny;
	
	private int mailRandom;
	
	private int mailFixed;
	
	private SimpleDateFormat timeFormat;

	public void init(int delay, int period, int minNum, int price, int cny, int mailRandom, int mailFixed) {
		myName = "exchange_task";
		startKey = "id=\"num_bean\">";
		endKey = "</em>";
		this.minNum = minNum;
		this.price = price;
		this.cny = cny;
		this.mailRandom = mailRandom;
		this.mailFixed = mailFixed;
		timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		new Timer(myName).schedule(this, delay * 1000, period * 1000);
	}
	
	@Override
	public void run() {
		try {
			for(Entry<Integer, LaborUnion> lus : CacheInfo.laborUnionCache.entrySet()) {
				LaborUnion lu = lus.getValue();
				if(lu.isAutoExchange()) {
					StringBuffer sb = null;
					for(Entry<Long, ChickenInfo> cis : CacheInfo.chickenInfoCache.entrySet()) {
						ChickenInfo ci = cis.getValue();
						try {
							if(ci.getlId() == lus.getKey() && StringUtils.isNotEmpty(ci.getCookie())) {
								Integer remainingBean = null;
				    			Request request = new Request.Builder()
					    		    .url("http://fanxing.kugou.com/index.php?action=userExchargeList")
					    		    .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
					    		    .addHeader("Accept-Language", "zh-CN,zh;q=0.8")
					    		    .addHeader("Cache-Control", "max-age=0")
					    		    .addHeader("Connection", "keep-alive")
					    		    .addHeader("Host", "fanxing.kugou.com")
					    		    .addHeader("Upgrade-Insecure-Requests", "1")
					    		    .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36")
					    		    .addHeader("Cookie", ci.getCookie())
					    		    .build();
				    			Response response = HttpService.sendHttp(request);
				    			if(response != null && response.isSuccessful()) {
				    				String result = response.body().string();
				    				int start = result.indexOf(startKey);
				    				if(start > -1) {
				    					int end = result.indexOf(endKey, start);
				    					if(end > -1) {
				    						remainingBean = (int)Double.parseDouble(result.substring(start + startKey.length(), end));
				    					}
				    				}
				    			}
				    			if(remainingBean != null) {
				    				int exchangeNumber;
				    				if(remainingBean > minNum * price) {
				    					exchangeNumber = remainingBean / price;
					        			request = new Request.Builder()
					    	    		    .url("http://fanxing.kugou.com/UServices/Settlement/SettlementService/apply?args=[" + exchangeNumber * price + "]&_=" + System.currentTimeMillis())
					    	    		    .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
					    	    		    .addHeader("Accept-Language", "zh-CN,zh;q=0.8")
					    	    		    .addHeader("Cache-Control", "max-age=0")
					    	    		    .addHeader("Connection", "keep-alive")
					    	    		    .addHeader("Host", "fanxing.kugou.com")
					    	    		    .addHeader("Upgrade-Insecure-Requests", "1")
					    	    		    .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36")
					    	    		    .addHeader("Cookie", ci.getCookie())
					    	    		    .build();
					        			response = HttpService.sendHttp(request);
					        			if(response != null && response.isSuccessful()) {
					        				JSONObject result = JSON.parseObject(response.body().string());
					        				if(result.getInteger("status") == 1) {
					        					if(sb == null) sb = new StringBuffer();
					        					sb.append(lu.getName()).append("/").append(ci.getUserName()).append("(")
						        					.append(ci.getNickName()).append(")于").append(timeFormat.format(new Date()))
						        					.append("兑换了").append(exchangeNumber).append(" * ").append(price).append(" = ")
						        					.append(exchangeNumber * price).append("个星豆（").append(exchangeNumber * cny).append("元），兑换前星豆数量")
						        					.append(remainingBean).append("（").append((float)remainingBean / (price / cny)).append("元），兑换后星豆数量")
						        					.append(remainingBean - exchangeNumber * price).append("（")
						        					.append((float)(remainingBean - exchangeNumber * price) / (price / cny)).append("元）<br>");
					        				}
					        			}
				    				}
				    			}
							}
						} catch(Exception e) {
							logger.error("用户{}兑换异常：{} : ", ci.getStarId(), myName, e);
						}
					}
					try {
						String address = lu.getEmail();
						if(StringUtils.isNotEmpty(address) && sb != null) {
							String[] addrs = address.split(",");
							for(String addr : addrs) {
								Thread.sleep((long)(new Random().nextFloat() * mailRandom * 1000 + mailFixed * 1000));
								MailUtils.sendMail("幸运星自动兑换短信", sb.toString(), addr);											
							}
						}						
					} catch(Exception e) {
						logger.error("邮件发送异常：{} : ", myName, e);
					}
				}
			}
		} catch(Exception e) {
			logger.error("{} : ", myName, e);
		} finally {
			logger.info("{} auto exchange successful", myName);
		}
	}

}
