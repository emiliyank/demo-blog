package com.blog.demo.image;

import org.springframework.data.repository.CrudRepository;

public interface ImageOneToOneRepository extends CrudRepository<Image, Integer> {
}
