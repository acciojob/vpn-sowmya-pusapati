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
//        List<Connection> connectionList=user.getConnectionList();
//        for(Connection connection:connectionList)
//        {
//            if(serviceProviderList.contains(connection.getServiceProvider()))
//            {
//                throw new Exception("Already connected");
//            }
//
//        }
        if(user.isConnected()==true)
        {
            throw new Exception("Already connected");
        }
        if(user.getCountry().equals(countryName)){
            return user;
        }
        ServiceProvider serviceProvider=null;
        for(ServiceProvider serviceProvider1:serviceProviderList)
        {
            if(serviceProvider1.getCountryList().contains(countryName))
            {
                if(serviceProvider==null||serviceProvider1.getId()<serviceProvider.getId())
                {
                    serviceProvider=serviceProvider1;
                }
            }
        }
        if(serviceProvider==null)
        {
            throw new Exception("Unable to connect");

        }

        List<Country> countryList=serviceProvider.getCountryList();
        Connection connection=new Connection();
        connection.setServiceProvider(serviceProvider);


        for(Country country:countryList)
        {
            if(country.getCountryName().equals(countryName))
            {
                user.setCountry(country);
                user.setMaskedIp(country.getCountryCode()+"."+serviceProvider.getId()+"."+userId);
                user.setConnected(true);
                user.getServiceProviderList().add(serviceProvider);
                user.getConnectionList().add(connection);
                serviceProvider.getUserList().add(user);
                serviceProvider.getConnectionList().add(connection);
                connection.setUser(user);

                userRepository2.save(user);
                serviceProviderRepository2.save(serviceProvider);
                break;
            }
        }


       return user;


    }
    @Override
    public User disconnect(int userId) throws Exception {
         User user=userRepository2.findById(userId).get();
         if(user.isConnected()==true)
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
         if(receiver.isConnected())
         {
             receiverCountryCode=receiver.getMaskedIp().substring(0,3);

         }
         else{
             receiverCountryCode=receiver.getCountry().getCountryCode();
         }
         String senderCountryCode=sender.getCountry().getCountryCode();
         if(senderCountryCode.equals(receiverCountryCode)){
             return sender;
         }
         String receiverCountryName= String.valueOf(receiver.getCountry().getCountryName());
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
