package com.inyoon.bookkureomi.address;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inyoon.bookkureomi.domain.Address;

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

    public int countAddressList(int userNo) { return addressMapper.countAddressList(userNo); }
}
