package com.inyoon.bookkureomi.image;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inyoon.bookkureomi.domain.Image;


@Service
public class ImageService {
	@Autowired
	private ImageMapper imageMapper;

	//새로운 이미지번호 가져오기
	public int getImageNo() {
		return imageMapper.getImageNo();
	}
	
	//해당 판매번호의 사진 파일 확인 
	public List<Image> getImageList(Map<String, Object> paramMap) {
		return imageMapper.getImageList(paramMap);
	}
	

	//일반 중고판매 사진파일 추가
	public void createSaleImage(List<Image> imageList){
		for(int i=0; i < imageList.size(); i++) {
			imageMapper.createSaleImage(imageList.get(i));		
		}
	}
	
	//경매 사진파일 추가
	public void createAuctionImage(List<Image> imageList){
		for(int i=0; i < imageList.size(); i++) {
			imageMapper.createAuctionImage(imageList.get(i));		
		}
	}	
	
	//사진파일 수정
	public void updateImage(List<Image> imageList){
		for(int i=0; i < imageList.size(); i++) {
			imageMapper.updateImage(imageList.get(i));		
		}
	}
	
	//사진파일 삭제
	public void deleteImage(int imageNo){
		imageMapper.deleteImage(imageNo);
	}
	
	//판매의 전체 사진파일 삭제
	public void deleteAllSaleImage(int saleNo){
		imageMapper.deleteAllSaleImage(saleNo);
	}	
}
