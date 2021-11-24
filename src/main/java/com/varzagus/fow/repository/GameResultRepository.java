package com.varzagus.fow.repository;

import com.varzagus.fow.domain.GameResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface GameResultRepository extends CrudRepository<GameResult,Long> {

}
