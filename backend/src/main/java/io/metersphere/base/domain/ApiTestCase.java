package io.metersphere.base.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class ApiTestCase implements Serializable {
    private String id;

    private String projectId;

    private String name;

    private String priority;

    private String apiDefinitionId;

    private String description;

    private String request;

    private String response;

    private String createUserId;

    private String updateUserId;

    private Long createTime;

    private Long updateTime;

    private static final long serialVersionUID = 1L;
}