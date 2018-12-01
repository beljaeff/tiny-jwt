package us.cleansite.very.tinyjwt;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import us.cleansite.very.tinyjwt.entity.Payload;

@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
class PayloadExample extends Payload {
    private String data;
}
