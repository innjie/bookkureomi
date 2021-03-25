package com.inyoon.bookkureomi.mapper;

import com.inyoon.bookkureomi.domain.Address;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AddressMapper {
    public List<Address> getAddressList(int userNo);
    public Address getAddress(int addrNo);
    public void insertAdress(Address address);
    public void updateAddress(int addressNo, Address address);
    public void deleteAddress(int addressNo);
}
