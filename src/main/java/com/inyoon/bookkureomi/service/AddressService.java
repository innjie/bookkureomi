package com.inyoon.bookkureomi.service;

import com.inyoon.bookkureomi.domain.Address;
import com.inyoon.bookkureomi.mapper.AddressMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {
    @Autowired
    private AddressMapper addressMapper;

    public List<Address> getAddressList(int userNo) {
        return addressMapper.getAddressList(userNo);
    }
    public Address getAddress(int addrNo) {
        return addressMapper.getAddress(addrNo);
    }
    public void insertAddress(Address address) {
        addressMapper.insertAdress(address);
    }
    public void updateAddress(int addressNo, Address address) {
        addressMapper.updateAddress(addressNo, address);
    }
    public void deleteAddress(int addressNo) {
        addressMapper.deleteAddress(addressNo);
    }

}
