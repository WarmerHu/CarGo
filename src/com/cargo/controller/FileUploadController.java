package com.cargo.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import net.minidev.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

//test
@Controller
public class FileUploadController {

	@RequestMapping(value="/upload", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject upload(@RequestParam(value = "data", required = false) MultipartFile file,HttpServletRequest request) throws IllegalStateException, IOException{
		String path = request.getSession().getServletContext().getRealPath("/");
        File filePath = new File(path + "WEB-INF/resources/images/"  + file.getOriginalFilename());
        if(!(filePath.exists())){
        	filePath.createNewFile();
        }
		file.transferTo(filePath);
        JSONObject obj = new JSONObject();
        obj.put("picture", "resources/images/"  + file.getOriginalFilename());
        return obj;
	}
}
