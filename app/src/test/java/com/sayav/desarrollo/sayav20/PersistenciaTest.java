package com.sayav.desarrollo.sayav20;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;

/**
 * Created by Naza on 13/6/2017.
 */
@RunWith(JUnit4.class)
public class PersistenciaTest  {
    MyFirebaseIDService firebase = new MyFirebaseIDService();

    @Test
    public void guardarTokenTest(){
        String token = "myToken";
     //   firebase.guardarToken(token);
     //   assertEquals(token,firebase.leerToken());
    }
}
