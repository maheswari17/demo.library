package com.library.staff.dto;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class StaffDto {
    @NotNull
    private long staffId;
    @NotNull
    private String staffName;

}