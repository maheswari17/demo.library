package com.library.staff.controller;
import com.library.staff.dto.StaffDto;
import com.library.staff.service.StaffServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/staff")
public class StaffController {
    private StaffServiceImpl staffServiceImpl;
    public StaffController(StaffServiceImpl staffServiceImpl) {
        this.staffServiceImpl = staffServiceImpl;
    }

    @GetMapping
    public List<StaffDto> getAllStaffs() {
        return staffServiceImpl.getAllStaffs();
    }

    @GetMapping("/{id}")
    public ResponseEntity<StaffDto> get(@PathVariable Integer id) {
        return new ResponseEntity<>(staffServiceImpl.getStaff(id), HttpStatus.OK);
    }

    @PostMapping
    public StaffDto add(@RequestBody StaffDto staffDto) {
        return staffServiceImpl.saveStaff(staffDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(reason = "staff deleted",code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        staffServiceImpl.deleteStaff(id);
    }
}