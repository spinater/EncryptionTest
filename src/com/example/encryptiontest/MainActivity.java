package com.example.encryptiontest;

import java.security.Provider;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import android.app.Activity;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;



public class MainActivity extends Activity {
	
	private TextView mTxtCipher;
	private TextView mTxtCtLenght;
	private TextView mTxtPlainText;
	private TextView mTxtPtLength;
	private TextView mTxtInput;
	
	private  EditText mEdt;
	



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mTxtCipher = (TextView)findViewById(R.id.TextView_cipherText);
		mTxtCtLenght = (TextView)findViewById(R.id.TextView_ctLength);
		mTxtPlainText = (TextView)findViewById(R.id.TextView_plainText);
		mTxtPtLength = (TextView)findViewById(R.id.TextView_ptLength);
		mTxtInput = (TextView)findViewById(R.id.TextView_input);
		
		mEdt = (EditText)findViewById(R.id.editText1);
		
		Button btn = (Button)findViewById(R.id.button1);
		
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				try {
					BouncyCastleProvider();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

	}

	private void Encrypter(String data) throws Exception
	{
		
	   // Security.addProvider(new MainActivity().BouncyCastleProvider());    
	    
	    byte[] input = data.getBytes();
	    byte[] keyBytes = new byte[] { 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08};
	    
	    int keySize = keyBytes.length;
	    SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");

	    Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding", "BC");
	    
	    mTxtInput.setText(new String(data));
	    //System.out.println(new String(input));

	    // encryption pass
	    cipher.init(Cipher.ENCRYPT_MODE, key);

	    byte[] cipherText = new byte[cipher.getOutputSize(input.length)];
	    int ctLength = cipher.update(input, 0, input.length, cipherText, 0);
	    ctLength += cipher.doFinal(cipherText, ctLength);
	    
	    //System.out.println(new String(cipherText));
	    //System.out.println(ctLength);
	    
	    mTxtCipher.setText(new String(cipherText));
	    mTxtCtLenght.setText(String.valueOf(ctLength));
	    // decryption pass
	    String x = new String(cipherText);

	}
	
	private void Decryptor(byte[] cipherText) throws Exception
	{
		
	    byte[] keyBytes = new byte[] { 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08};

	    SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");

	    Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding", "BC");
	    int keySize = keyBytes.length;
	    int x = cipherText.length;
	    
	    // decryption pass
	    cipher.init(Cipher.DECRYPT_MODE, key);
	    byte[] plainText = new byte[cipher.getOutputSize(x)];
	    int ptLength = cipher.update(cipherText, 0, x, plainText, 0);
	    ptLength += cipher.doFinal(plainText, ptLength);  	    

	    mTxtPlainText.setText(new String(plainText));
	    //mTxtPtLength.setText(String.valueOf(ptLength));
	}
	
	

	public Provider BouncyCastleProvider() throws Exception {
		// TODO Auto-generated method stub
		String data =  mEdt.getText().toString();
		Encrypter(data);
		return null;
	}
}

