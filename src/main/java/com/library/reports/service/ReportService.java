package com.library.reports.service;
import com.library.reports.dto.ReportDto;
import java.util.List;

public interface ReportService {
    List<ReportDto> getAllReports();
    ReportDto addReport(ReportDto reportDto);
    ReportDto getReport(long id);
    void deleteReport(long id);
}