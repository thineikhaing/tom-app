package com.nus.tom.controller;

import com.nus.tom.model.ResponseValueObject;
import com.nus.tom.service.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("api/tom/project")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping(path = "/saveProject", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseValueObject> save(@RequestBody String project) {
        log.info("project payload received {} ", project);
        return projectService.save(project);
    }

}
