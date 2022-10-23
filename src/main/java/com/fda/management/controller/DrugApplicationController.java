package com.fda.management.controller;

import com.fda.management.openapi.api.DrugsApi;
import com.fda.management.openapi.model.DrugRecordDto;
import com.fda.management.service.DrugApplicationSearchService;
import com.fda.management.service.DrugRecordManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class DrugApplicationController implements DrugsApi {
    private final DrugApplicationSearchService drugApplicationSearchService;
    private final DrugRecordManagementService drugRecordManagementService;

    @Override
    public ResponseEntity<List<DrugRecordDto>> searchDrugApplications(String manufactureName,
                                                                                   Integer pageIndex,
                                                                                   Integer pageSize,
                                                                                   String fdaBrandName) {
        return ResponseEntity.ok(drugApplicationSearchService.searchDrugApplications(manufactureName, fdaBrandName, pageIndex, pageSize));
    }

    @Override
    public ResponseEntity<List<DrugRecordDto>> getAllDrugApplications(Integer pageIndex, Integer pageSize) {
        return ResponseEntity.ok(drugRecordManagementService.getAllDrugRecords(pageIndex, pageSize));
    }

    @Override
    public ResponseEntity<DrugRecordDto> addDrugApplication(DrugRecordDto drugRecordDto) {
        DrugRecordDto savedRecord = drugRecordManagementService.createDrugRecord(drugRecordDto);
        return ResponseEntity.created(buildResponseUriForDrugRecord(savedRecord)).body(savedRecord);
    }

    @Override
    public ResponseEntity<DrugRecordDto> getDrugApplicationByAppNumber(String appNumber) {
        return ResponseEntity.ok(drugRecordManagementService.getDrugRecordByAppNumber(appNumber));
    }

    private URI buildResponseUriForDrugRecord(DrugRecordDto recipeDto) {
        return URI.create(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DrugApplicationController.class)
                .getDrugApplicationByAppNumber(recipeDto.getAppNumber())).withSelfRel().getHref());
    }
}
