package com.vincent.framework.utils;

import android.content.Context;

import com.vincent.framework.LibApplication;
import com.vincent.framework.exception.TripleDESException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * Created by Jin Qian on 6/3/2015.
 */
public class TripleDES {
    public static final String TAG = "TripleDES";
    public static final String KEY_ALGORITHM = "DESede";
    public static final String CIPHER_ALGORITHM_ECB = "DESede/ECB/PKCS5Padding";
    public static final String CIPHER_ALGORITHM_CBC = "DESede/CBC/PKCS5Padding";

    private KeyGenerator keyGen;
    private SecretKey secretKey; // the key should be saved in local.
    private SecretKey secretKey2;
    private Cipher cipher;
    private static byte[] encryptData;

    public TripleDES(String mode) {
        try {
            if ("ECB".equals(mode)) {
                cipher = Cipher.getInstance(CIPHER_ALGORITHM_ECB);
                secretKey = (SecretKey)fileRead("key.out");
                if (secretKey == null) {
                    keyGen = KeyGenerator.getInstance(KEY_ALGORITHM);
                    secretKey = keyGen.generateKey();
                    fileSave(secretKey, "key.out");
                }

            } else if ("CBC".equals(mode)) {
                cipher = Cipher.getInstance(CIPHER_ALGORITHM_CBC);
                secretKey2 = (SecretKey)fileRead("key.out");
                if (secretKey2 == null) {
                    keyGen = KeyGenerator.getInstance(KEY_ALGORITHM);
                    DESedeKeySpec spec = new DESedeKeySpec(keyGen.generateKey().getEncoded());
                    secretKey2 = SecretKeyFactory.getInstance(KEY_ALGORITHM).generateSecret(spec);
                    fileSave(secretKey2, "key.out");
                }

            }
        } catch (Exception e) {
            LogUtil.error(TAG,"TripleDES",e);
        }
    }

    /**
     * @param str
     * @return
     * @throws Exception
     */
    public byte[] encrypt(String str){
        try {
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return cipher.doFinal(str.getBytes());
        } catch (Exception e) {
            LogUtil.error(TAG,"encrypt",e);
        }
        return null;
    }

    public void savePassword(byte[] pass) {
        fileSave(pass, "pass.out");
    }

    public byte[] loadPassword() {
        return (byte[])fileRead("pass.out");
    }

    /**
     * @param encrypt
     * @return
     * @throws Exception
     */
    public byte[] decrypt(byte[] encrypt) throws TripleDESException {
        try {
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return cipher.doFinal(encrypt);
        } catch (Exception e) {
            LogUtil.error(TAG,"decrypt",e);
//            throw new TripleDESException("decrypt");
        }
        return null;
    }

    byte[] getIV() {
        return "administ".getBytes();
    }

    /**
     * @param str
     * @return
     * @throws Exception
     */
    public byte[] encrypt2(String str) throws TripleDESException {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, secretKey2, new IvParameterSpec(getIV()));
            return cipher.doFinal(str.getBytes());
        } catch (Exception e) {
            LogUtil.error(TAG,"encrypt2",e);
//            throw new TripleDESException("encrypt2");
        }
        return null;
    }

    /**
     * @param encrypt
     * @return
     * @throws Exception
     */
    public byte[] decrypt2(byte[] encrypt) throws TripleDESException {
        try {
            cipher.init(Cipher.DECRYPT_MODE, secretKey2, new IvParameterSpec(getIV()));
            return cipher.doFinal(encrypt);
        } catch (Exception e) {
            LogUtil.error(TAG,"decrypt2",e);
//            throw new TripleDESException("decrypt2");
        }
        return null;
    }

    public void fileSave(Object key, String fileName) {
        try {
            FileOutputStream fos = LibApplication.getContext().openFileOutput(fileName, Context.MODE_WORLD_READABLE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(key);
            fos.close();
        } catch (FileNotFoundException e) {
            LogUtil.error(TAG,"fileSave",e);
        } catch (IOException e) {
            LogUtil.error(TAG,"fileSave",e);
        }
    }

    public Object fileRead(String fileName) {
        Object key = null;
        try {
            FileInputStream fis = LibApplication.getContext().openFileInput(fileName);
            ObjectInputStream ois = new ObjectInputStream(fis);
            key = (Object) ois.readObject();

        } catch (StreamCorruptedException e) {
            LogUtil.log(LogUtil.LogLevel.ERROR, TAG, e.toString());
        } catch (OptionalDataException e) {
            LogUtil.log(LogUtil.LogLevel.ERROR, TAG, e.toString());
        } catch (FileNotFoundException e) {
            LogUtil.log(LogUtil.LogLevel.ERROR, TAG, e.toString());
        } catch (IOException e) {
            LogUtil.log(LogUtil.LogLevel.ERROR, TAG, e.toString());
        } catch (ClassNotFoundException e) {
            LogUtil.log(LogUtil.LogLevel.ERROR, TAG, e.toString());
        }
        return key;
    }


}
