package commonutils;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HTTPCustomReportUtil {

    PrintWriter m_out;

    public void generateReport(Map datamap) {
        //new File(outputDirectory).mkdirs();
        try {
            m_out = new PrintWriter(new BufferedWriter(new FileWriter(new File ("custom-report.html"))));
        } catch (IOException e) {
            System.out.println("Error in creating writer: " + e);
        }

        try {
            startHtml(datamap);
        } catch (Exception e) {

            e.printStackTrace();
        }

        m_out.flush();
        m_out.close();
    }

    protected void startHtml(Map map) throws Exception {

        m_out.println("<!DOCTYPE html>");
        m_out.println("<html>");
        m_out.println("<head>");
        m_out.println("<title>AUTOTHON CUSTOMIZED REPORT</title>");
        m_out.println("<style type=\"text/css\">");
        m_out.println("h3 {text-align: center;}");
        m_out.println("#title");
        m_out.println("{");
        m_out.println("font-family: \"Arial Black\", \"Arial Bold\", Gadget, sans-serif; background-color:orange ; width: 100%; height: 75px; margin-right: auto;margin-left: auto; margin-top:10px;");
        m_out.println("}");
        m_out.println("</style>");
        m_out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">");
        m_out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\" integrity=\"sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u\" crossorigin=\"anonymous\">");
        m_out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css\" integrity=\"sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp\" crossorigin=\"anonymous\">");
        m_out.println("<script type = \"text/javascript\" src = \"https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js\"></script>");
        m_out.println("<script type = \"text/javascript\" src = \"https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js\"></script>");
        m_out.println("<link rel = \"stylesheet\" href = \"https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.css\"/ >");
        m_out.println("<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js\" integrity=\"sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa\" crossorigin=\"anonymous\"></script>");
        m_out.println("</head>");
        m_out.println("<body>");
        m_out.println("<div class=\"container\">");
        m_out.println("<div id=\"title\" class=\"container\">");
        m_out.println("<h1 align=\"center\"> AUTOTHON CUSTOMIZED REPORT </h1>");
        m_out.println("</div>");
        m_out.println("<br>");
        m_out.println("<div id=\"ComparisonTable\" class=\"table-responsive\">");
        m_out.println("<table class=\"table table-striped table-bordered table-hover\">");
        m_out.println("<tr style=\"color: #ffffff; background-color:#F08080;\"> <th>Movie Name</th> <th>Wikipedia URL</th><th>Wikipedia Director(s)</th> <th>Imdb URL</th><th>Imdb Director(s)</th>");
        Iterator<HashMap.Entry<String,List<Object>>> entries = map.entrySet().iterator();


        while (entries.hasNext()) {

            HashMap.Entry<String, List<Object>> entry = entries.next();
            m_out.println("<tr style=\"color: #000000; background-color:#ffffff;\"> <th>" + entry.getKey() + "</th> <th>" + entry.getValue().get(0) + "</th><th>"+ entry.getValue().get(1) +"</th> <th>"+ entry.getValue().get(2) +"</th><th>"+ entry.getValue().get(3) +"</th>");

        }

    }


}
