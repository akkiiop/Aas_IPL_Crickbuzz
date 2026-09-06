package com.akshay.iplcrickbuzz.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.akshay.iplcrickbuzz.dto.DashboardResponseDTO;
import com.akshay.iplcrickbuzz.service.DashboardService;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(
            DashboardService dashboardService) {

        this.dashboardService = dashboardService;
    }

    @GetMapping
    public ResponseEntity<DashboardResponseDTO> getDashboard() {

        DashboardResponseDTO dashboard =
                dashboardService.getDashboardData();

        return ResponseEntity.ok(dashboard);
    }
}