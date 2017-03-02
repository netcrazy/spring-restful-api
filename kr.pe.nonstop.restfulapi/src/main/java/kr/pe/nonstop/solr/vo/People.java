package kr.pe.nonstop.solr.vo;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="people")
@SuppressWarnings("serial")
public class People 
{

	private int totCount;
	private List<Person> people;
    
	protected People() {}   // Keep JAXB happy
    
	public People(int totCount, List<Person> people)
	{
	  this.totCount = totCount;
	  this.people = people;
	}
   
    @XmlElement(name="person")
    public List<Person> getPeople() 
    {
     return people;
    }

} 