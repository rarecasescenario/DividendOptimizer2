package com.stock.yahoo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HttpClientUtilTest {

  HttpsClientUtil httpsClientUtil;

  @BeforeEach
  public void setup() {
    httpsClientUtil = new HttpsClientUtil();
  }

  @Test
  public void makeItDigitTest() {
    String d = httpsClientUtil.makeItDigit("$12,250.12");
    Assertions.assertEquals("12250.12", d);
  }
}
