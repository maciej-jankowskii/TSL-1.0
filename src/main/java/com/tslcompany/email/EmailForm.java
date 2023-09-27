package com.tslcompany.email;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailForm {

    private String recipientAddress;
    private String emailSubject;
    private String emailText;

}
