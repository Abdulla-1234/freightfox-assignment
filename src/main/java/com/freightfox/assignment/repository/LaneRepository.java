package com.freightfox.assignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.freightfox.assignment.entity.Lane;

@Repository
public interface LaneRepository extends JpaRepository<Lane, Long> {
    // JpaRepository gives us methods like save(), findAll(), findById() automatically!
}