package com.identify.product.FamilyKart.practise.interfacepractise.session15Exception;

public class IValidateImpl implements IValidateService {
    @Override
    public boolean validateUserName(String userName) {
        return false;
    }

    @Override
    public boolean validatePassword(String password) {
        return false;
    }
}
