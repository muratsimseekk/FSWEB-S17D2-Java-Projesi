package com.workintech.s17d2.model;

import org.springframework.stereotype.Component;

@Component
public class SeniorDeveloper extends Developer{
    public SeniorDeveloper(int id, String name, double salary, Experience experience) {
        super(id, name, salary, experience);
    }
}
