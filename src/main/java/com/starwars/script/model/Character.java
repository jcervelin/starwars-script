package com.starwars.script.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.starwars.script.model.util.Splittable;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Character implements Splittable {
	private String id;
	private String name;
	private String content;
	private Map<String, Integer> wordCountsMap;
}