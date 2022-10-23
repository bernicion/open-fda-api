package com.fda.management.service;

import com.fda.management.openapi.model.DrugRecordDto;

import java.util.List;

public interface DrugApplicationSearchService {

    List<DrugRecordDto> searchDrugApplications(String manufactureName, String fdaBrandName, Integer page, Integer size);
}
