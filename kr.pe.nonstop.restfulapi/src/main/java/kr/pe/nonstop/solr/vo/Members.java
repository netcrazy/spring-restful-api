package kr.pe.nonstop.solr.vo;


import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="people")
@SuppressWarnings("serial")
public class Members 
{
  private List<MemberVO> member;
    
  public Members() {}   // Keep JAXB happy
    
  public Members(List<MemberVO> member)
  {
     this.member = member;
  }
   
    @XmlElement(name="member")
    public List<MemberVO> getMembers() 
    {
     return member;
    }

} 