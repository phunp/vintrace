package com.vintrace.codingchallenge.controller;

import com.vintrace.codingchallenge.constants.BreakdownTypeEnum;
import com.vintrace.codingchallenge.controller.response.BreakdownResponse;
import com.vintrace.codingchallenge.repository.projection.IBreakdown;
import com.vintrace.codingchallenge.service.BreakdownService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BreakdownControllerTest {

    @InjectMocks
    private BreakdownController breakdownController;

    @Mock
    private BreakdownService breakdownServiceMock;

    @Test
    public void test__getBreakDown__shouldReturnSuccess() throws IOException {
        List<IBreakdown> breakdownResults = Arrays.asList(mock(IBreakdown.class), mock(IBreakdown.class));
        when(breakdownServiceMock.getBreakDown(any(BreakdownTypeEnum.class), anyString())).thenReturn(breakdownResults);

        ResponseEntity<BreakdownResponse> responseEntity = breakdownController.getBreakDown("year", "lotCode");

        verify(breakdownServiceMock, times(1)).getBreakDown(BreakdownTypeEnum.YEAR, "lotCode");
        assertNotNull(responseEntity);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(responseEntity.getBody().getBreakDownType(), "year");
        assertEquals(responseEntity.getBody().getBreakdowns().size(), breakdownResults.size());
    }

    @Test
    public void test__getBreakDown__invalidType__shouldReturnNotFound() throws IOException {
        ResponseEntity<BreakdownResponse> responseEntity = breakdownController.getBreakDown("NA", "lotCode");

        verify(breakdownServiceMock, never()).getBreakDown(any(), any());
        assertNotNull(responseEntity);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void test__getBreakDown__IOException__shouldReturnBadRequest() throws IOException {
        doThrow(new IOException()).when(breakdownServiceMock).getBreakDown(BreakdownTypeEnum.YEAR, "lotCode");

        ResponseEntity<BreakdownResponse> responseEntity = breakdownController.getBreakDown("year", "lotCode");

        verify(breakdownServiceMock, times(1)).getBreakDown(any(), any());
        assertNotNull(responseEntity);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

}