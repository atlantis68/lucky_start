package org.luckystar.service;

import java.io.IOException;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

public class HttpService {

	private static OkHttpClient okHttpClient = new OkHttpClient();

	public static Response sendHttp(Request request) {
		try {
			return okHttpClient.newCall(request).execute();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
