package org.luckystar.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class DataBaseService {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public List<Map<String, Object>> getLaborUnion(int state) {
		return jdbcTemplate.queryForList("select * from labor_union where state = ?", new Object[] {state});
	}
	
	public List<Map<String, Object>> getChickenInfo(int state) {
		return jdbcTemplate.queryForList("select * from chicken_info where state = ?", new Object[] {state});
	}
	
	public List<Map<String, Object>> checkWorkInfo(long starId, String curDay) {
		return jdbcTemplate.queryForList("select * from work_info where star_id = ? and cur_day = ?", new Object[] {starId, curDay});
	}
	
	public void insertWorkInfo1(Map<String, Object> workInfo) {
		jdbcTemplate.update("INSERT INTO work_info (star_id, l_id, work_time, cur_month, cur_day, last_time) VALUES(?, ?, ?, ?, ?, ?)",
				new Object[] {workInfo.get("star_id"), workInfo.get("l_id"), workInfo.get("work_time"), 
					workInfo.get("cur_month"), workInfo.get("cur_day"), workInfo.get("last_time")});
	}
	
	public void insertWorkInfo2(Map<String, Object> workInfo) {
		jdbcTemplate.update("INSERT INTO work_info (star_id, l_id, star_level, rich_level, fisrt_bean, bean_total, coin, coin_total, fans_count, "
				+ "follow_count, experience, cur_month, cur_day, last_time) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
				new Object[] {workInfo.get("star_id"), workInfo.get("l_id"), workInfo.get("star_level"), workInfo.get("rich_level"), 
						workInfo.get("fisrt_bean"), workInfo.get("bean_total"), workInfo.get("coin"), workInfo.get("coin_total"), 
						workInfo.get("fans_count"), workInfo.get("follow_count"), workInfo.get("experience"), 
						workInfo.get("cur_month"), workInfo.get("cur_day"), workInfo.get("last_time")});
	}
	
	public void updateWorkInfo1(Map<String, Object> workInfo) {
		jdbcTemplate.update("UPDATE work_info SET work_time = ?, last_time = ? where id = ?",
				new Object[] {workInfo.get("work_time"), workInfo.get("last_time"), workInfo.get("id")});
	}
	
	public void updateWorkInfo2(Map<String, Object> workInfo) {
		jdbcTemplate.update("UPDATE work_info SET star_level = ?, rich_level = ?, bean_total = ?, coin = ?, "
				+ "coin_total = ?, fans_count = ?, follow_count = ?, experience = ? where id = ?",
				new Object[] {workInfo.get("star_level"), workInfo.get("rich_level"), workInfo.get("bean_total"), 
				workInfo.get("coin"), workInfo.get("coin_total"), workInfo.get("fans_count"), 
				workInfo.get("follow_count"), workInfo.get("experience"), workInfo.get("id")});
	}
}
