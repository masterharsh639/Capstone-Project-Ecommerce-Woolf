package com.ecommerce.project.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {

    @Schema(description = "Category ID", example = "101")
    private Long categoryId;

    @Schema(description = "Category name for the category you wish to create", example = "Sports & Fitness")
    @NotBlank   // Use @Valid in controller layer to get a user-friendly error message and code
    @Size(min = 4, message = "Category name must contain at least 4 characters!")
    private String categoryName;

}

/* Category model represents data at the database level while CategoryDTO represents data
   at the presentation layer and to do a transition from reliance over Category model for
   response to the CategoryDTO model for getting custom response on Postman, we require
   something known as MODEL MAPPER which will map the objects of these two models so that
   we don't need to write custom logic for the same.
   Hence, the goal of Model mapper is to make object mapping easy so that two models remain
   segregated. */
