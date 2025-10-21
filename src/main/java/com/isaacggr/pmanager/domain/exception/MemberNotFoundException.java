package com.isaacggr.pmanager.domain.exception;

import com.isaacggr.pmanager.infrastructure.exception.RequestException;

public class MemberNotFoundException extends RequestException{
    public MemberNotFoundException(String memberId){
        super("MemberNotFound", "Membro não encontrado: " + memberId);
    }
}
