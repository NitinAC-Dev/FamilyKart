package com.identify.product.FamilyKart.roles.model;

import com.identify.product.FamilyKart.constanst.AppRole;
import com.identify.product.FamilyKart.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleID;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_name")
    private AppRole roleName;


    /*@ManyToMany(mappedBy = "setOfRoles")
    private Set<User> users;*/

    public Role(AppRole roleName) {
        this.roleName = roleName;
    }


}
