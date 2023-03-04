package com.driver.services.impl;

import com.driver.model.*;
import com.driver.repository.ConnectionRepository;
import com.driver.repository.ServiceProviderRepository;
import com.driver.repository.UserRepository;
import com.driver.services.ConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConnectionServiceImpl implements ConnectionService {
    @Autowired
    UserRepository userRepository2;
    @Autowired
    ServiceProviderRepository serviceProviderRepository2;
    @Autowired
    ConnectionRepository connectionRepository2;

    @Override
    public User connect(int userId, String countryName) throws Exception{
        User user=userRepository2.findById(userId).get();
       List<ServiceProvider> serviceProviderList=user.getServiceProviderList();

        if(user.getConnected()==true)
        {
            throw new Exception("Already connected");
        }
        if(countryName.equalsIgnoreCase(user.getOriginalCountry().getCountryName().toString())){
            return user;
        }


        ServiceProvider serviceProvider=null;
        int serviceId=Integer.MAX_VALUE;
        String countryCode=null;
        for(ServiceProvider serviceProvider1:serviceProviderList)
        {
            for(Country country:serviceProvider1.getCountryList()){
                if(countryName.equalsIgnoreCase(country.getCountryName().toString())&&serviceProvider1.getId()<serviceId)
                {
                    serviceProvider=serviceProvider1;
                    serviceId=serviceProvider1.getId();
                    countryCode=country.getCode();
                }
            }
        }
        if(serviceProvider==null)
        {
            throw new Exception("Unable to connect");

        }

        Connection connection=new Connection();
        connection.setUser(user);

        connection.setServiceProvider(serviceProvider);
        connectionRepository2.save(connection);
        serviceProvider.getConnectionList().add(connection);
        String maskedIp=countryCode+"."+serviceProvider.getId()+"."+user.getId();
        user.setMaskedIp(maskedIp);
        user.setConnected(true);
        user.getConnectionList().add(connection);
        serviceProviderRepository2.save(serviceProvider);

        userRepository2.save(user);


       return user;


    }
    @Override
    public User disconnect(int userId) throws Exception {
         User user=userRepository2.findById(userId).get();
         if(user.getConnected()==true)
         {
             throw  new Exception("Already disconnected");
         }

         user.setConnected(false);
         user.setMaskedIp(null);
         userRepository2.save(user);

          return user;
    }
    @Override
    public User communicate(int senderId, int receiverId) throws Exception {
       User sender=userRepository2.findById(senderId).get();
       User receiver=userRepository2.findById(receiverId).get();
       String receiverCountryCode=null;
         if(receiver.getConnected())
         {
             receiverCountryCode=receiver.getMaskedIp().substring(0,3);

         }
         else{
             receiverCountryCode=receiver.getOriginalCountry().getCode();
         }
         String senderCountryCode=sender.getOriginalCountry().getCode();
         if(senderCountryCode.equals(receiverCountryCode)){
             return sender;
         }
         String receiverCountryName= String.valueOf(receiver.getOriginalCountry().getCountryName());
         try{
             User updatedSender=connect(senderId,receiverCountryName);
             userRepository2.save(updatedSender);
             return updatedSender;
         }
         catch(Exception e)
         {
             throw new Exception("Cannot establish communication");
         }

    }
}
