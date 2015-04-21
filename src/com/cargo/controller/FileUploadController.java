package com.cargo.controller;

import java.io.File;
import java.io.IOException;

import net.minidev.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileUploadController {

	@RequestMapping(value="/upload", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject upload(@RequestParam(value = "data", required = false) MultipartFile file) throws IllegalStateException, IOException{
        File filePath = new File("WebRoot/WEB-INF/resources/images/"  + file.getOriginalFilename());
		file.transferTo(filePath);
        JSONObject obj = new JSONObject();
        obj.put("url", "WEB-INF/resources/images/"  + file.getOriginalFilename());
        return obj;
	}
}
