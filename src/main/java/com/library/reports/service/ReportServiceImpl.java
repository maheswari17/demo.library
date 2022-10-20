package com.library.reports.service;
import com.library.exceptions.CustomNotFoundException.ReportNotFoundException;
import com.library.reports.dto.ReportDto;
import com.library.reports.model.Report;
import com.library.reports.repository.ReportRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {

    private ReportRepository reportRepository;
    private ModelMapper modelMapper;
    public ReportServiceImpl(ReportRepository reportRepository,ModelMapper modelMapper) {
        this.reportRepository = reportRepository;
        this.modelMapper=modelMapper;
    }
    @Override
    public List<ReportDto> getAllReports() {
        return reportRepository.findAll().stream().map(this::buildReportDto).collect(Collectors.toList());
    }

    @Override
    public ReportDto addReport(ReportDto reportDto) {
        Report report= reportRepository.save(buildReport(reportDto));
        return buildReportDto(report);
    }

    @Override
    public ReportDto getReport(long id) {
        Optional<Report> report = reportRepository.findById(id);
        if(report.isPresent()) {
            return buildReportDto(report.get());
        }throw new ReportNotFoundException("report details not found");
    }

    @Override
    public void deleteReport(long id) {
        try {
            reportRepository.deleteById(id);
        } catch (DataAccessException e) {
            throw new ReportNotFoundException("report with " + id + " doesn't exist");
        }
    }

    public ReportDto buildReportDto(Report report)   {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        ReportDto reportDto = modelMapper.map(report,ReportDto.class);
        return reportDto;
    }

    public Report buildReport(ReportDto reportDto)  {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        Report report = modelMapper.map(reportDto,Report.class);
        return report;
    }
}