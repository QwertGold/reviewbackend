package com.example.review.backend.web;

import com.example.review.backend.dto.IDResult;
import com.example.review.backend.dto.ReviewableDTO;
import com.example.review.backend.jpa.Reviewable;
import org.apache.commons.io.IOUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.InputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Klaus Groenbaek
 * Created  15/04/2019
 */
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReviewControllerTest {

    @Autowired
    private TestRestTemplate template;

    @Test
    public void upLoadDownload() throws Exception {

        ClassPathResource resource = new ClassPathResource("/lagkage.jpg");

        String base64Image;
        try(InputStream inputStream = resource.getInputStream()) {
            byte[] bytes = IOUtils.toByteArray(inputStream);
            base64Image = Base64.encodeBase64String(bytes);
        }

        ReviewableDTO dto = new ReviewableDTO()
                .setDescription("Look delicious food")
                .setLat(55.693838)
                .setLon(12.493249)
                .setRating(5.0)
                .setBase64Image(base64Image);

        IDResult result = template.postForEntity("/review/", dto, IDResult.class).getBody();
        assertNotNull(result);

        ReviewableDTO body = template.getForEntity("/review/{id}", ReviewableDTO.class, result.getId()).getBody();
        assertNotNull(body);
        assertEquals(dto.getDescription(), body.getDescription());
        assertEquals(dto.getLat(), body.getLat(), 0.00000001);
        assertEquals(dto.getLon(), body.getLon(), 0.00000001);
        assertEquals(dto.getRating(), body.getRating(), 0.001);
        assertEquals(1L, (long) body.getNumberOfVotes());
        assertEquals(dto.getBase64Image(), body.getBase64Image());
    }

    @Test
    public void testPagination() throws Exception {

        ClassPathResource resource = new ClassPathResource("/lagkage.jpg");

        String base64Image;
        try(InputStream inputStream = resource.getInputStream()) {
            byte[] bytes = IOUtils.toByteArray(inputStream);
            base64Image = Base64.encodeBase64String(bytes);
        }

        for(int i = 0; i < 50; i++){

            ReviewableDTO dto = new ReviewableDTO()
                    .setDescription("Look delicious food")
                    .setLat(55.693838)
                    .setLon(12.493249)
                    .setRating(5.0)
                    .setBase64Image(base64Image);

            assertEquals(HttpStatus.OK, template.postForEntity("/review/", dto, IDResult.class).getStatusCode());
        }

        ReviewableDTO[] reviews = template.getForEntity("/review?page=0&size=30", ReviewableDTO[].class).getBody();
        assertNotNull(reviews);
        assertEquals(30, reviews.length);
        reviews = template.getForEntity("/review?page=1&size=30", ReviewableDTO[].class).getBody();
        assertNotNull(reviews);
        assertEquals(20, reviews.length);



    }


}
