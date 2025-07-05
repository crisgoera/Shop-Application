package com.goenaga.shop.photo.repository;

import com.goenaga.shop.photo.model.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PhotoRepository extends JpaRepository<Photo, UUID> {
}
