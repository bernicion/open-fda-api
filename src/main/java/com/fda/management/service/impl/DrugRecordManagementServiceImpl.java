package com.fda.management.service.impl;

import com.fda.management.entity.DrugRecordEntity;
import com.fda.management.mapping.DrugRecordMapper;
import com.fda.management.openapi.model.DrugRecordDto;
import com.fda.management.repository.DrugRecordEntityRepository;
import com.fda.management.service.DrugRecordManagementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DrugRecordManagementServiceImpl implements DrugRecordManagementService {
    private final DrugRecordEntityRepository drugRecordEntityRepository;
    private final DrugRecordMapper drugRecordMapper;

    @Override
    @Transactional
    public DrugRecordDto createDrugRecord(DrugRecordDto drugRecordDto) {
        log.info("Creating a new drug record with Application Number {}", drugRecordDto.getAppNumber());
        DrugRecordEntity drugRecordEntity = drugRecordMapper.toDrugRecordEntity(drugRecordDto);
        DrugRecordEntity savedDrugRecordEntity = drugRecordEntityRepository.saveAndFlush(drugRecordEntity);
        return drugRecordMapper.toDrugRecordFromDbEntity(savedDrugRecordEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public DrugRecordDto getDrugRecordByAppNumber(String appNumber) {
        log.info("Extracting the drug record with Application Number {}", appNumber);
        Optional<DrugRecordEntity> drugRecordEntityOptional = drugRecordEntityRepository.findById(appNumber);
        if (drugRecordEntityOptional.isPresent()) {
            return drugRecordMapper.toDrugRecordFromDbEntity(drugRecordEntityOptional.get());
        } else {
            throw new NoSuchElementException(String.format("No such Drug Record with Application Number %s", appNumber));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<DrugRecordDto> getAllDrugRecords(Integer page, Integer size) {
        return drugRecordEntityRepository.findAll(PageRequest.of(page, size)).get().map(drugRecordMapper::toDrugRecordFromDbEntity).toList();
    }
}
