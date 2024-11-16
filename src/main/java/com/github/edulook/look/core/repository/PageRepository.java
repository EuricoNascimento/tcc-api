package com.github.edulook.look.core.repository;

import com.github.edulook.look.core.model.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PageRepository extends JpaRepository<Page, UUID> {
}
