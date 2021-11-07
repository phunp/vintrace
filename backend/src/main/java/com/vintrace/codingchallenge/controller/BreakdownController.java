package com.vintrace.codingchallenge.controller;

import com.vintrace.codingchallenge.aspect.LogAround;
import com.vintrace.codingchallenge.constants.BreakdownTypeEnum;
import com.vintrace.codingchallenge.controller.response.BreakdownResponse;
import com.vintrace.codingchallenge.repository.projection.IBreakdown;
import com.vintrace.codingchallenge.service.BreakdownService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/breakdown")
public class BreakdownController {

    @Autowired
    private BreakdownService breakdownService;

    @GetMapping(value = "/{breakdownType}/{lotCode}", produces = MediaType.APPLICATION_JSON_VALUE)
    @LogAround(message = "Get breakdown")
    public ResponseEntity<BreakdownResponse> getBreakDown(@PathVariable(name = "breakdownType") String breakdownType,
                                                                @PathVariable(name = "lotCode") String lotCode) {
        BreakdownTypeEnum typeEnum = BreakdownTypeEnum.fromType(breakdownType);
        if (typeEnum == null) return ResponseEntity.notFound().build();
        try {
            List<IBreakdown> breakDowns = breakdownService.getBreakDown(typeEnum, lotCode);

            BreakdownResponse response = new BreakdownResponse(typeEnum.type(), breakDowns.stream().map(BreakdownResponse.BreakDown::new)
                    .collect(Collectors.toList()));
            return ResponseEntity.ok(response);
        } catch (IOException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

}
