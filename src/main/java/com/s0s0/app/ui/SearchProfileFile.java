package com.s0s0.app.ui;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.s0s0.app.exception.JcsException;

public class SearchProfileFile {

	public static void save(SearchProfile searchprofile, String filepath) throws JcsException
	{
        JSONObject obj = new JSONObject();
        obj.put("query", searchprofile.getQuery());
        obj.put("casesensitive", Boolean.valueOf(searchprofile.isCasesensitive()));
        obj.put("regex", Boolean.valueOf(searchprofile.isRegex()));
        
        JSONArray list = new JSONArray();
        for (String path : searchprofile.getPaths())
		{
            list.add(path);
		}
        obj.put("paths", list);
        try {
            FileWriter file = new FileWriter(filepath);
            file.write(obj.toJSONString());
            file.flush();
            file.close();
        } catch (IOException e) {
            throw new JcsException(e);
        }
	}
	
	public static SearchProfile load(String filepath) throws JcsException
	{
		SearchProfile searchprofile = new SearchProfile();
		JSONParser parser = new JSONParser();
		 
		try {
            Object obj = parser.parse(new FileReader(filepath));
            JSONObject jsonObject = (JSONObject) obj;
            
            String query = (String) jsonObject.get("query");
            searchprofile.setQuery(query);
            boolean casesensitive = (Boolean) jsonObject.get("casesensitive");
            searchprofile.setCasesensitive(casesensitive);
            boolean regex = (Boolean) jsonObject.get("regex");
            searchprofile.setCasesensitive(regex);
            JSONArray pathsarray = (JSONArray) jsonObject.get("paths");
            List<String> paths = new ArrayList<String>();
            Iterator<String> iterator = pathsarray.iterator();
            while (iterator.hasNext()) {
                paths.add(iterator.next());
            }
            searchprofile.setPaths(paths);
        } catch (FileNotFoundException e) {
            throw new JcsException(e);
        } catch (IOException e) {
            throw new JcsException(e);
        } catch (ParseException e) {
            throw new JcsException(e);
        }
		return searchprofile;
	}
}
