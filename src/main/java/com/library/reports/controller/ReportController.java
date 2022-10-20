package com.library.reports.controller;
import com.library.reports.dto.ReportDto;
import com.library.reports.service.ReportServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/report")
public class ReportController {
    ReportServiceImpl reportServiceImpl;
    public ReportController(ReportServiceImpl reportServiceImpl) {
        this.reportServiceImpl = reportServiceImpl;
    }

    @GetMapping
    public List<ReportDto> getAllReports() {
        return reportServiceImpl.getAllReports();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReportDto> get(@PathVariable long id) {
        return new ResponseEntity<>(reportServiceImpl.getReport(id), HttpStatus.OK);
    }

    @PostMapping
    public ReportDto add(@RequestBody ReportDto reportDto) {
        return reportServiceImpl.addReport(reportDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(reason = "report deleted",code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        reportServiceImpl.deleteReport(id);
    }
}