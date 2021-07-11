package com.inyoon.bookkureomi.address;

import org.apache.ibatis.annotations.Mapper;

import com.inyoon.bookkureomi.domain.Address;

import java.util.List;

@Mapper
public interface AddressMapper {
    public List<Address> getAddressList(int userNo);
    public Address getAddress(int addrNo);
    public void insertAddress(Address address);
    public void updateAddress(Address address);
    public void deleteAddress(int addressNo);
    public int countAddressList(int userNo);
}
