package com.library.staff.service;
import com.library.staff.dto.StaffDto;
import java.util.List;

public interface StaffService {
    List<StaffDto> getAllStaffs();
    StaffDto saveStaff(StaffDto staffDto);
    StaffDto getStaff(long id);
    void deleteStaff(long id);
}