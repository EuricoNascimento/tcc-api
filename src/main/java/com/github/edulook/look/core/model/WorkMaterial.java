package com.github.edulook.look.core.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

@Entity
@Table(name = "work_materials")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WorkMaterial {
    @Id
    private String id;
    @Column(name = "course_id")
    private String courseId;
    @Column
    private String title;
    @Column
    private String description;
    @Column
    private String createdAt;
    @OneToMany(mappedBy = "workMaterial", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Material> materials;
    @ManyToMany
    @JoinTable(
            name = "work_material_access",
            joinColumns = @JoinColumn(name = "work_material_id"),
            inverseJoinColumns = @JoinColumn(name = "access_id")
    )
    private List<UserCourseAccess> access;

    public void forEachMaterial(Consumer<Material> consumer) {
        if (Objects.isNull(materials))
            return;

        materials.forEach(consumer);
    }

    public WorkMaterial also(Consumer<WorkMaterial> consumer) {
        consumer.accept(this);
        return this;
    }
}
