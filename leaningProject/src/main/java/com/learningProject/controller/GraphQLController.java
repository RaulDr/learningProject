package com.learningProject.controller;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class GraphQLController {

//    private final GraphQL graphQL;
//
//    public GraphQLController(UserService userService) {
//        GraphQLSchema schema = new GraphQLSchemaGenerator()
//                .withResolverBuilders(
//                        //Resolve by annotations
//                        new AnnotatedResolverBuilder())
//                .withOperationsFromSingleton(userService)
//                .withValueMapperFactory(new JacksonValueMapperFactory())
//                .generate();
//        graphQL = GraphQL.newGraphQL(schema).build();
//    }
//
//    @PostMapping(value = "/graphqlno", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    @ResponseBody
//    public Map<String, Object> graphql(@RequestBody Map<String, String> request, HttpServletRequest raw) {
//        ExecutionResult executionResult = graphQL.execute(ExecutionInput.newExecutionInput()
//                .query(request.get("query"))
//                .operationName(request.get("operationName"))
//                .context(raw)
//                .build());
//        return executionResult.toSpecification();
//    }
}
