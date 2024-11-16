package com.github.edulook.look.core.model;

import com.github.edulook.look.core.data.Option;
import com.github.edulook.look.core.data.Typename;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Material {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer internalId;

    private String id;

    private String name;

    private String description;

    private String type;

    private String originLink;

    private String previewLink;
    @OneToOne(cascade = CascadeType.ALL)
    private PageContent content;
    @Embedded
    private Option option;
    @ManyToOne
    private WorkMaterial workMaterial;


    public Material(Integer internalId, String id,
                    String name, String description, String type, String originLink,
                    String previewLink, PageContent content,
                    WorkMaterial workMaterial) {
        this.internalId = internalId;
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.originLink = originLink;
        this.previewLink = previewLink;
        this.content = content;
        this.option = Option.withDefaults();
        this.workMaterial = workMaterial;
    }

    public Optional<PageContent> getContent() {
        return Optional.ofNullable(content);
    }

    public Optional<Option> getOption() {
        return Optional.ofNullable(option);
    }

    public boolean isPDFContent() {
        return Objects.nonNull(getType()) && getType().equalsIgnoreCase(Typename.PDF);
    }

    public boolean notPDFContent() {
        return !isPDFContent();
    }

    public void ifOptionPresent(Consumer<Option> consumer) {
        getOption().ifPresent(consumer);
    }
}
