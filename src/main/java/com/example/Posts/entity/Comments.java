package com.example.Posts.entity;

//import com.example.Posts.dto.CommentDTO;
import com.example.Posts.dto.PostComment;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Document
public class Comments {
    @Id
    String postId;
    Map<String,List<PostComment>> comments=new HashMap<String,List<PostComment>>();   //In map String is userId
    String userName;
}
