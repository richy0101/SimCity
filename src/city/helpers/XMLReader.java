package city.helpers;
import java.awt.List;
import java.io.File;  
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;  
import javax.xml.parsers.DocumentBuilderFactory;  

import org.w3c.dom.Document;  
import org.w3c.dom.Element;  
import org.w3c.dom.Node;  
import org.w3c.dom.NodeList;  

import city.PersonAgent;

public class XMLReader {

	private ArrayList<PersonAgent> persons = new ArrayList<PersonAgent>();
	 public void populateCity() {  
		 try {  
	  
			 File xmlFile = new File("supernormative.xml");  
			 DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();  
			 DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();  
			 Document doc = documentBuilder.parse(xmlFile);  
	  
			 doc.getDocumentElement().normalize();  
			 NodeList nodeList = doc.getElementsByTagName("Person");  
	  
			 for (int i = 0; i < nodeList.getLength(); i++) {  
				 String name;
				 String job;
				 String jobLocation;
				 int aggressiveness;
				 int initialFunds;
				 String housing;
				 String transportation;
				 
				 Node node = nodeList.item(i);  
	  
				 System.out.println("\nElement type :" + node.getNodeName());  
	  
				 if (node.getNodeType() == Node.ELEMENT_NODE) {  
					 Element person = (Element) node;  
	  
					 person.getAttribute("id");  
					 name = person.getElementsByTagName("name").item(0).getTextContent();
					 job = person.getElementsByTagName("job").item(0).getTextContent();
					 jobLocation = person.getElementsByTagName("job_location").item(0).getTextContent();
					 aggressiveness = Integer.parseInt(person.getElementsByTagName("aggressiveness").item(0).getTextContent());
					 initialFunds = Integer.parseInt(person.getElementsByTagName("startingFunds").item(0).getTextContent());
					 housing = person.getElementsByTagName("housingStatus").item(0).getTextContent();
					 transportation = person.getElementsByTagName("vehicleStatus").item(0).getTextContent();
				 }  
			 }  
		 } catch (Exception e) {  
			 e.printStackTrace();  
		 }  
	 }   
}
