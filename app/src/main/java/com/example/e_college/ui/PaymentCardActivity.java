package com.example.e_college.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.e_college.R;
import com.example.e_college.model.CardType;
import com.example.e_college.model.PaymentCardView;

public class PaymentCardActivity extends AppCompatActivity {

    private static final char space = ' ';


    private Context mContext;
    private Button mBtnSubmit;
    private ImageView mImgCard;
    private TextView mTxtCardTitle;
    private Typeface RegularTypeFace;
    private EditText mEdtCardNumber, mEdtCvv;
    private EditText mSpinnerMonth, mSpinnerYear;
    private PaymentCardView.OnPaymentCardEventListener mListener;



   // public PaymentCardActivity(Context context, AttributeSet attrs)  {
void initViews(){
       // mContext = context;

       // LayoutInflater mInflater = LayoutInflater.from(context);
        //View mView = (View) mInflater.inflate(R.id.activity,PaymentCardActivity.this,true);

       // TypedArray a = this.obtainStyledAttributes(attrs, R.styleable.PaymentCardView);
       // String cardTitle = a.getString(R.styleable.PaymentCardView_cv_cardTitle);
       // String submitBtnText = a.getString(R.styleable.PaymentCardView_cv_submitBtnText);
       // a.recycle();

        RegularTypeFace = Typeface.createFromAsset(this.getAssets(), "Montserrat-Regular.ttf");

       /* mImgCard = mView.findViewById(R.id.imgCard);
        mBtnSubmit = mView.findViewById(R.id.btnDone);
        mSpinnerYear = mView.findViewById(R.id.spryear);
        mSpinnerMonth = mView.findViewById(R.id.sprmonth);
        mEdtCvv = mView.findViewById(R.id.edtCvvNumber);
        mTxtCardTitle = mView.findViewById(R.id.txtCardTitle);
        mEdtCardNumber = mView.findViewById(R.id.edtCardNumber);
        ImageView mImgCancel = mView.findViewById(R.id.iconCancel);*/
        mImgCard = findViewById(R.id.imgCard);
        mBtnSubmit = findViewById(R.id.btnDone);
        mSpinnerYear = findViewById(R.id.spryear);
        mSpinnerMonth = findViewById(R.id.sprmonth);
        mEdtCvv = findViewById(R.id.edtCvvNumber);
        mTxtCardTitle = findViewById(R.id.txtCardTitle);
        mEdtCardNumber = findViewById(R.id.edtCardNumber);
        ImageView mImgCancel = findViewById(R.id.iconCancel);


        mBtnSubmit.setTypeface(RegularTypeFace);
        mSpinnerYear.setTypeface(RegularTypeFace);
        mSpinnerMonth.setTypeface(RegularTypeFace);
        mEdtCardNumber.setTypeface(RegularTypeFace);
        /*((TextView) mView.findViewById(R.id.txtCardTitle)).setTypeface(RegularTypeFace);
        ((EditText) mView.findViewById(R.id.edtCvvNumber)).setTypeface(RegularTypeFace);*/
        ((TextView) findViewById(R.id.txtCardTitle)).setTypeface(RegularTypeFace);
        ((EditText) findViewById(R.id.edtCvvNumber)).setTypeface(RegularTypeFace);

        getSupportActionBar().setTitle(mTxtCardTitle.getText().toString());

        //setCardTitle(cardTitle);
        //setSubmitButtonText(submitBtnText);

        mEdtCardNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0 && (s.length() % 5) == 0) {
                    final char c = s.charAt(s.length() - 1);
                    if (space == c) {
                        s.delete(s.length() - 1, s.length());
                    }
                }
                if (s.length() > 0 && (s.length() % 5) == 0) {
                    char c = s.charAt(s.length() - 1);
                    if (Character.isDigit(c) && TextUtils.split(s.toString(), String.valueOf(space)).length <= 3) {
                        s.insert(s.length() - 1, String.valueOf(space));
                    }
                }
                showCard(s.toString());
            }
        });

        mSpinnerMonth.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                String strEnteredVal = s.toString();

                if (!strEnteredVal.equals("")) {
                    int num = Integer.parseInt(strEnteredVal);
                    if (num > 13) {
                        s.delete(s.length() - 1, s.length());
                    }
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });


        mImgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null)
                    mListener.onCancelClick();
            }
        });

        mBtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mSpinnerMonth.getText().length() > 0) {
                    if (mSpinnerYear.getText().length() > 0) {
                        if (mEdtCardNumber.getText().length() == 19) {
//                            if (isValidCardNumber(mEdtCardNumber.getText().toString())) {
                            if (mEdtCvv.getText().length() == 3) {
                                if (mListener != null)
                                    mListener.onCardDetailsSubmit(mSpinnerMonth.getText().toString(), mSpinnerYear.getText().toString(), mEdtCardNumber.getText().toString(), mEdtCvv.getText().toString());
                            } else {
                                if (mListener != null)
                                    mListener.onError("Enter valid CVV");
                            }
//                            } else {
//                                if (mListener != null)
//                                    mListener.onError("Enter valid card number");
//                            }

                        } else {
                            if (mListener != null)
                                mListener.onError("Enter valid card number");
                        }
                    } else {
                        if (mListener != null)
                            mListener.onError("Enter valid year");
                    }
                } else {
                    if (mListener != null)
                        mListener.onError("Enter valid month");
                }
            }
        });
    }

    private void showCard(String cNumber) {
        String cardNumber = cNumber.replaceAll("\\s+", "");
        if (CardType.detect(cardNumber) == CardType.MASTERCARD) {
            mImgCard.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_mastercard));
        } else if (CardType.detect(cardNumber) == CardType.VISA) {
            mImgCard.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_visa));
        } else if (CardType.detect(cardNumber) == CardType.AMERICAN_EXPRESS) {
            mImgCard.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_american_express));
        } else if (CardType.detect(cardNumber) == CardType.DISCOVER) {
            mImgCard.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_discover));
        } else if (CardType.detect(cardNumber) == CardType.CHINA_UNION_PAY) {
            mImgCard.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_unionpay));
        } else if (CardType.detect(cardNumber) == CardType.DINERS_CLUB) {
            mImgCard.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_credit_card));
        } else if (CardType.detect(cardNumber) == CardType.UNKNOWN) {
            mImgCard.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_credit_card));
        }
    }

    public void setCardTitle(String cardTitle) {
        if (cardTitle != null && cardTitle.length() == 0)
            mTxtCardTitle.setVisibility(View.GONE);
        mTxtCardTitle.setText(cardTitle);
    }

    public void setSubmitButtonText(String submitButtonText) {
        if (submitButtonText == null)
            mBtnSubmit.setText("Done");
        else if (submitButtonText.length() == 0) {
            mBtnSubmit.setText("Done");
        } else {
            mBtnSubmit.setText(submitButtonText);
        }
    }

    public boolean isValidCardNumber(String cNumber) {
        int sum = 0;
        boolean alternate = false;
        String cardNumber = cNumber.replaceAll("\\s+", "");
        for (int i = cardNumber.length() - 1; i >= 0; i--) {
            int n = Integer.parseInt(cardNumber.substring(i, i + 1));
            if (alternate) {
                n *= 2;
                if (n > 9) {
                    n = (n % 10) + 1;
                }
            }
            sum += n;
            alternate = !alternate;
        }
        return (sum % 10 == 0);
    }


    public void setOnPaymentCardEventListener(PaymentCardView.OnPaymentCardEventListener listener) {
        mListener = listener;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    public interface OnPaymentCardEventListener {
        void onCardDetailsSubmit(String month, String year, String cardNumber, String cvv);

        void onError(String error);

        void onCancelClick();
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_card2);
        //PaymentCardActivity cardActivity=new PaymentCardActivity;
    }
}
