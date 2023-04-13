package com.erphotel.AuthSecurity.domain;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import java.util.ArrayList;
import lombok.Data;

@Data
@Entity
@Table(name = "employees")
public class LoginDomain implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "employee_id")
    private Long employee_id;
    private String contract_start;
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id")
    private List<RolDomain> rols;

    public LoginDomain() {
        this.rols = new ArrayList<>();
    }

}
