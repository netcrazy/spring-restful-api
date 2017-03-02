package kr.pe.nonstop.solr;

import java.util.Arrays;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import kr.pe.nonstop.libs.utils.SolrUtils;
import kr.pe.nonstop.solr.vo.People;
import kr.pe.nonstop.solr.vo.Person;

@Controller
public class SolrController extends SolrUtils {
	
	@Autowired
    private SqlSession sqlSession;
	
    @RequestMapping(value="/solr", 
	        method = RequestMethod.GET, 
	        produces={"application/json;charset=UTF-8"})
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String listWithJSON(
		  @RequestParam(value="ct", required=false) String ct
		, @RequestParam(value="ty", required=false) String ty
		, @RequestParam(value="cd", required=false) String cd)
	{
    	
		try{
			
			/**
			 * HTTP로 SOLR 연결
			 */
			SolrServer solr = getHttpSolrServer("job");

			/**
			 *  라이브러리로 SOLR를 직접 참조한다.
			 */
			
			//SolrServer solr = connect.getEmbeddedSolrServer("job");
			
			SolrQuery query = new SolrQuery();
			
			query.setQuery("*:*");		
			query.set("wt", "json");
			query.setRows(10);
			query.setStart(0);
			query.setParam("indent", "on");
			query.setParam("version", "2.2");
			
			query.setFacet(true);
			query.setFacetMinCount(1);
			query.setFacetLimit(50);
			
/*			query.addFacetField("REGIONNAME");
			query.addFacetField("INDUSTRYNAME");
*/

			QueryResponse resp = solr.query(query);
			SolrDocumentList docList = resp.getResults();
						
			mLogger.info("\n\n결과2=" + docList.getNumFound());


	    	/**
	    	 * numFound 를 담을 JSONArray
	    	 */
	    	JSONArray countArr = new JSONArray();
	    	JSONObject countObject=new JSONObject();
	    	countObject.put("numFound", docList.getNumFound());
	    	countArr.put(countObject);
			
			/**
			 * 컨텐츠 결과를 json으로
			 */
			JSONArray docsArr = new JSONArray();
			for (int i = 0; i < docList.size(); i++) {
			     JSONObject json = new JSONObject(docList.get(i));
			     docsArr.put(json);           
			}
			
			/**
			 * 패싯결과를 json으로
			 */
			JSONArray facetArr = new JSONArray();
			List<FacetField> facetList = resp.getFacetFields();

			for(FacetField facet : facetList){
			    String ffname = facet.getName();
			    int ffcount = facet.getValueCount();
			    
			    JSONObject facetObject=new JSONObject();
			    
			    List<Count> counts = facet.getValues();
			    
			    for(Count c : counts){
			        String facetLabel = c.getName();
			        long facetCount = c.getCount();
			        
			    	facetObject.put(facetLabel, facetCount);
			    }
			    facetArr.put(facetObject);  
			}

			/**
			 * 각각의 JSONArray를 returnArr 다시 담는다.
			 */
			JSONArray returnArr = new JSONArray();
			returnArr.put(countArr);
			returnArr.put(docsArr);
			returnArr.put(facetArr);

			return returnArr.toString(2);
			
		} catch (Exception e){
			
			mLogger.error(e.toString());			
			return e.toString();
		}
	}
    
    private static List<Person> personList;
    static
    {
     personList = 
       Arrays.asList(new Person[] 
         { new Person(1, "Pas", "Apicella"),
              new Person(2, "Lucia", "Apicella"),
              new Person(3, "Lucas", "Apicella"),
              new Person(4, "Siena", "Apicella")
            });   
    }
    
    //people.xml, people.json 호출에 따라 json또는 xml 데이터 출력
    @RequestMapping(value="/people", method = RequestMethod.GET, produces={"application/xml", "application/json"})
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody People listWithJSON() 
    {
    	return new People(personList.size(), personList);
    }
}
