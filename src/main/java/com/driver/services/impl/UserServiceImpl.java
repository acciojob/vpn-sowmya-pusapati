package com.driver.services.impl;

import com.driver.model.Country;
import com.driver.model.CountryName;
import com.driver.model.ServiceProvider;
import com.driver.model.User;
import com.driver.repository.CountryRepository;
import com.driver.repository.ServiceProviderRepository;
import com.driver.repository.UserRepository;
import com.driver.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository3;
    @Autowired
    ServiceProviderRepository serviceProviderRepository3;
    @Autowired
    CountryRepository countryRepository3;

    @Override
    public User register(String username, String password, String countryName) throws Exception{
         if(!isValidCountryName(countryName))
         {
             throw new Exception("Country not found");
         }
         Country country=new Country();
         country.setCountryName(getCountryName(countryName));
         country.setCode(getCountryName(countryName).toCode());

         User user=new User();
         user.setUsername(username);
         user.setPassword(password);
         user.setConnected(false);
         user.setOriginalCountry(country);
         country.setUser(user);

         userRepository3.save(user);
        user.setOriginalIp(country.getCode()+"."+user.getId());
        userRepository3.save(user);

        return user;

    }

    @Override
    public User subscribe(Integer userId, Integer serviceProviderId) {
          User user=userRepository3.findById(userId).get();
        ServiceProvider serviceProvider=serviceProviderRepository3.findById(serviceProviderId).get();
        user.getServiceProviderList().add(serviceProvider);
        serviceProvider.getUsers().add(user);
        userRepository3.save(user);
        return user;

    }

    private boolean isValidCountryName(String countryName)
    {
        if(countryName.equalsIgnoreCase("IND")){
            return true;
        }
        else if(countryName.equalsIgnoreCase("USA")){
            return true;
        }
        else if(countryName.equalsIgnoreCase("AUS"))
            return true;
        else if(countryName.equalsIgnoreCase("CHI"))
            return true;
        else if(countryName.equalsIgnoreCase("JPN"))
            return true;
        else
            return false;
    }
    public CountryName getCountryName(String countryName) throws Exception
    {
        if(countryName.equalsIgnoreCase("IND"))
        {
            return CountryName.IND;
        }
        if(countryName.equalsIgnoreCase("USA"))
        {
            return CountryName.USA;
        }
        if(countryName.equalsIgnoreCase("AUS"))
        {
            return CountryName.AUS;
        }
        if(countryName.equalsIgnoreCase("CHI"))
        {
            return CountryName.CHI;
        }
        if(countryName.equalsIgnoreCase("JPN"))
        {
            return CountryName.JPN;
        }
        throw new Exception("Country not found");
    }
}
