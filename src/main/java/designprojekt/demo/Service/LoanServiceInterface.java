package designprojekt.demo.Service;

import org.springframework.stereotype.Service;

@Service
public interface LoanServiceInterface {
    double amountToPayBack(int k0, double R, int n);
    double howManyPaymentsLeft(double monthlyPayment, double k, int timesPayed);
}
