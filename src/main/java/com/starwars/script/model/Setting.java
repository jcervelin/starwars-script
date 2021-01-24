package com.starwars.script.model;

import com.starwars.script.model.Character;
import com.starwars.script.model.util.Splittable;
import lombok.Builder;
import lombok.Data;

import java.util.Collection;
import java.util.Set;

@Data
@Builder
public class Setting implements Splittable {
    private String id;
    private String name;
    private String content;
    private Collection<Character> characters;
}
