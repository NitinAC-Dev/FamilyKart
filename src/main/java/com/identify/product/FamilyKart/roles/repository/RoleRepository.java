package com.identify.product.FamilyKart.roles.repository;

import com.identify.product.FamilyKart.constanst.AppRole;
import com.identify.product.FamilyKart.roles.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {

    Optional<Role> findByRoleName(AppRole appRole);
}
