package com.allaroundjava.webpage;

import com.allaroundjava.model.WebPage;
import org.springframework.data.jpa.repository.JpaRepository;

interface WebPageRepository extends JpaRepository<WebPage, Long> {
}
