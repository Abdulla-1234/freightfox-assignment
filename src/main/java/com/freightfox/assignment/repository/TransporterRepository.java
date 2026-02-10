package com.freightfox.assignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.freightfox.assignment.entity.Transporter;

@Repository
public interface TransporterRepository extends JpaRepository<Transporter, Long> {
}