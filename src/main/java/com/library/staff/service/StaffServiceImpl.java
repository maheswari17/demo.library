package com.library.staff.service;
import com.library.exceptions.CustomNotFoundException.StaffNotFoundException;
import com.library.staff.dto.StaffDto;
import com.library.staff.model.Staff;
import com.library.staff.repository.StaffRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StaffServiceImpl implements StaffService {

    private StaffRepository staffRepository;
    private ModelMapper modelMapper;

    public StaffServiceImpl(StaffRepository staffRepository,ModelMapper modelMapper) {
        this.staffRepository = staffRepository;
        this.modelMapper=modelMapper;
    }

    @Override
    public List<StaffDto> getAllStaffs() {
        return staffRepository.findAll().stream().map(this::buildStaffDto).collect(Collectors.toList());
    }
    @Override
    public StaffDto saveStaff(StaffDto staffDto) {
        Staff staff= staffRepository.save(buildStaff(staffDto));
        return buildStaffDto(staff);
    }

    @Override
    public StaffDto getStaff(long id) {
        Optional<Staff> staff = staffRepository.findById(id);
        if(staff.isPresent()) {
            return buildStaffDto(staff.get());
        }throw new StaffNotFoundException("staff details not found");
    }

    @Override
    public void deleteStaff(long id) {
        try {
            staffRepository.deleteById(id);
        } catch (DataAccessException e) {
            throw new StaffNotFoundException("member with id:" + id + " does not exist");
        }

    }
    public StaffDto buildStaffDto(Staff staff)   {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        StaffDto staffDto = modelMapper.map(staff,StaffDto.class);
        return staffDto;
    }

    public Staff buildStaff(StaffDto staffDto)  {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        Staff staff = modelMapper.map(staffDto,Staff.class);
        return staff;
    }
}