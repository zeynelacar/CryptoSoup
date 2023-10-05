package com.example.cryptostuff.businessTests;

import com.example.cryptostuff.business.rsa.RSA;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RSATest {

    @InjectMocks
    RSA rsa;

    @Test
    void crackUnsafeRSA(){
        String cip = "62324783949134119159408816513334912534343517300880137691662780895409992760262021";
        String num = "1280678415822214057864524798453297819181910621573945477544758171055968245116423923";
        Long exponent = 65537L;
        String p = "1899107986527483535344517113948531328331";
        String q = "674357869540600933870145899564746495319033" ;
        String decoded = rsa.crackRSAUnsafe(cip,num,exponent,p,q);
        StringBuilder sb = new StringBuilder(decoded);
        sb.reverse();
        Assertions.assertEquals("picoCTF{sma11_N_n0_g0od_05012767}",sb.toString());

    }
}
