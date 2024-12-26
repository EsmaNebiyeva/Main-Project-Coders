package org.example.project.service.subscribetion;

import java.util.Optional;

import org.example.project.entity.subscribetion.Payment;
import org.example.project.repository.subscribetion.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
@Service
public class PaymentServiceImpl implements PaymentServicr{
@Autowired
private final PaymentRepository paymentRepository;
    @Override
    public Boolean findByPayment(Payment payment) {
        Optional<Payment> byNumberAndNameAndCvvAndDate = paymentRepository.findByNumberAndNameAndCvvAndDate(payment);
        if(byNumberAndNameAndCvvAndDate.isPresent()){
        
            return true;
        }else{
            return false;
        }
    }
    
}
