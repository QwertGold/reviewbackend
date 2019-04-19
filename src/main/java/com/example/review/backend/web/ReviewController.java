package com.example.review.backend.web;

import com.example.review.backend.dto.IDResult;
import com.example.review.backend.dto.ReviewableDTO;
import com.example.review.backend.jpa.Reviewable;
import com.example.review.backend.jpa.ReviewableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Klaus Groenbaek
 * Created  15/04/2019
 */
@RequestMapping(value = "/review")
@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewableRepository repository;

    @GetMapping(value = "/{id}")
    public ReviewableDTO getReview(@PathVariable("id") String id) {
        Reviewable reviewable = repository.findByUniqueId(id);
        if (reviewable != null) {
            return reviewable.toDTO();
        } else {
            throw new ResourceNotFoundException();
        }
    }

    @PostMapping(value = {"", "/"})
    public IDResult createReviewable(@Valid @RequestBody ReviewableDTO dto) {
        Reviewable reviewable = Reviewable.fromDTO(dto);
        repository.save(reviewable);
        return new IDResult().setId(reviewable .getUniqueId());
    }

    @GetMapping(value = {"", "/"}, params = {"page", "size"})
    public List<ReviewableDTO> list(@RequestParam("page") int pageNumber, @RequestParam("size") int size) {

        Page<Reviewable> page = repository.findAll(PageRequest.of(pageNumber, size));
        return page.stream()
                .map(r->r.toDTO())
                .collect(Collectors.toList());
    }

}
