package com.library.staff.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.library.exceptions.CustomNotFoundException.StaffNotFoundException;
import com.library.reports.model.Report;
import com.library.staff.dto.StaffDto;
import com.library.staff.model.Staff;
import com.library.staff.repository.StaffRepository;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.internal.InheritingConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {StaffServiceImpl.class})
@ExtendWith(SpringExtension.class)
class StaffServiceImplTest {
    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private StaffRepository staffRepository;

    @Autowired
    private StaffServiceImpl staffServiceImpl;


    @Test
    void testGetAllStaffs() {
        when(this.staffRepository.findAll()).thenReturn(new ArrayList<>());
        assertTrue(this.staffServiceImpl.getAllStaffs().isEmpty());
        verify(this.staffRepository).findAll();
    }


    @Test
    void testGetAllStaffs2() {
        Staff staff = new Staff();
        staff.setReport(new ArrayList<>());
        staff.setStaffId(123L);
        staff.setStaffName(" banu");

        ArrayList<Staff> staffList = new ArrayList<>();
        staffList.add(staff);
        when(this.staffRepository.findAll()).thenReturn(staffList);

        StaffDto staffDto = new StaffDto();
        staffDto.setStaffId(123L);
        staffDto.setStaffName(" banu");
        when(this.modelMapper.map((Object) any(), (Class<StaffDto>) any())).thenReturn(staffDto);
        when(this.modelMapper.getConfiguration()).thenReturn(new InheritingConfiguration());
        assertEquals(1, this.staffServiceImpl.getAllStaffs().size());
        verify(this.staffRepository).findAll();
        verify(this.modelMapper).map((Object) any(), (Class<StaffDto>) any());
        verify(this.modelMapper).getConfiguration();
    }


    @Test
    void testGetAllStaffs3() {
        Staff staff = new Staff();
        staff.setReport(new ArrayList<>());
        staff.setStaffId(123L);
        staff.setStaffName(" banu");

        ArrayList<Staff> staffList = new ArrayList<>();
        staffList.add(staff);
        when(this.staffRepository.findAll()).thenReturn(staffList);
        when(this.modelMapper.map((Object) any(), (Class<StaffDto>) any()))
                .thenThrow(new StaffNotFoundException("Exception"));
        when(this.modelMapper.getConfiguration()).thenReturn(new InheritingConfiguration());
        assertThrows(StaffNotFoundException.class, () -> this.staffServiceImpl.getAllStaffs());
        verify(this.staffRepository).findAll();
        verify(this.modelMapper).map((Object) any(), (Class<StaffDto>) any());
        verify(this.modelMapper).getConfiguration();
    }

    @Test
    void testSaveStaff() {
        Staff staff = new Staff();
        staff.setReport(new ArrayList<>());
        staff.setStaffId(123L);
        staff.setStaffName(" banu");
        when(this.staffRepository.save((Staff) any())).thenReturn(staff);
        when(this.modelMapper.map((Object) any(), (Class<Object>) any()))
                .thenThrow(new StaffNotFoundException("Exception"));
        when(this.modelMapper.getConfiguration()).thenReturn(new InheritingConfiguration());

        StaffDto staffDto = new StaffDto();
        staffDto.setStaffId(123L);
        staffDto.setStaffName(" banu");
        assertThrows(StaffNotFoundException.class, () -> this.staffServiceImpl.saveStaff(staffDto));
        verify(this.modelMapper).map((Object) any(), (Class<Staff>) any());
        verify(this.modelMapper).getConfiguration();
    }


    @Test
    void testSaveStaff2() {
        Staff staff = mock(Staff.class);
        doNothing().when(staff).setReport((java.util.List<Report>) any());
        doNothing().when(staff).setStaffId(anyLong());
        doNothing().when(staff).setStaffName((String) any());
        staff.setReport(new ArrayList<>());
        staff.setStaffId(123L);
        staff.setStaffName(" banu");
        when(this.staffRepository.save((Staff) any())).thenReturn(staff);
        when(this.modelMapper.map((Object) any(), (Class<Object>) any())).thenReturn(null);
        when(this.modelMapper.getConfiguration()).thenReturn(new InheritingConfiguration());

        StaffDto staffDto = new StaffDto();
        staffDto.setStaffId(123L);
        staffDto.setStaffName(" banu");
        assertNull(this.staffServiceImpl.saveStaff(staffDto));
        verify(this.staffRepository).save((Staff) any());
        verify(staff).setReport((java.util.List<Report>) any());
        verify(staff).setStaffId(anyLong());
        verify(staff).setStaffName((String) any());
        verify(this.modelMapper, atLeast(1)).map((Object) any(), (Class<Staff>) any());
        verify(this.modelMapper, atLeast(1)).getConfiguration();
    }


