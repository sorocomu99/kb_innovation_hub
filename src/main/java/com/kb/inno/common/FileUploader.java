package com.kb.inno.common;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;

public class FileUploader {
    public int uploadFile(MultipartFile file, String path, String databasePath) throws IOException {
        File filePath = new File(path);

        if(!filePath.exists()) {
            filePath.mkdirs();
        }

        if(!file.getOriginalFilename().isEmpty()) {
            BufferedOutputStream outputStream = new BufferedOutputStream(
                    new FileOutputStream(
                            new File(path + "/" + databasePath)
                    )
            );
            outputStream.write(file.getBytes());
            outputStream.flush();
            outputStream.close();
        } else {
            return 1;
        }

        return 0;
    }
}
