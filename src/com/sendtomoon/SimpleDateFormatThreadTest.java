package com.sendtomoon;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * SimpleDateFormat线程不安全引发的多线程故障
 *
 */
public class SimpleDateFormatThreadTest {
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static String formatDate(Date date) throws ParseException {
		return sdf.format(date);
	}

	public static Date parse(String strDate) throws ParseException {
		return sdf.parse(strDate);
	}

	public static void main(String[] args) throws InterruptedException, ParseException {

//		System.out.println(sdf.format(new Date())); //单线程

		/*
		 * 多线程
		 */
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

		/**
		 * 多线程不安全原因
		 * 
		 * 因为把SimpleDateFormat定义为静态变量， 那么多线程下SimpleDateFormat的实例就会被多个线程共享，
		 * B线程会读取到A线程的时间， 就会出现时间差异和其它各种问题。 SimpleDateFormat和它继承的DateFormat类也不是线程安全的
		 */

	}
}
