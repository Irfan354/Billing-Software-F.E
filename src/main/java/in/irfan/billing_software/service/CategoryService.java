package in.irfan.billing_software.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import in.irfan.billing_software.io.CategoryRequest;
import in.irfan.billing_software.io.CategoryResponse;

public interface CategoryService {
    //1.POST - to add category --> needs save() method
    CategoryResponse add(CategoryRequest request, MultipartFile file);//in services only methods are wriiten --> keeps logic modular


    //2.GET --> to read --> we need findAll()
    List<CategoryResponse> read(); //we are getting the collection of response in Category REsponse

    //3.DELETE --> delete() method
    void delete(String categoryId);

}
