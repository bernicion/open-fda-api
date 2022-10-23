package com.fda.management.service;

import com.fda.management.entity.DrugRecordEntity;
import com.fda.management.mapping.DrugRecordMapper;
import com.fda.management.openapi.model.DrugRecordDto;
import com.fda.management.repository.DrugRecordEntityRepository;
import com.fda.management.service.impl.DrugRecordManagementServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static com.fda.management.mock.FdaManagementTestMocks.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DrugRecordManagementServiceTest {

    @InjectMocks
    DrugRecordManagementServiceImpl drugRecordManagementService;

    @Mock
    DrugRecordEntityRepository drugRecordEntityRepository;

    @Spy
    DrugRecordMapper drugRecordMapper = Mappers.getMapper(DrugRecordMapper.class);

    @Captor
    ArgumentCaptor<DrugRecordEntity> drugRecordEntityArgumentCaptor;

    @Test
    void getDrugRecordByAppNumber() {
        var appNumber = "ANDA088551";
        when(drugRecordEntityRepository.findById(appNumber)).thenReturn(Optional.of(mockDrugRecordEntity()));
        DrugRecordDto drugRecordDto = drugRecordManagementService.getDrugRecordByAppNumber(appNumber);
        assertRecipeDtoProperties(drugRecordDto, "ANDA088551", "Taro Pharmaceuticals U.S.A., Inc.",
                List.of("PROPAFENONE HYDROCHLORIDE"), List.of("002", "001"));
    }

    @Test
    void getAllDrugRecords() {
        var pageable = PageRequest.of(0, 50);
        when(drugRecordEntityRepository.findAll(pageable)).thenReturn(new PageImpl<>(mockDrugRecordEntities()));
        List<DrugRecordDto> drugRecords = drugRecordManagementService.getAllDrugRecords(0, 50);
        assertEquals(1, drugRecords.size(), "Wrong number of expected drugRecords");
        assertRecipeDtoProperties(drugRecords.get(0), "ANDA088551", "Taro Pharmaceuticals U.S.A., Inc.",
                List.of("PROPAFENONE HYDROCHLORIDE"), List.of("002", "001"));
    }

    @Test
    void testAddDrugRecord() {
        drugRecordManagementService.createDrugRecord(mockDrugRecordDto());
        Mockito.verify(drugRecordEntityRepository).saveAndFlush(drugRecordEntityArgumentCaptor.capture());
        DrugRecordEntity recipeEntity = drugRecordEntityArgumentCaptor.getValue();

        DrugRecordDto drugRecordDto = drugRecordMapper.toDrugRecordFromDbEntity(recipeEntity);
        assertRecipeDtoProperties(drugRecordDto, "ANDA088551", "Taro Pharmaceuticals U.S.A., Inc.",
                List.of("PROPAFENONE HYDROCHLORIDE"), List.of("002", "001"));
    }

    public static void assertRecipeDtoProperties(DrugRecordDto drugRecordDto, String appNumber, String manufacturerName, List<String> substances, List<String> productNumbers) {
        assertEquals(appNumber, drugRecordDto.getAppNumber(), "Wrong drug record app number");
        assertEquals(manufacturerName, drugRecordDto.getManufacturerName(), "Wrong drug record manufacturer name");
        assertTrue(drugRecordDto.getSubstances().containsAll(substances), "Wrong drug record substances");
        assertTrue(drugRecordDto.getProductNumbers().containsAll(productNumbers), "Wrong drug record product numbers");
    }
}
