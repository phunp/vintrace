package com.vintrace.codingchallenge.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vintrace.codingchallenge.constants.BreakdownTypeEnum;
import com.vintrace.codingchallenge.repository.ComponentRepository;
import com.vintrace.codingchallenge.repository.WineRepository;
import com.vintrace.codingchallenge.repository.entity.ComponentEntity;
import com.vintrace.codingchallenge.repository.entity.WineEntity;
import com.vintrace.codingchallenge.repository.projection.IBreakdown;
import com.vintrace.codingchallenge.service.model.Wine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@Service
public class BreakdownService {
    @Autowired
    private WineRepository wineRepository;

    @Autowired
    private ComponentRepository componentRepository;

    @Autowired
    private ObjectMapper objectMapper;

    public List<IBreakdown> getBreakDown(BreakdownTypeEnum breakdownType, String lotCode) throws IOException {
        WineEntity wineEntity = wineRepository.findOneByLotCode(lotCode);
        if (wineEntity == null) this.loadResource(lotCode);

        List<IBreakdown> breakdowns;
        switch (breakdownType) {
            case VARIETY:
                breakdowns = componentRepository.getBreakdownByVariety(lotCode);
                break;
            case REGION:
                breakdowns = componentRepository.getBreakdownByRegion(lotCode);
                break;
            case YEAR_VARIETY:
                breakdowns = componentRepository.getBreakdownByYearVariety(lotCode);
                break;
            case YEAR:
            default:
                breakdowns = componentRepository.getBreakdownByYear(lotCode);
        }
        return breakdowns;
    }

    private void loadResource(String lotCode) throws IOException {
        Wine newWine = objectMapper.readValue(readFileToString("data/" + lotCode + ".json"), Wine.class);
        WineEntity wineEntity = newWine.getWineEntity();
        wineRepository.save(wineEntity);

        List<ComponentEntity> components = newWine.getComponentEntities();
        if (!components.isEmpty()) componentRepository.saveAll(components);
    }

    private String readFileToString(String path) throws IOException {
        String content = Files.readString(new ClassPathResource(path).getFile().toPath());
        return content;
    }
}
