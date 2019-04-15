package com.example.review.backend.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Lob;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

/**
 * @author Klaus Groenbaek
 * Created  15/04/2019
 */
@Data
@Accessors(chain = true)
public class ReviewableDTO {

    @Null
    private String id;
    @Size(min = 10, max = 1024, message = "Description must be between 10 and 1024 characters")
    private String description;
    @Min(-180)
    @Max(180)
    private double lon;
    @Min(-90)
    @Max((90))
    private double lat;
    @Min(0)
    @Max(5)
    private Double rating;
    @Null
    private Long numberOfVotes;
    private String base64Image;

}
