package com.fda.management.controller;

import com.fda.management.openapi.model.DrugRecordDto;
import com.fda.management.service.DrugApplicationSearchService;
import com.fda.management.service.DrugRecordManagementService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.ResultActions;

import static com.fda.management.mock.FdaManagementTestMocks.mockDrugRecordDto;
import static com.fda.management.mock.FdaManagementTestMocks.mockDrugRecordsDto;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class DrugApplicationControllerTest extends BaseControllerUnitTest {

    @MockBean
    private DrugApplicationSearchService drugApplicationSearchService;

    @MockBean
    private DrugRecordManagementService drugRecordManagementService;

    @Test
    void testSearchFDAApplicationByCriteria() throws Exception {
        when(drugApplicationSearchService.searchDrugApplications(anyString(), any(), anyInt(), anyInt()))
                .thenReturn(mockDrugRecordsDto());

        ResultActions result = performGETRequestWithPagination("/api/drugs/search?manufactureName=SomeManuf", 0, 10);
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].appNumber").value("ANDA088551"))
                .andExpect(jsonPath("$.[0].manufacturerName").value("Taro Pharmaceuticals U.S.A., Inc."))
                .andExpect(jsonPath("$.[0].substances.[0]").value("PROPAFENONE HYDROCHLORIDE"))
                .andExpect(jsonPath("$.[0].productNumbers.[0]").value("001"))
                .andExpect(jsonPath("$.[0].productNumbers.[1]").value("002"))
                .andExpect(jsonPath("$.[1].appNumber").value("ANDA088552"))
                .andExpect(jsonPath("$.[1].manufacturerName").value("null"))
                .andExpect(jsonPath("$.[1].substances.[0]").value("IMATINIB MESYLATE"))
                .andExpect(jsonPath("$.[1].productNumbers.[0]").value("003"));
    }

    @Test
    void testSaveDrugRecord() throws Exception {
        when(drugRecordManagementService.createDrugRecord(any(DrugRecordDto.class)))
                .thenReturn(mockDrugRecordDto());
        String jsonBody = jsonMapper.writeValueAsString(new DrugRecordDto());
        ResultActions result = performPOSTRequest("/api/drugs", jsonBody);

        result.andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://a.fda.management:8080/api/drugs/ANDA088551"))
                .andExpect(jsonPath("$.appNumber").value("ANDA088551"))
                .andExpect(jsonPath("$.manufacturerName").value("Taro Pharmaceuticals U.S.A., Inc."))
                .andExpect(jsonPath("$.substances.[0]").value("PROPAFENONE HYDROCHLORIDE"))
                .andExpect(jsonPath("$.productNumbers.[0]").value("001"))
                .andExpect(jsonPath("$.productNumbers.[1]").value("002"));
    }


    @Test
    void testGetAllDrugRecords() throws Exception {
        when(drugRecordManagementService.getAllDrugRecords(anyInt(), anyInt()))
                .thenReturn(mockDrugRecordsDto());
        ResultActions result = performGETRequestWithPagination("/api/drugs/?", 0, 10);
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].appNumber").value("ANDA088551"))
                .andExpect(jsonPath("$.[0].manufacturerName").value("Taro Pharmaceuticals U.S.A., Inc."))
                .andExpect(jsonPath("$.[0].substances.[0]").value("PROPAFENONE HYDROCHLORIDE"))
                .andExpect(jsonPath("$.[0].productNumbers.[0]").value("001"))
                .andExpect(jsonPath("$.[0].productNumbers.[1]").value("002"))
                .andExpect(jsonPath("$.[1].appNumber").value("ANDA088552"))
                .andExpect(jsonPath("$.[1].manufacturerName").value("null"))
                .andExpect(jsonPath("$.[1].substances.[0]").value("IMATINIB MESYLATE"))
                .andExpect(jsonPath("$.[1].productNumbers.[0]").value("003"));
    }

    @Test
    void testGetDrugRecordByAccountNumber() throws Exception {
        var appNumber = "ANDA088551";
        when(drugRecordManagementService.getDrugRecordByAppNumber(anyString()))
                .thenReturn(mockDrugRecordDto());
        ResultActions result = performGETRequestWithPathParam("/api/drugs/", appNumber);
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.appNumber").value(appNumber))
                .andExpect(jsonPath("$.manufacturerName").value("Taro Pharmaceuticals U.S.A., Inc."))
                .andExpect(jsonPath("$.substances.[0]").value("PROPAFENONE HYDROCHLORIDE"))
                .andExpect(jsonPath("$.productNumbers.[0]").value("001"))
                .andExpect(jsonPath("$.productNumbers.[1]").value("002"));
    }
}
