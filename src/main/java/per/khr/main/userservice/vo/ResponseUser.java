package per.khr.main.userservice.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * 주로 JSON으로 변환해서 리턴할 데이터
 * @JSONInclude(JsonInclude.Include.NON_NULL)
 * -> JSON으로 응답을 할건데 특정 key 값이 Null인 경우에는 해당 key를 아예 포함하지않고 가게끔
 * ex) {
 *     "name": "김형록"
 *     "password": "123"
 *     "userId": "" (X) userId가 널이기때문에 아예 key 값에서 제외
 * }
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseUser {
    private String email;
    private String name;
    private String userId;
}
