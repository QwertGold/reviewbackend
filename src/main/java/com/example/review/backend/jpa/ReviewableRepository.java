package com.example.review.backend.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Klaus Groenbaek
 * Created  15/04/2019
 */
public interface ReviewableRepository extends JpaRepository<Reviewable, Long> {
    Reviewable findByUniqueId(String uniqueId);
}
