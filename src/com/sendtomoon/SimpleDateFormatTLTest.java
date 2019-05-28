package com.sendtomoon;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SimpleDateFormatTLTest {
	
	private static ThreadLocal<DateFormat> threadLocal = new ThreadLocal<DateFormat>() {
		@Override
		protected DateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}
	};

	public static Date parse(String dateStr) throws ParseException {
		return threadLocal.get().parse(dateStr);
	}

	public static String format(Date date) {
		return threadLocal.get().format(date);
	}

	/**
	 * 使用ThreadLocal解决问题，也是一种思路，虽然我觉得怪怪的，并不是一种好的代码风格
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
