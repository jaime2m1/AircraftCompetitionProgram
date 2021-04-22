package Controlador;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
//import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DBConfigDAO {
	DocumentBuilderFactory dbf;
	File file;
	public DBConfigDAO() {
		file = new File("resources/data/DBConfig.xml");
		dbf = DocumentBuilderFactory.newInstance();
	}
	
	public String[] readDBConfig() throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilder db = dbf.newDocumentBuilder();  
		Document doc = db.parse(file);  
		doc.getDocumentElement().normalize();  
		NodeList nodeList = doc.getElementsByTagName("DBConfig");
		Node node = nodeList.item(0);
		Element element = (Element) node;
		String[] DBConfig = new String[4];
		DBConfig[0]=element.getElementsByTagName("host").item(0).getTextContent();
		DBConfig[1]=element.getElementsByTagName("database").item(0).getTextContent();
		DBConfig[2]=element.getElementsByTagName("user").item(0).getTextContent();
		DBConfig[3]=element.getElementsByTagName("password").item(0).getTextContent();
		
		return DBConfig;
	}
	
	public void updateDBConfig(String[] DBConfig) throws ParserConfigurationException, SAXException, IOException, TransformerException {
		DocumentBuilder db = dbf.newDocumentBuilder();  
		Document doc = db.parse(file);  
		doc.getDocumentElement().normalize();  
		NodeList nodeList = doc.getElementsByTagName("DBConfig");
		Node node = nodeList.item(0);
		Element element = (Element) node;
		Node host=element.getElementsByTagName("host").item(0);
		Node database=element.getElementsByTagName("database").item(0);
		Node user=element.getElementsByTagName("user").item(0);
		Node password=element.getElementsByTagName("password").item(0);
		
		host.setTextContent(DBConfig[0]);
		database.setTextContent(DBConfig[1]);
		user.setTextContent(DBConfig[2]);
		password.setTextContent(DBConfig[3]);
		
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(file);
        transformer.transform(source, result);
	}
	
}
