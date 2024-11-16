package com.github.edulook.look.core.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Page {
    @Id
    private UUID id;
    private Integer page;
    @Column(columnDefinition = "TEXT")
    private String content;
    @ManyToOne(fetch = FetchType.EAGER)
    private PageContent pageContent;
}
