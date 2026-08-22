package com.vellora.vellora_backend.service;

import com.vellora.vellora_backend.repository.ReportRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {

    private final ReportRepository reportRepository;

    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public List<ReportRepository.TopSongView> getTopSongs(int limit) {
        return reportRepository.topSongs(limit);
    }

    public List<ReportRepository.TopArtistView> getTopArtists(int limit) {
        return reportRepository.topArtists(limit);
    }
}