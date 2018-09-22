/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.luisjjavier.excelreader.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 *
 * @author LuisJavier
 */
@RestController
@RequestMapping("/documents")
public class WordDocumentsController {

    @RequestMapping(method = POST, consumes = "application/json")
    @CrossOrigin
    public ResponseEntity<byte[]> post(@RequestBody DocumentRequest input) throws IOException {

        XWPFDocument document = new XWPFDocument();
        File file =new File(input.getFilename() + ".docx");

        FileOutputStream out = new FileOutputStream(file);

        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setText(input.getParagraph());
        document.write(out);
        out.close();
          Path path = Paths.get(file.getAbsolutePath());
        byte[] resource = Files.readAllBytes(path);
           return ResponseEntity.ok()
                             .contentLength(file.length())
                             .header(HttpHeaders.CONTENT_TYPE,   "application/octet-stream")
                             .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" +  file.getName())
                             .body(resource);
 
    }

}
