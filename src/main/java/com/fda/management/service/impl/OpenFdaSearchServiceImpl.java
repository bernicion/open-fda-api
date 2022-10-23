package com.fda.management.service.impl;

import com.fda.management.mapping.DrugRecordMapper;
import com.fda.management.openapi.model.DrugRecordDto;
import com.fda.management.service.DrugApplicationSearchService;
import lombok.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OpenFdaSearchServiceImpl implements DrugApplicationSearchService {
    private final DrugRecordMapper drugRecordMapper;
    private final RestTemplate restTemplate;
    private static final String OPEN_FDA_BASE_URL = "https://api.fda.gov/drug/drugsfda.json";

    @Override
    public List<DrugRecordDto> searchDrugApplications(String manufactureName, String fdaBrandName, Integer pageIndex, Integer pageSize) {
        StringBuilder urlBuilder = new StringBuilder(OPEN_FDA_BASE_URL);
        if (StringUtils.isNotBlank(manufactureName) || StringUtils.isNotBlank(fdaBrandName)) {
            urlBuilder.append("?search=");
        }
        if (StringUtils.isNotBlank(manufactureName)) {
            urlBuilder.append("openfda.manufacturer_name.exact=").append("\"").append(manufactureName).append("\"");
        }
        if (StringUtils.isNotBlank(fdaBrandName)) {
            if (StringUtils.isNotBlank(manufactureName)) {
                urlBuilder.append("+AND+");
            }
            urlBuilder.append("openfda.brand_name.exact=").append("\"").append(fdaBrandName).append("\"");
        }
        if (pageSize != null) {
            urlBuilder.append("&limit=").append(pageSize);
            if (pageIndex != null) {
                urlBuilder.append("&skip=").append(pageIndex * pageSize);
            }
        }

        OpenFdaResponse openFdaResponse = restTemplate.getForObject(urlBuilder.toString(), OpenFdaResponse.class);
        return openFdaResponse == null ? Collections.emptyList() : toResult(openFdaResponse);
    }

    private List<DrugRecordDto> toResult(OpenFdaResponse openFdaResponse) {
        return openFdaResponse.getResults()
                .stream()
                .map(drugRecordMapper::toDrugRecordFromApiResponse)
                .toList();
    }
}
