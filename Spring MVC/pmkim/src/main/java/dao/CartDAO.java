package dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import vo.CartVO;
import vo.GoodsEventShopMemberVO;
import vo.GoodsInformVO;

@Repository
public class CartDAO {
	@Autowired
	SqlSession session = null;
	
	//cart담기
	public boolean cartInsert(CartVO cvo) {
		boolean result = false;
		String statement = "resource.CartMapper.insertCart";
		if(session.insert(statement,cvo)==1) {
			result=true;
		}
		return result;
	}
	
	//장바구니 비우기
	public boolean cartDelete(String id) {
		boolean result = false;
		String statement = "resource.CartMapper.deleteCart";
		if(session.delete(statement,id)==1) {
			result = true;
		}
		return result;
	}
	
	//저장된 카트 보여주기
	public List<GoodsEventShopMemberVO> cartView(String id) {
		List<GoodsEventShopMemberVO> list = new ArrayList<GoodsEventShopMemberVO>();
		String statement = "resource.CartMapper.selectCart";
		list = session.selectList(statement,id);
		return list;
	}
	
	//상품검색
	public List<GoodsEventShopMemberVO> searchGoods(String keyword){
		List<GoodsEventShopMemberVO> list = new ArrayList<>();
		String statement = "resource.CartMapper.searchGoods";
		list = session.selectList(statement,keyword);
		return list;
	}
	
	//paging
	public int listCount(String event_name, String shop_code) {
		int count = 0;
		String statement = "resource.CartMapper.listCount";
		if(session.selectOne(statement)!=null) {
			Map<String,String> map = new HashMap<String,String>();
			map.put("event_name", event_name);
			map.put("shop_code", shop_code);
			count = session.selectOne(statement,map);
		}else {
			System.out.println("null이에유");
		}
		return count;
	}
	//상품 전체 리스트 출력
	public List<GoodsEventShopMemberVO> goodsAll(){
		List<GoodsEventShopMemberVO> list = new ArrayList<GoodsEventShopMemberVO>();
		String statement = "resource.CartMapper.goodsListAll";
		list = session.selectList(statement);
		System.out.println(list.get(0));
		return list;
	}
	
	//shopName으로 찾아오기
	public List<GoodsEventShopMemberVO> goodsSortShop(String shop_code) {
		List<GoodsEventShopMemberVO> list = new ArrayList<GoodsEventShopMemberVO>();
		String statement = "resource.CartMapper.goodsList_shopName";
		list = session.selectList(statement,shop_code);
		return list;
	}
	
	//eventName으로 찾아오기
	public List<GoodsEventShopMemberVO> goodsSortEvent(String event_name){
		List<GoodsEventShopMemberVO> list = new ArrayList<GoodsEventShopMemberVO>();
		String statement = "resource.CartMapper.goodsList_eventName";
		list = session.selectList(statement,event_name);
		return list;
	}
	
	//shopName&eventName 모두로 찾아오기
	public List<GoodsEventShopMemberVO> goodsShopEvent(String event_name, String shop_code){
		List<GoodsEventShopMemberVO> list = new ArrayList<GoodsEventShopMemberVO>();
		String statement = "resource.CartMapper.goodsList_shopEvent";
		Map<String,String> map = new HashMap<String,String>();
		map.put("event_name", event_name);
		map.put("shop_code", shop_code);
		//System.out.println(map.keySet());
		list = session.selectList(statement,map);
		//System.out.println("list : "+ list);
		return list;
	}
	
	//shopName&eventName 모두로 찾아오기 + paging
	public List<GoodsEventShopMemberVO> goodsShopEvent(String event_name, String shop_code, int startNum, int endNum){
		List<GoodsEventShopMemberVO> list = new ArrayList<GoodsEventShopMemberVO>();
		String statement = "resource.CartMapper.goodsList_shopEvent_paging";
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("startNum",startNum);
		map.put("endNum", endNum);
		map.put("event_name", event_name);
		map.put("shop_code", shop_code);

		list = session.selectList(statement,map);
		//System.out.println("list : "+ list);
		return list;
	}
	
	//2020.05.26 추가
	//ctg_1='식품'인 세부 카테고리 가져오기
	public List<GoodsInformVO> foodsCtg() {
		List<GoodsInformVO> list = new ArrayList<>();
		String statement = "resource.CartMapper.foodsCtg";
		list = session.selectList(statement);
		return list;
	}
	
	//ctg_3에 따른 추천 상품 정보
	public List<GoodsInformVO> recomGoodsList(String ctg_3,int min, int max){
		List<GoodsInformVO> list = new ArrayList<>();
		String statement = "resource.CartMapper.recommendGoods";
		Map<String, Object> map = new HashMap<>();
		map.put("ctg_3",ctg_3);
		map.put("min",min);
		map.put("max",max);
		list = session.selectList(statement,map);
		return list;
	}
	
	//카테고리 체크해줌
	public GoodsInformVO checkCtg(String good_id){
		GoodsInformVO vo = new GoodsInformVO();
		String statement = "resource.CartMapper.checkCtg";
		vo = session.selectOne(statement,good_id);
		return vo;
	}
	
	//ctg_3 갯수 count (Random함수 사용하기 위함)
	public int countCtg3(String ctg_3) {
		String statement = "resource.CartMapper.countCtg3";
		return session.selectOne(statement,ctg_3);
	}
	
}
