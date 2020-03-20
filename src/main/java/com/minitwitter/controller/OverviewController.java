package com.minitwitter.controller;

import com.minitwitter.domain.dto.UserOverviewDTO;
import com.minitwitter.service.OverviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("overview")
@Slf4j
public class OverviewController extends ExceptionHandlingController {

  private OverviewService overviewService;

  public OverviewController(OverviewService overviewService) {
    this.overviewService = overviewService;
  }

  @GetMapping(value = "{username}")
  public UserOverviewDTO userOverview(@PathVariable String username) {
    return overviewService.getUserOverview(username);
  }

}
