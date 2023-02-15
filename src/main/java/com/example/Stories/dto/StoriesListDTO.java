package com.example.Stories.dto;

import com.example.Stories.entities.Stories;
import lombok.Data;

import java.util.List;

@Data
public class StoriesListDTO {

    List<Stories> storiesList;
}
