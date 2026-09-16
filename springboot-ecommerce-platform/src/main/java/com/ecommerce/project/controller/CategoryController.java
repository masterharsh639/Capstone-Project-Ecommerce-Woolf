package com.ecommerce.project.controller;

import com.ecommerce.project.config.AppConstants;
import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;
import com.ecommerce.project.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/* @RestController & @RequestMapping are class level annotations and picked by Swagger automatically */
@RestController
@RequestMapping("/api")     // Now, all APIs start with "/api"
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /* Service dependency is being injected into the controller via @Service annotation.
    This is Constructor Injection, we can also use field injection via @Autowired annotation */
//    public CategoryController(CategoryService categoryService) {
//        this.categoryService = categoryService;
//    }

//    @GetMapping("/echo")
//    public ResponseEntity<String> echoMessage(@RequestParam(name = "message", defaultValue = "Default value") String message){
//    public ResponseEntity<String> echoMessage(@RequestParam(name = "message", required = false) String message){
//        return new ResponseEntity<>("Echoed message: " + message, HttpStatus.OK);
//    }

    /* Remember not to use too much Swagger annotations as they made the code bulky and unreadable */

    @Tag(name = "Category APIs", description = "APIs for managing categories")  // Method level annot. for Swagger
    @Operation(summary = "Get all category", description = "API to get all categories")
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "Invalid Input!", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error!", content = @Content)
    })
    @GetMapping("/public/categories")
    public ResponseEntity<CategoryResponse> getAllCategories(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_CATEGORIES_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder)
    {
        CategoryResponse categoryResponse = categoryService.getAllCategories(pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(categoryResponse, HttpStatus.OK);
    }

    @Tag(name = "Category APIs", description = "APIs for managing categories")
    @Operation(summary = "Create a category", description = "API to create a new category")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Category created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid Input!", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error!", content = @Content)
    })
    @PostMapping("/public/categories")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO){
        CategoryDTO savedCategoryDTO = categoryService.createCategory(categoryDTO);
        return new ResponseEntity<>(savedCategoryDTO, HttpStatus.CREATED);
    }

    /* ResponseEntity is required to handle deletion and validation part with status codes */
    @Tag(name = "Editing Category APIs", description = "APIs for updating and deleting categories")
    @Operation(summary = "Delete a category", description = "API to delete a category")
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "Invalid Input!", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error!", content = @Content)
    })
    @DeleteMapping("/admin/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> deleteCategory(@Parameter(description = "Id of the category that you wish to delete")
                                                          @PathVariable Long categoryId){
            CategoryDTO deletedCategoryDTO = categoryService.deleteCategory(categoryId);
            return new ResponseEntity<>(deletedCategoryDTO, HttpStatus.OK);
                            // OR
//            return ResponseEntity.ok(status);
//            return ResponseEntity.status(HttpStatus.OK).body(status);
    }

    @Tag(name = "Editing Category APIs", description = "APIs for updating and deleting categories")
    @Operation(summary = "Update a category", description = "API to update a category")
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "Invalid Input!", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error!", content = @Content)
    })
    @PutMapping("/admin/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO,
                                                      @Parameter(description = "Id of the category that you wish to update")
                                                      @PathVariable Long categoryId){
            CategoryDTO savedCategoryDTO = categoryService.updateCategory(categoryDTO, categoryId);
            return new ResponseEntity<>(savedCategoryDTO, HttpStatus.OK);
    }

}
