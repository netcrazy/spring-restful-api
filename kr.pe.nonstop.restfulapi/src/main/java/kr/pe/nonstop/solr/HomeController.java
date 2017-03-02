package kr.pe.nonstop.solr;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.pe.nonstop.libs.utils.StringUtils;
import kr.pe.nonstop.solr.dao.BaseDao;
import kr.pe.nonstop.solr.vo.SyndicationArchiveVO;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class HomeController extends BaseDao
{

	private Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
    private SqlSession sqlSession;

	@RequestMapping(value = "/", method = RequestMethod.GET, produces="text/plain;charset=UTF-8")
	public String home() {
		return "redirect:job";
	}

	@SuppressWarnings("unchecked")
    @RequestMapping(value="/job", 
    		        method = RequestMethod.GET, 
    		        produces={"application/json;charset=UTF-8"})
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody String listWithJSON(
    		  @RequestParam(value="ct", required=false) String ct
    		, @RequestParam(value="ty", required=false) String ty
    		, @RequestParam(value="cd", required=false) String cd)
    {
    	
    	Map<String, Object> paramMap = new HashMap<String, Object>();
    	
    	paramMap.put("Provider_Nm"	, "naver");
    	paramMap.put("Site_Nm"		, "job");
    	paramMap.put("Svc_Nm"		, "jobpost");
    	paramMap.put("Flag_Cd"		, "I");
    	paramMap.put("PageSize"		, 100);
    	paramMap.put("PageNum"		, 1);
    	paramMap.put("StartTm"		, "2012-05-01");
    	paramMap.put("EndTm"		, "");
    	
    	paramMap = commonsMappedParams(paramMap);
    	   	    	
    	try {
    	
	    	/**
	    	 *	hashmap vs map의 차이는?
			 *
			 *	Map은 key와 value를 가진 집합이며, 중복을 허용하지 않는다.
			 *	즉, 한개의 key에 한개의 value가 매칭된다.
			 *	java.util 패키지에 여러 집합들을 사용하기 위한 여러 interface와 class 들이 정의되어 있다.
			 *	HashMap
			 *	HashMap은 Map interface를 implements 한 클래스로서 중복을 허용하지 않는다.
			 *	Map의 특징인 key와 value의 쌍으로 이루어지며, key 또는 value 값으로써 null을 허용한다.
			 *
	    	 *	mapper.USP_TEST는 mapper의 namespace.id 임 (착각하지 말자!!)
	    	 */
	    	List list = (List)sqlSession.selectList("DataMapper.USP_TEST", paramMap);
	    	
	    	List<Integer> 					list1 = (List<Integer>) list.get(0);
	    	List<SyndicationArchiveVO> 		list2 = (List<SyndicationArchiveVO>) list.get(1);
	    	
	    	Integer totCount = list1.get(0);
	    	
	    	//파라메터 값을 찍어본당... ㅎㅎ
	    	logger.info(String.format("ct=%s, ty=%s, cd=%s, list size=%s, a=%s", ct, ty, cd, list.size(), totCount));
    	
	    	/**
	    	 * map 데이터 결과를 json으로 리턴
	    	 */
/*		    JSONArray jsonArr = new JSONArray();
		    
		    for (Map<String, String> map : list2) {
		    	
		    	JSONObject json_obj=new JSONObject();
		        for (Map.Entry<String, String> entry : map.entrySet()) {
		        	
		            String key = entry.getKey();
		            Object value = entry.getValue();
		            try {
		                json_obj.put(key,value);
		            } catch (JSONException e) {	               
		                e.printStackTrace();
		            }
		        }
		        jsonArr.put(json_obj);
		    }*/
	    	
	    	
	    	/**
	    	 * SyndicationArchiveVO 자료형을 json으로 리턴
	    	 */
	    	JSONArray contentArr = new JSONArray();	    	
	    	
	    	/**
	    	 * Total Count를 담을 JSONArray
	    	 */
	    	JSONArray countArr = new JSONArray();
	    	JSONObject countObject=new JSONObject();
	    	countObject.put("Total_Count", totCount);
	    	countArr.put(countObject);
	    	
	    	/**
	    	 * 컨텐츠 리스트를 담을 JSONArray
	    	 */
	    	JSONArray jsonArr = new JSONArray();	   
		    
		    for (SyndicationArchiveVO vo : list2) {
		    	
		    	JSONObject jsonObject=new JSONObject();
		    	
		    	jsonObject.put("Provider_Nm"	,vo.getProviderNm());
		    	jsonObject.put("Site_Nm"		,vo.getSiteNm());
		    	jsonObject.put("Svc_Nm"			,vo.getSvcNm());
		    	jsonObject.put("Item_No"		,vo.getItemNo());
		    	jsonObject.put("Flag_Cd"		,vo.getFlagCd());
		    	jsonObject.put("Info1"			,vo.getInfo1());
		    	jsonObject.put("Info2"			,vo.getInfo2());
		    	jsonObject.put("Info3"			,vo.getInfo3());
		    	jsonObject.put("Info4"			,vo.getInfo4());
		    	jsonObject.put("Info5"			,vo.getInfo5());
		    	jsonObject.put("Cont1"			,vo.getCont1());
		    	jsonObject.put("Cont2"			,vo.getCont2());
		    	jsonObject.put("Cont3"			,vo.getCont3());
		    	jsonObject.put("Cont4"			,vo.getCont4());
		    	jsonObject.put("Cont5"			, StringUtils.nvl(vo.getCont5()));
		    	jsonObject.put("Reg_Date"		,vo.getRegDate());
		    	jsonObject.put("Mod_Date"		,vo.getModDate());
		    	jsonObject.put("Create_Date"	,vo.getCreateDate());

		        jsonArr.put(jsonObject);
		    }
	    	
		    /**
		     * 각각 담아, 리턴한다.
		     */
		    contentArr.put(countArr);
		    contentArr.put(jsonArr);
		    
		    return contentArr.toString(2);
	    
    	} catch (Exception ex) {
    		
    		/**
    		 * 에러로그를 찍는다..  SQL에러일 경우, out파라메터 결과를 보여준다.
    		 */
    		logger.info(String.format("ex=%s\n ErrNum=%s\n ErrSev=%s\n ErrState=%s\n ErrProc=%s\n ErrLine=%s\n ErrMsg=%s"
    				, ex
    				, paramMap.get("ErrNum")
    				, paramMap.get("ErrSev")
    				, paramMap.get("ErrState")
    				, paramMap.get("ErrProc")
    				, paramMap.get("ErrLine")
    				, paramMap.get("ErrMsg")));
    		
    			//공통 에러정보 반환 (JSON형)
    			JSONArray jsonArr = commonsErrorsInfo(paramMap);		        
		        return jsonArr.toString(2);
    	}
    }
}
