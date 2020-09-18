package id.org.test.restful.test;


import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class IDConverter {
    public static final IDConverter INSTANCE = new IDConverter();

    private IDConverter() {
        initializeCharToIndexTable();
        initializeIndexToCharTable();
    }

    private static HashMap<Character, Integer> charToIndexTable;
    private static List<Character> indexToCharTable;

    private void initializeCharToIndexTable() {
        charToIndexTable = new HashMap<>();
        // 0->a, 1->b, ..., 25->z, ..., 52->0, 61->9
        for (int i = 0; i < 26; ++i) {
            char c = 'a';
            c += i;
            charToIndexTable.put(c, i);
        }
        for (int i = 26; i < 52; ++i) {
            char c = 'A';
            c += (i-26);
            charToIndexTable.put(c, i);
        }
        for (int i = 52; i < 62; ++i) {
            char c = '0';
            c += (i - 52);
            charToIndexTable.put(c, i);
        }
    }

    private void initializeIndexToCharTable() {
        // 0->a, 1->b, ..., 25->z, ..., 52->0, 61->9
        indexToCharTable = new ArrayList<>();
        for (int i = 0; i < 26; ++i) {
            char c = 'a';
            c += i;
            indexToCharTable.add(c);
        }
        for (int i = 26; i < 52; ++i) {
            char c = 'A';
            c += (i-26);
            indexToCharTable.add(c);
        }
        for (int i = 52; i < 62; ++i) {
            char c = '0';
            c += (i - 52);
            indexToCharTable.add(c);
        }
    }

    public static String createUniqueID(Long id) {
        List<Integer> base62ID = convertBase10ToBase62ID(id);
        StringBuilder uniqueURLID = new StringBuilder();
        for (int digit: base62ID) {
            uniqueURLID.append(indexToCharTable.get(digit));
        }
        return uniqueURLID.toString();
    }

    private static List<Integer> convertBase10ToBase62ID(Long id) {
        List<Integer> digits = new LinkedList<>();
        while(id > 0) {
            int remainder = (int)(id % 62);
            ((LinkedList<Integer>) digits).addFirst(remainder);
            id /= 62;
        }
        return digits;
    }

    public static Long getDictionaryKeyFromUniqueID(String uniqueID) {
        List<Character> base62IDs = new ArrayList<>();
        for (int i = 0; i < uniqueID.length(); ++i) {
            base62IDs.add(uniqueID.charAt(i));
        }
        Long dictionaryKey = convertBase62ToBase10ID(base62IDs);
        return dictionaryKey;
    }

    private static Long convertBase62ToBase10ID(List<Character> ids) {
        long id = 0L;
        for (int i = 0, exp = ids.size() - 1; i < ids.size(); ++i, --exp) {
            int base10 = charToIndexTable.get(ids.get(i));
            id += (base10 * Math.pow(62.0, exp));
        }
        return id;
    }
    
    
    public static void main(String[] args) {
    	
    	PasswordEncoder pe = new BCryptPasswordEncoder(7);
    	
		IDConverter idc = IDConverter.INSTANCE;
		
		String uid = UUID.randomUUID().toString();
		System.out.println(uid);
		System.out.println(uid.replaceAll("-", "").length());
		System.out.println(pe.encode(uid.replaceAll("-", "")).replaceAll("[^a-zA-Z0-9]", "").length());
		
		String mox = idc.createUniqueID(Long.MAX_VALUE);
		System.out.println(mox);
		
		String max = idc.createUniqueID(((1000) * 1l) + 1l);
		System.out.println(max);
		String min = idc.createUniqueID(((1000 * 1000 * 1000) * 1l) + 2l);
		System.out.println(min);
		
		String email = "dprima10@gmail.com1";
		Long id = 1l;
		Long unid  = 1000l + id;
		String unidstr = idc.createUniqueID(unid);
		
		String refCode = email.substring(0,3).concat(unidstr);
		System.out.println(refCode);
		
		String emailEncoded = Base64.getEncoder().encodeToString(email.getBytes());
		System.out.println(emailEncoded.replaceAll("[^a-zA-Z0-9]", ""));
		
		
		byte[] emailDecoded = Base64.getDecoder().decode(emailEncoded);
		String em = new String(emailDecoded);
		System.out.println(em);
		
		String x = pe.encode("dprqi");
		System.out.println(x);
		System.out.println(x.replaceAll("[^a-zA-Z0-9]", ""));
		System.out.println(Base64.getEncoder().encodeToString(x.replaceAll("[^a-zA-Z0-9]", "").getBytes()));
		
		//refcode = //3digit email + id + uniqid(1000l+id)
		
		
	}
    
}

