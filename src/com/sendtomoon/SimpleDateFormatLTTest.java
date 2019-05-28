package com.sendtomoon;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SimpleDateFormatLTTest {
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	public static String formatDate2(LocalDateTime date) {
		return formatter.format(date);
	}

	public static LocalDateTime parse2(String dateNow) {
		return LocalDateTime.parse(dateNow, formatter);
	}

	/**
	 * Java8�Ľ��������ʹ�� LocalTime���
	 * 
	 * @param args
	 * @throws InterruptedException
	 * @throws ParseException
	 */
	public static void main(String[] args) throws InterruptedException, ParseException {

		ExecutorService service = Executors.newFixedThreadPool(100);

		// 20���߳�
		for (int i = 0; i < 20; i++) {
			service.execute(() -> {
				for (int j = 0; j < 10; j++) {
					try {
						System.out.println(parse2(formatDate2(LocalDateTime.now())));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
		// �ȴ��������߳�ִ����
		service.shutdown();
		service.awaitTermination(1, TimeUnit.DAYS);

	}
}
