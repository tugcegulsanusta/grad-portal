package com.tugceusta.project.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class GraduationTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Graduation getGraduationSample1() {
        return new Graduation().id(1L).startYear(1).graduationYear(1);
    }

    public static Graduation getGraduationSample2() {
        return new Graduation().id(2L).startYear(2).graduationYear(2);
    }

    public static Graduation getGraduationRandomSampleGenerator() {
        return new Graduation()
            .id(longCount.incrementAndGet())
            .startYear(intCount.incrementAndGet())
            .graduationYear(intCount.incrementAndGet());
    }
}
