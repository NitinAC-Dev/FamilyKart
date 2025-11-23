package com.identify.product.FamilyKart.address.service;

import com.identify.product.FamilyKart.address.payload.AddressDTO;
import com.identify.product.FamilyKart.security.jwt.payload.MessageResponse;
import com.identify.product.FamilyKart.user.model.User;

import java.util.List;

public interface AddressService {
    AddressDTO addUserAddress(AddressDTO addressDTO, User loggedInUser);



    List<AddressDTO> getAllAddress();

    AddressDTO findAddressById(Long addressId);

    List<AddressDTO> getAllUserAddress(Long loggedInUserCartID);

    AddressDTO updateAddressByID(AddressDTO addressDTO,Long addressId);

    MessageResponse deleteAddressByID(Long addressId);
}
