package com.tugceusta.project.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SchoolTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static School getSchoolSample1() {
        return new School().id(1L).universityName("universityName1").facultyName("facultyName1").departmentName("departmentName1");
    }

    public static School getSchoolSample2() {
        return new School().id(2L).universityName("universityName2").facultyName("facultyName2").departmentName("departmentName2");
    }

    public static School getSchoolRandomSampleGenerator() {
        return new School()
            .id(longCount.incrementAndGet())
            .universityName(UUID.randomUUID().toString())
            .facultyName(UUID.randomUUID().toString())
            .departmentName(UUID.randomUUID().toString());
    }
}
