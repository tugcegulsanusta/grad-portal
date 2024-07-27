package com.tugceusta.project.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class SkillTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Skill getSkillSample1() {
        return new Skill().id(1L).name("name1").description("description1").order(1);
    }

    public static Skill getSkillSample2() {
        return new Skill().id(2L).name("name2").description("description2").order(2);
    }

    public static Skill getSkillRandomSampleGenerator() {
        return new Skill()
            .id(longCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString())
            .order(intCount.incrementAndGet());
    }
}
