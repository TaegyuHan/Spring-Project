package com.project.webapp.film.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "film_text")
public class FilmText {

    @Id
    @Column(name = "film_id", nullable = false)
    private Integer filmId;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    @Lob // LOB (Large Object)  256자 이상인 경우 @Lob 애노테이션을 사용해서 큰 크기의 문자열을 저장할 수 있습니다.
    private String description;
}