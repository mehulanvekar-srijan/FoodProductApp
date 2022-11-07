package com.experiment.foodproductapp.utility

import android.app.Activity
import com.razorpay.Checkout
import org.json.JSONObject


fun payment(activity: Activity,email:String,phoneNumber:String,sum:String){

    Checkout.preload(activity)
    val co = Checkout()
    co.setKeyID("rzp_test_IK3XzQPXxZsts7") // Key removed for security reasons

    try {
        val options = JSONObject()
        options.put("name","Razorpay Corp")
        options.put("description","Demoing Charges")
        //You can omit the image option to fetch the image from the dashboard
        options.put("image","https://s3.amazonaws.com/rzp-mobile/images/rzp.jpg")
        options.put("theme.color", "#FDB515");
        options.put("currency","INR");
//        options.put("order_id", "order_DBJOWzybf0sJbb");
        options.put("amount", sum)//pass amount in currency subunits

//        val retryObj = JSONObject();
//        retryObj.put("enabled", true);
//        retryObj.put("max_count", 4);
//        options.put("retry", retryObj);

        val prefill = JSONObject()
        prefill.put("email",email)
        prefill.put("contact",phoneNumber)

        options.put("prefill",prefill)
        co.open(activity,options)

    }catch (e: Exception){
        e.printStackTrace()
    }
}