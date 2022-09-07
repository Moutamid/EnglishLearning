package app.com.englishlearning;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.PurchaseInfo;

import app.com.englishlearning.utilities.Constants;

public class PremiumAccessActivity extends AppCompatActivity implements BillingProcessor.IBillingHandler {
    BillingProcessor bp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_premium_access);

        bp = BillingProcessor.newBillingProcessor(this, Constants.LICENSE_KEY, this);
        bp.initialize();

        findViewById(R.id.btnBasic).setOnClickListener(view -> {
            bp.subscribe(PremiumAccessActivity.this, Constants.WEEKLY_SUBSCRIPTION);
        });
        findViewById(R.id.btnPrenium).setOnClickListener(view -> {
            bp.subscribe(PremiumAccessActivity.this, Constants.MONTHLY_SUBSCRIPTION);
        });

        findViewById(R.id.btn6months).setOnClickListener(view -> {
            bp.subscribe(PremiumAccessActivity.this, Constants.SIX_MONTHS_SUBSCRIPTION);
        });

        findViewById(R.id.imgHome).setOnClickListener(view ->
                startActivity(new Intent(PremiumAccessActivity.this, Dashboard.class)));
    }

    @Override
    public void onProductPurchased(@NonNull String productId, @Nullable PurchaseInfo details) {
        Toast.makeText(PremiumAccessActivity.this, "Purchase successful", Toast.LENGTH_SHORT).show();
//        TextView textView = findViewById(R.id.purchasedTextView);

        /*if (productId.equals(Constants.SIX_MONTHS_SUBSCRIPTION))
            textView.setText("Two hundred tokens purchased");
        if (productId.equals(Constants.TWO_TWENTY_FIVE_DOLLAR_PRODUCT))
            textView.setText("Two twenty five tokens purchased");
        if (productId.equals(Constants.WEEKLY_SUBSCRIPTION))
            textView.setText("Two forty six tokens purchased");
        if (productId.equals(Constants.TWO_SIXTY_FIVE_DOLLAR_PRODUCT))
            textView.setText("Two sixty five tokens purchased");
        if (productId.equals(Constants.THREE_HUNDRED_DOLLAR_PRODUCT))
            textView.setText("Three hundred purchased");

        textView.setVisibility(View.VISIBLE);*/
    }

    @Override
    public void onPurchaseHistoryRestored() {

    }

    @Override
    public void onBillingError(int errorCode, @Nullable Throwable error) {
        Toast.makeText(PremiumAccessActivity.this, "onBillingError: code: " + errorCode + " \n" + error.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBillingInitialized() {

    }

    @Override
    protected void onDestroy() {
        if (bp != null) {
            bp.release();
        }
        super.onDestroy();
    }
}