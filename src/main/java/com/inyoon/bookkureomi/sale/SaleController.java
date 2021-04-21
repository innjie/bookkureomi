package com.inyoon.bookkureomi.sale;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.inyoon.bookkureomi.delivery.DeliveryService;
import com.inyoon.bookkureomi.domain.Delivery;
import com.inyoon.bookkureomi.domain.Genre;
import com.inyoon.bookkureomi.domain.OrderDetail;
import com.inyoon.bookkureomi.domain.Sale;
import com.inyoon.bookkureomi.domain.User;
import com.inyoon.bookkureomi.genre.GenreService;
import com.inyoon.bookkureomi.order.OrderService;
import com.inyoon.bookkureomi.test.Test;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
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
	
	
	@ApiOperation(value="중고거래 화면 이동", notes="중고거래 목록화면으로 이동한다.")
	@GetMapping("/sale/view")
    public String viewSale() {		
        return "sale/sale";
    }
	
	@ApiOperation(value="중고거래 목록", notes="중고거래 전체 목록을 보여준다.")
	@ResponseBody //@RestController 시 생략 가능
	@GetMapping("/sale/list")
	public Map<String, Object> listSale() {

		List<Sale> saleList = new ArrayList<>();	
		saleList = saleService.getSaleList();
	
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("saleList", saleList);
		
        return map;
    }
	
	@ApiOperation(value="중고거래 찾기", notes="제목/장르로 중고거래를 찾는다.")
	@ResponseBody //@RestController 시 생략 가능
	@GetMapping("/sale/find")
	public Map<String, Object> findSale(
			@RequestParam("title") String title,
			@RequestParam("genre") String genre) {

		List<Sale> saleList = new ArrayList<>();
		
		if(!title.equals("") && title != null) {
			saleList = saleService.findSaleByTitle(title);
		} else if(!genre.equals("") && genre != null) {
			saleList = saleService.findSaleByGenre(genre);
		} 
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("saleList", saleList);
		
        return map;
    }
	
	@ApiOperation(value="중고 책 상세 보기", notes="판매되는 중고 책을 상세히 본다.")
	@ResponseBody //@RestController 시 생략 가능
	@GetMapping("/sale/detail")
	public Map<String, Object> detailSale(
				@RequestParam("saleNo") int saleNo) throws Exception {

		Sale sale = new Sale();
		sale = saleService.getSale(saleNo);
		
		Delivery delivery = null;		
		OrderDetail orderDetail = new OrderDetail();
		orderDetail = orderService.getOrderBySale(saleNo);
		
		if(orderDetail != null) {
			delivery = deliveryService.getDelivery(orderDetail.getOrder().getOrderNo());
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sale", sale);
		map.put("delivery", delivery);
		
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
			@RequestParam("image") String image,
			@RequestParam("userNo") int userNo,
			@RequestParam("author") String author,
			@RequestParam("genreType") String genreType) throws Exception {
		
		Sale sale = new Sale();
		User user = new User();
		user.setUserNo(userNo);
		Genre genre = genreService.getGenreByName(genreType);
		
		sale.setSaleNo(saleService.getSaleNo());
		sale.setImage(image);
		sale.setTitle(title);
		sale.setPublisher(publisher);
		sale.setSalePrice(salePrice);
		sale.setInfo(info);
		sale.setCostPrice(costPrice);
		sale.setAuthor(author);
		sale.setUser(user);
		sale.setGenre(genre);
		
		saleService.saleBook(sale);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", "success");
		map.put("saleNo", sale.getSaleNo());
		
        return map;
	}
		
	@ApiOperation(value="판매 등록 책 수정 ", notes="판매하는 중고 책을 수정한다.")
	@ResponseBody //@RestController 시 생략 가능
	@PutMapping("/sale/update")
	public Map<String, Object> updateSale(
			@RequestParam("saleNo") String saleNo,
			@RequestParam("image") String image,
			@RequestParam("salePrice") String salePrice,
			@RequestParam("info") String info) throws Exception {

		Sale sale = new Sale();
		sale.setSaleNo(Integer.parseInt(saleNo));
		sale.setImage(image);
		sale.setSalePrice(Integer.parseInt(salePrice));
		sale.setInfo(info);
		
		saleService.updateSale(sale);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", "success");
		
        return map;
	}
	
	@ApiOperation(value="판매 등록 책 삭제 ", notes="판매하는 중고 책을 삭제한다.")
	@ResponseBody //@RestController 시 생략 가능
	@DeleteMapping("/sale/delete")
	public Map<String, Object> deleteSale(
			@RequestParam("saleNo") String saleNo) throws Exception {

		saleService.deleteSale(Integer.parseInt(saleNo));
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", "success");
		
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
	public Map<String, Object> listMySale() {

		int userNo = 1;
		
		List<Sale> saleList = new ArrayList<>();	
		saleList = saleService.getMySaleList(userNo);
	
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("saleList", saleList);
		
        return map;
    }
}

