package com.fda.management.repository;

import com.fda.management.entity.DrugRecordEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DrugRepositoryTest extends BaseRepositoryUnitTest {

    @Autowired
    DrugRecordEntityRepository drugRecordEntityRepository;

    @Test
    void testGetDrugRecordByAppNumber() {
        Optional<DrugRecordEntity> drugRecordEntityOptional = drugRecordEntityRepository.findById("ANDA088551");
        assertTrue(drugRecordEntityOptional.isPresent());

        DrugRecordEntity drugRecordEntity = drugRecordEntityOptional.get();
        assertRecipeDtoProperties(drugRecordEntity, "ANDA088551", "Taro Pharmaceuticals U.S.A., Inc.",
                Set.of("PROPAFENONE HYDROCHLORIDE"), Set.of("001", "002"));
    }

    @Test
    void testGetAllDrugRecordsWithPagination() {
        Page<DrugRecordEntity> drugRecordEntities = drugRecordEntityRepository.findAll(PageRequest.of(0, 50));
        List<DrugRecordEntity> drugRecordEntityList = drugRecordEntities.stream().toList();
        assertEquals(1, drugRecordEntityList.size(), "Wrong number of expected drug records");
        assertRecipeDtoProperties(drugRecordEntityList.get(0), "ANDA088551", "Taro Pharmaceuticals U.S.A., Inc.",
                Set.of("PROPAFENONE HYDROCHLORIDE"), Set.of("001", "002"));
    }

    private void assertRecipeDtoProperties(DrugRecordEntity drugRecordDto, String appNumber, String manufacturerName, Set<String> substances, Set<String> productNumbers) {
        assertEquals(appNumber, drugRecordDto.getAppNumber(), "Wrong drug record app number");
        assertEquals(manufacturerName, drugRecordDto.getManufacturerName(), "Wrong drug record manufacturer name");
        assertTrue(drugRecordDto.getSubstances().containsAll(substances), "Wrong drug record substances");
        assertTrue(drugRecordDto.getProductNumbers().containsAll(productNumbers), "Wrong drug record product numbers");
    }
}
