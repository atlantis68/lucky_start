package org.luckystar.util;

import org.luckystar.model.CacheInfo;

public class ToolUtils {

	public static boolean isHitLocal(long id) {
		if(CacheInfo.totalNumber == 1) {
			return true;
		}
		if(CacheInfo.totalNumber == 2 && (id % CacheInfo.totalNumber == CacheInfo.modNumber)) {
			return true;
		}
		return false;
	}
}
