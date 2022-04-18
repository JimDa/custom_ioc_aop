package org.arch_learn.ioc_aop_test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.atomic.AtomicReference;

public class Test {

    private static Integer[] runArr = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private static Integer[] pingArr = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};


    public static void main(String[] args) throws IOException {
        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("请输入");
        String next = stdin.readLine();

        String[] arr = next.split(" ");
        String yearString = arr[0];
        String dayStr = arr[1];
        Integer year = Integer.valueOf(yearString);
        Integer day = Integer.valueOf(dayStr);
        AtomicReference<Integer> dayCopy = new AtomicReference<>(day);
        AtomicReference<Integer> dayRef = new AtomicReference<>(null);
        AtomicReference<Integer> monthRef = new AtomicReference<>(null);

        //闰年
        if ((year % 100 != 0 && year % 4 == 0) || (year % 100 == 0 && year % 400 == 0)) {
            compute(dayCopy, dayRef, monthRef, runArr);
        } else {
            compute(dayCopy, dayRef, monthRef, pingArr);
        }
        System.out.println(yearString + (monthRef.get() < 10 ? ("0" + monthRef.get()) : monthRef.get()) + (dayRef.get() < 10 ? ("0" + dayRef.get()) : dayRef.get()));


    }

    private static void compute(AtomicReference<Integer> dayCopy, AtomicReference<Integer> dayRef, AtomicReference<Integer> monthRef, Integer[] arr) {

        for (int i = 0; i < arr.length; i++) {
            int temp = dayCopy.get() - arr[i];
            if (temp < 0) {
                dayRef.set(dayCopy.get());
                monthRef.set(i + 1);
                break;
            } else if (temp == 0) {
                dayRef.set(arr[i]);
                monthRef.set(i + 1);
                break;
            }
            dayCopy.set(temp);
        }
    }
}
