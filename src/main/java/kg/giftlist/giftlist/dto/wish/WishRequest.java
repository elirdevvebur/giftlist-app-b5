package kg.giftlist.giftlist.dto.wish;

import kg.giftlist.giftlist.models.Holiday;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
public class WishRequest {
    private String photo;
    private String giftName;
    private String giftLink;
    private String description;
    private String holidayName;
    private LocalDate wishDate;
}