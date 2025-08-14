package in.irfan.billing_software.service.impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import in.irfan.billing_software.entity.CategoryEntity;
import in.irfan.billing_software.io.CategoryRequest;
import in.irfan.billing_software.io.CategoryResponse;
import in.irfan.billing_software.repository.CategoryRepository;
import in.irfan.billing_software.service.CategoryService;
import in.irfan.billing_software.service.FileUploadService;
import lombok.Builder;
import lombok.Data;

@Service    //It's used to write business logic (like saving categories in DB).
@Data   //instead of required Args Const - i used Data - it will manages all methods
@Builder
public class CategoryServiceImpl  implements CategoryService{

    private final CategoryRepository categoryRepository;
    private final FileUploadService fileUploadService;

    //method 1 - add method from service --> 1.add, 2. convert, 3.save, 4.response
    @Override
    public CategoryResponse add(CategoryRequest request, MultipartFile file) {      //adding the entity for new category which client requests
        String imgUrl = fileUploadService.uploadFile(file);
        CategoryEntity newCategory = convertToEntity(request);  //converting categoryRequest 3 propt. entity to category entity all 7 propt.
        newCategory = categoryRepository.save(newCategory);  //using CategoryRepositorty im saving the converted data into Category Entity of variable newCategory
        newCategory.setImgUrl(imgUrl);
        return  convertToResponse(newCategory);                    //after saved and converted --> now giving response to Category response with 7properties 
    }

private CategoryResponse convertToResponse(CategoryEntity newCategory) {
        return CategoryResponse.builder()
                .categoryId(newCategory.getCategoryId())
                .name(newCategory.getName())
                .description(newCategory.getDescription())
                .bgColor(newCategory.getBgColor())
                .imgUrl(newCategory.getImgUrl())
                .createdAt(newCategory.getCreatedAt())
                .updatedAt(newCategory.getUpdatedAt())
                .build();
                //Authenticationmanager ---- username aur password.
                // UserDetailsService.loadUserByUsername
                //Userdetails userdetails = 
                //generateToken(userdetails)
}

private CategoryEntity convertToEntity(CategoryRequest request) {
        return CategoryEntity.builder()
                .categoryId(UUID.randomUUID().toString())
                .name(request.getName())
                .description(request.getDescription())
                .bgColor(request.getBgColor())
                .build();
}

//method 2 from service for read - to read we need to findAll in collection List.
@Override
public List<CategoryResponse> read() {
    return categoryRepository.findAll()
    .stream()
    .map(this::convertToResponse)
    .collect(Collectors.toList());
}

//method 3 delete the record using category id
@Override
public void delete(String categoryId) {
   CategoryEntity existingCategory = categoryRepository.findByCategoryId(categoryId)
    .orElseThrow(()-> new RuntimeException("Category Not found :" + categoryId));
    fileUploadService.deleteFile(existingCategory.getImgUrl());
    categoryRepository.delete(existingCategory);
}


}