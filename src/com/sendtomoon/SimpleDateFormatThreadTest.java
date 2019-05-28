package com.sendtomoon;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * SimpleDateFormat�̲߳���ȫ�����Ķ��̹߳���
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

//		System.out.println(sdf.format(new Date())); //���߳�

		/*
		 * ���߳�
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
		// �ȴ��������߳�ִ����
		service.shutdown();
		service.awaitTermination(1, TimeUnit.DAYS);

		/**
		 * ���̲߳���ȫԭ��
		 * 
		 * ��Ϊ��SimpleDateFormat����Ϊ��̬������ ��ô���߳���SimpleDateFormat��ʵ���ͻᱻ����̹߳���
		 * B�̻߳��ȡ��A�̵߳�ʱ�䣬 �ͻ����ʱ�����������������⡣ SimpleDateFormat�����̳е�DateFormat��Ҳ�����̰߳�ȫ��
		 */

	}
}
