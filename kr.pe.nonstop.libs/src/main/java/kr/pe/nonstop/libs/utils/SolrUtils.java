package kr.pe.nonstop.libs.utils;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

public class SolrUtils {
	
	/**
	 * 로깅 멤버변수
	 */
	protected Logger mLogger = LoggerFactory.getLogger(SolrUtils.class);
		
	/**
	 * SOLR HTTP 서버호출
	 * @return
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws SolrServerException
	 */
	public SolrServer getHttpSolrServer(String coreName) throws IOException, ParserConfigurationException, SAXException, SolrServerException {

    	String serverUrl = "http://localhost:8983/solr/" + coreName;
		return new HttpSolrServer(serverUrl);
	}
}
