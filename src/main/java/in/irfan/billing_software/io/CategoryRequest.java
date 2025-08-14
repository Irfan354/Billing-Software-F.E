package in.irfan.billing_software.io;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryRequest {
    //as a request in F.E am adding name, desc, img and sending it to DB and expecting Resposnse in 7 entity's
    private String name;
    private String description;
    public String bgColor;
  

}
