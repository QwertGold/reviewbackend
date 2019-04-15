package com.example.review.backend.jpa;

import com.example.review.backend.dto.ReviewableDTO;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.UUID;

/**
 * @author Klaus Groenbaek
 * Created  15/04/2019
 */
@SuppressFBWarnings(value = {"EI_EXPOSE_REP", "EI_EXPOSE_REP2"}, justification = "We don't care about exposing mutable objects")
@Data
@Accessors(chain = true)
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reviewable {

    public static Reviewable fromDTO(ReviewableDTO dto) {
        Reviewable reviewable = new Reviewable()
                .setUniqueId(DigestUtils.sha1Hex(UUID.randomUUID().toString()))
                .setDescription(dto.getDescription())
                .setLat(dto.getLat())
                .setLon(dto.getLon())
                .setImage(Base64.decodeBase64(dto.getBase64Image()));


        if (dto.getRating() != null) {
            reviewable.setRating(dto.getRating());
            reviewable.setNumberOfVotes(1);
        } else {
            reviewable.setNumberOfVotes(0);
        }
        return reviewable;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;
    @Setter(AccessLevel.PRIVATE)
    private String uniqueId;

    @Column(length = 1024)
    private String description;

    @Column(nullable = false)
    private double lon;

    @Column(nullable = false)
    private double lat;

    /**
     * rating and number of votes, is used to calculate the average rating
     */
    @Column()
    private Double rating;

    @Column(nullable = false)
    private long numberOfVotes;

    @Column(nullable = false)
    @Lob
    private byte[] image;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column
    @Setter(AccessLevel.PRIVATE)
    private Date creationDate = new Date();

    public ReviewableDTO toDTO() {
        return new ReviewableDTO()
                .setId(uniqueId)
                .setDescription(description)
                .setLat(lat)
                .setLon(lon)
                .setNumberOfVotes(numberOfVotes)
                .setRating(rating)
                .setBase64Image(Base64.encodeBase64String(image));
    }
}
