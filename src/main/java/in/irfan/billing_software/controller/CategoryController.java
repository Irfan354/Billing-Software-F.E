package in.irfan.billing_software.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import in.irfan.billing_software.io.CategoryRequest;
import in.irfan.billing_software.io.CategoryResponse;
import in.irfan.billing_software.service.CategoryService;
import lombok.RequiredArgsConstructor;

@RestController //method in this class returns JSON by default, suitable for APIs.
@RequestMapping  //Sets the base path for all HTTP endpoints in this controller.
@RequiredArgsConstructor //automatically generates a constructor with required arguments like --> this.repository= repository;

public class CategoryController {

    private final CategoryService categoryService; //(service layer) used to handle the business logic.

    @PostMapping("/admin/categories")       //Handles HTTP POST requests to '/categories'.
    @ResponseStatus(HttpStatus.CREATED)     //Returns or this will response - HTTP 201 (Created) status when this method executes successfully.
    public CategoryResponse addCategory(@RequestPart("category") String categoryString,
                                        @RequestPart("file") MultipartFile file){ 
        ObjectMapper objectMapper = new ObjectMapper(); //data binds
        CategoryRequest request = null;
        try {
            request = objectMapper.readValue(categoryString, CategoryRequest.class);
            return categoryService.add(request, file);  

        } catch (JsonProcessingException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Exception occurs while parsing the json:"+ex.getMessage());
        }
       }    

   
/*Purpose: Handles HTTP POST requests to create a new category, 
    receiving a CategoryRequest and returning a CategoryResponse. */

/*Logic:
    Calls categoryService.add(request) to process the request and create a new category.
    Returns the result (CategoryResponse) directly, which Spring serializes to JSON for the client uses (@Request body).
 */

 //method 2
    @GetMapping
    public List<CategoryResponse> fetchAllCategories(){
        return categoryService.read();
    }


 //method 3 - Delete

@DeleteMapping(value = "/admin/categories/{categoryId}")
@ResponseStatus(HttpStatus.NO_CONTENT)
public void remove(@PathVariable String categoryId){
    try {
        categoryService.delete(categoryId);
    } catch (Exception e) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
    }
}

}
