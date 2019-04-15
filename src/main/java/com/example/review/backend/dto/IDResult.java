package com.example.review.backend.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Klaus Groenbaek
 * Created  15/04/2019
 */
@Data
@Accessors(chain = true)
public class IDResult {
    private String id;
}
