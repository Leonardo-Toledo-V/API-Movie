package upchi.api.movie.director.controllers.dtos.requests;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdateDirectorRequest {
    
    private String nationality;
    private Integer age;

}