package com.example.demo1;

import static java.nio.file.Files.copy;
import static java.nio.file.Paths.get;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/bookCommunity/uploadBook")
public class FilesController {
	@Autowired
	private FileService filesService;
	
	@Autowired
	private LabyrinthITParsingImpl impl;
	
	@Autowired
	private BookService bookImpl;
	
	public static final String DIRECTORY = System.getProperty("user.home") + "/uploads/";

	@PostMapping("/uploadIntoDB")
	public ResponseEntity storeFilesIntoDB(@RequestParam("string") String link ,@RequestParam("file") MultipartFile file) throws IOException, IncorrectBookYear, IncorrectBookISBN, IncorrectBookID{
		String filename = StringUtils.cleanPath(file.getOriginalFilename());
        Path fileStorage = get(DIRECTORY, filename).toAbsolutePath().normalize();
        copy(file.getInputStream(), fileStorage, REPLACE_EXISTING);
        
		impl.parseBookPage(link);
		String response = null;
		if(!filesService.existsByName(filename)) {
			filesService.storeFile(file);
			ResponseEntity.ok();
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}
	
	@GetMapping("/getFileByName/{fileName}")
	public ResponseEntity<byte[]> getPdf(@PathVariable String fileName) {
		byte[] data = filesService.getFiles(fileName);
		if(data.length == 0) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(data);
		return ResponseEntity.status(HttpStatus.OK).body(data);
	}	
}
