package com.vyaparimitra.vyapari_mitra.service.impl;

import com.vyaparimitra.vyapari_mitra.dto.CustomerDTO;
import com.vyaparimitra.vyapari_mitra.model.Customer;
import com.vyaparimitra.vyapari_mitra.model.Owner;
import com.vyaparimitra.vyapari_mitra.repository.CustomerRepository;
import com.vyaparimitra.vyapari_mitra.service.CustomerService;
import com.vyaparimitra.vyapari_mitra.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OwnerService ownerService;

    @Override
    @Transactional
    public Customer createCustomer(CustomerDTO customerDTO) {

        // Get current logged in owner
        Owner currentOwner = ownerService.getCurrentOwner();

        // Validate required fields
        if (customerDTO.getName() == null || customerDTO.getName().trim().isEmpty()) {
            throw new RuntimeException("ग्राहकाचे नाव आवश्यक आहे!");
        }

        // Validate mobile if provided
        if (customerDTO.getMobile() != null && !customerDTO.getMobile().trim().isEmpty()) {
            String mobile = customerDTO.getMobile().trim();

            // Check if mobile is 10 digits
            if (mobile.length() != 10 || !mobile.matches("\\d+")) {
                throw new RuntimeException("मोबाईल नंबर १० अंकी असणे आवश्यक आहे!");
            }

            // Check if mobile already exists for THIS owner
            if (customerRepository.findByOwnerIdAndMobile(currentOwner.getId(), mobile).isPresent()) {
                throw new RuntimeException("हा मोबाईल नंबर आधीच नोंदणीकृत आहे!");
            }
        }

        // Create new customer
        Customer customer = new Customer();
        customer.setOwner(currentOwner);
        customer.setName(customerDTO.getName().trim());
        customer.setMobile(customerDTO.getMobile() != null ? customerDTO.getMobile().trim() : null);
        customer.setAddress(customerDTO.getAddress() != null ? customerDTO.getAddress().trim() : null);
        customer.setVillage(customerDTO.getVillage() != null ? customerDTO.getVillage().trim() : null);

        // Initialize financial fields
        customer.setTotalCredit(BigDecimal.ZERO);
        customer.setTotalPaid(BigDecimal.ZERO);
        customer.setBalance(BigDecimal.ZERO);
        customer.setCreatedAt(LocalDateTime.now());

        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> getAllCustomers() {
        Owner currentOwner = ownerService.getCurrentOwner();
        return customerRepository.findByOwnerId(currentOwner.getId());
    }

    @Override
    public Customer getCustomerById(Long id) {

        Owner currentOwner = ownerService.getCurrentOwner();

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ग्राहक सापडला नाही! ID: " + id));

        // Verify customer belongs to current owner
        if (!customer.getOwner().getId().equals(currentOwner.getId())) {
            throw new RuntimeException("तुम्हाला हा ग्राहक पाहण्याची परवानगी नाही!");
        }

        return customer;
    }

    @Override
    @Transactional
    public Customer updateCustomer(Long id, CustomerDTO customerDTO) {

        Owner currentOwner = ownerService.getCurrentOwner();

        Customer customer = getCustomerById(id);

        // Verify customer belongs to current owner
        if (!customer.getOwner().getId().equals(currentOwner.getId())) {
            throw new RuntimeException("तुम्हाला या ग्राहकाची माहिती बदलण्याची परवानगी नाही!");
        }

        // Update name if provided
        if (customerDTO.getName() != null && !customerDTO.getName().trim().isEmpty()) {
            customer.setName(customerDTO.getName().trim());
        }

        // Update mobile if provided
        if (customerDTO.getMobile() != null && !customerDTO.getMobile().trim().isEmpty()) {
            String newMobile = customerDTO.getMobile().trim();

            // Validate mobile
            if (newMobile.length() != 10 || !newMobile.matches("\\d+")) {
                throw new RuntimeException("मोबाईल नंबर १० अंकी असणे आवश्यक आहे!");
            }

            // Check if mobile is already taken by another customer of THIS owner
            if (!newMobile.equals(customer.getMobile())) {
                customerRepository.findByOwnerIdAndMobile(currentOwner.getId(), newMobile).ifPresent(existingCustomer -> {
                    if (!existingCustomer.getId().equals(id)) {
                        throw new RuntimeException("हा मोबाईल नंबर दुसऱ्या ग्राहकाकडे आहे!");
                    }
                });
            }

            customer.setMobile(newMobile);
        }

        // Update address if provided
        if (customerDTO.getAddress() != null) {
            customer.setAddress(customerDTO.getAddress().trim());
        }

        // Update village if provided
        if (customerDTO.getVillage() != null) {
            customer.setVillage(customerDTO.getVillage().trim());
        }

        return customerRepository.save(customer);
    }

    @Override
    @Transactional
    public void deleteCustomer(Long id) {

        Owner currentOwner = ownerService.getCurrentOwner();

        Customer customer = getCustomerById(id);

        // Verify customer belongs to current owner
        if (!customer.getOwner().getId().equals(currentOwner.getId())) {
            throw new RuntimeException("तुम्हाला हा ग्राहक हटवण्याची परवानगी नाही!");
        }

        // Check if customer has any pending balance
        if (customer.getBalance().compareTo(BigDecimal.ZERO) > 0) {
            throw new RuntimeException("या ग्राहकावर उधारी थकबाकी आहे! प्रथम पैसे वसूल करा. बाकी: ₹" +
                    customer.getBalance());
        }

        customerRepository.delete(customer);
    }

    @Override
    public List<Customer> searchCustomers(String keyword) {

        Owner currentOwner = ownerService.getCurrentOwner();

        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllCustomers();
        }

        return customerRepository.searchCustomers(currentOwner.getId(), keyword.trim());
    }

    @Override
    public List<Customer> getCustomersWithBalance() {
        Owner currentOwner = ownerService.getCurrentOwner();
        return customerRepository.findByOwnerIdAndBalanceGreaterThan(currentOwner.getId(), BigDecimal.ZERO);
    }

    @Override
    public List<Customer> getCustomersByVillage(String village) {

        Owner currentOwner = ownerService.getCurrentOwner();

        if (village == null || village.trim().isEmpty()) {
            return getAllCustomers();
        }

        return customerRepository.findByOwnerIdAndVillageIgnoreCase(currentOwner.getId(), village.trim());
    }

    @Override
    public long getCustomerCount() {
        Owner currentOwner = ownerService.getCurrentOwner();
        return customerRepository.countByOwnerId(currentOwner.getId());
    }
}