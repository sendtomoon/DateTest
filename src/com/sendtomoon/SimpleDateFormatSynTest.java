package com.sendtomoon;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SimpleDateFormatSynTest {
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static String formatDate(Date date) throws ParseException {
		synchronized (sdf) {
			return sdf.format(date);
		}
	}

	public static Date parse(String strDate) throws ParseException {
		synchronized (sdf) {
			return sdf.parse(strDate);
		}
	}

	/**
	 * 使用synchronized块解决static情况下遇到的线程不安全情况 但是synchronized有可能出现高并发阻塞的情况
	 * 
	 * @param args
	 * @throws InterruptedException
	 * @throws ParseException
	 */
	public static void main(String[] args) throws InterruptedException, ParseException {
		ExecutorService service = Executors.newFixedThreadPool(100);
		for (int i = 0; i < 20; i++) {
			service.execute(() -> {
				for (int j = 0; j < 10; j++) {
					try {
						System.out.println(parse("2019-05-13 14:45:59"));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
			});
		}
		// 等待上述的线程执行完
		service.shutdown();
		service.awaitTermination(1, TimeUnit.DAYS);

	}
}
