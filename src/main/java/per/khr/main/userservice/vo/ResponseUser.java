package per.khr.main.userservice.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * 주로 JSON으로 변환해서 리턴할 데이터
 * @JSONInclude(JsonInclude.Include.NON_NULL) -> JSON으로 응답을 할건데 Null이 없게끔
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseUser {
    private String email;
    private String name;
    private String userId;


}
