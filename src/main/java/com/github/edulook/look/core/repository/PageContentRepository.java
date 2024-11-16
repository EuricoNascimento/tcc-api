package com.github.edulook.look.core.repository;

import com.github.edulook.look.core.model.PageContent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PageContentRepository extends JpaRepository<PageContent, UUID> {
}
