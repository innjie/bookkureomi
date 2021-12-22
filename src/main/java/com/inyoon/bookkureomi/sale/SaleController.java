package com.inyoon.bookkureomi.sale;

import java.util.List;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.inyoon.bookkureomi.user.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
import com.inyoon.bookkureomi.delivery.DeliveryService;
import com.inyoon.bookkureomi.domain.Delivery;
import com.inyoon.bookkureomi.domain.Genre;
import com.inyoon.bookkureomi.domain.Image;
import com.inyoon.bookkureomi.domain.OrderDetail;
import com.inyoon.bookkureomi.domain.Sale;
import com.inyoon.bookkureomi.domain.User;
import com.inyoon.bookkureomi.genre.GenreService;
import com.inyoon.bookkureomi.image.ImageService;
import com.inyoon.bookkureomi.order.OrderService;
import com.inyoon.bookkureomi.user.MyAuthentication;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;



@Controller
@RequestMapping("/book")
@Api(value = "SaleController", description = "일반 중고 거래 API")
public class SaleController{
	
	@Autowired
	private SaleService saleService;
	@Autowired
	private GenreService genreService;
	@Autowired
	private DeliveryService deliveryService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private ImageService imageService;
	
	
	@ApiOperation(value="중고거래 화면 이동", notes="중고거래 목록화면으로 이동한다.")
	@GetMapping("/sale/view")
    public String viewSale() {		
        return "sale/sale";
    }
	
	@ApiOperation(value="중고거래 목록", notes="중고거래 전체 목록을 보여준다.")
	@ResponseBody //@RestController 시 생략 가능
	@GetMapping("/sale/list")
	public Map<String, Object> listSale(
			@RequestParam("pageNo") int pageNo) {

		int showCnt = 12;	//보여주는 개수
		int saleCnt = saleService.countSaleList();	//리스트 개수
		int pageCnt = 0;
		
		List<Sale> saleList = new ArrayList<>();	

		if(saleCnt > 0) {
			pageCnt = (saleCnt % showCnt == 0) ? (saleCnt / showCnt) : (saleCnt / showCnt + 1);		//페이지 개수
			int start = 1+(showCnt*(pageNo-1));
			int end = showCnt+(showCnt*(pageNo-1));
			
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("start", start);
			paramMap.put("end", end);
			
			saleList = saleService.getSaleList(paramMap);
		}
	
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("saleList", saleList);
		map.put("saleCnt", saleCnt);
		map.put("pageCnt", pageCnt);
		
        return map;
    }
	
	@ApiOperation(value="중고거래 찾기", notes="제목/장르로 중고거래를 찾는다.")
	@ResponseBody //@RestController 시 생략 가능
	@GetMapping("/sale/find")
	public Map<String, Object> findSale(
			@RequestParam("title") String title,
			@RequestParam("genre") String genre,
			@RequestParam("pageNo") int pageNo) {

		Map<String, Object> paramMap = new HashMap<String, Object>();
		if(!title.equals("") && title != null) {
			paramMap.put("title", title);
		} else if(!genre.equals("") && genre != null) {
			paramMap.put("genreType", genre);
		} 
		
		int showCnt = 12;	//보여주는 개수
		int saleCnt = saleService.countFindSaleList(paramMap);	//리스트 개수
		int pageCnt = 0;
		
		List<Sale> saleList = new ArrayList<>();
		
		if(saleCnt > 0) {
			pageCnt = (saleCnt % showCnt == 0) ? (saleCnt / showCnt) : (saleCnt / showCnt + 1);		//페이지 개수
			int start = 1+(showCnt*(pageNo-1));
			int end = showCnt+(showCnt*(pageNo-1));
			
			paramMap.put("start", start);
			paramMap.put("end", end);			
			
			saleList = saleService.findSaleList(paramMap);
		}
	
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("saleList", saleList);
		map.put("saleCnt", saleCnt);
		map.put("pageCnt", pageCnt);
		
        return map;
    }
	
