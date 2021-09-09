package kz.eal.freedomfinance.currencyStorage;

import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class CurrencyValues {
    private final HashMap<String,String> value = new HashMap<String, String>();
    private void fill(){
        value.put("ARS","Argentine Peso");
        value.put("AUD","Australian Dollar");
        value.put("BHD","Bahraini Dinar");
        value.put("BWP","Botswana Pula");
        value.put("BRL","Brazilian Real");
        value.put("BND","Bruneian Dollar");
        value.put("BGN","Bulgarian Lev");
        value.put("CAD","Canadian Dollar");
        value.put("CLP","Chilean Peso");
        value.put("CNY","Chinese Yuan Renminbi");
        value.put("COP","Colombian Peso");
        value.put("HRK","Croatian Kuna");
        value.put("CZK","Czech Koruna");
        value.put("DKK","Danish Krone");
        value.put("EUR","Euro");
        value.put("HKD","Hong Kong Dollar");
        value.put("HUF","Hungarian Forint");
        value.put("ISK","Icelandic Krona");
        value.put("INR","Indian Rupee");
        value.put("IDR","Indonesian Rupiah");
        value.put("IRR","Iranian Rial");
        value.put("ILS","Israeli Shekel");
        value.put("JPY","Japanese Yen");
        value.put("KRW","South Korean Won");
        value.put("KWD","Kuwaiti Dinar");
        value.put("LYD","Libyan Dinar");
        value.put("MYR","Malaysian Ringgit");
        value.put("MUR","Mauritian Rupee");
        value.put("MXN","Mexican Peso");
        value.put("NPR","Nepalese Rupee");
        value.put("NZD","New Zealand Dollar");
        value.put("NOK","Norwegian Krone");
        value.put("OMR","Omani Rial");
        value.put("PKR","Pakistani Rupee");
        value.put("PHP","Philippine Peso");
        value.put("PLN","Polish Zloty");
        value.put("QAR","Qatari Riyal");
        value.put("RON","Romanian New Leu");
        value.put("RUB","Russian Ruble");
        value.put("SAR","Saudi Arabian Riyal");
        value.put("SGD","Singapore Dollar");
        value.put("ZAR","South African Rand");
        value.put("LKR","Sri Lankan Rupee");
        value.put("CHF","Swiss Franc");
        value.put("TWD","Taiwan New Dollar");
        value.put("THB","Thai Baht");
        value.put("TTD","Trinidadian Dollar");
        value.put("TRY","Turkish Lira");
        value.put("AED","Emirati Dirham");
        value.put("GBP","British Pound");
        value.put("USD","US Dollar");
    }
    public HashMap<String,String> getCurrencies(){
        fill();
        return value;
    }
}
