package com.embabel.database.agent.util;

import com.embabel.database.core.repository.domain.Model;

import java.util.List;
import java.util.Map;

public interface ModelParser {

    List<Model> parseModels(List<Map<String,Object>> rawModels);

    List<Model> parseModels(String json);

    Model parseModel(String json);

    Model parseModel(Map<String,Object> rawModel);
}
