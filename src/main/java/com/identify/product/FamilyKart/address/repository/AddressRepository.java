package com.identify.product.FamilyKart.address.repository;

import com.identify.product.FamilyKart.address.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {

    @Query("SELECT ad FROM Address ad WHERE ad.users.userID=?1")
    List<Address> findListOfUserAddress(Long loggedInUserCartID);
}
