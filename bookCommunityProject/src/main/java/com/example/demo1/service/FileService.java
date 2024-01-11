package com.example.demo1;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {
	
	@Autowired
	private FileRepository fileRepository;
	
	public String storeFile(MultipartFile file) throws IOException {
		Files files = Files
		.builder()
		.name(file.getOriginalFilename())
		.type(file.getContentType())
		.pdfData(file.getBytes())
		.build();
		
		files = fileRepository.save(files);
		
		if (files.getId() != null) {
			return "File uploaded successfuly into database";
		}
		
		return null;
	}
	
	public byte[] getFiles(String fileName) {
		if(fileRepository.findByName(fileName).getPdfData() != null)
			return fileRepository.findByName(fileName).getPdfData();
		return new byte[0];
	}
	
	boolean existsByName(String name)
	{
		return fileRepository.existsByName(name);
	}
}
