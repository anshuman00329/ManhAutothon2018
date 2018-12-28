package commonutils;


import com.mongodb.util.JSON;
import io.restassured.RestAssured;
import io.restassured.internal.http.BasicNameValuePairWithNoValueSupport;
import io.restassured.path.json.JsonPath;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.*;
import org.apache.xmlbeans.impl.regex.Match;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class HttpClientUtil {

    public List wikipediaDirector(String moviename) throws ClientProtocolException, IOException, ParserConfigurationException, SAXException, XPathExpressionException {
        CloseableHttpClient client = HttpClients.createDefault();
        List wikiList = new ArrayList();
        String wikiMovieLink;
        String smovieName = moviename.replaceAll(" ","_");
        HttpGet httpGet = new HttpGet("https://en.wikipedia.org/wiki/"+smovieName);
        wikiMovieLink = httpGet.getURI().toString();
        wikiList.add(wikiMovieLink);
        System.out.println("wikiMovieLink"+wikiMovieLink);
       // List<NameValuePair> params = new ArrayList<NameValuePair>();
        //params.add(new BasicNameValuePair("search", "The Shawshank Redemption"));
        //httpPost.setEntity(new UrlEncodedFormEntity());

        CloseableHttpResponse response = client.execute(httpGet);
        String Out = EntityUtils.toString(response.getEntity());
        //System.out.println(Out);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = Jsoup.parse(Out);
        Element table = doc.select("table").get(0); //select the first table.
        Elements rows = table.select("tr");
        Element row = rows.get(2);
        Elements cols = row.select("td");
        System.out.println(" Director name " + cols.get(0).text());
        String wikiDirectorName = cols.get(0).text();
        wikiList.add(wikiDirectorName);
        return wikiList;
    }

    public List imdbDirector(String movieName) throws IOException, ParserConfigurationException {
    //String movieName ="The Godfather";
        List imdbList = new ArrayList();
        String imbdMovieLink;
        String smovieName = movieName.replaceAll(" ", "+");
        System.out.println("Movie " + smovieName);

        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("https://www.imdb.com/find?ref_=nv_sr_fn&q=" + smovieName + "&s=all");
        imbdMovieLink = httpGet.getURI().toString();
        imdbList.add(imbdMovieLink);
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        CloseableHttpResponse response = client.execute(httpGet);
        String Out = EntityUtils.toString(response.getEntity());
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = Jsoup.parse(Out);
        Element table = doc.select("table").get(0); //select the first table.
        Elements rows = table.select("tr");
        Element row = rows.get(0);
        Elements cols = row.select("td");
        String movieLink = cols.get(1).getElementsByTag("a").attr("href");
        System.out.println("Movie" + movieLink);
        httpGet = new HttpGet("https://www.imdb.com" + movieLink);
        System.out.println("IMDB Movie Link "+httpGet.getURI());
        response = client.execute(httpGet);
        Out = EntityUtils.toString(response.getEntity());
        doc = Jsoup.parse(Out);

        Element scriptElements = doc.getElementsByTag("script").get(11);

        JSONArray jsonArray = new JSONArray(scriptElements.dataNodes().toString());
//        for (int index = 0; index < jsonArray.length(); index++) {
//            JSONObject jsonObject = jsonArray.getJSONObject(index);
//            System.out.println("" + jsonObject);
//            System.out.println("" + jsonObject.getJSONObject("director").getString("name"));
//        }

        JSONObject jsonObject = jsonArray.getJSONObject(0);
        String directorName= jsonObject.getJSONObject("director").getString("name");
        System.out.println("IMDB Director name" + directorName );
        imdbList.add(directorName);
        return imdbList;
    }

    public void assertDirectorName(String wikiDirector, String imdbDirector)
    {
        Assert.assertEquals(wikiDirector,imdbDirector,"Director Name Doesnt Match");
    }
}