    @Test
    void testGetStaff() {
        Staff staff = new Staff();
        staff.setReport(new ArrayList<>());
        staff.setStaffId(123L);
        staff.setStaffName(" banu");
        Optional<Staff> ofResult = Optional.of(staff);
        when(this.staffRepository.findById((Long) any())).thenReturn(ofResult);

        StaffDto staffDto = new StaffDto();
        staffDto.setStaffId(123L);
        staffDto.setStaffName(" banu");
        when(this.modelMapper.map((Object) any(), (Class<StaffDto>) any())).thenReturn(staffDto);
        when(this.modelMapper.getConfiguration()).thenReturn(new InheritingConfiguration());
        assertSame(staffDto, this.staffServiceImpl.getStaff(123L));
        verify(this.staffRepository).findById((Long) any());
        verify(this.modelMapper).map((Object) any(), (Class<StaffDto>) any());
        verify(this.modelMapper).getConfiguration();
    }

    @Test
    void testGetStaff2() {
        Staff staff = new Staff();
        staff.setReport(new ArrayList<>());
        staff.setStaffId(123L);
        staff.setStaffName(" banu");
        Optional<Staff> ofResult = Optional.of(staff);
        when(this.staffRepository.findById((Long) any())).thenReturn(ofResult);
        when(this.modelMapper.map((Object) any(), (Class<StaffDto>) any()))
                .thenThrow(new StaffNotFoundException("Exception"));
        when(this.modelMapper.getConfiguration()).thenReturn(new InheritingConfiguration());
        assertThrows(StaffNotFoundException.class, () -> this.staffServiceImpl.getStaff(123L));
        verify(this.staffRepository).findById((Long) any());
        verify(this.modelMapper).map((Object) any(), (Class<StaffDto>) any());
        verify(this.modelMapper).getConfiguration();
    }



    @Test
    void testDeleteStaff() {
        doNothing().when(this.staffRepository).deleteById((Long) any());
        this.staffServiceImpl.deleteStaff(123L);
        verify(this.staffRepository).deleteById((Long) any());
        assertTrue(this.staffServiceImpl.getAllStaffs().isEmpty());
    }


    @Test
    void testDeleteStaff2() {
        doThrow(new StaffNotFoundException("Exception")).when(this.staffRepository).deleteById((Long) any());
        assertThrows(StaffNotFoundException.class, () -> this.staffServiceImpl.deleteStaff(123L));
        verify(this.staffRepository).deleteById((Long) any());
    }

    @Test
    void testBuildStaffDto() {
        StaffDto staffDto = new StaffDto();
        staffDto.setStaffId(123L);
        staffDto.setStaffName(" banu");
        when(this.modelMapper.map((Object) any(), (Class<StaffDto>) any())).thenReturn(staffDto);
        when(this.modelMapper.getConfiguration()).thenReturn(new InheritingConfiguration());

        Staff staff = new Staff();
        staff.setReport(new ArrayList<>());
        staff.setStaffId(123L);
        staff.setStaffName(" banu");
        assertSame(staffDto, this.staffServiceImpl.buildStaffDto(staff));
        verify(this.modelMapper).map((Object) any(), (Class<StaffDto>) any());
        verify(this.modelMapper).getConfiguration();
    }


    @Test
    void testBuildStaffDto2() {
        when(this.modelMapper.map((Object) any(), (Class<StaffDto>) any()))
                .thenThrow(new StaffNotFoundException("Exception"));
        when(this.modelMapper.getConfiguration()).thenReturn(new InheritingConfiguration());

        Staff staff = new Staff();
        staff.setReport(new ArrayList<>());
        staff.setStaffId(123L);
        staff.setStaffName(" banu");
        assertThrows(StaffNotFoundException.class, () -> this.staffServiceImpl.buildStaffDto(staff));
        verify(this.modelMapper).map((Object) any(), (Class<StaffDto>) any());
        verify(this.modelMapper).getConfiguration();
    }

    @Test
    void testBuildStaff() {
        Staff staff = new Staff();
        staff.setReport(new ArrayList<>());
        staff.setStaffId(123L);
        staff.setStaffName(" banu");
        when(this.modelMapper.map((Object) any(), (Class<Staff>) any())).thenReturn(staff);
        when(this.modelMapper.getConfiguration()).thenReturn(new InheritingConfiguration());

        StaffDto staffDto = new StaffDto();
        staffDto.setStaffId(123L);
        staffDto.setStaffName(" banu");
        assertSame(staff, this.staffServiceImpl.buildStaff(staffDto));
        verify(this.modelMapper).map((Object) any(), (Class<Staff>) any());
        verify(this.modelMapper).getConfiguration();
    }


    @Test
    void testBuildStaff2() {
        when(this.modelMapper.map((Object) any(), (Class<Staff>) any())).thenThrow(new StaffNotFoundException("Exception"));
        when(this.modelMapper.getConfiguration()).thenReturn(new InheritingConfiguration());

        StaffDto staffDto = new StaffDto();
        staffDto.setStaffId(123L);
        staffDto.setStaffName(" banu");
        assertThrows(StaffNotFoundException.class, () -> this.staffServiceImpl.buildStaff(staffDto));
        verify(this.modelMapper).map((Object) any(), (Class<Staff>) any());
        verify(this.modelMapper).getConfiguration();
    }

}

