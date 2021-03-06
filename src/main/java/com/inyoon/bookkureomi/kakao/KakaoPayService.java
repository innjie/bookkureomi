package com.inyoon.bookkureomi.kakao;
 
import java.net.URI;
import java.net.URISyntaxException;
 
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.inyoon.bookkureomi.domain.Recharge;
import com.inyoon.bookkureomi.domain.User;
 
 
@Service
public class KakaoPayService {
 
    private static final String HOST = "https://kapi.kakao.com";
    
    private KakaoPayReadyVO kakaoPayReadyVO;
    private KakaoPayApprovalVO kakaoPayApprovalVO;
    
    //public String kakaoPayReady(Sale sale, User user) {
    public String kakaoPayReady(Recharge recharge) {
    	
    	System.out.println("kakaoPayReady");
        RestTemplate restTemplate = new RestTemplate();
 
        //서버로 요청할 Header
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + "6b3d258606985783d2c11c2a0cefd63d");
        headers.add("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE);
        headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");
        
        //서버로 요청할 Body
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("cid", "TC0ONETIME");
        params.add("partner_order_id", recharge.getUser().getUserNo() + "-" + recharge.getRechargeNo());
        params.add("partner_user_id", recharge.getUser().getId());
        params.add("item_name", "북꾸러미 포인트");
        params.add("quantity", "1");
        params.add("total_amount", String.valueOf(recharge.getRcPoint()));
        params.add("tax_free_amount", "100");
        params.add("approval_url", "http://localhost:8000/book/kakao/success");
        params.add("cancel_url", "http://localhost:8000/book/kakao/cancel");
        params.add("fail_url", "http://localhost:8000/book/kakao/fail");
 
        HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<MultiValueMap<String, String>>(params, headers);
 
        try {
            kakaoPayReadyVO = restTemplate.postForObject(new URI(HOST + "/v1/payment/ready"), body, KakaoPayReadyVO.class);
            
            System.out.println("" + kakaoPayReadyVO);
            
            return kakaoPayReadyVO.getNext_redirect_pc_url();
 
        } catch (RestClientException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return "/";
    }
    
    public KakaoPayApprovalVO kakaoPayInfo(String pg_token) {
    	 
       // log.info("KakaoPayInfoVO............................................");
       // log.info("-----------------------------");
        
        RestTemplate restTemplate = new RestTemplate();
 
        // 서버로 요청할 Header
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + "6b3d258606985783d2c11c2a0cefd63d");
        headers.add("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE);
        headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");
 
        // 서버로 요청할 Body
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("cid", "TC0ONETIME");
        params.add("tid", kakaoPayReadyVO.getTid());
        //params.add("partner_order_id", recharge.getUser().getUserNo() + "-" + recharge.getRechargeNo());
        //params.add("partner_user_id", recharge.getUser().getId());
        //params.add("total_amount", String.valueOf(recharge.getRcPoint()));
        params.add("partner_order_id", "1-0");
        params.add("partner_user_id", "im");
        params.add("total_amount", "1000");
        params.add("pg_token", pg_token);
        
        HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<MultiValueMap<String, String>>(params, headers);
        
        try {
            kakaoPayApprovalVO = restTemplate.postForObject(new URI(HOST + "/v1/payment/approve"), body, KakaoPayApprovalVO.class);
            System.out.println("" + kakaoPayApprovalVO);
          
            return kakaoPayApprovalVO;
        
        } catch (RestClientException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return null;
    }
}
 
