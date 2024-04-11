package kmd.backend.magazine.services;

import java.util.Random;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@Service
public class CommonService {

    public String generateRandomWord(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder(length);
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }
        return sb.toString();
    }

    public String fileDownloadURL(String baseMapping,byte[] fileData,String fileName,int id) {
        if (fileData != null && fileData.length > 0
                && fileName != null) {
                    String downloadURL = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path(baseMapping+"/download/")
                    .path(String.valueOf(id))
                    .toUriString();
                    return downloadURL;
        }
        return "";
        
    }
}
