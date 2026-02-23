package com.karot.mrs.backend.repositories;

import com.karot.mrs.backend.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    List<Movie> findByGenreIgnoreCase(String genre);

    List<Movie> findByTitleContainingIgnoreCase(String title);

}
