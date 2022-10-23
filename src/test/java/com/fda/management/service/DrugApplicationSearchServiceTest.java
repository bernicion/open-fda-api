package com.fda.management.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fda.management.mapping.DrugRecordMapper;
import com.fda.management.openapi.model.DrugRecordDto;
import com.fda.management.service.impl.OpenFdaResponse;
import com.fda.management.service.impl.OpenFdaSearchServiceImpl;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static com.fda.management.service.DrugRecordManagementServiceTest.assertRecipeDtoProperties;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
public class DrugApplicationSearchServiceTest {
    protected static ObjectMapper jsonMapper;

    @BeforeAll
    static void init() {
        jsonMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        jsonMapper.registerModule(new JavaTimeModule());
    }

    @InjectMocks
    OpenFdaSearchServiceImpl drugApplicationSearchService;

    @Mock
    RestTemplate restTemplate;

    @Spy
    DrugRecordMapper drugRecordMapper = Mappers.getMapper(DrugRecordMapper.class);

    @Test
    void testSearchThroughOpenFda() throws Exception {
        OpenFdaResponse response = getOpenFdaResponseFromTxtResource();
        Mockito.when(restTemplate.getForObject(anyString(), eq(OpenFdaResponse.class))).thenReturn(response);

        List<DrugRecordDto> records = drugApplicationSearchService.searchDrugApplications("Taro Pharmaceuticals U.S.A., Inc.", null, 0, 2);
        assertEquals(2, records.size(), "Wrong number of returned drug records");

        assertRecipeDtoProperties(records.get(0), "ANDA074904", "Taro Pharmaceuticals U.S.A., Inc.",
                List.of("DESOXIMETASONE"), List.of("001"));
        assertRecipeDtoProperties(records.get(1), "ANDA201546", "Publix Super Markets Inc",
                List.of("CETIRIZINE HYDROCHLORIDE"), List.of("002", "001"));
    }

    private OpenFdaResponse getOpenFdaResponseFromTxtResource() throws Exception {
        String jsonString = readFromResourceFile("/open-fda/manufacturer-response.txt");
        return jsonMapper.readValue(jsonString, OpenFdaResponse.class);
    }

    private String readFromResourceFile(String resourceName) throws IOException {
        Path resourceDirectory = Paths.get("src","test","resources");
        String absolutePath = resourceDirectory.toFile().getAbsolutePath();
        return FileUtils.readFileToString(new File(absolutePath + resourceName), StandardCharsets.UTF_8);
    }

}
