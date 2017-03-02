package kr.pe.nonstop.solr.vo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MemberVO implements Serializable  {
	
	private String userId;
	private String name;
	private int age;	
	
	public MemberVO() {}
	
	public MemberVO(int age, String userId, String name) {
		super();
		this.age = age;
		this.name = name;
		this.userId = userId;
		
	}
	
	public MemberVO(String userId, String name, int age) {
		super();
		
		this.userId = userId;
		this.name = name;
		this.age = age;
	
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	


}
