package com.vintrace.codingchallenge.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vintrace.codingchallenge.constants.BreakdownTypeEnum;
import com.vintrace.codingchallenge.repository.ComponentRepository;
import com.vintrace.codingchallenge.repository.WineRepository;
import com.vintrace.codingchallenge.repository.entity.WineEntity;
import com.vintrace.codingchallenge.repository.projection.IBreakdown;
import com.vintrace.codingchallenge.service.model.Wine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BreakdownServiceTest {

    @InjectMocks
    private BreakdownService breakdownService;

    @Mock
    private WineRepository wineRepositoryMock;

    @Mock
    private ComponentRepository componentRepositoryMock;

    @Mock
    private ObjectMapper objectMapperMock;

    private static final String LOT_CODE = "11YVCHAR001";

    @BeforeEach
    public void setUp() throws IOException {
        when(wineRepositoryMock.findOneByLotCode(anyString())).thenReturn(mock(WineEntity.class));
    }

    @Test
    public void test__getBreakDown__loadResource() throws IOException {
        when(wineRepositoryMock.findOneByLotCode(anyString())).thenReturn(null);
        Wine wineMock = mock(Wine.class);
        when(wineMock.getComponentEntities()).thenReturn(mock(List.class));
        when(objectMapperMock.readValue(anyString(), any(Class.class))).thenReturn(wineMock);

        breakdownService.getBreakDown(BreakdownTypeEnum.YEAR, LOT_CODE);

        verify(wineRepositoryMock, times(1)).save(any());
        verify(componentRepositoryMock, times(1)).saveAll(any());
    }

    @Test
    public void test__getBreakDown__loadResource__withoutComponents() throws IOException {
        when(wineRepositoryMock.findOneByLotCode(anyString())).thenReturn(null);
        Wine wineMock = mock(Wine.class);
        when(wineMock.getComponentEntities()).thenReturn(new ArrayList<>());
        when(objectMapperMock.readValue(anyString(), any(Class.class))).thenReturn(wineMock);

        breakdownService.getBreakDown(BreakdownTypeEnum.YEAR, LOT_CODE);

        verify(wineRepositoryMock, times(1)).save(any());
        verify(componentRepositoryMock, never()).saveAll(any());
    }

    @Test
    public void test__getBreakDown__byYear() throws IOException {
        when(wineRepositoryMock.findOneByLotCode(anyString())).thenReturn(mock(WineEntity.class));
        List<IBreakdown> repoResults = mock(List.class);
        when(componentRepositoryMock.getBreakdownByYear(LOT_CODE)).thenReturn(repoResults);

        List<IBreakdown> result = breakdownService.getBreakDown(BreakdownTypeEnum.YEAR, LOT_CODE);

        verify(wineRepositoryMock, never()).save(any());
        verify(componentRepositoryMock, never()).saveAll(any());
        verify(componentRepositoryMock, times(1)).getBreakdownByYear(any());
        assertEquals(repoResults, result);
    }

    @Test
    public void test__getBreakDown__byVariety() throws IOException {
        when(wineRepositoryMock.findOneByLotCode(anyString())).thenReturn(mock(WineEntity.class));
        List<IBreakdown> repoResults = mock(List.class);
        when(componentRepositoryMock.getBreakdownByVariety(LOT_CODE)).thenReturn(repoResults);

        List<IBreakdown> result = breakdownService.getBreakDown(BreakdownTypeEnum.VARIETY, LOT_CODE);

        verify(wineRepositoryMock, never()).save(any());
        verify(componentRepositoryMock, never()).saveAll(any());
        verify(componentRepositoryMock, times(1)).getBreakdownByVariety(any());
        assertEquals(repoResults, result);
    }

    @Test
    public void test__getBreakDown__byRegion() throws IOException {
        when(wineRepositoryMock.findOneByLotCode(anyString())).thenReturn(mock(WineEntity.class));
        List<IBreakdown> repoResults = mock(List.class);
        when(componentRepositoryMock.getBreakdownByRegion(LOT_CODE)).thenReturn(repoResults);

        List<IBreakdown> result = breakdownService.getBreakDown(BreakdownTypeEnum.REGION, LOT_CODE);

        verify(wineRepositoryMock, never()).save(any());
        verify(componentRepositoryMock, never()).saveAll(any());
        verify(componentRepositoryMock, times(1)).getBreakdownByRegion(any());
        assertEquals(repoResults, result);
    }

    @Test
    public void test__getBreakDown__byYearVariety() throws IOException {
        when(wineRepositoryMock.findOneByLotCode(anyString())).thenReturn(mock(WineEntity.class));
        List<IBreakdown> repoResults = mock(List.class);
        when(componentRepositoryMock.getBreakdownByYearVariety(LOT_CODE)).thenReturn(repoResults);

        List<IBreakdown> result = breakdownService.getBreakDown(BreakdownTypeEnum.YEAR_VARIETY, LOT_CODE);

        verify(wineRepositoryMock, never()).save(any());
        verify(componentRepositoryMock, never()).saveAll(any());
        verify(componentRepositoryMock, times(1)).getBreakdownByYearVariety(any());
        assertEquals(repoResults, result);
    }

    @Test
    public void test__getBreakDown__resourceNotFound() {
        when(wineRepositoryMock.findOneByLotCode(anyString())).thenReturn(null);

        try {
            breakdownService.getBreakDown(BreakdownTypeEnum.YEAR_VARIETY, "bogus");
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof IOException);
            verify(wineRepositoryMock, never()).save(any());
            verify(componentRepositoryMock, never()).saveAll(any());
        }
    }

}