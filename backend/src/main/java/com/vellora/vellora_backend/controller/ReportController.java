package com.vellora.vellora_backend.controller;

import com.vellora.vellora_backend.repository.ReportRepository;
import com.vellora.vellora_backend.service.ReportService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/top-songs")
    public List<ReportRepository.TopSongView> topSongs(@RequestParam(defaultValue = "10") int limit) {
        return reportService.getTopSongs(limit);
    }

    @GetMapping("/top-artists")
    public List<ReportRepository.TopArtistView> topArtists(@RequestParam(defaultValue = "10") int limit) {
        return reportService.getTopArtists(limit);
    }
}