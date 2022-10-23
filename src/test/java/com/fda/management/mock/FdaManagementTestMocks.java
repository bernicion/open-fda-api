package com.fda.management.mock;

import com.fda.management.entity.DrugRecordEntity;
import com.fda.management.openapi.model.DrugRecordDto;

import java.util.List;
import java.util.Set;

public class FdaManagementTestMocks {

    public static DrugRecordDto mockDrugRecordDto() {
        return createDrugRecord("ANDA088551", "Taro Pharmaceuticals U.S.A., Inc.",
                List.of("PROPAFENONE HYDROCHLORIDE"), List.of("001", "002"));
    }

    public static List<DrugRecordEntity> mockDrugRecordEntities() {
        return List.of(createDrugRecordEntity("ANDA088551", "Taro Pharmaceuticals U.S.A., Inc.",
                Set.of("PROPAFENONE HYDROCHLORIDE"), Set.of("001", "002")));
    }

    public static DrugRecordEntity mockDrugRecordEntity() {
        return createDrugRecordEntity("ANDA088551", "Taro Pharmaceuticals U.S.A., Inc.",
                Set.of("PROPAFENONE HYDROCHLORIDE"), Set.of("001", "002"));
    }

    public static List<DrugRecordDto> mockDrugRecordsDto() {
        DrugRecordDto drug1 = createDrugRecord("ANDA088551", "Taro Pharmaceuticals U.S.A., Inc.",
                List.of("PROPAFENONE HYDROCHLORIDE"), List.of("001", "002"));
        DrugRecordDto drug2 = createDrugRecord("ANDA088552", "null",
                List.of("IMATINIB MESYLATE"), List.of("003"));
        return List.of(drug1, drug2);
    }

    private static DrugRecordDto createDrugRecord(String appNumber, String manufacturerNumber, List<String> substances, List<String> productNumbers) {
        DrugRecordDto drugRecordDto = new DrugRecordDto();
        drugRecordDto.setAppNumber(appNumber);
        drugRecordDto.setManufacturerName(manufacturerNumber);
        drugRecordDto.setSubstances(substances);
        drugRecordDto.setProductNumbers(productNumbers);
        return drugRecordDto;
    }

    public static DrugRecordEntity createDrugRecordEntity(String appNumber, String manufacturerNumber, Set<String> substances, Set<String> productNumbers) {
        DrugRecordEntity drugRecordEntity = new DrugRecordEntity();
        drugRecordEntity.setAppNumber(appNumber);
        drugRecordEntity.setManufacturerName(manufacturerNumber);
        drugRecordEntity.setSubstances(substances);
        drugRecordEntity.setProductNumbers(productNumbers);
        return drugRecordEntity;
    }
}
