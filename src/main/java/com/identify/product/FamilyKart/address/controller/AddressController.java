package com.identify.product.FamilyKart.address.controller;

import com.identify.product.FamilyKart.address.payload.AddressDTO;
import com.identify.product.FamilyKart.address.service.AddressService;
import com.identify.product.FamilyKart.security.jwt.payload.MessageResponse;
import com.identify.product.FamilyKart.user.model.User;
import com.identify.product.FamilyKart.utils.AuthUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @Autowired
    private AuthUtils authUtils;

    @Tag(name = "Address api's", description = "APIs for managing user addresses")
    @PostMapping("address")
    public ResponseEntity<AddressDTO> createAddress(@Valid @RequestBody AddressDTO addressDTO) {
        User loggedInUser = authUtils.getLoggedInUser();
        AddressDTO userAddress=addressService.addUserAddress(addressDTO,loggedInUser);
        return ResponseEntity.ok(userAddress);
    }

    @Tag(name = "Address api's", description = "APIs for managing user addresses")
    @GetMapping("getAllAddresses")
    public ResponseEntity<List<AddressDTO>> getAllAddresses() {

        List<AddressDTO> listofAlladdressDTO=addressService.getAllAddress();
        return ResponseEntity.status(HttpStatus.OK).body(listofAlladdressDTO);
    }
    @Tag(name = "Address api's", description = "APIs for managing user addresses")
    @GetMapping("getUserAddress")
    public ResponseEntity<List<AddressDTO>> getUserAddresses() {

        Long loggedInUserCartID = authUtils.getLoggedInUser().getUserID();
        List<AddressDTO> listofAlladdressDTO=addressService.getAllUserAddress(loggedInUserCartID);
        return ResponseEntity.status(HttpStatus.OK).body(listofAlladdressDTO);
    }
    @Tag(name = "Address api's", description = "APIs for managing user addresses")
    @GetMapping("getAddresses/{addressId}")
    public ResponseEntity<AddressDTO> getSpecificAddress(@PathVariable Long addressId) {

        AddressDTO listofAlladdressDTO=addressService.findAddressById(addressId);
        return ResponseEntity.status(HttpStatus.OK).body(listofAlladdressDTO);
    }
    @Tag(name = "Address api's", description = "APIs for managing user addresses")
    @PutMapping("update/Addresses/{addressId}")
    public ResponseEntity<AddressDTO> updateSpecificAddress(@Valid @RequestBody AddressDTO addressDTO,@PathVariable Long addressId) {

        AddressDTO listofAlladdressDTO=addressService.updateAddressByID(addressDTO,
                addressId);
        return ResponseEntity.status(HttpStatus.OK).body(listofAlladdressDTO);
    }
    @Tag(name = "Address api's", description = "APIs for managing user addresses")
    @DeleteMapping("delete/Address/{addressId}")
    public ResponseEntity<MessageResponse> deleteSpecificAddress(@PathVariable Long addressId) {

        MessageResponse listofAlladdressDTO=addressService.deleteAddressByID(
                addressId);
        return ResponseEntity.status(HttpStatus.OK).body(listofAlladdressDTO);
    }
}
