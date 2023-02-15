package com.example.Stories.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@Document(collection = Stories.COLLECTION_NAME2)
public class Stories {

    public static final String COLLECTION_NAME2 = "Stories";
    @Id
    public String storyId;
    public String userId;
    public String storyType;
//    public List<String> viewers;
    public String imageUrl;
    public String text;
    public LocalDateTime localDateTime;
    public String videoUrl;
    public String displayPicture;


}
