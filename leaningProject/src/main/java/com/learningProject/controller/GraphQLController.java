package com.learningProject.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learningProject.service.UserService;

import graphql.ErrorType;
import graphql.ExceptionWhileDataFetching;
import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.GraphQLError;
import graphql.language.SourceLocation;
import graphql.schema.GraphQLSchema;
import io.leangen.graphql.GraphQLSchemaGenerator;
import io.leangen.graphql.metadata.strategy.query.AnnotatedResolverBuilder;
import io.leangen.graphql.metadata.strategy.value.jackson.JacksonValueMapperFactory;
import javassist.NotFoundException;

@RestController
public class GraphQLController {

    private final GraphQL graphQL;

    public GraphQLController(UserService userService) {
        GraphQLSchema schema = new GraphQLSchemaGenerator()
                .withResolverBuilders(
                        //Resolve by annotations
                        new AnnotatedResolverBuilder())
                .withOperationsFromSingleton(userService)
                .withValueMapperFactory(new JacksonValueMapperFactory())
                .generate();
        graphQL = GraphQL.newGraphQL(schema).build();
    }

    @PostMapping(value = "/graphql", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public /*Map<String, Object>*/ Object graphql(@RequestBody Map<String, String> request) {
        ExecutionResult executionResult = graphQL.execute(ExecutionInput.newExecutionInput()
                .query(request.get("query"))
                .operationName(request.get("operationName"))
                .variables(getVariables(request.get("variables")))
//                .context(raw)
                .build());
//        if (!executionResult.getErrors().isEmpty()) {
//            return sanitize(executionResult.getErrors().get(0));
//        }
        return executionResult;
//        return executionResult.toSpecification();
    }
    
    private Map<String, Object> getVariables(final Object request) {
        if (request instanceof Map) {
            return (Map<String, Object>) request;
        } else if (request instanceof String) {
            try {
                return new ObjectMapper().readValue((String) request, HashMap.class);
            } catch (final IOException e) {
                System.out.println(e.getLocalizedMessage());
                return null;
            }
        }
        return null;
    }
    
    private GraphQLError sanitize(GraphQLError error) {
        if (error instanceof ExceptionWhileDataFetching) {
            return new GraphQLError() {
                @Override
                public String getMessage() {
                    Throwable cause = ((ExceptionWhileDataFetching) error).getException().getCause();
                    return cause instanceof NotFoundException ? ((NotFoundException) cause).getMessage() : cause.getMessage();
                }

                @Override
                public List<SourceLocation> getLocations() {
                    return error.getLocations();
                }

                @Override
                public ErrorType getErrorType() {
                    return error.getErrorType();
                }
            };
        }
        return error;
    }
}
