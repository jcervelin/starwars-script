package com.starwars.script.dao;

import com.starwars.script.model.entity.SettingEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SettingDao extends MongoRepository<SettingEntity, String> {
}