package com.identify.product.FamilyKart.address.service;

import com.identify.product.FamilyKart.address.model.Address;
import com.identify.product.FamilyKart.address.payload.AddressDTO;
import com.identify.product.FamilyKart.address.repository.AddressRepository;
import com.identify.product.FamilyKart.exceptionhandling.ResourceNotFoundException;
import com.identify.product.FamilyKart.security.jwt.payload.MessageResponse;
import com.identify.product.FamilyKart.user.model.User;
import com.identify.product.FamilyKart.utils.AuthUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {


    @Autowired
    AuthUtils authUtils;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    AddressRepository addressRepository;

    @Override
    public AddressDTO addUserAddress(AddressDTO addressDTO, User loggedInUser) {

        Address address=modelMapper.map(addressDTO, Address.class);

        List<Address> addresses = loggedInUser.getAddresses();
        addresses.add(address);


        loggedInUser.setAddresses(addresses);


        address.setUsers(loggedInUser);


        Address savedAddress=addressRepository.save(address);



        return modelMapper.map(savedAddress, AddressDTO.class);
    }

    @Override
    public List<AddressDTO> getAllAddress() {
        List<Address> addressListl = addressRepository.findAll();
        List<AddressDTO> listaddress=addressListl.stream().map(address -> modelMapper.map(address,AddressDTO.class)).toList();

        return listaddress;
    }

    @Override
    public AddressDTO findAddressById(Long addressId) {

        Address address = addressRepository.findById(addressId).orElseThrow(() -> new ResourceNotFoundException("Address", "AddressID", addressId));



        return modelMapper.map(address,AddressDTO.class);
    }

    @Override
    public List<AddressDTO> getAllUserAddress(Long loggedInUserCartID) {


        List<Address> address =addressRepository.findListOfUserAddress(loggedInUserCartID);
        List<AddressDTO> listaddress=address.stream().map(addr -> modelMapper.map(addr,AddressDTO.class)).toList();
        return listaddress;
    }

    @Override
    public AddressDTO updateAddressByID(AddressDTO addressDTO,Long addressId) {

        Address updatedAddress = modelMapper.map(addressDTO, Address.class);
        Address address = addressRepository.findById(addressId).orElseThrow(() -> new ResourceNotFoundException("Address", "AddressID", addressId));

        address.setCity(updatedAddress.getCity());
        address.setState(updatedAddress.getState());
        address.setCountry(updatedAddress.getCountry());
        address.setZipCode(updatedAddress.getZipCode());
        address.setBuildingName(updatedAddress.getBuildingName());
        address.setStreet(updatedAddress.getStreet());

        Address savedAddress = addressRepository.save(address);
        //how to save this new address to user's address list?

        User users = address.getUsers();
        List<Address> usersAddresses = users.getAddresses();

        for (int i = 0; i < usersAddresses.size(); i++) {
            if (usersAddresses.get(i).getAddressID().equals(addressId)) {
                usersAddresses.remove(i);
                usersAddresses.add(savedAddress);
                break;
            }
        }


        return modelMapper.map(savedAddress, AddressDTO.class);



    }

    @Override
    public MessageResponse deleteAddressByID(Long addressId) {

        Address address = addressRepository.findById(addressId).orElseThrow(() -> new ResourceNotFoundException("Address", "AddressID", addressId));
        User users = address.getUsers();
        List<Address> usersAddresses = users.getAddresses();

        for (int i = 0; i < usersAddresses.size(); i++) {
            if (usersAddresses.get(i).getAddressID().equals(addressId)) {
                usersAddresses.remove(i);
                break;
            }
        }

         addressRepository.delete(address);


        return new MessageResponse("Address deleted successfully!");
    }


}
