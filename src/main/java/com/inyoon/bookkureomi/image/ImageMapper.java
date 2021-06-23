package com.inyoon.bookkureomi.image;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.inyoon.bookkureomi.domain.Image;

@Mapper
public interface ImageMapper {
	public int getImageNo();
	
	public List<Image> getImageList(Map<String, Object> paramMap);
	
	public void createSaleImage(Image image);
	public void createAuctionImage(Image image);
	public void updateImage(Image image);
	public void deleteImage(int imageNo);
	public void deleteAllSaleImage(int saleNo);
}
