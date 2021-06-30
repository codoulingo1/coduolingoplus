package com.getcodly.codly;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.getcodly.codly.adapter.AdapterWalkthrough;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

public class codlyPlusActivity extends AppCompatActivity implements BillingProcessor.IBillingHandler {


    public ViewPager viewPager;
    AdapterWalkthrough adapterWalkthrough;
    private TransactionDetails transactionDetails = null;

    private RelativeLayout monthlySubscription;
    private RelativeLayout semiAnnualSubscription;

    private BillingProcessor bp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codly_plus);



        viewPager = findViewById(R.id.viewPager3);
        CircleIndicator indicator = findViewById(R.id.indicator);
        monthlySubscription = findViewById(R.id.monthlySubscription);
        semiAnnualSubscription = findViewById(R.id.moneyyy);

        adapterWalkthrough = new AdapterWalkthrough(getSupportFragmentManager());

        viewPager.setAdapter(adapterWalkthrough);

        indicator.setViewPager(viewPager);

        adapterWalkthrough.registerDataSetObserver(indicator.getDataSetObserver());

        bp = new BillingProcessor(this, getResources().getString(R.string.license_key), this);
    }

    private Boolean hasSubscription(){
        if(transactionDetails != null){
            return transactionDetails.purchaseInfo != null;
        }else
            return false;
    }

    @Override
    public void onBillingInitialized() {


        monthlySubscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transactionDetails = bp.getSubscriptionTransactionDetails("plus_subscription_monthly");
                if(bp.isSubscriptionUpdateSupported()){
                    bp.subscribe(codlyPlusActivity.this, "plus_subscription_monthly");
                }else{
                    Toast.makeText(codlyPlusActivity.this, "אירעה שגיאה", Toast.LENGTH_SHORT).show();
                }
            }
        });

        semiAnnualSubscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transactionDetails = bp.getSubscriptionTransactionDetails("plus_monthly");
                if(bp.isSubscriptionUpdateSupported()){
                    bp.subscribe(codlyPlusActivity.this, "plus_monthly");
                }else{
                    Toast.makeText(codlyPlusActivity.this, "אירעה שגיאה", Toast.LENGTH_SHORT).show();
                }
            }
        });

        if(hasSubscription())
            Toast.makeText(codlyPlusActivity.this, "ברשותך כבר משתמש פלוס", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProductPurchased(String productId, TransactionDetails details) {

    }

    @Override
    public void onPurchaseHistoryRestored() {

    }

    @Override
    public void onBillingError(int errorCode, Throwable error) {

    }

    @Override
    protected void onDestroy() {
        if(bp != null){
            bp.release();
        }
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(!bp.handleActivityResult(requestCode, resultCode, data))
            super.onActivityResult(requestCode, resultCode, data);
    }
}