package kr.pe.nonstop.solr.dao;

import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * 공통로직 initial version
 * @since	2014-08-12
 * @author 	최일규
 *
 */
public class BaseDao {
	
	/**
	 * 공통 Stored Procedure Error 맵핑 파라메터
	 * 
	 * @since 	2014-08-12
	 * @author	최일규
	 * @param 	paramMap
	 * @return
	 */
    public Map<String, Object> commonsMappedParams(Map<String, Object> paramMap) {  
    	paramMap.put("ErrNum"		, 0);
    	paramMap.put("ErrSev"		, 0);
    	paramMap.put("ErrState"		, 0);
    	paramMap.put("ErrProc"		, "");
    	paramMap.put("ErrLine"		, 0);
    	paramMap.put("ErrMsg"		, "");
        
        return paramMap;
    }
    
    /**
     * 공통 에러반환정보
     * 
	 * @since 	2014-08-12
	 * @author	최일규
     * @param 	paramMap
     * @return
     */
    public JSONArray commonsErrorsInfo(Map<String, Object> paramMap) {
    
		JSONArray jsonArr = new JSONArray();
		JSONObject json_obj=new JSONObject();
	  
	    json_obj.put("ErrNum"	,paramMap.get("ErrNum"));
	    json_obj.put("ErrSev"	,paramMap.get("ErrSev"));
	    json_obj.put("ErrState"	,paramMap.get("ErrState"));
	    json_obj.put("ErrProc"	,paramMap.get("ErrProc"));
	    json_obj.put("ErrLine"	,paramMap.get("ErrLine"));
	    json_obj.put("ErrMsg"	,paramMap.get("ErrMsg"));
	  
	    jsonArr.put(json_obj);
	    
	    return jsonArr;
    }

}
