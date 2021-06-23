package com.inyoon.bookkureomi.image;



import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.inyoon.bookkureomi.MD5Generator;
import com.inyoon.bookkureomi.domain.Image;
import com.inyoon.bookkureomi.domain.Sale;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;



@Controller
@RequestMapping("/book")
@Api(value = "ImageController", description = "이미지 파일 API")
public class ImageController{

	@Autowired
	private ImageService imageService;

	
	@ApiOperation(value="사진 파일 가져오기", notes="사진 파일을 가져온다.")
	@ResponseBody //@RestController 시 생략 가능
	@GetMapping(value = "/image", produces = MediaType.IMAGE_JPEG_VALUE)
	public  byte[] image(@RequestParam("path") String path) {

		byte[] data = new byte[0];
		
		try {
			InputStream inputStream = new FileInputStream(path);
	        long fileSize = new File(path).length();
	        data = new byte[(int) fileSize];
	        inputStream.read(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return data;
    }
	
	@ApiOperation(value="사진 파일 추가 ", notes="사진 파일을 추가한다.")
	@ResponseBody //@RestController 시 생략 가능
	@PostMapping("/image/create")
	public Map<String, Object> createImage(
			@RequestParam("saleNo") int saleNo,
			@RequestParam("file") List<MultipartFile> files,
			HttpServletRequest req) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		if(!SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")) {
			List<Image> imageList = new ArrayList<Image>();
			for(int i = 0; i < files.size(); i++) {
				if(!files.get(i).getOriginalFilename().equals("")) {
					String date = String.valueOf((new Date()).getYear()+1900)
									+String.valueOf((new Date()).getMonth()+1)
									+String.valueOf((new Date()).getDate())
									+String.valueOf((new Date()).getHours())
									+String.valueOf((new Date()).getMinutes())
									+String.valueOf((new Date()).getSeconds());
					String originName = date+files.get(i).getOriginalFilename();
		            String filename = new MD5Generator(originName).toString();
		            
		            String savePath = System.getProperty("user.dir") + "\\files";
		            if (!new File(savePath).exists()) {
		                try{
		                    new File(savePath).mkdir();
		                }
		                catch(Exception e){
		                    e.getStackTrace();
		                }
		            }
		            String path = savePath + "\\" + filename;
		            files.get(i).transferTo(new File(path));

		            Sale sale = new Sale();
		            sale.setSaleNo(saleNo);
		            
		            Image image = new Image();
		            image.setOriginName(originName);
		            image.setName(filename);
		            image.setFilePath(path);
		            image.setSale(sale);
		            image.setImageNo(imageService.getImageNo());
		            
		            imageList.add(image);
				}
			}
			
			if(files.size() > 0) {
				imageService.createSaleImage(imageList);
				
				map.put("result", "success");
			} else {
				map.put("result", "fail");
				map.put("reason", "추가할 파일이 없습니다.");
			}
		} else {
			map.put("result", "fail");
			map.put("reason", "로그인 후 이용이 가능합니다.");
		}
		
        return map;
	}
	
	
	@ApiOperation(value="사진 파일 수정 ", notes="사진 파일을 수정한다.")
	@ResponseBody //@RestController 시 생략 가능
	@PostMapping("/image/update")
	public Map<String, Object> updateImage(
			@RequestParam("imageNo") int imageNo,
			@RequestParam("filePath") String filePath,
			@RequestParam("file") List<MultipartFile> files,
			HttpServletRequest req) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		if(!SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")) {
			List<Image> imageList = new ArrayList<Image>();
			for(int i = 0; i < files.size(); i++) {
				if(!files.get(i).getOriginalFilename().equals("")) {
					String date = String.valueOf((new Date()).getYear()+1900)
									+String.valueOf((new Date()).getMonth()+1)
									+String.valueOf((new Date()).getDate())
									+String.valueOf((new Date()).getHours())
									+String.valueOf((new Date()).getMinutes())
									+String.valueOf((new Date()).getSeconds());
					String originName = date+files.get(i).getOriginalFilename();
		            String filename = new MD5Generator(originName).toString();
		            
		            String savePath = System.getProperty("user.dir") + "\\files";
		            if (!new File(savePath).exists()) {
		                try{
		                    new File(savePath).mkdir();
		                }
		                catch(Exception e){
		                    e.getStackTrace();
		                }
		            }
		            String path = savePath + "\\" + filename;
		            files.get(i).transferTo(new File(path));

		            Image image = new Image();
		            image.setOriginName(originName);
		            image.setName(filename);
		            image.setFilePath(path);
		            image.setImageNo(imageNo);
		            
		            imageList.add(image);
				}
			}
			
			if(files.size() > 0) {
				imageService.updateImage(imageList);
				
				File deleteFile = new File(filePath);
		        if(deleteFile.exists()) {
		            deleteFile.delete(); 
		        }
				
				map.put("result", "success");
			} else {
				map.put("result", "fail");
				map.put("reason", "수정할 파일이 없습니다.");
			}
		} else {
			map.put("result", "fail");
			map.put("reason", "로그인 후 이용이 가능합니다.");
		}
		
        return map;
	}
	
	@ApiOperation(value="사진 파일 삭제 ", notes="사진 파일을 삭제한다.")
	@ResponseBody //@RestController 시 생략 가능
	@DeleteMapping("/image/delete")
	public Map<String, Object> deleteImage(
			@RequestParam("filePath") String filePath,
			@RequestParam("imageNo") int imageNo) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		if(!SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")) {
			if(imageNo != -1) {
				imageService.deleteImage(imageNo);
		        
		        File deleteFile = new File(filePath);
		        if(deleteFile.exists()) {
		            deleteFile.delete(); 
		        }
				
				map.put("result", "success");
			} else {
				map.put("result", "fail");
				map.put("reason", "삭제할 파일이 없습니다.");
			}
		} else {
			map.put("result", "fail");
			map.put("reason", "로그인 후 이용이 가능합니다.");
		}
		
        return map;
	}
}

