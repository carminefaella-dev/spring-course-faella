package com.example.spring_course.controller;

import com.example.spring_course.controller.model.request.UserRequest;
import com.example.spring_course.controller.model.response.UserResponse;
import com.example.spring_course.service.UserService;
import com.example.spring_course.service.dto.UserRequestDTO;
import com.example.spring_course.service.dto.UserResponseDTO;
import com.example.spring_course.service.mapper.UserMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping ("/api/v1")
@Tag(
    name = "User Management",
    description = "APIs for managing users, including creation, retrieval, update and deletion operation"
)
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/getAllUser")
    @Operation(
            summary = "Retrieve all users",
            description = "Returns the complete list of users registered in the system"
    )
    @ApiResponses({
        @ApiResponse(
                responseCode = "200",
                description = "Users successfully retrieved",
                content = @Content(
                        mediaType = "application/json",
                        examples = @ExampleObject(
                                name = "UsersList",
                                value = """
                            [
                              {
                                "firstName": "Mario",
                                "lastName": "Rossi",
                                "username": "mrossi",
                                "email": "mario.rossi@example.com"
                              }
                            ]
                            """
                        )
                )
        ),
        @ApiResponse(
                responseCode = "500",
                description = "Internal server error"
        )
    })
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> responses = userService.getAllUser()
                .stream()
                .map(userMapper::mapResponse)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/getUserByName/{firstName}")
    @Operation(
        summary = "Search users by first name",
        description = "Retrieves all users matching the provided first name"
    )
    @ApiResponses({
        @ApiResponse(
                responseCode = "200",
                description = "Users successfully retrieved",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = UserResponse.class),
                        examples = @ExampleObject(
                                name = "UsersFound",
                                value = """
                            [
                              {
                                "firstName": "Mario",
                                "lastName": "Rossi",
                                "username": "mrossi",
                                "email": "mario.rossi@example.com"
                              }
                            ]
                            """
                        )
                )
        ),
        @ApiResponse(
                responseCode = "404",
                description = "No users found with the specified first name",
                content = @Content(
                        examples = @ExampleObject(
                                value = """
                            {
                              "message": "No users found"
                            }
                            """
                        )
                )
        )
    })
    public ResponseEntity<List<UserResponse>> getUserByName(
            @PathVariable String firstName) {
        List<UserResponse> responses = userService.searchByName(firstName)
                .stream()
                .map(userMapper::mapResponse)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/getUserByEmail/{email}")
    @Operation(
        summary = "Search user by email",
        description = "Retrieves the user associated with the specified email address"
    )
    @ApiResponses({
        @ApiResponse(
                responseCode = "200",
                description = "User successfully retrieved",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = UserResponse.class),
                        examples = @ExampleObject(
                                name = "UserFound",
                                value = """
                            [
                              {
                                "firstName": "Mario",
                                "lastName": "Rossi",
                                "username": "mrossi",
                                "email": "mario.rossi@example.com"
                              }
                            ]
                            """
                        )
                )
        ),
        @ApiResponse(
                responseCode = "404",
                description = "User not found",
                content = @Content(
                        examples = @ExampleObject(
                                value = """
                            {
                              "message": "User not found"
                            }
                            """
                        )
                )
        )
    })
    public ResponseEntity<List<UserResponse>> getUserByEmail(
            @PathVariable String email) {
        List<UserResponse> responses = userService.searchByEmail(email)
                .stream()
                .map(userMapper::mapResponse)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @PostMapping("/createUser")
    @Operation(
        summary = "Create user",
        description = "Creates a new user in the system using the provided information"
    )
    @ApiResponses({
        @ApiResponse(
                responseCode = "201",
                description = "User successfully created",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = UserResponse.class),
                        examples = @ExampleObject(
                                name = "UserCreated",
                                value = """
                            {
                              "firstName": "Mario",
                              "lastName": "Rossi",
                              "username": "mrossi",
                              "email": "mario.rossi@example.com"
                            }
                            """
                        )
                )
        ),
        @ApiResponse(
                responseCode = "400",
                description = "Invalid request data"
        ),
        @ApiResponse(
                responseCode = "409",
                description = "A user with the provided email already exists"
        )
    })
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserRequest request) {
        UserRequestDTO requestDTO = userMapper.mapRequest(request);
        UserResponseDTO responseDTO = userService.createUser(requestDTO);
        UserResponse response = userMapper.mapResponse(responseDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/updateUser/{email}")
    @Operation(
        summary = "Update an existing user",
        description = "Updates the information of an existing user identified by email"
    )
    @ApiResponses({
        @ApiResponse(
                responseCode = "200",
                description = "User successfully updated",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = UserResponse.class),
                        examples = @ExampleObject(
                                name = "UserUpdated",
                                value = """
                            {
                              "firstName": "Mario",
                              "lastName": "Rossi",
                              "username": "mario.rossi",
                              "email": "mario.rossi@example.com"
                            }
                            """
                        )
                )
        ),
        @ApiResponse(
                responseCode = "400",
                description = "Invalid request data"
        ),
        @ApiResponse(
                responseCode = "404",
                description = "User not found"
        )
    })
    public ResponseEntity<UserResponse> updateUser(@PathVariable String email, @RequestBody @Valid UserRequest request) {
        UserRequestDTO requestDTO = userMapper.mapRequest(request);
        UserResponseDTO responseDTO = userService.updateUser(email, requestDTO);
        UserResponse response = userMapper.mapResponse(responseDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/deleteUser/{email}")
    @Operation(
        summary = "Delete user by email",
        description = "Deletes an existing user identified by the provided email address"
    )
    @ApiResponses({
        @ApiResponse(
                responseCode = "200",
                description = "User successfully deleted",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = UserResponse.class),
                        examples = @ExampleObject(
                                name = "DeletedUser",
                                value = """
                            {
                              "firstName": "Mario",
                              "lastName": "Rossi",
                              "username": "mrossi",
                              "email": "mario.rossi@example.com"
                            }
                            """
                        )
                )
        ),
        @ApiResponse(
                responseCode = "404",
                description = "User not found"
        )
    })
    public ResponseEntity<UserResponse> deleteUser(@PathVariable String email) {
        UserResponseDTO responseDTO = userService.deleteByEmail(email);
        UserResponse response = userMapper.mapResponse(responseDTO);
        return ResponseEntity.ok(response);
    }
}
