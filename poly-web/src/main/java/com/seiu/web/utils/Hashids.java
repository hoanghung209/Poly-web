package com.seiu.web.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Hashids
{
  private String salt = "";
  private String alphabet = "";
  private String seps = "cfhistuCFHISTU";
  private int minHashLength = 0;
  private String guards;
  
  public Hashids()
  {
    this("");
  }
  
  public Hashids(String salt)
  {
    this(salt, 0);
  }
  
  public Hashids(String salt, int minHashLength)
  {
    this(salt, minHashLength, "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890");
  }
  
  public Hashids(String salt, int minHashLength, String alphabet)
  {
    this.salt = salt;
    if (minHashLength < 0) {
      this.minHashLength = 0;
    } else {
      this.minHashLength = minHashLength;
    }
    this.alphabet = alphabet;
    
    String uniqueAlphabet = "";
    for (int i = 0; i < this.alphabet.length(); i++) {
      if (!uniqueAlphabet.contains(String.valueOf(this.alphabet.charAt(i)))) {
        uniqueAlphabet = uniqueAlphabet + this.alphabet.charAt(i);
      }
    }
    this.alphabet = uniqueAlphabet;
    
    int minAlphabetLength = 16;
    if (this.alphabet.length() < minAlphabetLength) {
      throw new IllegalArgumentException("alphabet must contain at least " + minAlphabetLength + " unique characters");
    }
    if (this.alphabet.contains(" ")) {
      throw new IllegalArgumentException("alphabet cannot contains spaces");
    }
    for (int i = 0; i < this.seps.length(); i++)
    {
      int j = this.alphabet.indexOf(this.seps.charAt(i));
      if (j == -1) {
        this.seps = (this.seps.substring(0, i) + " " + this.seps.substring(i + 1));
      } else {
        this.alphabet = (this.alphabet.substring(0, j) + " " + this.alphabet.substring(j + 1));
      }
    }
    this.alphabet = this.alphabet.replaceAll("\\s+", "");
    this.seps = this.seps.replaceAll("\\s+", "");
    this.seps = consistentShuffle(this.seps, this.salt);
    
    double sepDiv = 3.5D;
    if ((this.seps.equals("")) || (this.alphabet.length() / this.seps.length() > sepDiv))
    {
      int seps_len = (int)Math.ceil(this.alphabet.length() / sepDiv);
      if (seps_len == 1) {
        seps_len++;
      }
      if (seps_len > this.seps.length())
      {
        int diff = seps_len - this.seps.length();
        this.seps += this.alphabet.substring(0, diff);
        this.alphabet = this.alphabet.substring(diff);
      }
      else
      {
        this.seps = this.seps.substring(0, seps_len);
      }
    }
    this.alphabet = consistentShuffle(this.alphabet, this.salt);
    
    int guardDiv = 12;
    int guardCount = (int)Math.ceil(this.alphabet.length() / guardDiv);
    if (this.alphabet.length() < 3)
    {
      this.guards = this.seps.substring(0, guardCount);
      this.seps = this.seps.substring(guardCount);
    }
    else
    {
      this.guards = this.alphabet.substring(0, guardCount);
      this.alphabet = this.alphabet.substring(guardCount);
    }
  }
  
  @Deprecated
  public String encrypt(long... numbers)
  {
    return encode(numbers);
  }
  
  @Deprecated
  public long[] decrypt(String hash)
  {
    return decode(hash);
  }
  
  @Deprecated
  public String encryptHex(String hexa)
  {
    return encodeHex(hexa);
  }
  
  @Deprecated
  public String decryptHex(String hash)
  {
    return decodeHex(hash);
  }
  
  public String encode(long... numbers)
  {
    for (long number : numbers) {
      if (number > 9007199254740992L) {
        throw new IllegalArgumentException("number can not be greater than 9007199254740992L");
      }
    }
    String retval = "";
    if (numbers.length == 0) {
      return retval;
    }
    return _encode(numbers);
  }
  
  public long[] decode(String hash)
  {
    long[] ret = new long[0];
    if (hash.equals("")) {
      return ret;
    }
    return _decode(hash, this.alphabet);
  }
  
  public String encodeHex(String hexa)
  {
    if (!hexa.matches("^[0-9a-fA-F]+$")) {
      return "";
    }
    List<Long> matched = new ArrayList<Long>();
    Matcher matcher = Pattern.compile("[\\w\\W]{1,12}").matcher(hexa);
    while (matcher.find()) {
      matched.add(Long.valueOf(Long.parseLong("1" + matcher.group(), 16)));
    }
    long[] result = new long[matched.size()];
    for (int i = 0; i < matched.size(); i++) {
      result[i] = ((Long)matched.get(i)).longValue();
    }
    return _encode(result);
  }
  
  public String decodeHex(String hash)
  {
    String result = "";
    long[] numbers = decode(hash);
    for (long number : numbers) {
      result = result + Long.toHexString(number).substring(1);
    }
    return result;
  }
  
  private String _encode(long... numbers)
  {
    int numberHashInt = 0;
    for (int i = 0; i < numbers.length; i++) {
      numberHashInt = (int)(numberHashInt + numbers[i] % (i + 100));
    }
    String alphabet = this.alphabet;
    char ret = alphabet.toCharArray()[(numberHashInt % alphabet.length())];
    


    String ret_str = String.valueOf(ret);
    for (int i = 0; i < numbers.length; i++)
    {
      long num = numbers[i];
      String buffer = ret + this.salt + alphabet;
      
      alphabet = consistentShuffle(alphabet, buffer.substring(0, alphabet.length()));
      String last = hash(num, alphabet);
      
      ret_str = ret_str + last;
      if (i + 1 < numbers.length)
      {
        num %= (last.toCharArray()[0] + i);
        int sepsIndex = (int)(num % this.seps.length());
        ret_str = ret_str + this.seps.toCharArray()[sepsIndex];
      }
    }
    if (ret_str.length() < this.minHashLength)
    {
      int guardIndex = (numberHashInt + ret_str.toCharArray()[0]) % this.guards.length();
      char guard = this.guards.toCharArray()[guardIndex];
      
      ret_str = guard + ret_str;
      if (ret_str.length() < this.minHashLength)
      {
        guardIndex = (numberHashInt + ret_str.toCharArray()[2]) % this.guards.length();
        guard = this.guards.toCharArray()[guardIndex];
        
        ret_str = ret_str + guard;
      }
    }
    int halfLen = alphabet.length() / 2;
    while (ret_str.length() < this.minHashLength)
    {
      alphabet = consistentShuffle(alphabet, alphabet);
      ret_str = alphabet.substring(halfLen) + ret_str + alphabet.substring(0, halfLen);
      int excess = ret_str.length() - this.minHashLength;
      if (excess > 0)
      {
        int start_pos = excess / 2;
        ret_str = ret_str.substring(start_pos, start_pos + this.minHashLength);
      }
    }
    return ret_str;
  }
  
  private long[] _decode(String hash, String alphabet)
  {
    ArrayList<Long> ret = new ArrayList<Long>();
    
    int i = 0;
    String regexp = "[" + this.guards + "]";
    String hashBreakdown = hash.replaceAll(regexp, " ");
    String[] hashArray = hashBreakdown.split(" ");
    if ((hashArray.length == 3) || (hashArray.length == 2)) {
      i = 1;
    }
    hashBreakdown = hashArray[i];
    
    char lottery = hashBreakdown.toCharArray()[0];
    
    hashBreakdown = hashBreakdown.substring(1);
    hashBreakdown = hashBreakdown.replaceAll("[" + this.seps + "]", " ");
    hashArray = hashBreakdown.split(" ");
    for (String aHashArray : hashArray)
    {
      String subHash = aHashArray;
      String buffer = lottery + this.salt + alphabet;
      alphabet = consistentShuffle(alphabet, buffer.substring(0, alphabet.length()));
      ret.add(unhash(subHash, alphabet));
    }
    long[] arr = new long[ret.size()];
    for (int k = 0; k < arr.length; k++) {
      arr[k] = ((Long)ret.get(k)).longValue();
    }
    if (!_encode(arr).equals(hash)) {
      arr = new long[0];
    }
    return arr;
  }
  
  private String consistentShuffle(String alphabet, String salt)
  {
    if (salt.length() <= 0) {
      return alphabet;
    }
    char[] arr = salt.toCharArray();
    

    int i = alphabet.length() - 1;int v = 0;
    for (int p = 0; i > 0; v++)
    {
      v %= salt.length();
      int asc_val = arr[v];
      p += asc_val;
      int j = (asc_val + v + p) % i;
      
      char tmp = alphabet.charAt(j);
      alphabet = alphabet.substring(0, j) + alphabet.charAt(i) + alphabet.substring(j + 1);
      alphabet = alphabet.substring(0, i) + tmp + alphabet.substring(i + 1);i--;
    }
    return alphabet;
  }
  
  private String hash(long input, String alphabet)
  {
    String hash = "";
    int alphabetLen = alphabet.length();
    char[] arr = alphabet.toCharArray();
    do
    {
      hash = arr[((int)(input % alphabetLen))] + hash;
      input /= alphabetLen;
    } while (
    

      input > 0L);
    return hash;
  }
  
  private Long unhash(String input, String alphabet)
  {
    long number = 0L;
    char[] input_arr = input.toCharArray();
    for (int i = 0; i < input.length(); i++)
    {
      long pos = alphabet.indexOf(input_arr[i]);
      number = (long) (number + pos * Math.pow(alphabet.length(), input.length() - i - 1));
    }
    return Long.valueOf(number);
  }
  
  public static int checkedCast(long value)
  {
    int result = (int)value;
    if (result != value) {
      throw new IllegalArgumentException("Out of range: " + value);
    }
    return result;
  }
  
  public String getVersion()
  {
    return "1.0.0";
  }
}

