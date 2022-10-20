package com.library.staff.model;
import com.library.reports.model.Report;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "staff")
public class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(name = "staff_id")
    private long staffId;
    @NotNull
    private String staffName;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_staff_id", referencedColumnName = "staff_id")
    private List<Report> report;




}