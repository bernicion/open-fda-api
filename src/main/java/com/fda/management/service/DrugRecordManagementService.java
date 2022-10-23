package com.fda.management.service;

import com.fda.management.openapi.model.DrugRecordDto;

import java.util.List;

public interface DrugRecordManagementService {

    DrugRecordDto createDrugRecord(DrugRecordDto drugRecordDto);

    DrugRecordDto getDrugRecordByAppNumber(String appNumber);

    List<DrugRecordDto> getAllDrugRecords(Integer page, Integer size);
}
