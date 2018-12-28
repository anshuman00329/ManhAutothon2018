package testcases;

import commonutils.HTTPCustomReportUtil;
import commonutils.HttpClientUtil;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpTestClass {
    List reportList = new ArrayList();
    HTTPCustomReportUtil reportUtil = new HTTPCustomReportUtil();

    @Test
    public void httpTestCase() throws ParserConfigurationException, SAXException, XPathExpressionException, IOException {

        String[] movies = {"The Shawshank Redemption"};
        List wikiList = new ArrayList();
        List imdbList = new ArrayList();
        HttpClientUtil test = new HttpClientUtil();

        for (int i = 0; i < movies.length; i++) {
            String movietobeSerarched = movies[i];
            wikiList = test.wikipediaDirector(movietobeSerarched);
            imdbList = test.imdbDirector(movietobeSerarched);
            reportList.addAll(wikiList);
            reportList.addAll(imdbList);

            Map<String, List<Object>> mapdata = new HashMap<String, List<Object>>();
            mapdata.put(movietobeSerarched,reportList);
            reportUtil.generateReport(mapdata);

            test.assertDirectorName(wikiList.get(1).toString(), imdbList.get(1).toString());
        }

    }
}
