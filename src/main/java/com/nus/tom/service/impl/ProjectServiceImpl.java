package com.nus.tom.service.impl;

import com.nus.tom.model.Project;
import com.nus.tom.model.ResponseValueObject;
import com.nus.tom.repository.ProjectRepository;
import com.nus.tom.service.ProjectService;
import com.nus.tom.util.JsonHandler;
import com.nus.tom.util.ResponseHelper;
import com.nus.tom.util.TOMConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * @author Mya Pwint
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final JsonHandler jsonHandler;

    private final ResponseHelper responseHelper;

    @Override
    public ResponseEntity<ResponseValueObject> save(String payload) {
        try {
            Project project = jsonHandler.fromJson(payload, Project.class);
            projectRepository.save(project);
            return responseHelper.setResponseEntity(TOMConstants.SUCCESS, TOMConstants.EMPTY_STRING, project.getId());
        } catch (Exception ex) {
            log.error("Exception in saving project {}", ex.getStackTrace());
            return responseHelper.setResponseEntity(TOMConstants.ERROR, TOMConstants.EMPTY_STRING, payload);
        }

    }
}