	@ApiOperation(value="중고 책 상세 보기", notes="판매되는 중고 책을 상세히 본다.")
	@ResponseBody //@RestController 시 생략 가능
	@GetMapping("/sale/detail")
	public Map<String, Object> detailSale(
				@RequestParam("saleNo") int saleNo) throws Exception {

		int userNo = -1;
		
//		if(!SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")) {
//			//user
//			MyAuthentication authentication = (MyAuthentication) SecurityContextHolder.getContext().getAuthentication();
//			User user = principal.getUser();
//			userNo = user.getUserNo();
//		}
//
		Sale sale = new Sale();
		sale = saleService.getSale(saleNo);
				
		Delivery delivery = null;		
		OrderDetail orderDetail = new OrderDetail();
		orderDetail = orderService.getOrderBySale(saleNo);	//주문 정보 확인
				
		if(orderDetail != null) {
			delivery = deliveryService.getDelivery(orderDetail.getOdNo());	//배송 정보 확인
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sale", sale);
		map.put("delivery", delivery);
		map.put("orderDetail", orderDetail);
		map.put("isSeller", userNo == sale.getUser().getUserNo());
		
        return map;
	}
	
	
	@ApiOperation(value="판매 책 등록 ", notes="중고 책을 판매한다.")
	@ResponseBody //@RestController 시 생략 가능
	@PostMapping("/sale/create")
	public Map<String, Object> createSale(
			@RequestParam("title") String title,
			@RequestParam("publisher") String publisher,
			@RequestParam("salePrice") int salePrice,
			@RequestParam("info") String info,
			@RequestParam("costPrice") int costPrice,
			//@RequestParam("image") String image,
			@RequestParam("author") String author,
			@RequestParam("genreType") String genreType,
			@RequestParam("file") List<MultipartFile> files,
			@AuthenticationPrincipal Login principal,
			HttpServletRequest req) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();

		if(!SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")) {
			//user
			MyAuthentication authentication = (MyAuthentication) SecurityContextHolder.getContext().getAuthentication(); 
			User user = principal.getUser();
		
			Sale sale = new Sale();
			Genre genre = genreService.getGenreByName(genreType);
			
			sale.setSaleNo(saleService.getSaleNo());
			//sale.setImage(image);
			sale.setTitle(title);
			sale.setPublisher(publisher);
			sale.setSalePrice(salePrice);
			sale.setInfo(info);
			sale.setCostPrice(costPrice);
			sale.setAuthor(author);
			sale.setUser(user);
			sale.setGenre(genre);
			
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
		            String filePath = savePath + "\\" + filename;
		            files.get(i).transferTo(new File(filePath));

		            Image image = new Image();
		            image.setOriginName(originName);
		            image.setName(filename);
		            image.setFilePath(filePath);
		            image.setSale(sale);
		            image.setImageNo(imageService.getImageNo());
		            
		            imageList.add(image);
				}
			}
			
			saleService.saleBook(sale, imageList);
			
			map.put("result", "success");
			map.put("saleNo", sale.getSaleNo());
		} else {
			map.put("result", "fail");
			map.put("reason", "로그인 후 이용이 가능합니다.");
		}
		
        return map;
	}
		
	@ApiOperation(value="판매 등록 책 수정 ", notes="판매하는 중고 책을 수정한다.")
	@ResponseBody //@RestController 시 생략 가능
	@PutMapping("/sale/update")
	public Map<String, Object> updateSale(
			@RequestParam("saleNo") String saleNo,
			//@RequestParam("image") String image,
			@RequestParam("salePrice") String salePrice,
			@RequestParam("info") String info) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		if(!SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")) {
			Sale sale = new Sale();
			sale.setSaleNo(Integer.parseInt(saleNo));
			//sale.setImage(image);
			sale.setSalePrice(Integer.parseInt(salePrice));
			sale.setInfo(info);
			
			saleService.updateSale(sale);
		
			map.put("result", "success");
		} else {
			map.put("result", "fail");
			map.put("reason", "로그인 후 이용이 가능합니다.");
		}
		
        return map;
	}
	
	@ApiOperation(value="판매 등록 책 삭제 ", notes="판매하는 중고 책을 삭제한다.")
	@ResponseBody //@RestController 시 생략 가능
	@DeleteMapping("/sale/delete")
	public Map<String, Object> deleteSale(
			@RequestParam("saleNo") String saleNo) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		if(!SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")) {
			saleService.deleteSale(Integer.parseInt(saleNo));
			
			map.put("result", "success");
		} else {
			map.put("result", "fail");
			map.put("reason", "로그인 후 이용이 가능합니다.");
		}
		
        return map;
	}
	
	
	@ApiOperation(value="나의 판매 화면 이동", notes="나의 판매 화면으로 이동한다.")
	@GetMapping("/mypage/sale/view")
    public String viewMySale() {	
        return "mypage/mysale";
    }
	
	@ApiOperation(value="나의 판매 목록", notes="나의 판매 전체 목록을 보여준다.")
	@ResponseBody //@RestController 시 생략 가능
	@GetMapping("/mypage/sale/list")
	public Map<String, Object> listMySale(
			@RequestParam("pageNo") int pageNo,
			@AuthenticationPrincipal Login principal) {
		
		int showCnt = 12;	//보여주는 개수
		int saleCnt = 0;	//리스트 개수
		int pageCnt = 0;
		
		List<Sale> saleList = new ArrayList<>();
		
		if(!SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")) {
			//user
			MyAuthentication authentication = (MyAuthentication) SecurityContextHolder.getContext().getAuthentication(); 
			User user = principal.getUser();
			int userNo = user.getUserNo();
		
			saleCnt = saleService.countMySaleList(userNo);
			
			if(saleCnt > 0) {
				pageCnt = (saleCnt % showCnt == 0) ? (saleCnt / showCnt) : (saleCnt / showCnt + 1);		//페이지 개수
				int start = 1+(showCnt*(pageNo-1));
				int end = showCnt+(showCnt*(pageNo-1));
				
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("start", start);
				paramMap.put("end", end);					
				paramMap.put("userNo", userNo);					
				
				saleList = saleService.getMySaleList(paramMap);
			}
		}
	
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("saleList", saleList);
		map.put("saleCnt", saleCnt);
		map.put("pageCnt", pageCnt);
		
        return map;
    }
}

