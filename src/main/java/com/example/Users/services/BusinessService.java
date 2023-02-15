package com.example.Users.services;

import com.example.Users.dto.BusinessDTO;
import com.example.Users.dto.ValidationStatus;
import com.example.Users.entities.Business;

public interface BusinessService {

    public Business addBusinessUsers(BusinessDTO businessDTO);

    public Business getBusinessById(String businessId);

    public Business getBusinessByName(String businessName);

    public ValidationStatus addOrUpdateBio(String businessId,String bio);

    public ValidationStatus sendModeratorRequest(String businessId,String userId);
}
