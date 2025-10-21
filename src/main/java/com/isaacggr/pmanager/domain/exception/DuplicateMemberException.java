package com.isaacggr.pmanager.domain.exception;

import com.isaacggr.pmanager.infrastructure.exception.RequestException;

public class DuplicateMemberException extends RequestException{
    public DuplicateMemberException(String email){
        super("MemberDuplicate", "Já existe um um membro com este e-mail: " + email);
    }
}
